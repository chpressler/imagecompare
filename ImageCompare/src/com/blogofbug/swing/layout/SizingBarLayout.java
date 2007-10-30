/*
 * SizingBarLayout.java
 * 
 * Created on Oct 1, 2007, 20:58:58 PM
 * 
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

package com.blogofbug.swing.layout;

import com.blogofbug.swing.components.SizingBar;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 *
 * @author nigel
 */
public class SizingBarLayout implements LayoutManager{
    private SizingBar bar = null;
    
    public SizingBarLayout(SizingBar bar){
        this.bar = bar;
    }
    
    public void addLayoutComponent(String arg0, Component arg1) {
    }

    public void removeLayoutComponent(Component arg0) {
    }

    public Dimension preferredLayoutSize(Container arg0) {
        Dimension d = new Dimension();
        
        d.height = bar.getBarHeight();
        d.width = arg0.getPreferredSize().width;
        
        return d;
    }

    public Dimension minimumLayoutSize(Container arg0) {
        return preferredLayoutSize(arg0);        
    }

    public void layoutContainer(Container arg0) {
        
        int limit = arg0.getWidth()-bar.getGrabBlockWidth();
        int x = 0;
        
        for (int c = 0; c<arg0.getComponentCount();c++){
            Component comp = arg0.getComponent(c);
            
            if (x+comp.getPreferredSize().width+3 > limit){
                comp.setBounds(0,0,0,0);
            } else {
                int y = (arg0.getHeight()-comp.getPreferredSize().height) /2 +1;
                comp.setBounds(x, y, comp.getPreferredSize().width, comp.getPreferredSize().height);            
            }
            
            x+=comp.getWidth()+4;
        }
    }

}
