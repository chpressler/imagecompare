/*
 * FadeEffect.java
 *
 * Created on March 19, 2007, 1:41 AM
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

import com.blogofbug.swing.components.EffectsPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Fades out the whole area of the efect container from one color to transparent, 
 * or vice-versa. 
 *
 * @author nigel
 */
public class FadeEffect implements Effect{
    float startAlpha = 0.0f;
    float endAlpha   = 0.0f;
    Color color      = null;
    EffectContainer panel = null;
    
    /**
     * Creates a new instance of FadeEffect
     * @param color The color to create
     * @param startAlpha The initial alpha value
     * @param endAlpha The final alpha value
     * 
     * @param panel The effects container
     */
    public FadeEffect(Color color, float startAlpha, float endAlpha, EffectContainer panel) {
        this.color = color;
        this.startAlpha = startAlpha;
        this.endAlpha = endAlpha;
        this.panel=panel;
    }

    /**
     * Always returns true
     * @return true
     */
    public boolean isLocalEffect() {
        return true;
    }

    /**
     * Paints the effect
     * @param graphics The graphics context
     */
    public void paintEffect(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, startAlpha));
        graphics.fillRect(0,0,panel.getWidth(), panel.getHeight());
    }

    /**
     * Updates the fade
     * @return The time to the next update
     */
    public long update() {
        float delta = (startAlpha-endAlpha)/4.0f;

        if (delta>0.0f){
            if (delta<0.01f){
                delta=0.01f;
            }
        } else {
            if (delta>-0.01f){
                delta=-0.01f;
            }
        }
        startAlpha-=delta;
        if (Math.abs(startAlpha-endAlpha)<0.01f){
            return Effect.EFFECT_FINISHED;
        } else {
            return 1;
        }
    }
    
}
