package com.stfciz.sandbox.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stfciz.sandbox.configuration.SdxConfiguration;

@Aspect
@Component
public class SdxServiceTimeoutedCaller extends AbtractSdxServiceProxy {

  /**
   * 
   * @author stfciz
   *
   * 9 sept. 2015
   */
  private static class WorkerThreadFactory implements ThreadFactory {
    private int counter = 0;
    private String prefix = "WorkThread";

    public Thread newThread(Runnable r) {
      return new Thread(r, prefix + "-" + ++counter);
    }
 }

  @Autowired
  private SdxConfiguration sdxConfiguration;
  
  private ExecutorService executorService = Executors.newCachedThreadPool(new SdxServiceTimeoutedCaller.WorkerThreadFactory());
  
  @Override
  public int getOrder() {
    return 2;
  }
  
  @PostConstruct
  public void postConstruct() {
    LOGGER.info("Max Timeout: {}", this.sdxConfiguration.getMaxTimeout());
  }

  @Override
  protected Object aroundInternal(ProceedingJoinPoint pjp) throws Throwable {
    try {
      return this.executorService.submit(new Callable<String>() {
        @Override
        public String call() throws Exception {
          try {
            return (String) pjp.proceed();
          } catch (Throwable e) {
            throw new SdxServiceTechnicalException(e);
          }
        }
      }).get(this.sdxConfiguration.getMaxTimeout(), TimeUnit.MILLISECONDS);

    } catch (InterruptedException | ExecutionException e) {
      throw new SdxServiceTechnicalException(e);
    } catch (TimeoutException e) {
      throw new SdxServiceTimeoutException(this.sdxConfiguration.getMaxTimeout());
    }
  }
}
