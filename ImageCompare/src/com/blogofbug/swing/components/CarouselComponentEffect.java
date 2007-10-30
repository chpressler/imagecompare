/*
 * CarouselComponentEffect.java
 *
 * Created on March 31, 2007, 11:14 AM
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

package com.blogofbug.swing.components;

import com.blogofbug.swing.components.effects.BloomComponent;
import com.blogofbug.swing.components.effects.ComponentEffect;
import com.blogofbug.swing.components.effects.ComponentTextEffect;
import com.blogofbug.swing.components.effects.EffectContainer;
import com.blogofbug.swing.components.effects.ReflectComponent;
import java.awt.Container;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * This class implements a carousel component effect and therefore how
 * each component in the carousel is decorated. This implementation
 * has at least three known types of effect, the reflection (drawn first)
 * the selection highlight (a bloom) drawn second, and then finally text.
 * 
 * @author nigel
 */
public class CarouselComponentEffect extends ComponentEffect{
    protected ReflectComponent    reflection;
    protected CarouselTextEffect  text;
    protected CarouselBloomEffect highlight;
    
    /** Creates a new instance of CarouselComponentEffect */
    public CarouselComponentEffect(Container container, JComponent component, EffectContainer effectContainer,String label) {
        super(container,component, effectContainer);
        reflection = new ReflectComponent(container,component,effectContainer);   
        text = new CarouselTextEffect(container,component,effectContainer,label);
        highlight = new CarouselBloomEffect(container,component,effectContainer);   
    }

    protected CarouselComponentEffect(Container container, JComponent component, EffectContainer effectContainer){
        super(container, component, effectContainer);
    }
    
    public ComponentTextEffect getTextEffect() {
        return text;
    }

    public ComponentEffect getHighlight() {
        return highlight;
    }
    


    
    public void initializeEffect() {
        if (reflection!=null){
            reflection.initializeEffect();
        }
    }
    
    public void paintEffect(Graphics2D graphics) {
        reflection.paintEffect(graphics);
        text.paintEffect(graphics);
    }
    
    /** 
     * Minor sub-class of normal text effect to be brutal with the alpha value
     * scaling aggressively
     */
    private class CarouselTextEffect extends ComponentTextEffect{    
        /** Creates a new instance of ComponentTextEffect */
        public CarouselTextEffect(Container container, JComponent component, EffectContainer effectContainer, String text) {
            super(container,component,effectContainer,text);
        }
        public void setAlpha(float alpha) {
            if (alpha<0.49f){
                super.setAlpha(0.0f);
                return;
            }
            
            alpha = (alpha-0.49f) * 100.0f;
            super.setAlpha(alpha);
        }
    }
    
    /** 
     * Minor sub-class of normal text effect to be brutal with the alpha value
     * scaling aggressively
     */
    public class CarouselBloomEffect extends BloomComponent{    
        /** Creates a new instance of ComponentTextEffect */
        public CarouselBloomEffect(Container container, JComponent component, EffectContainer effectContainer) {
            super(container,component,effectContainer);
        }
        public void setAlpha(float alpha) {
            if (alpha<0.49f){
                super.setAlpha(0.0f);
                return;
            }
            
            alpha = (alpha-0.49f) * 100.0f;
            super.setAlpha(alpha);
        }
    }    
}
