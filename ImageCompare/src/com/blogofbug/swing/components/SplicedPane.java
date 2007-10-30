/*
 * SplicedPane.java
 *
 * Created on September 30, 2007, 5:09 AM
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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author  nigel
 */
public class SplicedPane extends javax.swing.JPanel {
    private final int   DRAGGER_SIZE = 22;
    private Point   resizePoint = null;
    private int     leftWidth = 24;
    
    private Layout  spliceLayout = Layout.HORIZONTAL_BOTTOM;
    
    public enum Layout { HORIZONTAL_BOTTOM, HORIZONTAL_TOP, VERTICAL};
    
    /** Creates new form SplicedPane */
    public SplicedPane() {
        initComponents();
        setSizes(160);
        setLayout(new SplicedLayout(this));
        sizingBar.setSplicedPane(this);
        //Set up the button listener
        sizingBar.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {}
            public void mouseEntered(MouseEvent arg0) {}
 
            public void mouseExited(MouseEvent arg0) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(MouseEvent arg0) {}

            public void mouseReleased(MouseEvent arg0) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        //Set up the drag listener
        sizingBar.addMouseMotionListener(new MouseMotionListener() {
            //This needs to only trigger when the mouse is down in the right area
            public void mouseDragged(MouseEvent arg0) {
                if (resizePoint!=null){
                    switch (spliceLayout){
                        case HORIZONTAL_BOTTOM:
                        case HORIZONTAL_TOP:
                            {
                                int difference = arg0.getX() - resizePoint.x;
                                resizePoint = arg0.getPoint();
                                setSizes(leftWidth+difference);
                                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                            }
                            break;
                        case VERTICAL:
                            {
                                int difference = arg0.getY() - resizePoint.y;
                                resizePoint = arg0.getPoint();
                                //Need to translate the sample by the amount
                                //we are shifting. We don't get this problem with 
                                //the horizontal layout because we are not moving the
                                //reference axis (in this case the top, for horizontal 
                                //it's the left)
                                resizePoint.y -= difference;
                                setSizes(leftWidth+difference);
                                setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                            }
                            break;
                    }
                }
            }

            public void mouseMoved(MouseEvent arg0) {
                int useWidth = leftWidth;
                
                if (spliceLayout == Layout.VERTICAL){
                    useWidth = getWidth();
                }
                
                if (arg0.getX()>useWidth-DRAGGER_SIZE){
                    switch (spliceLayout){
                        case HORIZONTAL_BOTTOM:
                        case HORIZONTAL_TOP:
                            setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                            break;
                        case VERTICAL:
                            setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                            break;
                    }
                    resizePoint = arg0.getPoint();
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    resizePoint = null;
                }
            }
        });
    }
        
    public void addBarComponent(Component component){
        sizingBar.add(component);
    }
    
    public void removeBarComponent(Component component){
        sizingBar.remove(component);
    }
    
    public int getBarComponentCount(){
        return sizingBar.getComponentCount();
    }
    
    public Component getBarComponent(int at){
        return sizingBar.getComponent(at);
    }
    
    public Layout getSpliceLayout(){
        return this.spliceLayout;
    }
    
    public void setSpliceLayout(Layout layout){
        this.spliceLayout = layout;
    }
    
    public void setBarHeight(int height){
        sizingBar.setBarHeight(height);
    }
    
    public int getBarHeight(){
        return sizingBar.getBarHeight();
    }
    
    /** 
     * Sets the sizes of the split panes based on the percentage of the container
     *  size that the left hand side should occupy. 
     *  
     *  @param leftSize The size of the left hand size
     */
    public void setSizes(float leftSize){
        switch (spliceLayout){
            case HORIZONTAL_BOTTOM:
            case HORIZONTAL_TOP:
                int l_leftWidth = (int) ( (float) getWidth() * leftSize );
                setSizes(l_leftWidth);
                break;
            case VERTICAL:
                l_leftWidth = (int) ( (float) getHeight() * leftSize );
                setSizes(l_leftWidth);
                break;
        }
    }

    /**
     * Over draws with the seperator lines
     * 
     * @param g Graphics context
     */
    //*
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        switch (spliceLayout){
            case HORIZONTAL_BOTTOM:
            case HORIZONTAL_TOP:
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(leftWidth-1, 0, leftWidth-1, getHeight());
                break;
            case VERTICAL:
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, leftWidth, getWidth(), leftWidth);
                break;
        }
        
    }
    //*/
    
    /**
     * Gets the width of the left pane
     * 
     *  @return The left hand side size
     */
    public int getLeftWidth() {
        return leftWidth;
    }
    
    public void setSecondComponent(JComponent secondComponent){
        if (this.secondComponent!=null){
           remove(this.secondComponent);
        }
        
        this.secondComponent = secondComponent;
        add(secondComponent);
        setComponentZOrder(secondComponent, 0);
        invalidate();
        validate();
    }
    
    public void setFirstComponent(JComponent firstComponent){
        if (this.firstComponent!=null){
            remove(this.firstComponent);
        }
        
        this.firstComponent = firstComponent;        
        add(firstComponent);
        setComponentZOrder(firstComponent, getComponentCount()-1);
        invalidate();
        validate();
    }
    
    public Component getFirstComponent(){
        return firstComponent;
    }
    
    public Component getSecondComponent(){
        return secondComponent;
    }
    
    public SizingBar getSizingBar(){
        return sizingBar;
    }
    
    public void setSizes(int leftWidth){
        this.leftWidth = leftWidth;
        this.invalidate();
        this.validate();
    }
    
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        JPanel first = new javax.swing.JPanel();
        JPanel second = new javax.swing.JPanel();
        sizingBar = new SizingBar();

        setLayout(new java.awt.GridBagLayout());

        first.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(first, gridBagConstraints);

        second.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(second, gridBagConstraints);

        setFirstComponent(first);
        setSecondComponent(second);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(sizingBar, gridBagConstraints);
    }
    
    // Variables declaration - do not modify
    private Component firstComponent;
    private Component secondComponent;
    private SizingBar sizingBar;
    // End of variables declaration
    
}
