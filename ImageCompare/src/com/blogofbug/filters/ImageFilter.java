/*
 * ImageFilter.java
 *
 * Created on March 3, 2007, 9:46 AM
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
import java.util.LinkedList;

/**
 *
 * @author nigel
 */
public interface ImageFilter {
    
    /**
     * Apply the filter to image, and returns the adjusted image (which may or may not
     * be the same image depending on the filter
     *
     * @param to The image to filter
     * @return The filtered image (may be the same as to depending on the filter
     */
    BufferedImage   apply(BufferedImage to);
    
    /**
     * Gets a list of attributes that the filter can process, these should be configured on an 
     * instance using setAttribute(String key, String value)
     *
     * @return Linked list of string keys
     */
    LinkedList<String>  getAttributes();
    
    /** 
     * Gets the value of a particular attribute
     *
     * @return The value of the attribute
     */
    String  getAttribute(String attribute);
    
    /** 
     * Sts an attribute (use getAttributes to determine valid keys
     *
     * @param key The key
     * @param value The value
     */
    void setAttribute(String key, String value);
}
