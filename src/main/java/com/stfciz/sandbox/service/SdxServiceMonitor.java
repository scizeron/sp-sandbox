package com.stfciz.sandbox.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SdxServiceMonitor extends AbtractSdxServiceProxy {

  @Autowired
  private CounterService counterService;
  
  @Override
  public int getOrder() {
    return 1;
  }

  @Override
  protected Object aroundInternal(ProceedingJoinPoint pjp) throws Throwable {
    String targetName = getTargetName(pjp);
    Object result = null;
    
    try {
      result = pjp.proceed();
      
    } catch(SdxServiceException e) {
      LOGGER.error("A service error has occured", e);
      throw e;
    } catch(Exception e) {
      LOGGER.error("A unexpected error has occured", e);
      throw new SdxServiceTechnicalException(e);
    } finally {
      this.counterService.increment(String.format("%s.total", targetName));
      if (result == null) {
        this.counterService.increment(String.format("%s.errors", targetName));
        LOGGER.error("{} has failed", targetName);
      } else { 
        LOGGER.debug("{} is ok", targetName);
      }
    }
    return result;
  }
}
