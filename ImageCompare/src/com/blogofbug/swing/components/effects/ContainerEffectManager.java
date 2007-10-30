/*
 * ContainerEffectManager.java
 *
 * Created on March 31, 2007, 9:31 AM
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

import java.awt.Container;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.lang.UnsupportedOperationException;
import java.util.Hashtable;
import javax.swing.JComponent;

/**
 *
 * @author nigel
 */
public class ContainerEffectManager extends EffectEngine implements ContainerListener{
    protected ComponentEffectSupplier supplier;
    protected JComponent              component;
    protected Container               container;
    protected Hashtable<JComponent,ComponentEffect> componentEffects = new Hashtable<JComponent,ComponentEffect>();
    
    /** Creates a new instance of ContainerEffectManager */
    public ContainerEffectManager(JComponent containerComponent, ComponentEffectSupplier supplier, boolean paintOnly) {
        super(containerComponent,paintOnly);
        
        if (!(containerComponent instanceof Container)){
            throw new UnsupportedOperationException("Component must be a container");
        }
        
        container = (Container) containerComponent;
        this.supplier = supplier;
        
        container.addContainerListener(this);
    }
    
    public ComponentEffect  getEffectFor(JComponent component){
        return componentEffects.get(component);
    }
    
    public void componentAdded(ContainerEvent containerEvent) {
        ComponentEffect newEffect = supplier.getEffect((JComponent) containerEvent.getChild(), container,this);
        if (newEffect!=null){
            this.addEffect(newEffect);
            componentEffects.put((JComponent) containerEvent.getChild(), newEffect);
        }
    }

    public void componentRemoved(ContainerEvent containerEvent) {
        ComponentEffect effect = componentEffects.get(containerEvent.getChild());
        if (effect!=null){
            componentEffects.remove(containerEvent.getChild());
            removeEffect(effect);
        }
    }
    
    public interface ComponentEffectSupplier{
        public ComponentEffect getEffect(JComponent forComponent, Container inComponent, EffectContainer effectEngine);
    }
    
}
