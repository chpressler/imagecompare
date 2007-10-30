/*
 * MorphBoundedEffect.java
 *
 * Created on March 25, 2007, 10:34 PM
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
import java.awt.Rectangle;

/**
 *
 * @author nigel
 */
public class MorphBoundedEffect implements Effect{
    Rectangle        targetBounds     = null;
    /** 
     * If true the keepAlive parameter is set the effect does not die
     * when it reaches its destination, it simply becomes dormant
     */
    boolean keepAlive = false;
    
    /**
     * The effect being morphed etc
     */
    BoundEffect effect;
    
    /** 
     * Creates a new instance of MorphBoundedEffect 
     */
    public MorphBoundedEffect(BoundEffect effect, boolean keepAlive) {
        this.keepAlive = keepAlive;
        targetBounds = effect.getBounds();
        this.effect = effect;
    }

    public void setBounds(Rectangle rectangle){
        targetBounds = rectangle;
    }

    public Rectangle getBounds(){
        return (Rectangle) targetBounds.clone(); 
    }
    
    public void morphBounds(Rectangle rectangle){
        
    }
    
    public boolean isLocalEffect() {
        return true;
    }

    public void paintEffect(Graphics2D graphics) {
        effect.paintEffect(graphics);
    }
    
    public BoundEffect getEffect(){
        return effect;
    }

    public Rectangle morph(Rectangle from, Rectangle to, int rate){
        from.x = EffectUtilities.easedClose(from.x,to.x,rate);
        from.y = EffectUtilities.easedClose(from.y,to.y,rate);
        from.width = EffectUtilities.easedClose(from.width,to.width,rate);
        from.height = EffectUtilities.easedClose(from.height,to.height,rate);
        
        return from;
    }
    
    public long update() {
        boolean     morphDone = false;
        boolean     effectDone = false;
        Rectangle   currentBounds = effect.getBounds();
        
        if (currentBounds.equals(this.targetBounds)){
            morphDone = true;   
        } else {
            effect.setBounds(morph(currentBounds,targetBounds,4));
        } 
        
        long nextUpdate = effect.update();
        if (nextUpdate==Effect.EFFECT_FINISHED){
            if (keepAlive){
                //If it's finished and the morph is done, we're done
                if (morphDone){
                    return Effect.EFFECT_INACTIVE;
                } 
            } 
        } else if (nextUpdate==Effect.EFFECT_INACTIVE){
            if (morphDone){
                return nextUpdate;
            } else {
                nextUpdate=1;
            }
        }
     
        //Either the effect isn't done or the morph isn't done pass on the 
        //shortest update
        return Math.min(nextUpdate,1);
    }
    
}
