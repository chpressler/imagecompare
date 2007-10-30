/*
 * RichComponent.java
 *
 * Created on March 16, 2007, 3:58 PM
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

import com.blogofbug.swing.components.effects.Effect;
import java.awt.Image;
import java.io.File;
import java.net.URL;

/**
 * A component that can be part of the a rich user interface
 * @author nigel
 */
public interface RichComponent {
    /** 
     * Assisgns a uniform alpha to the component
     *
     * @param alpha A value from 0.0 to 1.0 where 1.0 is fully visible, and 0.0
     * is completely invisible.
     */
    public void setAlpha(float alpha);
    
    /**
     * Adds an effect which should be rendered before the component paints itself
     *
     * @param The effect
     */
    public void addBackgroundEffect(Effect effect);
    
    /**
     * Adds an effect which should be rendered after the component paints itself
     *
     * @param The effect
     */
    public void addForegroundEffect(Effect  effect);
    
    /**
     * Removes an effect from the background effects
     *
     * @param The effect
     */
    public void removeBackgroundEffect(Effect effect);
    
    /**
     * Removes an effect from the foreground effects
     *
     * @param The effect
     */
    public void removeForegroundEffect(Effect  effect);  
}
