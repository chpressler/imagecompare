/*
 * MouseTrackerDebugDelegate.java
 *
 * Created on November 28, 2006, 8:44 AM
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

import java.awt.Component;
import java.awt.Point;

/**
 * Simple delegate the acts as a mouse tracker listener printing out the events as they arrive, to save you doing it.
 * @author nigel
 */
public class MouseTrackerDebugDelegate implements MouseTrackerListener{
    
    /**
     * Creates a new instance of MouseTrackerDebugDelegate
     * @param component The component to be tracked
     */
    public MouseTrackerDebugDelegate(Component component) {
        new MouseTracker(component,this);
    }

    /**
     * When called, prints on the status of the mouse-in/out
     * @param mouseEntered True if the mosue is inside
     */
    public void mouseCrossThreshold(boolean mouseEntered) {
        System.out.println("Mouse moved in: "+mouseEntered);
    }

    /**
     * Mouse moves within the container
     * @param position The location of the mouse pointer relative to the tracked component
     */
    public void mouseMoved(Point position) {
        System.out.println("Mouse moved: "+position.x+", "+position.y);
    }
    
}
