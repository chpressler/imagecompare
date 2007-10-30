/*
 * PerformanceMonitor.java
 *
 * Created on June 12, 2003, 7:36 PM
 *
 * Copyright 2006-2007 Nigel Hughes 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at http://www.apache.org/
 * licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License.
 */

package com.blogofbug.utility;

/**
 * A utility class which assists with profiling the performance of some operations
 * @author buggles
 */
public class PerformanceMonitor {
    /**
     * A set of the last group of action timings
     */
    private long    operationTimes[];
    /**
     * The next slot (in operation times) that the next mark will go to
     */
    private int     nextSlot=-1;
    /**
     * The name of the monitor
     */
    private String  monitorName = "Unset";
    /**
     * The time the last operation started at
     */
    private long    operationStartTime = -1;
    
    /**
     * Creates a new instance of PerformanceMonitor
     * @param monitorName Name of the monitor
     * @param samples How many samples to take (for averaging purposes etc)
     */
    public PerformanceMonitor(String monitorName, int samples) {
        operationTimes = new long[samples];
        nextSlot = 0;
        this.monitorName = monitorName;
        for (int i = 0;i<operationTimes.length;i++){
            operationTimes[i]=-1;
        }
    }
    
    /**
     * Call when an operation started
     */
    public void operationStarted(){
        operationStartTime = System.currentTimeMillis();
    }
    
    /**
     * Call when an operation is stopped
     */
    public void operationStopped(){
        operationTimes[nextSlot++]=System.currentTimeMillis() - operationStartTime;
        if (nextSlot == operationTimes.length){
            nextSlot = 0;
        }
    }
    
    /**
     * 
     * Produces a string containing the key metrics for the operation
     * @return A string containg the key metrics.
     */
    public String generateMetrics(){
       long totalTime = 0;
       long tally = 0;
       long fastest = 1000000;
       long slowest = 0;
       for (int i=0;i<operationTimes.length;i++){
           if (operationTimes[i]>-1){
               totalTime+=operationTimes[i];
               tally++;
               fastest = Math.min(fastest, operationTimes[i]);
               slowest = Math.max(slowest, operationTimes[i]);
           } else{
               break;
           }
       }
       int lastSlot = nextSlot - 1;
       if (lastSlot == -1){
           lastSlot=operationTimes.length-1;
       }
       return monitorName+" "+tally+" operations - Range: "+fastest+"ms to "+slowest+"ms Total: "+totalTime+"ms Average: "+(totalTime/tally)+"ms Last: "+operationTimes[lastSlot]+"ms";
    }
}
