/*
 * BloomFilter.java
 *
 * Created on March 30, 2007, 7:43 AM
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

import com.blogofbug.filters.AbstractFilter;
import com.blogofbug.utility.ImageUtilities;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.MapColorsFilter;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author nigel
 */
public class BloomFilter extends AbstractFilter{
    public   final   String  THRESHOLD="threshold";
    public   final   String  RADIUS="radius";
    public   final   String  BLUR_ITERATIONS="blur-count";
    public   final   String  BLOOM_ONLY="bloom-only";
    
    /** Creates a new instance of BloomFilter */
    public BloomFilter() {
        declareAttribute(THRESHOLD,"140");
        declareAttribute(RADIUS,"20.0");
        declareAttribute(BLUR_ITERATIONS,"1");
        declareAttribute(BLOOM_ONLY,"no");
    }
    
    public BufferedImage apply(BufferedImage to) {
        BufferedImage copy = ImageUtilities.copyImage(to);
        
        com.jhlabs.image.ThresholdFilter threshold = new com.jhlabs.image.ThresholdFilter(getIntAttribute(THRESHOLD));
        threshold.filter(copy,copy);
        RemoveColorFilter mapColors = new RemoveColorFilter();
        mapColors.filter(copy,copy);
        int iterations = getIntAttribute(BLUR_ITERATIONS);
        
        
        GaussianFilter blur = new GaussianFilter(getFloatAttribute(RADIUS,5.0f));
        for (int i=0;i<iterations;i++){
            blur.filter(copy,copy);
        }

        mapColors.setMode(RemoveColorFilter.UN);
        mapColors.filter(copy,copy);
     
        if (getAttribute(BLOOM_ONLY).equals("no")){
            Graphics g = to.createGraphics();
            g.drawImage(copy,0,0,null);
            g.dispose();
            return to;
        } else {
            return copy;
        }
    }
    
    public class RemoveColorFilter extends MapColorsFilter{
        private int removeColor = 0xFFFFFFFF;
        private int mode        = 0;
        private static final int MASK = 0;
        private static final int PRE = 1;
        private static final int UN  = 2;


        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }
        

        public int unMultiplyComponent(int color, float alpha){
            return (int) ((255.0f * (float) color - 127.0f)/alpha);
            
            //return (int) ((float) color / alpha);
        }
        
        public int preMultiplyComponent(int color, float alpha){
            return (int) ((alpha * (float) color + 127.0f)/255.0f);
            
            
            //return (int)((float) color * alpha);
        }

        public int unMultiplyRGB(int rgb){
            int r = (rgb >> 16) & 0xff;
            int g = (rgb >> 8) & 0xff;
            int b = rgb & 0xff;
            
            int a = b;
            return (a << 24) | 0x00FFFFFF;
//            return a | (unMultiplyComponent(r,alpha) << 16) | (unMultiplyComponent(g,alpha) << 8) | unMultiplyComponent(b,alpha);
        }        
        
        public int preMultiplyRGB(int rgb){
            int a = rgb & 0xff000000;
            int r = (rgb >> 16) & 0xff;
            int g = (rgb >> 8) & 0xff;
            int b = rgb & 0xff;
            
            float alpha = (float) (a >> 24) /255.0f;
            System.out.println("A:"+(a >> 24)+" alpha: "+alpha);
            
            return a | (preMultiplyComponent(r,alpha) << 16) | (preMultiplyComponent(g,alpha) << 8) | preMultiplyComponent(b,alpha);
        }
        
        public int filterRGB(int x, int y, int rgb) {
            switch (mode){
                case MASK:
                        if ((rgb & 0x00FFFFFF)!= (removeColor & 0x00FFFFFF))
                            //return removeColor & 0x00FFFFFF;
                            return 0xFF000000;
                        return 0xFFFFFFFF;
                case PRE:
                    return preMultiplyRGB(rgb);
                case UN: 
                    return unMultiplyRGB(rgb);
            }
            return 0xFFFF0000;
        }
    }
    
}
