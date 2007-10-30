/*
 * ReflectComponent.java
 *
 * Created on March 30, 2007, 10:13 PM
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
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author nigel
 */
public class ReflectComponent extends ComponentEffect{
    protected BufferedImage     image;
    
    /** Creates a new instance of ReflectComponent */
    public ReflectComponent(Container container, JComponent component, EffectContainer effectContainer) {
        super(container,component,effectContainer);
        initializeEffect();
    }
    
    public void reinitializeEffect(){
        image = null;
        initializeEffect();
    }
    
    public void initializeEffect(){
        if (theComponent.getWidth()+theComponent.getHeight()==0){
            image = ImageUtilities.createCompatibleImage(1,1);
            return;
        }
        
        //If we already have a high-fidelity image, don't re-render
        if (image!=null){
            if ((image.getWidth()>theComponent.getWidth()) && (image.getHeight()<<1>theComponent.getHeight())){
                return;
            }
        }
        
        BufferedImage normalImage=ImageUtilities.renderComponentToImage(theComponent);
        image = ImageUtilities.createCompatibleImage(normalImage.getWidth(),normalImage.getHeight()/2);
        Graphics2D g = image.createGraphics();
        
        AffineTransform tranform = AffineTransform.getScaleInstance(1.0, -1.0);
        tranform.translate(0, -normalImage.getHeight());
        AffineTransform oldTransform = g.getTransform();
        g.setTransform(tranform);
        g.drawImage(normalImage,0,0,null);
        g.setTransform(oldTransform);
        GradientPaint painter = new GradientPaint(0.0f, 0.0f,
                new Color(0.0f, 0.0f, 0.0f, 0.5f),
                0.0f, normalImage.getHeight() / 2.0f,
                new Color(0.0f, 0.0f, 0.0f, 1.0f));
        
        g.setComposite(AlphaComposite.DstOut);
        g.setPaint(painter);
        g.fill(new Rectangle2D.Double(0, 0, normalImage.getWidth(), normalImage.getHeight()));
        g.dispose();    
    }
    
    public void paintEffect(Graphics2D graphics) {
        graphics.drawImage(image,theComponent.getX(),theComponent.getY()+theComponent.getHeight(),theComponent.getWidth(),theComponent.getHeight()/2,null);
    }

}
