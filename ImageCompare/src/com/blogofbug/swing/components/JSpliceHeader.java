/*
 * JSpliceHeader.java
 * 
 * Created on Oct 19, 2007, 1:05:16 AM
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;

public class JSpliceHeader extends JLabel {
    private     int         forcedHeight=20;
    private     boolean     drawTopLine = false;
    private     boolean     drawBottomLine = true;
    
    public JSpliceHeader() {
        super();
        setOpaque(false);
        setBackground(null);
        setHorizontalTextPosition(JLabel.CENTER);
        setHorizontalAlignment(JLabel.CENTER);
    }

    public int getForcedHeight() {
        return forcedHeight;
    }

    public void setForcedHeight(int forcedHeight) {
        this.forcedHeight = forcedHeight;
    }

    public void setDrawTopLine(boolean drawTopLine) {
        this.drawTopLine = drawTopLine;
    }

    public void setDrawBottomLine(boolean drawBottomLine) {
        this.drawBottomLine = drawBottomLine;
    }
    
    public boolean getDrawBottomLine(){
        return this.drawBottomLine;
    }
    
    public boolean getDrawTopLine(){
        return this.drawTopLine;
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.height = forcedHeight;
        return d;
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        Graphics2D g = (Graphics2D) arg0;
        int midPoint = getHeight() / 3;
        midPoint *= 3;
        GradientPaint gpTop = new GradientPaint(0.0F, 0.0F, Color.WHITE, 0.0F, (float) midPoint, Color.LIGHT_GRAY, true);
        g.setPaint(gpTop);
        g.fillRect(0, 0, getWidth(), midPoint);
        GradientPaint gpBottom = new GradientPaint(0.0F, (float) getHeight() / 2, Color.LIGHT_GRAY, 0.0F, (float) midPoint, Color.LIGHT_GRAY, true);
        g.setPaint(gpBottom);
        g.fillRect(0, midPoint, getWidth(), getHeight() / 2);
        if (drawBottomLine){
            g.setColor(new Color(160, 160, 160));
            g.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
        }
        super.paintComponent(arg0);
        if (drawTopLine){
            g.setColor(new Color(160, 160, 160));
            g.drawLine(0, 1, getWidth() - 1, 1);
        }
    }
}
