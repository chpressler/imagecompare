/*
 * EffectSequence.java
 *
 * Created on March 19, 2007, 1:35 AM
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
 * An effect which chains together multiple effects into a sequence starting each
 * one as the previous effect ends. 
 *
 * @author nigel
 */
public class EffectSequence implements Effect{
    int     effectIndex = 0;
    Effect[]    effects;
    /**
     * Creates a new instance of EffectSequence
     * @param effects An array containing the sequence of effects
     */
    public EffectSequence(Effect[] effects) {
        this.effects = effects;
    }

    /**
     * Indicates if the current effect in the sequence is a local effect
     * @return Calls the equivalent function for the running effect
     */
    public boolean isLocalEffect() {
        return effects[effectIndex].isLocalEffect();
    }

    /**
     * Paints the current effect
     * @param graphics The graphics context
     */
    public void paintEffect(Graphics2D graphics) {
        effects[effectIndex].paintEffect(graphics);
    }

    /**
     * Updates the current effect, moving to the next one if the current effect has 
     * completed
     * @return The next time it is to be called or indicates that it no longer needs to be called.
     */
    public long update() {
        long result = effects[effectIndex].update();
        
        if (result==EFFECT_FINISHED){
            effectIndex++;
            if (effectIndex>=effects.length){
                return EFFECT_FINISHED;
            }
            return update();
        }
        
        return result;
    }
    
}
