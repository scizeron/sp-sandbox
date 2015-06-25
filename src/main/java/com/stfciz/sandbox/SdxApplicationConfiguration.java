package com.stfciz.sandbox;

import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SdxApplicationConfiguration {
 
  /**
   * 
   * @param metricReader
   * @return
   */
  @Bean(name="servicesPublicMetrics") PublicMetrics getServicesPublicMetrics(MetricReader metricReader) {
    return new MetricReaderPublicMetrics(metricReader);
  }
}
