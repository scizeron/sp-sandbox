package com.stfciz.sandbox.service;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
public class SdxServiceTimeoutException extends SdxServiceTechnicalException {

  /**
   * 
   */
  private static final long serialVersionUID = -6661167585281904210L;
  
  /**
   * 
   * @param elapsedTime
   */
  public SdxServiceTimeoutException(long elapsedTime) {
    super(String.format("Timeout after %d ms", elapsedTime));
  }

}
