package com.stfciz.sandbox.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
/**
 * The service health indicator is a part of the global application health indicator
 * The default aggregator collects each health indicator an sort them and return the first one 
 * (DOWN, OUT_OF_SERVICE, UP, UNKNOWN)
 * If one of these indicators is marked as DOWN, the application health indicator is DOWN
 * 
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
@Component
public class SleepSdxServiceHealthIndicator extends AbstractHealthIndicator {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private CounterService counterService;
  
  @Autowired
  @Qualifier("servicesPublicMetrics")
  private PublicMetrics servicesPublicMetrics;
  
  private Status current;
  
  private final String metricName;
  
  public SleepSdxServiceHealthIndicator() {
    this.metricName = "counter.com.stfciz.sandbox.service.SdxService.sleep";
  }

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    Map<String, Double> counters = this.servicesPublicMetrics.metrics().stream().filter(m -> m.getName().startsWith(this.metricName)).collect(Collectors.toMap(m -> StringUtils.replace(m.getName(), this.metricName + ".", "")
                                              , m -> m.getValue() != null ? new Double(m.getValue().toString()) : 0));
    
    if (counters == null || counters.size() < 2 || ! counters.containsKey("total") || counters.get("total") == 0) {
      this.current = Status.UP; // optimistic approach
      builder.withDetail("msg", "no input");
    } else {
      double failureRatio = counters.get("errors") / counters.get("total");
      if (failureRatio < 0.5) {
        this.current = Status.UP;
        builder.withDetail("ratio", failureRatio);
        builder.withDetail("rule", "the failure ratio is less than 0.5");
      } else if (failureRatio > 0.8) {
        if (counters.get("total") > 5) {
          this.current = Status.DOWN;
          builder.withDetail("ratio", failureRatio);
          builder.withDetail("rule", "the failure ratio is greater than 0.8 and total is greater than 5");
        } else {
          this.current = Status.OUT_OF_SERVICE;
          builder.withDetail("ratio", failureRatio);
          builder.withDetail("rule", "the failure ratio is greater than 0.8 and total is less than 5");
        }
      } else {
        this.current = Status.OUT_OF_SERVICE;
        builder.withDetail("ratio", failureRatio);
        builder.withDetail("rule", "the failure ratio is between [0.5, 0.8[");
      }
    }
    
    builder.status(this.current); 
  }
  
  @Scheduled(initialDelay = 2*60*1000, fixedDelay = 2*60*1000)
  public void resetCounterWhenStatusIsDown() {
    if (this.current != null && !Status.UP.equals(this.current)) {
      LOGGER.debug("Reset the \"{}\" counters.", this.metricName);
      this.counterService.reset(this.metricName + ".total");
      this.counterService.reset(this.metricName + ".errors");
    }
  }
}
