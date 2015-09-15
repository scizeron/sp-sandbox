package com.stfciz.sandbox.service;

/**
 * 
 * @author stfciz
 *
 * 15 sept. 2015
 */
public abstract class SdxServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 2413765056869701181L;

  public SdxServiceException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public SdxServiceException(String msg) {
    super(msg);
  }
  
  public SdxServiceException(Throwable cause) {
    super(cause);
  }
}
