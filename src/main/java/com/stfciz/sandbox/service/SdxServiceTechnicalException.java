package com.stfciz.sandbox.service;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
public class SdxServiceTechnicalException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -6661167585281904210L;
  
  /**
   * 
   * @param elapsedTime
   */
  public SdxServiceTechnicalException(String msg, Throwable cause) {
    super(msg, cause);
  }
  
  /**
   * 
   * @param msg
   */
  public SdxServiceTechnicalException(String msg) {
    super(msg);
  }
  
  /**
   * 
   * @param cause
   */
  public SdxServiceTechnicalException(Throwable cause) {
    super(cause);
  }

}
