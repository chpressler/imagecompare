/*
 * DefaultNodeViewer.java
 * 
 * Created on Oct 20, 2007, 00:10:50 AM
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JLabel;

/**
 *
 * @author nigel
 */
public class DefaultNodeViewer extends JLabel implements PropertyChangeListener{
    private     SplicedNavigator    splicedDirectory = null;
    protected static final String[] fileSizeUnits = {"bytes","KB","MB","GB","TB","PB"};
    
    
    public DefaultNodeViewer(SplicedNavigator  splicedDirectory){
        this.splicedDirectory = splicedDirectory;
        setVerticalTextPosition(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setHorizontalAlignment(JLabel.CENTER);
        setBackground(Color.WHITE);
        setText("<html>Nothing Selected");
        setOpaque(true);
        setMinimumSize(new Dimension(180,100)); 
        splicedDirectory.addPropertyChangeListener(SplicedNavigator.SELECTED_NODE,this);
    }
    
    /** 
     * Returns true if the supplied indiciates it could be an image that 
     * can be included in a web page
     * 
     * @param name The name of the object
     * @return true if it is a jpeg, gif, or png
     */
    protected boolean isWebImage(String name){
        name = name.toLowerCase();
        if (name.endsWith("jpg")){
            return true;
        } else if (name.endsWith("gif")){
            return true;
        } else if (name.endsWith("png")){
            return true;
        }
        return false;
    }
    
    /**
     * Converts a long into a string in the form of (X X X) MB or GB or whatever the
     * appropriate unit is
     * 
     * @param size The size of the object in bytes
     * @return A string containing the normalized form
     */
    protected String getFileSize(long size){
        int i=0;
        while (size>999){
            i++;
            size/=1024;
        }
        
        return Long.toString(size)+" "+fileSizeUnits[i];
    }
    
    /**
     *  Respond to the selection change by updating the text in the label
     * 
     * @param event The event, expected to be SELECTED_NODE
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(SplicedNavigator.SELECTED_NODE)){            
            String text = "<html>";
            NavigationNode  node = (NavigationNode) event.getNewValue();
            
            if (node==null){
                text = "Nothing Selected";
            } else {
                if (node.getObject() instanceof File){
                    File file = (File) node.getObject();
                    if (isWebImage(node.getName())){
                        text+="<center><img src=\"file://"+file.getAbsolutePath()+"\" " +
                                "width=\"120\" height=\"120\" > </center><br>";
                    }
                    text+="<b>Name: </b>"+node.getName()+"<br>";
                    text+="<b>Size: </b>"+getFileSize(file.length())+"<br>";
                    
                } else {
                    text+="<b>Name: </b>"+node.getName()+"<br>";
                    text+="<b>Description: </b>"+node.getDescription()+"<br>";                       
                }
            }
            setText(text);
        }
    }

}
