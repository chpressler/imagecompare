/*
 * JAnimatedList.java
 *
 * Created on March 24, 2007, 1:06 PM
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

import com.blogofbug.swing.components.effects.BoundEffect;
import com.blogofbug.swing.components.effects.Effect;
import com.blogofbug.swing.components.effects.EffectEngine;
import com.blogofbug.swing.components.effects.EffectUtilities;
import com.blogofbug.swing.components.effects.MorphBoundedEffect;
import com.blogofbug.swing.components.effects.RoundRectangleEffect;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author nigel
 */
public class JAnimatedList extends JList implements ListSelectionListener, 
                                                    ComponentListener{
    EffectEngine        effectEngine    = new EffectEngine(this,false);
    MorphBoundedEffect  selectionWindow;
    float               selectionAlpha  = 0.0f;
    float               selectionAlphaTarget = 0.0f;

    
    /**
     * Creates a new instance of JAnimatedList
     */
    public JAnimatedList(BoundEffect cellHighlighter) {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addListSelectionListener(this);
        addComponentListener(this);
        
        selectionWindow = new MorphBoundedEffect(cellHighlighter,false);
        effectEngine.addEffect(selectionWindow);
    }

    /** Creates a new instance of EffectList */
    public JAnimatedList() {
        this(new RoundRectangleEffect(new Rectangle(0,0,0,0),new Color(0x66,0x66,0xFF),new Color(0x99,0x99,0xFF,128)));
        ((RoundRectangleEffect) selectionWindow.getEffect()).setInsets(new Insets(2,2,2,2));
    }    
    
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        selectionAlpha = EffectUtilities.easedClose(selectionAlpha,selectionAlphaTarget,8.0f);
        ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,selectionAlpha));
        effectEngine.paintEffects(graphics);
   }
    
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        if (!listSelectionEvent.getValueIsAdjusting()){
            updateSelectionHighlight();
        }
    }

    public void setSelectionMode(int i) {
        switch (i){
            case ListSelectionModel.SINGLE_SELECTION:
            case ListSelectionModel.SINGLE_INTERVAL_SELECTION:
                super.setSelectionMode(i);
                break;
            default:
                throw new UnsupportedOperationException("JAnimatedList does not" +
                        " support Multiple Interval selection models");
        }
    }


    
    public void updateSelectionHighlight(){
        int index = getSelectedIndex();
        if (index>=0){
            int lowIndex=Integer.MAX_VALUE;
            int highIndex=0;
            int selected[] = getSelectedIndices();
            for (int selection : selected){
                lowIndex = Math.min(lowIndex,selection);
                highIndex = Math.max(highIndex, selection);
            }
            selectionWindow.setBounds(getCellBounds(lowIndex,highIndex));
            selectionAlphaTarget = 1.0f;
            effectEngine.wakeEffect(selectionWindow);
        } else {
           
           Rectangle gone = getBounds();
           if (gone!=null){
               gone.x=gone.x+gone.width/2;
               gone.y=gone.y+gone.height/2;
               gone.width=0;
               gone.height=0;
               selectionAlphaTarget = 0.0f;
               selectionWindow.setBounds(gone);
               effectEngine.wakeEffect(selectionWindow);
           }
        }
    }
    
    public void componentResized(ComponentEvent componentEvent) {
            updateSelectionHighlight();
    }

    public void componentMoved(ComponentEvent componentEvent) {
            updateSelectionHighlight();
    }

    public void componentShown(ComponentEvent componentEvent) {
            updateSelectionHighlight();
    }

    public void componentHidden(ComponentEvent componentEvent) {
    }
    
}
