package com.stfciz.sandbox;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class Java8Tests {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void testSequential() throws Exception {
    StopWatch sw = new StopWatch();
    sw.start();
    List<String> list = IntStream.range(0, 10).boxed().map(this::generateTask)
        .collect(Collectors.toList());
    sw.stop();
    logger.info("{} in {} ms", list.toString(), sw.getTotalTimeMillis());
  }

  @Test
  public void testParallel() throws Exception {
    StopWatch sw = new StopWatch();
    sw.start();
    
    List<CompletableFuture<String>> futures = IntStream.range(0, 10).boxed()
        .map(i -> this.generateAsyncTask(i).exceptionally(t -> t.getMessage()))
        .collect(Collectors.toList());
    
    CompletableFuture<List<String>> result = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
        .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    
    result.thenAccept(l -> {
      sw.stop();
      logger.info("{} in {} ms", l.toString(), sw.getTotalTimeMillis());
     }).join();
    
    /**
     * get
     *  Waits if necessary for this future to complete, and then returns its result.
     *  Specified by:
     *  get in interface Future<T>
     *  Returns:
     *  the result value
     *  throws:
     *  CancellationException - if this future was cancelled
     *  ExecutionException - if this future completed exceptionally
     *  InterruptedException - if the current thread was interrupted while waiting 
     */
    
    /**
     * join
     * 
     * Returns the result value when complete, or throws an (unchecked) exception if completed exceptionally. 
     * To better conform with the use of common functional forms, if a computation involved in the completion of this CompletableFuture threw an exception, 
     * this method throws an (unchecked) CompletionException with the underlying exception as its cause.
     * Returns:
     * the result value
     * Throws:
     * CancellationException - if the computation was cancelled
     * CompletionException - if this future completed exceptionally or a completion computation threw an exception 
     */
    
    //result.join();
  }

  /**
   * 
   * @param i
   * @return
   */
  private String generateTask(int i) {
    try {
      logger.info("Task {} is sleeping ...", i);
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return i + "-" + "test";
  }

  /**
   * 
   * @param i
   * @param executorService
   * @return
   */
  private CompletableFuture<String> generateAsyncTask(int i) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        logger.info("Task {} is sleeping ...", i);
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (i == 5) {
        throw new RuntimeException("Run, it is a 5!");
      }
      return i + "-" + "test";
    });
  }
}
