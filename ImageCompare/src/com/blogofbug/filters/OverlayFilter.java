/*
 * CanvasSizeFilter.java
 *
 * Created on March 3, 2007, 4:43 PM
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

package com.blogofbug.filters;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import de.fherfurt.imagecompare.ResourceHandler;

/**
 *
 * @author nigel
 */
public class OverlayFilter extends AbstractFilter{
    protected   final   String  SOURCE="image";
    protected   final   String  X="x";
    protected   final   String  Y="y";
    protected   final   String  MATCH_SIZE="match-size";
    
    protected   BufferedImage   overlayImage= null;
    
    public OverlayFilter(){
        attributes.add(X);
        attributes.add(Y);
        attributes.add(SOURCE);
        attributes.add(MATCH_SIZE);
    }
    

    public BufferedImage apply(BufferedImage to) {
        int x = getIntAttribute(X);
        int y = getIntAttribute(Y);
        
        Graphics2D      g = (Graphics2D) to.getGraphics();
        if ("true".equals(getAttribute(MATCH_SIZE))){
            g.drawImage(overlayImage,0,0,to.getWidth(),to.getHeight(),null);
        } else {
            g.drawImage(overlayImage,x,y,null);            
        }
        g.dispose();
        
        return to;
    }

    
    public void setAttribute(String key, String value) {
        super.setAttribute(key,value);
        if (SOURCE.equals(key)){    
            try {
                overlayImage = ImageIO.read(new File(value));                 
            } catch (IOException ex) {
            	JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + ex.getMessage());
                ex.printStackTrace();
            }    
        }
    }
    
}
