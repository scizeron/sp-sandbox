package com.stfciz.sandbox.api;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author stfciz
 *
 * 15 sept. 2015
 */
public class ParallelComputingRequestBody {
  
  int delayMin = 0;
  
  int delayMax = 1000;
  
  int delayStep = 100;
  
  boolean ascendingStep = true;
  
  List<Integer> delays;

  public ParallelComputingRequestBody(int delayMin, int delayMax, int delayStep, boolean ascendingStep) {
    this.delayMin = delayMin;
    this.delayMax = delayMax;
    this.delayStep = delayStep;
    this.ascendingStep = ascendingStep;
    
    initDelays();
  }
  
  public ParallelComputingRequestBody() {
    initDelays();
  }
  
  /**
   * 
   */
  private void initDelays() {
    this.delays = new ArrayList<Integer>();
    int current = delayMin;
    while (current < delayMax) {
      if (ascendingStep) {
        delays.add(current);  
      } else {
        delays.add(current, 0);
      }
      current = current + this.delayStep;
    }
  }

  public int getDelayMin() {
    return delayMin;
  }

  public int getDelayMax() {
    return delayMax;
  }

  public int getDelayStep() {
    return delayStep;
  }

  public boolean isAscendingStep() {
    return ascendingStep;
  }

  public List<Integer> getDelays() {
    return this.delays;
  }

  

}
