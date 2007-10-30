/*
 * EffectContainer.java
 *
 * Created on March 19, 2007, 9:42 PM
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

import java.awt.Graphics;
import java.awt.event.ActionEvent;

/**
 * The interface defines a component that can contain effects. 
 *
 * @author nigel
 */
public interface EffectContainer {
    
    /**
     * Called when the timer fires. It updates any effects that need updating,
     * removes any that have finished, and then if at leat one has updated 
     * stimulates a repaint. 
     *
     * @return true if a repaint should occur
     */
    boolean update();

    /**
     * Adds an effect to the container. 
     *
     * @param effect The effect to add
     */
    void addEffect(Effect effect);

    /** 
     * Removes an effect from the container
     *
     * @param effect The effect to remove
     */
    void removeEffect(Effect effect);
    
    /**
     * Wakes an inactive effect. 
     *
     * @param effect The effect
     */
    void wakeEffect(Effect effect);
    
    /**
     * 
     * Called when the effects need paintin'
     * 
     * @param graphics The graphics context
     */
    void paintEffects(Graphics graphics);
 
    /** 
     * Determines the width of the container
     *
     * @return The width in pixels of the container
     */
    int  getWidth();
    
    /** 
     * Determines the height of the container
     * 
     * @return The height in pixels of the container
     */
    int  getHeight();
}
