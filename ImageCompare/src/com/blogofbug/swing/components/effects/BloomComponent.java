/*
 * BloomComponent.java
 *
 * Created on March 31, 2007, 12:38 PM
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

import com.blogofbug.filters.BloomFilter;
import com.blogofbug.utility.ImageUtilities;
import java.awt.AlphaComposite;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author nigel
 */
public class BloomComponent extends ComponentEffect implements AlphaEffect, SlowPaintEffect{
    private float alpha = 0.0f;
    private boolean paintLock = true;
    
    BufferedImage image;
        
    /** Creates a new instance of BloomComponent */
    public BloomComponent(Container container, JComponent component, EffectContainer effectContainer) {
        super(container,component,effectContainer);
    }


    /**
     * Generating a bloom takes a long time so the effect will not repaint itself
     * until the bloom lock is set to true
     */
    public void removePaintLock() {
        paintLock=false;
    }
    
    public void initializeEffect() {
        if (!paintLock){
            BloomFilter    bloomFilter= new BloomFilter();
            bloomFilter.setAttribute(bloomFilter.BLOOM_ONLY,"yes");
            
            image = bloomFilter.apply(ImageUtilities.renderComponentToImage(theComponent));
            
            paintLock=true;
        }        
    }
        
    public void paintEffect(Graphics2D graphics) {
        if ((image!=null) && (alpha>0.0f)){
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            graphics.drawImage(image,theComponent.getX(),theComponent.getY(),
                    theComponent.getWidth(),theComponent.getHeight(),null);        
        }
    }

    public void componentResized(ComponentEvent componentEvent) {   
    }

    public void componentShown(ComponentEvent componentEvent) {
    }
    

    
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    
    
}
