/*
 * GrayScaleFilter.java
 *
 * Created on March 4, 2007, 2:04 PM
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

import com.jhlabs.image.GrayscaleFilter;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

/**
 *
 * @author nigel
 */
public class GrayScaleFilter extends AbstractFilter{
    
    /**
     * Creates a new instance of GrayScaleFilter
     */
    public GrayScaleFilter() {
    }

    public BufferedImage apply(BufferedImage to) {
        
        GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
        grayscaleFilter.filter(to,to);
        
        return to;
//        ColorSpace gray_space =
//             ColorSpace.getInstance (ColorSpace.CS_GRAY);
//          ColorConvertOp convertToGrayOp = new ColorConvertOp ( gray_space, null);
//         return convertToGrayOp.filter (to, null);
    }
    
}
