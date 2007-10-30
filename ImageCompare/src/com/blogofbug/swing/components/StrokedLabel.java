/*
 * StrokedLabel.java
 *
 * Created on December 7, 2006, 10:16 AM
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import javax.swing.JLabel;


/**
 * A text label that draws it's text with a fill color and an outline drawn around the outside. Created for DockPanels but useful elsewhere as well
 * @author nigel
 */
public class StrokedLabel extends JLabel {
    
    /** 
     * Creates a new instance of stroked label with no content
     */
    public StrokedLabel(){
        
    }
    
    /**
     * Creates a new instance of StrokedLabel
     * @param text The text to display in the label
     */
    public StrokedLabel(String text) {
        super(text);
    }

    /**
     * Get the prefered size of the component
     * @return The prefered size
     */
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        
        d.width+=8;
        d.height+=8;
        return d;
    }
    
    /**
     * Paints the component
     * @param g The graphics context
     */
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout tl = new TextLayout(getText(), getFont(), frc);
        float sw = (float) tl.getBounds().getWidth();
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(4, getFont().getSize()+4);
        Shape shape = tl.getOutline(transform);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);
        g2.draw(shape);

        g2.setColor(Color.WHITE);
        g2.drawString(getText(),4,getFont().getSize()+4);
    }
}
