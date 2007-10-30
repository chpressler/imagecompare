/*
 * GaussianBlurFilter.java
 *
 * Created on March 5, 2007, 11:51 AM
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

import java.awt.image.BufferedImage;

/**
 *
 * @author nigel
 */
public class GaussianBlurFilter extends AbstractFilter{
    
    public static final String  RADIUS = "radius";
    
    /** Creates a new instance of GaussianBlurFilter */
    public GaussianBlurFilter() {
        attributes.add(RADIUS);
        setAttribute(RADIUS,"3.0");
    }

    public BufferedImage apply(BufferedImage to) {
        com.jhlabs.image.GaussianFilter filter = new com.jhlabs.image.GaussianFilter(getFloatAttribute(RADIUS,1.0f));
        filter.filter(to,to);
        
        return to;        

    }
    
}
