/*
 * AlphaBurst.java
 *
 * Created on March 18, 2007, 9:51 AM
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

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Creates an animation of an image expanding out from a central point, fading as 
 * it goes. 
 *
 * @author nigel
 */
public class AlphaBurst implements Effect{
    protected int   width = 0;
    protected int   height = 0;
    protected float alpha   = 1.0f;
    protected BufferedImage image = null;
    protected Point center = null;
    protected float fadeRate = 8.0f;
    
    
    /** Creates a new instance of AlphaBurst 
     *
     * @param image The image to burst
     * @param center The center of the image (the burst-out point)
     *
     */
    public AlphaBurst(BufferedImage image, Point center) {
        this.center = center;
        this.image=image;
        width = image.getWidth();
        height = image.getHeight();
    }

    /** Creates a new instance of AlphaBurst 
     *
     * @param image The image to burst
     * @param center The center of the image (the burst-out point)
     * @param fadeRate The rate at which the image fades
     *
     */
    public AlphaBurst(BufferedImage image, Point center, float fadeRate){
        this(image,center);
        setFadeRate(fadeRate);
    }

    /** 
     * Sets the fadeRate which must be greater than 1.0. It controls the speed
     * with which the image fades and the effect ends
     *
     * @param fadeRate The rate the image fades at
     */
    public void setFadeRate(float fadeRate) {
        if (fadeRate>1.0f){
            this.fadeRate = fadeRate;
        }
    }
    

    /**
     * Paints the burst
     *
     * @param graphics The graphics context
     *
     */
    public void paintEffect(Graphics2D graphics) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
        graphics.drawImage(image,center.x-width/2,center.y-height/2,width,height,null);
    }

    /**
     * Updates the burst
     *
     * @return The next time the effect should be updated
     */
    public long update() {
        alpha=alpha-(alpha/8.0f);
        width=(int) ((float)width*1.2f);
        height=(int) ((float)height*1.2f);
        
        if (alpha>0.1){
            return 20;
        } else {
            return Effect.EFFECT_FINISHED;
        }
    }

    /**
     * It's a local effect
     *
     * @return Always true
     */
    public boolean isLocalEffect() {
        return true;
    }
    
}
