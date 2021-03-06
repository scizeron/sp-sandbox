package com.stfciz.sandbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author stfciz
 *
 *         25 juin 2015
 */
@Service
public class SdxServiceImpl implements SdxService<String> {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Override
  public String sleep(long delay) throws SdxServiceException {
    try {
      if (delay > 0) {
        Thread.sleep(delay);
        String msg = String.format("OK after a %d ms sleeping time", delay);
        LOGGER.info(msg);
        return msg;
      } else {
        Thread.sleep(Math.abs(delay));
        throw new SdxServiceFunctionalException(String.format(
            "The delay \"%d\" must be > 0", delay));
      }
    } catch (InterruptedException e) {
      throw new RuntimeException("sleep execution error", e);
    }
  }
}
