/*
 * SizingBar.java
 * 
 * Created on Sep 30, 2007, 20:13:01 PM
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

import com.blogofbug.swing.layout.SizingBarLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author nigel
 */
public class SizingBar extends JPanel{
    private static final Color  GRAB_BAR_LOWLIGHT  = new Color(0,0,0,128);
    private static final Color  GRAB_BAR_HIGHLIGHT = new Color(255,255,255,128);
    
    private SplicedPane splicedPane = null;
    private int         barHeight      = 24;
    private Color         topColor       = Color.WHITE;
    private Color         middleColor    = Color.LIGHT_GRAY;
    private Color         bottomColor    = Color.LIGHT_GRAY;
    
    public SizingBar(){
        setLayout(new SizingBarLayout(this));
    }
    
    public void setSplicedPane(SplicedPane splicePane){
        this.splicedPane = splicePane;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d =  super.getPreferredSize();
        d.height = barHeight;
        return d;
    }

    @Override
    public Dimension getMaximumSize() {
        Dimension d = super.getMaximumSize();
        d.height = barHeight;
        return d;
    }

    @Override
    public void paint(Graphics arg0) {
        super.paint(arg0);
        Color dark = new Color(0,0,0,128);
        Color light = new Color(0xFF,0xFF,0xFF,128);
        //Now draw the seperators
        for (int c=0;c<getComponentCount()-1;c++){
            Component comp = getComponent(c);
            
            int x = comp.getX()+comp.getWidth()+1;
            if (comp.getWidth()>0){
                arg0.setColor(dark);
                arg0.drawLine(x, 0, x, getHeight());
                arg0.setColor(light);
                arg0.drawLine(x+1, 0, x+1, getHeight());
            }
        }
    }

    
    @Override
    protected void paintComponent(Graphics arg0) {
        //Bit of setup
        SplicedPane.Layout splicedLayout = SplicedPane.Layout.HORIZONTAL_BOTTOM;
        if (splicedPane!=null){
            splicedLayout = splicedPane.getSpliceLayout();
        }
        
        
        Graphics2D g = (Graphics2D) arg0;

        int midPoint = getHeight()/3;
        midPoint *= 3;
        
        //Background
        GradientPaint gpTop = new GradientPaint(0.0f,0.0f,topColor,
                0.0f,(float) midPoint,middleColor,true);
        g.setPaint(gpTop);
        g.fillRect(0, 0, getWidth(), midPoint);
        
        GradientPaint gpBottom = new GradientPaint(0.0f,(float) getHeight()/2,middleColor,
                0.0f,(float) midPoint,bottomColor,true);
        g.setPaint(gpBottom);
        g.fillRect(0, midPoint, getWidth(), getHeight()/2);

        //top line
        g.setColor(bottomColor);
        g.drawLine(0, 0, getWidth(),0);
        
        int thirdHeight = getHeight()/3 - 1 ;
        
        for (int i=0;i < 3; i++){
            if (splicedLayout == SplicedPane.Layout.VERTICAL){
                int startX = (getWidth()-12) - thirdHeight;
                int endX = getWidth()-8;

                int offset = i * 4 + 2;

                g.setColor(GRAB_BAR_LOWLIGHT);
                g.drawLine(startX,thirdHeight+offset, endX, thirdHeight+offset);
                g.setColor(GRAB_BAR_HIGHLIGHT);
                g.drawLine(startX,thirdHeight+offset+1, endX, thirdHeight+offset+1);
                
            } else {
                int x = getWidth()-thirdHeight;
                x-= i*4;
                g.setColor(GRAB_BAR_LOWLIGHT);
                g.drawLine(x,thirdHeight, x, getHeight()-thirdHeight);
                g.setColor(GRAB_BAR_HIGHLIGHT);
                g.drawLine(x+1,thirdHeight, x+1, getHeight()-thirdHeight);                
            }            
        }
    }

    public int getBarHeight() {
        return barHeight;
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
    }

    public int getGrabBlockWidth() {
        return getHeight();
    }

    public void setTopColor(Color topColor) {
        this.topColor = topColor;
    }

    public void setMiddleColor(Color middleColor) {
        this.middleColor = middleColor;
    }

    public void setBottomColor(Color bottomColor) {
        this.bottomColor = bottomColor;
    }
    
}
