/*
 * SplicedLayout.java
 * 
 * Created on Sep 30, 2007, 11:22:48 PM
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

package com.blogofbug.swing.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 *
 * @author nigel
 */
public class SplicedLayout implements LayoutManager{
    /**
     *  The panel it's laying out
     */
    SplicedPane     pane = null;
    
    public SplicedLayout(SplicedPane splicedPane){
        this.pane=splicedPane;
    }
    
    public void addLayoutComponent(String arg0, Component arg1) {
    }

    public void removeLayoutComponent(Component arg0) {
    }

    public Dimension preferredLayoutSize(Container container) {
        Dimension d = new Dimension(container.getWidth(),container.getHeight());
        return d;
    }

    public Dimension minimumLayoutSize(Container arg0) {
        return new Dimension(32,32);
    }

    public void layoutContainer(Container container) {
        if (pane==null){
            return;
        }
                
        int leftWidth = pane.getLeftWidth();
        int bH = pane.getBarHeight();
        
        switch(pane.getSpliceLayout()){
        case HORIZONTAL_BOTTOM:
            pane.getFirstComponent().setBounds(0, 0, leftWidth, container.getHeight()-bH);
            pane.getSizingBar().setBounds(0,container.getHeight()-bH,
                    leftWidth,bH);
            pane.getSecondComponent().setBounds(leftWidth,0,container.getWidth()-leftWidth,
                    container.getHeight());
            break;
        case HORIZONTAL_TOP:
            pane.getFirstComponent().setBounds(0, bH, leftWidth, container.getHeight()-bH);
            pane.getSizingBar().setBounds(0,0,
                    leftWidth,bH);
            pane.getSecondComponent().setBounds(leftWidth,0,container.getWidth()-leftWidth,
                    container.getHeight());
            break;
        case VERTICAL:
            pane.getFirstComponent().setBounds(0, 0, container.getWidth(), leftWidth);
            pane.getSizingBar().setBounds(0,leftWidth,
                    container.getWidth(),bH);
            pane.getSecondComponent().setBounds(0,leftWidth+bH,container.getWidth(),
                    (container.getHeight()-leftWidth)-(bH+1));
            break;
        }

    }

}
