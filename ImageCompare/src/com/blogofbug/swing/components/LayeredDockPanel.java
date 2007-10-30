/*
 * GlassDock.java
 *
 * Created on January 6, 2007, 10:21 AM
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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * A dock that is designed to go into a Frame's layered pane so that elements like combobox menus can appear over the top
 * @author nigel
 */
public class LayeredDockPanel extends DockPanel implements ComponentListener{
    /**
     * The layer (in the layered panel) that the dock should be drawn into
     */
    public static final Integer     DOCK_LAYER = new Integer(250);
    /**
     * The frame the dock is in
     */
    private JFrame frame = null;
    /**
     * Creates a new instance of GlassDock
     * @param frame The frame the dock will be drawn into
     */
    public LayeredDockPanel(JFrame frame) {
        super(48,96);
        this.frame = frame;
        frame.addComponentListener(this);
    }        

    /**
     * Called when the containing frame has been resized so the dock can resize
     * @param componentEvent The event
     */
    public void componentResized(ComponentEvent componentEvent) {
        GridBagLayout gbl = new GridBagLayout();
        setSize(frame.getContentPane().getSize());
        setLocation(0,0);
        validate();
    }

    /**
     * Ignored
     * @param componentEvent The event   
     */
    public void componentMoved(ComponentEvent componentEvent) {
    }

    /**
     * Ignored
     * @param componentEvent The event
     */
    public void componentShown(ComponentEvent componentEvent) {
    }

    /**
     * Ignored
     * @param componentEvent The event
     */
    public void componentHidden(ComponentEvent componentEvent) {
    }
    
      
}
