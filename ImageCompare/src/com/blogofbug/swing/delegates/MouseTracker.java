/*
 * MouseTracker.java
 *
 * Created on November 28, 2006, 7:49 AM
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
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

/**
 * A class which abstracts away some of the complexity of a mouse listener meaning that listeners (See MouseTrackerListener) need to implement fewer methods.
 * Furthermore, unlike a normal mouse listener it still considers the mouse to be inside 
 * even if it is over a component inside a container, and will translate the co-ordintates to be 
 * relative to the "listened to" component
 * @author nigel
 */
public class MouseTracker implements ContainerListener,MouseListener, MouseMotionListener {
    /**
     * The lisetners
     */
    protected LinkedList<MouseTrackerListener>    clients = new LinkedList<MouseTrackerListener>();
    /**
     * The component being tracked
     */
    protected Component                           component;
    /**
     * True if the mouse is inside
     */
    protected boolean                             mouseInside = false;
    /**
     * The last known position
     */
    protected Point                               position = new Point(0,0);
    
    /**
     * Adds a mouse tracker to the specified component
     *
     * @param component the component to track
     */
    public MouseTracker(Component component) {
        this.component = component;
        initialize();
    }

    /**
     * Retreives the point position, returns null if the pointer is outside the 
     * component
     *
     * @return The pointer position or null if the pointer is not inside the tracked object
     */
    public Point getPosition() {
        if (!mouseInside){
            return null;
        }
        return position;
    }

    /**
     * Determines if the mouse is inside the tracked component
     *
     * @return true if it is, false if it isn't
     */
    public boolean isMouseInside() {
        return mouseInside;
    }


    
    /**
     * Adds a mouse tracker to the specified component, and adds the specified
     * listener
     *
     * @param component The component to track
     * @param client A listener
     */
    public MouseTracker(Component component, MouseTrackerListener client){
        this(component);
        addListener(client);
    }

    /**
     * Adds a listener to the MouseTracker. The listener will get all of the mouse
     * tracker events
     *
     * @param client The listener
     */
    public void addListener(MouseTrackerListener client) {
        if (clients.contains(client)){
            return;
        }
        clients.addLast(client);
    }

    /**
     * Removes a listener from the listener list
     *
     * @param client the listener to remove from the list
     */
    public void removeListener(MouseTrackerListener client){
       clients.remove(client);
    }
    
    /**
     * Adds mouse listeners to the component. If the component is also a container
     * adds itself as a container listener so it can track added components also
     */
    private void initialize() {
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        if (component instanceof Container){
            ((Container) component).addContainerListener(this);
        }
    }

    /**
     * NOT USED
     * @param e Mouse Event
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * NOT USED
     * @param e Mouse Event
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * NOT USED
     * @param e Mouse Event
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Tracks when the mouse moves into the component
     * @param e Mouse Event
     */
    public void mouseEntered(MouseEvent e) {
        if (!mouseInside){
            mouseInside =true;
            for (MouseTrackerListener listener : clients){
                listener.mouseCrossThreshold(true);
            }
        }
    }

    /** 
     * Called when the mouse has been determined to have REALLY exited the component
     */
    protected void mouseExited(){
        mouseInside=false;
        for (MouseTrackerListener listener : clients){
            listener.mouseCrossThreshold(false);
        }        
    }
    
    /**
     * Captures the exit event and checks to see if the compnent is a container. If it's
     * not it just passes on the event, otherwise the event is only passed on if the 
     * component the mouse is now over is not contained in the container.
     * @param e Mouse Event
     */
    public void mouseExited(MouseEvent e) {
        if (mouseInside){
            if (component instanceof Container){
                if (((Container)component).getComponentAt(e.getPoint())==null){
                   mouseExited();
                }
            } else {
                if (e.getSource()==component){
                    mouseExited();
                } 
            }
        }
    }

    /**
     * NOT USED
     * @param e Mouse Event
     */
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * When the mouse moves within the component, or one of the contained componets
     * determines the point within the tracked component and passes that on to the 
     * listeners.
     * @param e Mouse Event
     */
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        //If it's not in the container, but one of the contained objects then 
        //tranlate the point to container relative co-ordinates
        
        if (e.getSource()!=component){
            Component comp = (Component) e.getSource();
            int dx = p.x;
            int dy = p.y;
            //Should be modified by where it is inside the component
            comp.getLocation(p);
            p.x+=dx;
            p.y+=dy;
        }
        
        //Notify all of the listeners
        for (MouseTrackerListener listener : clients){
            listener.mouseMoved(p);
        }
    }

    /**
     * Adds a mouseMotion listener to the added component
     * @param e The event when something is added
     */
    public void componentAdded(ContainerEvent e) {
        e.getChild().addMouseMotionListener(this);
        e.getChild().addMouseListener(this);
    }

    /**
     * Removes the mouseMotion listener from the removed component
     * @param e 
     */
    public void componentRemoved(ContainerEvent e) {
        e.getChild().removeMouseMotionListener(this);
        e.getChild().addMouseListener(this);
    }
    
}
