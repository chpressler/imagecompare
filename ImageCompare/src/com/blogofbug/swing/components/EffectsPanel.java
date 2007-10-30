/*
 * EffectsPanel.java
 *
 * Created on March 18, 2007, 7:48 AM
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

import com.blogofbug.swing.components.effects.*;
import com.blogofbug.swing.components.effects.EffectContainer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * An effects panel is one that is intended for the drawing of special effects
 * (things like spot-lights, shadows, alpha-bursts, sparks, even transitions for GUIs). 
 *
 * @author nigel
 */
public class EffectsPanel extends JComponent implements EffectContainer{

    private EffectEngine    effectEngine;
    
    public EffectsPanel(boolean paintOnly){
        effectEngine = new EffectEngine(this,paintOnly);
    }
    
    public void paintComponent(Graphics graphics){
        effectEngine.paintEffects(graphics);
    }

    public void addEffect(Effect effect) {
        effectEngine.addEffect(effect);
    }

    public boolean update() {
        return effectEngine.update();
    }

    public void paintEffects(Graphics graphics) {
        effectEngine.paintEffects(graphics);
    }

    public void wakeEffect(Effect effect) {
        effectEngine.wakeEffect(effect);
    }

    public void removeEffect(Effect effect) {
        effectEngine.removeEffect(effect);
    }

}
