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
import java.awt.image.BufferedImage;

/**
 *
 * @author nigel
 */
public class CanvasSizeFilter extends AbstractFilter{
    protected   final   String  WIDTH="width";
    protected   final   String  HEIGHT="height";
    protected   final   String  X="x";
    protected   final   String  Y="y";
    
    public CanvasSizeFilter(){
        attributes.add(WIDTH);
        attributes.add(HEIGHT);
        attributes.add(X);
        attributes.add(Y);
    }
    


    public BufferedImage apply(BufferedImage to) {
        int newWidth = getPercentageAttribute(WIDTH, to.getWidth());
        int newHeight = getPercentageAttribute(HEIGHT, to.getHeight());
        int x = getIntAttribute(X);
        int y = getIntAttribute(Y);
        
        if (newWidth==0){
            newWidth=to.getWidth();
        }
        
        if (newHeight==0){
            newHeight=to.getHeight();
        }
 
        BufferedImage   newImage = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D      g = (Graphics2D) newImage.getGraphics();
        g.drawImage(to,x,y,null);
        g.dispose();
        
        return newImage;
    }
    
}
