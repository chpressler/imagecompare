/*
 * NavigationNode.java
 * 
 * Created on Oct 19, 2007, 02:50:25 AM
 * 
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

package com.blogofbug.swing.components;

import java.util.List;
import javax.swing.Icon;

/**
 *
 * @author nigel
 */
public interface NavigationNode {
    /**
     *  Determine if the node has children or not
     * 
     *  @return true if it has
     */
    public boolean                  isLeaf();
    
    /**
     * Determines if the node has a parent node or not.
     * 
     * @return true if it has a parent, false if it does not
     */
    public boolean                  isChild();
    
    /**
     *  Determine if the navigator is allowed to visit the node
     *
     *  @return true if they can
     */
    public boolean                  isVisitable();
    
    /**
     * Gets all of the children for the node. 
     * 
     * @return A list of the children 
     */
    public List<NavigationNode>     getChildren();
    
    
    /**
     * Gets the named child of this node
     * 
     * @param name The name of the child
     * @return The child node or null if it cannot be found
     */
    public NavigationNode           getChild(String name);
    
    /**
     *  Gets the parent node of this node
     * 
     *  @return The parent node, or null if it does not have one
     */
    public NavigationNode           getParent();
    
    /**
     *  Gets the name of the node
     * 
     *  @return The name of the node
     */ 
    public String                   getName();
    
    /**
     *  Gets an appropriate icon for the node, or null if none available
     * 
     * @return An icon for the node
     */
    public Icon                   getIcon();
    
    /**
     * Gets the description of the node
     * 
     * @return The description of the node in a string
     */
    public String                   getDescription();
    
    /**
     *  If the node has a single object representing it, it can return
     * it with this method. However, there is no obligation to do this, 
     * so the function may always return null
     * 
     * @return The object
     */
    public Object                   getObject();
}
