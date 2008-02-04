/*
 * ComponentTextEffect.java
 *
 * Created on March 31, 2007, 11:32 AM
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

import com.blogofbug.swing.components.DockPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * 
 * @author nigel
 */
public class ComponentTextEffect extends ComponentEffect implements AlphaEffect{
    private String          text    = "";
    private Color           background = Color.BLACK;
    private Color           foreground = Color.WHITE;
    private float           alpha      = 1.0f;
    private TextLocation    side       = TextLocation.SOUTH;
    
    public enum TextLocation {NORTH,SOUTH};
            
    /**
     * A font used for reference purposes when evaluating the size of the rendered component
     */
    private static final Font reference = new Font("Arial",Font.BOLD,14);
    
    /** Creates a new instance of ComponentTextEffect */
    public ComponentTextEffect(Container container, JComponent component, EffectContainer effectContainer, String text) {
        super(container,component,effectContainer);
        this.text = text;
    }

    /** Creates a new instance of ComponentTextEffect */
    public ComponentTextEffect(Container container, JComponent component, EffectContainer effectContainer, String text, TextLocation where) {
        this(container,component,effectContainer,text);
        this.side = where;
    }
    
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    /** 
     * This does nothing, the effect is always determined and painted automagically
     */
    public void initializeEffect() {

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

    
    public void paintEffect(Graphics2D graphics) {
        // Draw text if there is any...
        if ((text.length() > 0) && (alpha>0.0f)) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            Rectangle2D bounds = reference.getStringBounds(text, graphics
                    .getFontRenderContext());
            double scaleFactor = (double) theComponent.getWidth() / theComponent.getPreferredSize().getWidth();
            scaleFactor=10000.0;
            double scaleFactor2 = (double) theComponent.getWidth() / bounds.getWidth();
            int fontSize = (int) Math.min(25.0 * scaleFactor,
                    14.0 * scaleFactor2);
            Font font = new Font("Arial", Font.BOLD, fontSize);
            graphics.setFont(font);

            int red = background.getRed();
            int green = background.getRed();
            int blue = background.getRed();
            graphics.setColor(new Color(red,green,blue,96));
            FontMetrics fm = graphics.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(text,graphics);

            int dx = theComponent.getX()+(theComponent.getWidth()-(int) font.getStringBounds(text,
                    graphics.getFontRenderContext()).getWidth())/2;
            int dy = theComponent.getY()+theComponent.getHeight()+fm.getAscent()+fm.getDescent();
           
            switch (side){
                case NORTH:
                    dy = theComponent.getY()-fm.getDescent();
                    break;
            }
            
            graphics.fillRoundRect(dx-(int)rect.getHeight()/2, dy - (int) graphics.getFontMetrics().getAscent(),
                    (int)rect.getWidth()+((int)rect.getHeight()), fm.getAscent() + fm.getDescent(),(int)rect.getHeight(),(int)rect.getHeight());
            graphics.setColor(foreground);
            graphics.drawString(text, dx, dy);
        }
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha=alpha;
    }
    
    
}
