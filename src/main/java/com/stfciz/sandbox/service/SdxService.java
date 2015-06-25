package com.stfciz.sandbox.service;

import org.springframework.stereotype.Service;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
@Service
public interface SdxService<T> {

  /**
   * 
   * @param delay
   * @return
   * @throws SdxServiceTimeoutException
   */
  T sleep(long delay) throws SdxServiceTimeoutException;
  
}
