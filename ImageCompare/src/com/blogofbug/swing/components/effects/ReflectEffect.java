/*
 * ReflectEffect.java
 *
 * Created on March 18, 2007, 12:54 PM
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
import java.awt.Graphics2D;

/**
 * An effect that will reflect another effect onto the bottom third of the screen.
 * WARNING: The API for this will change to allow more ways to identify the reflection
 * location
 * @author nigel
 */
public class ReflectEffect implements Effect{
    /**
     * The reflected effect
     */
    protected   Effect          reflectThis;
    /**
     * The effect container
     */
    protected   EffectContainer    thePanel;
    
    /**
     * Creates a new instance of ReflectEffect
     * @param reflectThis The effect to reflect
     * @param panel The container for the effects to be drawn in
     */
    public ReflectEffect(Effect reflectThis, EffectContainer panel) {
        this.reflectThis = reflectThis;
        this.thePanel = panel;
    }

    /**
     * Always returns true
     * @return True
     */
    public boolean isLocalEffect() {
        return true;
    }

    /**
     * Paints the effect, and its reflection
     * @param graphics The graphics context
     */
    public void paintEffect(Graphics2D graphics) {
        reflectThis.paintEffect(graphics);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        graphics.translate(0,thePanel.getHeight());
        graphics.scale(1.0,-0.333333333);
        reflectThis.paintEffect(graphics);
    }

    /**
     * Updates the effect
     * @return The next update
     */
    public long update() {
        return reflectThis.update();
    }
    
}
