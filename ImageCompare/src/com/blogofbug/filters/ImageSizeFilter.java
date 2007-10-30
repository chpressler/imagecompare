/*
 * ImageSizeFilter.java
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

/**
 *
 * @author nigel
 */
public class ImageSizeFilter extends AbstractFilter{
    public   final   String  WIDTH="width";
    public   final   String  HEIGHT="height";
    
    public ImageSizeFilter(){
        attributes.add(WIDTH);
        attributes.add(HEIGHT);
        setAttribute(WIDTH,"100%");
        setAttribute(HEIGHT,"100%");
    }
    

    public BufferedImage apply(BufferedImage to) {
        int newWidth = Math.max(1,getPercentageAttribute(WIDTH,to.getWidth()));
        int newHeight = Math.max(1,getPercentageAttribute(HEIGHT,to.getHeight()));
 
        BufferedImage   newImage = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D      g = (Graphics2D) newImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC); 
        g.drawImage(to,0,0,newWidth,newHeight,0,0,to.getWidth(),to.getHeight(),null);
        g.dispose();
        
        return newImage;
    }
    
}
