/*
 * ComponentEffect.java
 *
 * Created on March 31, 2007, 1:12 AM
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author nigel
 */
public abstract class ComponentEffect implements ContainerEffect,ComponentListener{
    protected JComponent        theComponent;
    
    protected Container        theContainer;
    
    protected EffectContainer  effectContainer;
    
    /** Creates a new instance of ReflectComponent */
    public ComponentEffect(Container container, JComponent component, EffectContainer effectContainer) {
        theContainer = container;
        theComponent = component;
        this.effectContainer = effectContainer;
        theComponent.addComponentListener(this);
        initializeEffect();
    }
    
    public abstract void initializeEffect();
    
    public boolean isLocalEffect() {
        return true;
    }
    
    public long update() {
        return EFFECT_INACTIVE;
    }

    public void setComponent(JComponent theComponent) {
        this.theComponent = theComponent;
        initializeEffect();
    }

    public JComponent getComponent(JComponent theComponent) {
        return theComponent;
    }

    public void componentResized(ComponentEvent componentEvent) {
        if (componentEvent.getComponent().isVisible()){
            initializeEffect();
        }
    }

    public void componentMoved(ComponentEvent componentEvent) {       
    }

    public void componentShown(ComponentEvent componentEvent) {
        initializeEffect();
    }

    public void componentHidden(ComponentEvent componentEvent) {
    }
    
}
