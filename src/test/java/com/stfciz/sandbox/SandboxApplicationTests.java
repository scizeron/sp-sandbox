package com.stfciz.sandbox;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import com.stfciz.sandbox.api.SleepResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SdxApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class SandboxApplicationTests {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  
  private RestTemplate template = new TestRestTemplate();
  
  @Value("${local.server.port}")
  private int port;
  
  @Value("${local.management.port}")
  private int managementPort;
  
  /**
   * 
   * @param delay
   * @return
   */
  private String getSleepUrl(long delay) {
    return getHost() + "/sleep?d=" + delay;
  }
  
  /**
   * 
   * @return
   */
  private String getHost() {
    return "http://localhost:" + port;
  }
  
  /**
   * 
   * @return
   */
  private String getAdminHost() {
    return "http://localhost:" + managementPort;
  }
  
	@Test
	public void sleepOK() {
	  long delay = 100;
	  Assert.assertThat(this.template.getForEntity(getSleepUrl(delay), SleepResponse.class).getStatusCode().is2xxSuccessful()
	      , CoreMatchers.is(true));
	}

 @Test
  public void sleepKO() {
    long delay = 10000;
    Assert.assertThat(this.template.getForEntity(getSleepUrl(delay), SleepResponse.class).getStatusCode().is5xxServerError()
        , CoreMatchers.is(true));
  }

  @Test
  public void metrics() {
    sleepOK();
    ResponseEntity<Integer> result = this.template.getForEntity(getAdminHost() + "/admin/metrics/counter.com.stfciz.sandbox.service.SdxService.sleep.total", Integer.class);
    Assert.assertThat(result.getStatusCode().is2xxSuccessful(), CoreMatchers.is(true));
    Assert.assertThat(result.getBody() > 0, CoreMatchers.is(true));
  }
  
  @Test
  public void health() {
    sleepOK();
    ResponseEntity<String> result = this.template.getForEntity(getAdminHost() + "/admin/health", String.class);
    Assert.assertThat(result.getStatusCode().is2xxSuccessful(), CoreMatchers.is(true));
  }
  
  @Test
  public void hello() {
    Assert.assertThat(this.template.getForEntity(getHost() + "/hello", String.class).getStatusCode().is2xxSuccessful()
        , CoreMatchers.is(true));
  }
  
  @Test
  public void sequentialComputing() {
    int delay = 1000;
    int tasks = 5;
    StopWatch sw = new StopWatch(); 
    sw.start();
    ResponseEntity<String> result = this.template.getForEntity(getHost() + String.format("/compute?t=%d&d=%d", tasks, delay), String.class);
    sw.stop();
    logger.info(result.getBody());
    Assert.assertThat(result.getStatusCode().is2xxSuccessful(), CoreMatchers.is(true));
    Assert.assertThat(sw.getTotalTimeMillis() > delay * 2,  CoreMatchers.is(true));
  }

  @Test
  public void parallelComputing() {
    int delay = 1000;
    int tasks = 10;
    StopWatch sw = new StopWatch(); 
    sw.start();
    ResponseEntity<String> result = this.template.getForEntity(getHost() + String.format("/compute?t=%d&d=%d&p=true", tasks, delay), String.class);
    sw.stop();
    logger.info(result.getBody());
    Assert.assertThat(result.getStatusCode().is2xxSuccessful(), CoreMatchers.is(true));
    Assert.assertThat(sw.getTotalTimeMillis() < delay * 2,  CoreMatchers.is(true));
  }
  
}
