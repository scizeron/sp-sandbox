package com.stfciz.sandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = false)
@EnableScheduling
public class SdxApplication {

  public static void main(String[] args) {
    SpringApplication.run(SdxApplication.class, args);
  }
}
