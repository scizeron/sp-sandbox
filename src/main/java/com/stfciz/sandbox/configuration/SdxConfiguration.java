package com.stfciz.sandbox.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="sandbox")
public class SdxConfiguration {

  private long maxTimeout;

  public long getMaxTimeout() {
    return maxTimeout;
  }

  public void setMaxTimeout(long maxTimeout) {
    this.maxTimeout = maxTimeout;
  }
}
