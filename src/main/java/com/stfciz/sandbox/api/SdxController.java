package com.stfciz.sandbox.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.stfciz.sandbox.configuration.SdxConfiguration;
import com.stfciz.sandbox.service.SdxService;
import com.stfciz.sandbox.service.SdxServiceException;

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
  public SleepResponse sleep(@RequestParam(defaultValue="500", value="d") int delay) throws SdxServiceException {
    return new SleepResponse(this.sbxService.sleep(delay));
  }
  
  @RequestMapping("/hello")
  public String hello() {
    return "Hi";
  }
  
  
  @RequestMapping("/compute")
  public String compute(@RequestParam(defaultValue="1000", value="d") int delay, @RequestParam(defaultValue="2", value="t") int nbTasks, @RequestParam(defaultValue="false", value="p") boolean parallel) {
    StopWatch sw = new StopWatch();
    ExecutorService executorService = Executors.newFixedThreadPool(nbTasks <= 10 ? nbTasks : Math.max(10, nbTasks / 2));
    sw.start();
    Stream<Integer> taskStream = IntStream.range(0, nbTasks).boxed();
    if (parallel) {
      CompletableFuture<Void> result = CompletableFuture.allOf(taskStream
                              .map(i -> CompletableFuture.supplyAsync(() -> { 
                                    try {
                                      return sbxService.sleep(delay);
                                    } catch(SdxServiceException e) { 
                                      throw new CompletionException(e);
                                    }
                                  }, executorService ))
                              .collect(Collectors.toList())
                              .stream().toArray(CompletableFuture[]::new));
      result.join();
    } else {
      taskStream.forEach( i -> { 
        try {
          this.sbxService.sleep(delay);
        } catch(SdxServiceException e) { 
          throw new CompletionException(e);
        }
      });
    }

    sw.stop();
    return String.format("The %d task(s) are completed in %d ms.", nbTasks, sw.getTotalTimeMillis());
  }
  
  @RequestMapping(value="/plcompute", method=RequestMethod.POST)
  public DeferredResult<String> parallelComputing(@RequestBody ParallelComputingRequestBody request) {
    DeferredResult<String> result = new DeferredResult<String>();
    ExecutorService executorService = Executors.newFixedThreadPool(request.getDelays().size() <= 10 ? request.getDelays().size() : Math.max(10, request.getDelays().size() / 2));
    
    StopWatch sw = new StopWatch();
    sw.start();
    
    Stream<Integer> delayStream = request.getDelays().stream();
    
    CompletableFuture<Void> cf = CompletableFuture.allOf(delayStream
        .map(i -> CompletableFuture.supplyAsync(() -> { 
              try {
                return sbxService.sleep((long)i);
              } catch(SdxServiceException e) { 
                throw new CompletionException(e);
              }
            }, executorService))
        .collect(Collectors.toList())
        .stream().toArray(CompletableFuture[]::new));
    
    cf.whenComplete((r,e) -> {
      sw.stop();
      if (e != null) {
        result.setErrorResult(e);
      } else {
        result.setResult(String.format("The %d task(s) are successfully completed in %d ms.",  request.getDelays().size(), sw.getTotalTimeMillis()));
      }
    });
    
    return result;
  }
}
