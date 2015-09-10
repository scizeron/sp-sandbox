package com.stfciz.sandbox.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stfciz.sandbox.configuration.SdxConfiguration;
import com.stfciz.sandbox.service.SdxService;

/**
 * 
 * @author stfciz
 *
 * 25 juin 2015
 */
@RestController
public class SdxController {

  @Autowired
  private SdxConfiguration sdxConfiguration;
  
  @Autowired
  private SdxService<String> sbxService;
  
  @RequestMapping("/sleep")
  public SleepResponse sleep(@RequestParam(defaultValue="500", value="d") int delay) {
    return new SleepResponse(this.sbxService.sleep(delay));
  }
  
  @RequestMapping("/hello")
  public String hello() {
    return "Hi";
  }
  
  
  @RequestMapping("/compute")
  public String compute(@RequestParam(defaultValue="1000", value="d") int delay, @RequestParam(defaultValue="2", value="t") int nbTasks, @RequestParam(defaultValue="false", value="p") boolean parallel) throws Exception {
    StopWatch sw = new StopWatch();
    ExecutorService executorService = Executors.newFixedThreadPool(nbTasks <= 10 ? nbTasks : Math.max(10, nbTasks / 2));
    sw.start();
    Stream<Integer> taskStream = IntStream.range(0, nbTasks).boxed();
    if (parallel) {
      CompletableFuture<Void> result = CompletableFuture.allOf(taskStream
                              .map(i -> CompletableFuture.supplyAsync(() -> sbxService.sleep(delay), executorService ))
                              .collect(Collectors.toList())
                              .stream().toArray(CompletableFuture[]::new));
      result.join();
    } else {
      taskStream.forEach( i -> this.sbxService.sleep(delay));
    }

    sw.stop();
    return String.format("The %d task(s) are completed in %d ms.", nbTasks, sw.getTotalTimeMillis());
  }
}
