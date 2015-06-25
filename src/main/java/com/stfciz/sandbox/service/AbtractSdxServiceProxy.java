package com.stfciz.sandbox.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.core.Ordered;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
public abstract class AbtractSdxServiceProxy implements Ordered {

  protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
  
  @Pointcut("execution(* com.stfciz.sandbox.service.SdxService.*(..))")
  public void sdxServiceInvocationPoint() {
  }
  
  /**
   * 
   * @param pjp
   * @return
   */
  protected String getTargetName(ProceedingJoinPoint pjp) {
   return String.format("%s.%s", pjp.getSignature().getDeclaringTypeName(), ((MethodInvocationProceedingJoinPoint)pjp).getSignature().getName());
  }
  
  /**
   * 
   * @param pjp
   * @return
   * @throws Throwable
   */
  @Around("sdxServiceInvocationPoint()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {
    LOGGER.debug("Proceed {} ...", getTargetName(pjp));
    return aroundInternal(pjp);
  }
  
  /**
   * 
   * @param pjp
   * @return
   * @throws Throwable
   */
  protected abstract Object aroundInternal(ProceedingJoinPoint pjp) throws Throwable;
}
