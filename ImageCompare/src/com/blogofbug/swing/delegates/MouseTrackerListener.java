/*
 * MouseTrackerListener.java
 *
 * Created on November 28, 2006, 7:50 AM
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

package com.blogofbug.swing.delegates;

import java.awt.Point;

/**
 * Implemented by objects wanting to respond to mouse tracker events, the interface provides
 * a lighter-weight version of the java mouse events.
 * @author nigel
 */
public interface MouseTrackerListener {
    
    /**
     * Called by a mouse tracker when the mouse moves inside the associated component
     *
     * @param mouseEntered True if it's entering, false if it's leaving
     */
    public void mouseCrossThreshold(boolean mouseEntered);
    
    
    /**
     * Called by a MouseTracker when the mouse moves, always in component local co-ordinates
     *
     * @param position The position the mouse is in relative to the component
     */
    public void mouseMoved(Point position);
}
