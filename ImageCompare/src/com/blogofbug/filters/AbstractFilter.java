/*
 * AbstractFilter.java
 *
 * Created on March 3, 2007, 11:03 AM
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

import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author nigel
 */
public abstract class AbstractFilter implements ImageFilter{
    public static final String              TYPE="type";
    protected LinkedList<String>            attributes = new LinkedList<String>();
    protected Hashtable<String,String>      currentAttributes = new Hashtable<String,String>();
    
    public void declareAttribute(String key, String defaultValue){
        attributes.add(key);
        currentAttributes.put(key,defaultValue);
    }
    
    public String getAttribute(String key){
        String value = currentAttributes.get(key);
        if (value==null){
            value="";
        }
        return value;
    }

    public int getPercentageAttribute(String key,int currentValue){
        String value= getAttribute(key);
        if (!value.endsWith("%")){
            return getIntAttribute(key);
        }
        value=value.substring(0,value.length()-1);
        try{
            float newValue = (float) Integer.parseInt(value) / 100.0f;
            return (int) (newValue * currentValue);
        } catch (NumberFormatException nfe){
            return 1;
        }
    }    
    
    public LinkedList<String> getAttributes() {
        LinkedList<String> publicAttributes = (LinkedList<String>)attributes.clone();
        publicAttributes.add(TYPE);
        return publicAttributes;
    }

    public float getFloatAttribute(String key, float defaultValue){
        String attributeValue = getAttribute(key);
        if (attributeValue==null){
            return defaultValue;
        }
        try{
            return Float.parseFloat(attributeValue);
        } catch (NumberFormatException nfe){
            return 1.0f;
        }
    }
    
    public int getIntAttribute(String key, int radix){
        String attributeValue = getAttribute(key);
        if (attributeValue==null){
            return 0;
        }
        try{
        return Integer.parseInt(attributeValue,radix);
        } catch (NumberFormatException nfe){
            return 0;
        }
    }    
    
    public int getIntAttribute(String key){
        return getIntAttribute(key,10);
    }

    public void setAttribute(String key, String value) {
        currentAttributes.put(key,value);
    }    
    
}
