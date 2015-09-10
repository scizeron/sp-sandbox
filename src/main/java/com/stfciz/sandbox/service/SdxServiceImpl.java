package com.stfciz.sandbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
@Service
public class SdxServiceImpl implements SdxService<String> {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
  @Override
  public String sleep(long delay) {
    try {
      Thread.sleep(delay);
      String msg = String.format("OK after a %d ms sleeping time", delay);
      LOGGER.info(msg);
      return msg;
    } catch (InterruptedException e) {
      throw new RuntimeException("sleep execution error", e);
    }
  }
}
