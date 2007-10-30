/*
 * ScrollImage.java
 *
 * Created on March 19, 2007, 1:12 AM
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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Scrolls an image up an effects container. WARNING: API will be extended for this 
 * effect
 * @author nigel
 */
public class ScrollImage implements Effect{
    int drawY = 0;
    EffectContainer  panel = null;
    BufferedImage image=null;
    Color background = null;
    
    /**
     * Creates a new instance of ScrollImage
     * @param background The background color
     * @param image The image to scroll
     * @param panel The effects container
     */
    public ScrollImage(Color background, BufferedImage image, EffectContainer panel) {
        drawY = panel.getHeight();
        this.panel = panel;
        this.image = image;
        this.background = background;
    }

    /**
     * Returns true
     * @return True
     */
    public boolean isLocalEffect() {
        return true;
    }

    /**
     * Paints the effect
     * @param graphics The graphics context
     */
    public void paintEffect(Graphics2D graphics) {
        graphics.setColor(background);
        graphics.fillRect(0,0,panel.getWidth(),panel.getHeight());
        
        graphics.drawImage(image,(panel.getWidth()-image.getWidth())/2,drawY,null);
    }

    /**
     * Updtes the effect
     * @return The next update
     */
    public long update() {
        drawY--;
        
        if (drawY+image.getHeight()<0){
            return Effect.EFFECT_FINISHED;
        }
        
        return 1;
    }
    
}
