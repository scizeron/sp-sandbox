package com.stfciz.sandbox.service;

import org.springframework.stereotype.Service;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
@Service
public class SdxServiceImpl implements SdxService<String> {

  @Override
  public String sleep(long delay) {
    try {
      Thread.sleep(delay);
      return String.format("OK after a %d ms sleeping time", delay);
    } catch (InterruptedException e) {
      throw new RuntimeException("sleep execution error", e);
    }
  }
}
