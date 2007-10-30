/*
 * ImageCarouselComponentEffect.java
 *
 * Created on April 12, 2007, 5:56 PM
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

package com.blogofbug.examples.yahooimagesearch;

import com.blogofbug.swing.components.CarouselComponentEffect;
import com.blogofbug.swing.components.effects.ComponentEffect;
import com.blogofbug.swing.components.effects.ComponentTextEffect;
import com.blogofbug.swing.components.effects.EffectContainer;
import com.blogofbug.swing.components.effects.ReflectComponent;
import java.awt.Container;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author nigel
 */
public class ImageCarouselComponentEffect extends CarouselComponentEffect{
    ComponentEffect         simpleHighlight;
    ComponentTextEffect     nullText;
    
    
    /** Creates a new instance of CarouselComponentEffect */
    public ImageCarouselComponentEffect(Container container, JComponent component, EffectContainer effectContainer,String label) {
        super(container,component,effectContainer);
        reflection = new ReflectComponent(container,component,effectContainer);   
        simpleHighlight = new ImageHighlightEffect(container,component,effectContainer);   
        nullText = new NullTextEffect(container,component,effectContainer,label);
    }    
    
    public ComponentTextEffect getTextEffect() {
        return nullText;
    }

    public ComponentEffect getHighlight() {
        return simpleHighlight;
        
    }    

    public void paintEffect(Graphics2D graphics) {
        reflection.paintEffect(graphics);
    }
    
    
    public class ImageHighlightEffect extends ComponentEffect{
        public ImageHighlightEffect(Container container, JComponent component, EffectContainer effectContainer){
            super(container, component, effectContainer);
        }
        public void initializeEffect() {
        }

        public void paintEffect(Graphics2D graphics) {
        }
        
    }
 
    
    /** 
     * Minor sub-class of normal text effect to be brutal with the alpha value
     * scaling aggressively
     */
    private class NullTextEffect extends ComponentTextEffect{    
        /** Creates a new instance of ComponentTextEffect */
        public NullTextEffect(Container container, JComponent component, EffectContainer effectContainer, String text) {
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

        public void paintEffect(Graphics2D graphics) {
        }
        

    }    
    
}
