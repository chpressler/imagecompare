/*
 * ImageCarousel.java
 *
 * Created on April 12, 2007, 2:45 PM
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

import com.blogofbug.swing.components.JCarosel;
import com.blogofbug.swing.components.ReflectedImageLabel;
import com.blogofbug.swing.components.effects.ComponentEffect;
import com.blogofbug.swing.components.effects.EffectContainer;
import com.blogofbug.swing.components.effects.EffectEngine;
import com.blogofbug.swing.components.effects.FadeEffect;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author nigel
 */
public class ImageCarousel extends JCarosel{
    
    private EffectEngine    effectEngine;
    private boolean         faded = false;

    public ImageCarousel(){
        super(128);
        effectEngine=new EffectEngine(this,false);
    }
    
    public void fadeOut(){
        faded = true;
    }
    
    public void fadeBack(){
        faded = false;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (faded){
            g.setColor(new Color(0,0,0,196));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
    
    
    /**
     * Returns a RelectComponent effect for the specified object, to change the
     * exact effect, just over-ride this method
     *
     * @param forComponent The component to create the effect for
     * @param inComponent The container
     * @param effectEngine The engine creatingit
     * @return The effect, by default a ReflectComponent effect
     */
    public ComponentEffect getEffect(JComponent forComponent, Container inComponent, EffectContainer effectEngine) {
        String label = "";
        if (forComponent instanceof ReflectedImageLabel){
            label = ((ReflectedImageLabel) forComponent).getRichText();
        } else {
            if (forComponent.getToolTipText()!=null){
                label = forComponent.getToolTipText();
            } else {
                label = forComponent.getName();
                if (label==null){
                    label = "";
                }
            }
        }
        return new ImageCarouselComponentEffect(inComponent,forComponent,effectEngine,label);
    }
    
}
