/*
 * EffectUtilities.java
 *
 * Created on March 25, 2007, 10:22 PM
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

package com.blogofbug.swing.components.effects;

/**
 * Utility methods for supporting effects
 *
 * @author nigel
 */
public class EffectUtilities {
    
    /** 
     * Returns a value between current and target, reducing the closure
     * the smaller the gap is (creating an easing effect at the end of animations.
     *
     * @param current The current value
     * @param target The target value
     * @param rate The bigger the number the slower the transition
     * @return The new value (closer to, or the same as target)
     */
    public static int  easedClose(int current, int target, int rate){
        if (current==target){
            return target;
        }
        int delta = Math.abs(current-target)/rate;
        
       if (delta==0){
            delta = 1;
       }
        
       if (current<target){
            return current+delta;
       } else {
            return current-delta;
       }
    }
  
    /** 
     * Returns a value between current and target, reducing the closure
     * the smaller the gap is (creating an easing effect at the end of animations.
     *
     * @param current The current value
     * @param target The target value
     * @param rate The bigger the number the slower the transition
     * @return The new value (closer to, or the same as target)
     */
    public static float  easedClose(float current, float target, float rate){
        if (current==target){
            return target;
        }
        
        float delta = Math.abs(current-target)/rate;
        
        if (delta<0.01f){
            return target;
        }
        
       if (delta<0.1){
            delta = 0.1f;
       }
        
       if (current<target){
            return Math.min(target,current+delta);
       } else {
            return Math.max(current-delta,target);
       }
    }    
    
}
