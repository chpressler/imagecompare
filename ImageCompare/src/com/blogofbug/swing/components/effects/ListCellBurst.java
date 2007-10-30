/*
 * ListCellBurst.java
 *
 * Created on March 20, 2007, 4:27 PM
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

import com.blogofbug.utility.ImageUtilities;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

/**
 * Adds a burst effect to a list cell, useful when they are selected. WARNING: This
 * effect is likely to be refactored to allow multiple effects to be applied to a 
 * list cell element.
 * @author nigel
 */
public class ListCellBurst extends AlphaBurst implements Effect{
    
    /**
     * Creates a new instance of ListCellBurst
     * @param list The list
     * @param index The index to burst
     */
    public ListCellBurst(JList list, int index) {
        super(getListCellImage(list,index), getListCellCenter(list,index));
    }
    
    /**
     * Gets the bounds of a list cell in the context of its parent container and
     * taking into account the viewport of the scroll pane IF the list is inside
     * on. 
     *
     * @param list The list
     * @param index The index of the cell
     *
     * @return The rectangle of the bounds
     */
    protected static Rectangle        getListCellBounds(JList list, int index){
        ListModel model = list.getModel();
        Rectangle cellBounds = list.getCellBounds(index,index);

        if (list == null){
            return new Rectangle(0,0,1,1);
        }
        
        if (list.getTopLevelAncestor()==null){
            return new Rectangle(0,0,1,1);
        }
        
        Point tlc = null;
        try{
            Method method = list.getTopLevelAncestor().getClass().getMethod("getContentPane");
            if (method!=null){
                Component component = (Component) method.invoke(list.getTopLevelAncestor());
                tlc = component.getLocationOnScreen();
            } else {
                return new Rectangle(0,0,1,1);
            }
        } catch (Exception e){
            return new Rectangle(0,0,1,1);
        }
        Point tl  = list.getLocationOnScreen();
                
        if (list.getParent() instanceof JViewport){
            tl = list.getParent().getParent().getLocationOnScreen();
            JViewport viewPort = (JViewport) list.getParent();
            cellBounds.x-=viewPort.getViewPosition().getX();
            cellBounds.y-=viewPort.getViewPosition().getY();
        }
        
        cellBounds.x = cellBounds.x + (tl.x-tlc.x);
        cellBounds.y = cellBounds.y + (tl.y-tlc.y);
     
        return cellBounds;
    }
    
    /**
     * Gets the centre of the list cell item
     * @param list The list
     * @param index The item to find the center of
     * @return A point indicating the centre of the cell based on the lists top level anceestor
     */
    protected static Point getListCellCenter(JList list, int index){
        Rectangle bounds = getListCellBounds(list,index);
        return new Point(bounds.x+bounds.width/2,bounds.y+bounds.height/2);
    }
    
    /**
     * Gets the image for a list cell
     * @param list The list cell
     * @param index The index of the list cell 
     * @return The image
     */
    protected static BufferedImage    getListCellImage(JList list, int index){ 
        Rectangle cellBounds = getListCellBounds(list,index);
        ListCellRenderer    cellRenderer = list.getCellRenderer();
        JComponent cell = (JComponent) cellRenderer.getListCellRendererComponent(list,list.getModel().getElementAt(index),index,true,false);
        cell.setSize(cellBounds.width,cellBounds.height);
        return ImageUtilities.renderComponentToImage(cell);
    }
    
}
