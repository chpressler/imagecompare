/*
 * ReflectionEffect.java
 *
 * Created on March 30, 2007, 3:53 PM
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

import com.blogofbug.utility.ImageUtilities;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author nigel
 */
public class ReflectionEffect implements Effect,ContainedEffect{
    protected Rectangle reflectArea       = new Rectangle(0,0,1,1);
    /** 
     * The container of the effect
     */
    private   EffectContainer effectContainer;
    
    protected BufferedImage   reflection;
    
    /** Creates a new instance of ReflectionEffect */
    public ReflectionEffect(BufferedImage image,Rectangle area, EffectContainer effectContainer) {
        setEffectContainer(effectContainer);
        reflectArea = area;
        reflectImage(image);
    }

    public void setImage(BufferedImage image){
        reflectImage(image);
    }
    
    protected void reflectImage(BufferedImage image){
        reflection = ImageUtilities.createCompatibleImage(image.getWidth(),image.getHeight()/2);

        Graphics2D g = reflection.createGraphics();
        int drawHeight = image.getHeight();
        AffineTransform tranform = AffineTransform.getScaleInstance(1.0, -1.0);
        tranform.translate(0, -drawHeight);
        
        // draw the flipped image
        AffineTransform oldTransform = g.getTransform();
        g.setTransform(tranform);
//        g.drawImage(image,0,0,null);
        g.setTransform(oldTransform);
        
        GradientPaint painter = new GradientPaint(0.0f, 0.0f,
                new Color(0.0f, 0.0f, 0.0f, 0.5f),
                0.0f, drawHeight / 3.0f,
                new Color(0.0f, 0.0f, 0.0f, 1.0f));
        
        // this use : Ar = Ad*(1-As) and Cr = Cd*(1-As)
        g.setComposite(AlphaComposite.DstOut);
        g.setPaint(painter);
        // this will make our image transluent ...
//        g.fill(new Rectangle2D.Double(0, 0, reflectArea.width, drawHeight));
        g.dispose();
        
        
    }
    
    public boolean isLocalEffect() {
        return true;
    }

    public void paintEffect(Graphics2D graphics) {
        graphics.drawImage(reflection,0,effectContainer.getWidth(),effectContainer.getWidth(),effectContainer.getWidth()/2,null);
        //graphics.drawImage(reflection,0,effectContainer.getHeight()/2,effectContainer.getWidth(),effectContainer.getHeight()/2,null);
        //graphics.fillRect(0,effectContainer.getHeight()/2,effectContainer.getWidth(),effectContainer.getHeight()/2);
    }

    public long update() {
        return EFFECT_INACTIVE;
    }

    public EffectContainer getEffectContainer() {
        return effectContainer;
    }

    public void setEffectContainer(EffectContainer effectContainer) {
        this.effectContainer = effectContainer;
    }
    
}
