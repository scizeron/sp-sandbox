package com.stfciz.sandbox.monitoring;

import org.springframework.cloud.aws.context.annotation.ConditionalOnAwsCloudEnvironment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnAwsCloudEnvironment
public class MemoryMonitoring {

  @Scheduled(initialDelay = 60*1000, fixedDelay = 60*1000)
  public void pushInfos() {
    // memory usage cloud watch ...
  }
}
