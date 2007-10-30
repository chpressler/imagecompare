/*
 * Effect.java
 *
 * Created on March 18, 2007, 7:35 AM
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

import java.awt.Graphics2D;

/**
 * Interface to capture an animation effect for the effects panel intended to provide
 * data back to the core controller as to when to stop, start or refresh an animation.
 *
 * @author nigel
 */
public interface Effect {
    /**
     * Returned by update() if no further animations are required
     */
    public static final long     EFFECT_FINISHED     = -1;
    
    /**
     * Returned by update() if no further timer based animations are 
     * required
     */
    public static final long     EFFECT_INACTIVE     = -2;
    
    /** 
     * This method should return true if the graphics context supplied to the 
     * update method should be protected (a-la paintComponent()), or preserved
     * (a-la paint()). 
     *  
     * @return True if the context should be protected during this effects update
     * false if it's fair game.
     */
    public boolean  isLocalEffect();
    
    /** 
     * Causes the effect to be painted, the global context will be preserved 
     * based on the value of the isLocalEffect() call. 
     *
     * @param graphics The graphics context
     */
    public void paintEffect(Graphics2D graphics);
    
    /** 
     * Asks the effect to update itself, returning the
     * delay in milli-seconds before the next required update
     *
     * @return The number of milliseconds before the effect would like to update itself again, or
     * EFFECT_FINISHED if there will be no further updates. To keep the effect being
     * painted, but not based on timers (instead on repaint trigger only) use EFFECT_INACTIVE (effect will be drawn
     *  when repaint called, but not at other times).
     */
    public long  update();
}
