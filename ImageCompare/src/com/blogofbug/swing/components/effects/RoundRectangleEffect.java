/*
 * RoundRectangleEffect.java
 *
 * Created on March 25, 2007, 9:48 PM
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 *
 * @author nigel
 */
public class RoundRectangleEffect implements BoundEffect{
    protected Rectangle bounds;
    protected Color     foreground = Color.WHITE;
    protected Color     background = new Color(0,0,0,128);
    protected Insets    insets     = new Insets(0,0,0,0);
    
    /** Creates a new instance of RoundRectangleEffect */
    public RoundRectangleEffect(Rectangle bounds) {
        this.bounds=bounds;
    }

    /** Creates a new instance of RoundRectangleEffect */
    public RoundRectangleEffect(Rectangle bounds, Color foreground, Color background) {
        this.bounds=bounds;
        setForeground(foreground);
        setBackground(background);
    }    

    public void  setInsets(Insets insets){
        this.insets = (Insets) insets.clone();
    }
    
    
    
    public Color getBackground() {
        return background;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }    
    
    public boolean isLocalEffect() {
        return true;
    }

    public void paintEffect(Graphics2D graphics) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(foreground);
        graphics.setStroke(new BasicStroke(2.0f));
        int round = Math.min(bounds.height,bounds.width)/4;
        Rectangle   bounds2 = new Rectangle(bounds.x+insets.left,bounds.y+insets.top,bounds.width-insets.left-insets.right,
                bounds.height-insets.top-insets.bottom);
        graphics.drawRoundRect(bounds2.x,bounds2.y,bounds2.width-1,bounds2.height,round,round);
        graphics.setColor(background);
        graphics.fillRoundRect(bounds2.x,bounds2.y,bounds2.width-1,bounds2.height,round,round);
    }

    public long update() {
        return EFFECT_INACTIVE;
    }

    public void setBounds(Rectangle rectangle) {
        this.bounds = rectangle;
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }
    
}
