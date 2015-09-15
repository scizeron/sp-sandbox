package com.stfciz.sandbox.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author stfciz
 *
 * 15 sept. 2015
 */
public class ParallelComputingRequestBody {
  
  private int delayMin;
  
  private int delayMax;
  
  private int delayStep;
  
  private boolean ascendingStep;
  
  private List<Integer> delays;

  /**
   * 
   * @param delayMin
   * @param delayMax
   * @param delayStep
   * @param ascendingStep
   */
  @JsonCreator
  public ParallelComputingRequestBody(@JsonProperty("delayMin") int delayMin, @JsonProperty("delayMax") int delayMax, @JsonProperty("delayStep") int delayStep, @JsonProperty("ascendingStep") boolean ascendingStep) {
    this.delayMin = delayMin;
    this.delayMax = delayMax;
    this.delayStep = delayStep;
    this.ascendingStep = ascendingStep;
    
    initDelays();
  }
 
  /**
   * 
   */
  private void initDelays() {
    this.delays = new ArrayList<Integer>();
    int current = this.delayMin;
    while (current < this.delayMax) {
      if (this.ascendingStep) {
        this.delays.add(current);  
      } else {
        this.delays.add(current, 0);
      }
      current += this.delayStep;
    }
  }

  public int getDelayMin() {
    return this.delayMin;
  }

  public int getDelayMax() {
    return this.delayMax;
  }

  public int getDelayStep() {
    return this.delayStep;
  }

  public boolean isAscendingStep() {
    return this.ascendingStep;
  }

  public List<Integer> getDelays() {
    return this.delays;
  }
}
