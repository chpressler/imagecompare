/*
 * BloomEffect.java
 *
 * Created on March 30, 2007, 1:12 PM
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
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author nigel
 */
public class BloomEffect implements AlphaEffect, ContainedEffect{
    
    /** 
     * The bloom rendered into it's own image
     */
    protected BufferedImage   bloom;
    
    /**
     * The brightness of the bloom
     */
    private   float           alpha=1.0f;
    
    /** 
     * The container of the effect
     */
    private   EffectContainer effectContainer;
    
    protected static BloomFilter    bloomFilter= new BloomFilter();
    
    {
        bloomFilter.setAttribute(bloomFilter.BLOOM_ONLY,"yes");
    }
    
    public BloomEffect(){
        
    }

    public EffectContainer getEffectContainer() {
        return effectContainer;
    }

    public void setEffectContainer(EffectContainer effectContainer) {
        this.effectContainer = effectContainer;
    }
    

    
    /** Creates a new instance of BloomEffect */
    public BloomEffect(BufferedImage sourceImage) {
        bloom = bloomFilter.apply(sourceImage);
    }

    /** Creates a new instance of BloomEffect */
    public BloomEffect(BufferedImage sourceImage, float alpha) {
        this(sourceImage);
        setAlpha(alpha);
    }
    
    
    public boolean isLocalEffect() {
        return true;
    }

    public void paintEffect(Graphics2D graphics) {       
        if (alpha<0.1f){
            return;
        }
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        if (effectContainer!=null){
            graphics.drawImage(bloom,0,0,effectContainer.getWidth(),effectContainer.getHeight(),null);
        } else {
            graphics.drawImage(bloom,0,0,null);
        }
    }

    public long update() {
        return EFFECT_INACTIVE;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public BufferedImage getBloom() {
        return bloom;
    }

    public void setBloom(BufferedImage source) {
        this.bloom = bloom;
    }
    

}
