/*
 * MapColorFilter.java
 *
 * Created on March 13, 2007, 9:16 PM
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
public class MapColorFilter extends AbstractFilter{
    protected static final String   OLD_COLOR = "old-rgba";
    protected static final String   NEW_COLOR = "new-rgba";
    /** Creates a new instance of MapColorFilter */
    public MapColorFilter() {
        attributes.add(OLD_COLOR);
        attributes.add(NEW_COLOR);
    }

    public BufferedImage apply(BufferedImage to) {
        int oldColor = getIntAttribute(OLD_COLOR,16);
        int newColor = getIntAttribute(NEW_COLOR,16);
        com.jhlabs.image.MapColorsFilter filter = new com.jhlabs.image.MapColorsFilter(oldColor,newColor);
        
        filter.filter(to,to);
        
        return to;
    }
    
}
