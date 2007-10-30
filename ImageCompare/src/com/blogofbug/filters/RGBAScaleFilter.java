/*
 * RGBAScaleFilter.java
 *
 * Created on March 3, 2007, 11:02 AM
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 *
 * @author nigel
 */
public class RGBAScaleFilter extends AbstractFilter{
    public static final String A = "a";
    public static final String B = "b";
    public static final String R = "r";
    public static final String G = "g";
    
    
    public RGBAScaleFilter(){
        attributes.add(A);        
        attributes.add(R);        
        attributes.add(G);        
        attributes.add(B);  
        setAttribute(A,"50%");
        setAttribute(R,"0%");
        setAttribute(G,"0%");
        setAttribute(B,"100%");
    }
    
    public float scaleAttribute(String key){
       /* Create a rescale filter op that makes the image 50% opaque */
       float opacity = 1.0f;
       String opacityValue = currentAttributes.get(key);
       if (opacityValue!=null){
           if (opacityValue.endsWith("%")){
               opacityValue=opacityValue.substring(0,opacityValue.length()-1);
               int levelOpacity = Integer.parseInt(opacityValue);
               opacity = (float) levelOpacity / 100.0f;
               
           } else {
               int levelOpacity = 256;
               try{
                levelOpacity = Integer.parseInt(opacityValue);
               } catch (NumberFormatException nfe){
                   
               }
               opacity = (float) levelOpacity / 256.0f;
           }
       } 
       return opacity;
    }
    
    public BufferedImage apply(BufferedImage to) {
       BufferedImage bi = new BufferedImage(to.getWidth(), to.getHeight(), BufferedImage.TYPE_INT_ARGB);
       Graphics g = bi.getGraphics();

       float[] scales = { scaleAttribute(R), scaleAttribute(G), scaleAttribute(B), scaleAttribute(A) };
       float[] offsets = new float[4];
       RescaleOp rop = new RescaleOp(scales, offsets, null);

       /* Draw the image, applying the filter */
       ((Graphics2D)g).drawImage(to, rop, 0, 0);
       
       g.dispose();
       return bi;
        
    }    
    
}
