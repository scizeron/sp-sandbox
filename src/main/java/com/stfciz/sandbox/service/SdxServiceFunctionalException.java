package com.stfciz.sandbox.service;
/**
 * 
 * @author stfciz
 *
 * 15 sept. 2015
 */
public class SdxServiceFunctionalException extends SdxServiceException {

  /**
   * 
   */
  private static final long serialVersionUID = -227973433256720729L;

  public SdxServiceFunctionalException(String msg) {
    super(msg);
  }

  public SdxServiceFunctionalException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public SdxServiceFunctionalException(Throwable cause) {
    super(cause);
  }
}
