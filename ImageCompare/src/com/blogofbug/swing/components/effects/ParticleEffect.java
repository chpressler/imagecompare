/*
 * ParticleEffect.java
 *
 * Created on March 18, 2007, 11:38 AM
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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * An effect showing some kind of particle that is affected by gravity.
 * @author nigel
 */
public class ParticleEffect implements Effect{
    /**
     * The image of the particle
     */
    protected BufferedImage   particle    = null;
    /**
     * The current x location
     */
    protected double          x = 0.0;
    /**
     * The current y location
     */
    protected double          y = 0.0;
    /**
     * The current horizontal velocity
     */
    protected double          vx = 0.0;
    /**
     * The current vertical velocity
     */
    protected double          vy = 0.0;
    /**
     * A counter recording the life of the particles
     */
    protected int             life = 0;
    /**
     * The current level of transparency
     */
    protected float           alpha = 1.0f;
    /**
     * A floor (if specified) that particles will bounce off of
     */
    protected double          floor = 1000000.0f;
    
    /**
     * Creates a new instance of ParticleEffect
     * @param particle The image for the particle
     * @param x The x co-ordinate
     * @param y The y co-ordinate
     * @param age The life of the particle
     * @param floor A floor that the particle will bonce off
     */
    public ParticleEffect(BufferedImage particle, int x, int y,int age, int floor) {
        this.particle = particle;
        this.x = (double) x;
        this.y = (double) y;
        this.vx = 4.0-Math.random()*8.0;
        this.vy = 6.0-Math.random()*8.0;
        this.life = age;
        this.floor = (double) floor;
    }

    /**
     * Always returns true
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
        if (alpha<1.0f){
            float finalAlpha = alpha;
            
            if (graphics.getComposite() instanceof AlphaComposite){
                AlphaComposite  alphaComp = (AlphaComposite) graphics.getComposite();
                finalAlpha = alphaComp.getAlpha() * alpha;
            }
            
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,finalAlpha));
        }
        graphics.drawImage(particle,(int)x, (int)y,null);
    }

    /**
     * Updates the effect
     * @return The next update
     */
    public long update() {
        life--;
        if (life<=0){
            return Effect.EFFECT_FINISHED;
        }
        if ((y>floor) && (vy>0)) {
            vy=0.0-vy*0.5;
        }
        x+=vx;
        y+=vy;
        vy+=0.1;
        if (life<10){
            alpha = (float) life / 10.0f;
        }
        return 20;
    }
    
}
