/*
 * ShadowFilter.java
 *
 * Created on March 13, 2007, 9:42 PM
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
public class ShadowFilter extends AbstractFilter{
    public static final String  RADIUS="radius";
    public static final String  X_OFFSET="x-offset";
    public static final String  Y_OFFSET="y-offset";
    public static final String  OPACITY="opacity";
    /** Creates a new instance of ShadowFilter */
    public ShadowFilter() {
        attributes.add(RADIUS);
        attributes.add(X_OFFSET);
        attributes.add(Y_OFFSET);
        attributes.add(OPACITY);
        setAttribute(RADIUS,"4.0");
        setAttribute(X_OFFSET,"1.0");
        setAttribute(Y_OFFSET,"-1.0");
        setAttribute(OPACITY,"0.5");
    }

    public BufferedImage apply(BufferedImage to) {
        
        com.jhlabs.image.ShadowFilter filter = new com.jhlabs.image.ShadowFilter(getFloatAttribute(RADIUS,1.0f),getFloatAttribute(X_OFFSET,1.0f),getFloatAttribute(Y_OFFSET,1.0f), getFloatAttribute(OPACITY,1.0f));
        
        BufferedImage shadowLayer = new BufferedImage(to.getWidth(),to.getHeight(),BufferedImage.TYPE_INT_ARGB);
        
        
        filter.filter(to,shadowLayer);
        
        Graphics2D g2d = (Graphics2D) shadowLayer.getGraphics();
        
        g2d.drawImage(to,0,0,null);
        g2d.dispose();
        
        return shadowLayer;
    }
    
}
