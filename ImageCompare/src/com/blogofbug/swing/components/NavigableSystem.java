/*
 * NavigableSystem.java
 * 
 * Created on Oct 19, 2007, 12:52:05 AM
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

/**
 *
 * @author nigel
 */
public interface NavigableSystem {
    /**
     * Gets all valid start points of the system
     * 
     * @return A list of all start points for navigation
     */
    public List<NavigationNode>     getRoots();
    
    /**
     * Gets the default start point
     * 
     * @return The default starting node
     */
    public NavigationNode           getDefaultRoot();
    
}
