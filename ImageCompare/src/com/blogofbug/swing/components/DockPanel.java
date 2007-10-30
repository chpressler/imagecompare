/*
 * DockPanel.java
 *
 * Created on January 9, 2007, 6:22 AM
 *
 * Re-implementation of the dock to use a grid-bag-laid-out panel instead
 * of the very complicated code I was developing.
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

import com.blogofbug.swing.SwingBugUtilities;
import com.blogofbug.swing.components.effects.ComponentEffect;
import com.blogofbug.swing.components.effects.ComponentTextEffect;
import com.blogofbug.swing.components.effects.ContainerEffectManager;
import com.blogofbug.swing.components.effects.EffectContainer;
import com.blogofbug.swing.delegates.MouseTracker;
import com.blogofbug.swing.delegates.MouseTrackerListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Insets;

/**
 * A dock that can be added to the glass pane of a frame
 * @author nigel
 */
public class DockPanel extends JPanel implements MouseTrackerListener, ActionListener, 
                                        ContainerEffectManager.ComponentEffectSupplier{
    /**
     * Enumeration object to contain the side of the screen/window the dock is against
     *
     */
    public enum       Side {
        /**
         * Lock dock to top of the window
         */
        NORTH, 
        /**
         * Lock the dock to the bottom of the window
         */
        SOUTH, 
        /**
         * Locks the dock to the right of the dock
         */
        EAST, 
        /**
         * Lock the dock to the left of the window
         */
        WEST};
    
    //The enlarged size of the icons
    /**
     * The "mouse over" size of a dock entry
     */
    private int       enlargedSize = 96;
    
    //The normal size of the icons
    /**
     * The "mouse out" size
     */
    private int       normalSize   = 32;
    
    //Insets around each dock icon
    /**
     * The insets around the dock
     */
    private int       insets       = 2;
    
    //The side the dock is docked to
    /**
     * The frame the dock is associated with
     */
    private Side      dockedTo     = Side.SOUTH;
    
    //Autohide?
    /**
     * Should the dock automatically hide when the mouse is outside of the area
     */
    private boolean   autoHide     = false;
    
    //Auto-hiding!
    /**
     * True if the dock is currently being hidden
     */
    private boolean   hiding       = false;
    
    //Layout and standard constraints
    /**
     * The layout being used for the container
     */
    private GridBagLayout       layout = new GridBagLayout();
    /**
     * The constraint for the filler objects
     */
    private GridBagConstraints  filler = new GridBagConstraints();
    /**
     * The constraints for the title object
     */
    private GridBagConstraints  title  = new GridBagConstraints();
    /**
     * The constraints for an icon in the dock...
     */
    private GridBagConstraints  icon   = new GridBagConstraints();
    
    /**
     * Spacer object above the dock
     */
    protected JSpacer              spacer; ;
    /**
     * Spacer on the "left" of the dock
     */
    protected JSpacer              firstSpacer; ;
    /**
     * Spacer on the right of the dock
     */
    protected JSpacer              lastSpacer;
    
    /**
     * Controls whether or not autohiding is already on a trigger
     */
    protected boolean               delayedAutoHide = false;
    
    /** 
     * Determines current in/out status
     */
    protected boolean               couldAutoHide = false;
    
    /**
     * Hold index of components and their titles
     */
    private Hashtable<Component,String>   iconLabels = new Hashtable<Component,String>();
    
    
    /**
     * Track mouse movements (will be over the entire component)
     */
    private MouseTracker        mouseTracker;
    
    
    /**
     * Animation timer
     */
    //private Timer               timer = new Timer(0,this);
    /**
     * Records in an animation is currently planning
     */
    private boolean             animating = false;
    
    //Mouse location (last known)
    /**
     * Last known location of the mouse
     */
    private Point            lastKnownMouse = new Point(0,0);
    
    private boolean          returnToNormal     = true;
    private boolean          overDockComponent  = false;
    private boolean          normalTimerRunning = false;
    
    /** 
     * Effects for the dock components
     */
    protected ContainerEffectManager    effects = 
                                        new ContainerEffectManager(this,this,true);
    
    /**
     * Debug mode
     */
    private static final boolean        DEBUG_MODE = true;
    
    /**
     * Creates a new instance of DockPanel
     * @param normalSize The normal size of the dock icons
     * @param enlargedSize The "mouse over" size of the dock icons
     * @param dockedTo The side the dock is attached to
     */
    public DockPanel(int normalSize, int enlargedSize, Side dockedTo) {
        //Set up the basics
        this.dockedTo = dockedTo;
        this.enlargedSize = enlargedSize;
        this.normalSize = normalSize;
        
        //Prepare the timer
//        timer.setCoalesce(true);
//        timer.setDelay(20);
//        timer.setRepeats(true);
//        timer.start();
        startTimer();
        addComponentListener(new ComponentListener() {
            public void componentHidden(ComponentEvent componentEvent) {
                stopTimer();
            }
            public void componentMoved(ComponentEvent componentEvent) {
            }
            public void componentResized(ComponentEvent componentEvent) {
            }
            public void componentShown(ComponentEvent componentEvent) {
                startTimer();
            }
        });
        
        //make me transparent
        setBackground(null);
        setOpaque(false);
        
        //Set up the mouse tracker
        mouseTracker = new MouseTracker(this,this);
        
        //Set up the spacers
        spacer = new JSpacer();
        firstSpacer = new JSpacer();
        lastSpacer = new JSpacer();
        
        //Initial setup
        setLayout(layout);
        initializeLayout();
        add(spacer,filler);
        add(firstSpacer,icon);
        add(lastSpacer,icon);
        
        //Perform initial layout
        updateLayout(null);
    }
    
    private void startTimer(){
        SwingBugUtilities.addTimerListener(this);
    }
    
    private void stopTimer(){
        SwingBugUtilities.removeTimerListener(this);
    }
    
    /**
     * Determines if the dock is currently hiding (or hidden)
     * @return True if it is auto-hiding...
     */
    public boolean isAutoHiding(){
        return autoHide;
    }
    
    /**
     * Turns auto-hiding on or off
     * @param autoHide True to turn it on, false to turn it off
     */
    public void setAutoHiding(boolean autoHide){
        this.autoHide = autoHide;
        mouseMoved(lastKnownMouse);
    }
    
    /**
     * Creates a new instance of the dock which will attach to the south of the frame
     * @param normalSize The normal size of the icon
     * @param enlargedSize The mouse-over size of icons in the dock
     */
    public DockPanel(int normalSize, int enlargedSize){
        this(normalSize,enlargedSize,Side.SOUTH);
    }
    
    /**
     * Sets the normal size of an icon in the dock
     * @param normalSize The normal size. 
     */
    public void setNormalSize(int normalSize){
        this.normalSize = normalSize;
        updateLayout(null);
        layout.layoutContainer(this);
    }
    
    /**
     * Sets the mouse-over size of an icon in the dock
     * @param enlargedSize The enlarged size
     */
    public void setEnlargedSize(int enlargedSize){
        this.enlargedSize = enlargedSize;
        updateLayout(null);
        layout.layoutContainer(this);
    }
    
    /**
     * Sets up the basic layout and then calls updateLayout to finalize the
     * individual dock elements
     */
    private void initializeLayout(){
        //Standard settings for the filler panel
        filler.fill = GridBagConstraints.BOTH;
        filler.weightx = 1.0;
        filler.weighty = 1.0;
        
        //Standard settings for the title components
        title.anchor = GridBagConstraints.CENTER;
        
        //Standard settings for the icon components
        icon.anchor = GridBagConstraints.CENTER;
        icon.weightx = 0.0;
        icon.weighty = 0.0;
        icon.insets = new Insets(insets/2,insets/2,insets/2,insets/2);
        
        //Do various bits of specific constraints layout
        switch (dockedTo){
            case SOUTH:
                filler.gridx = 0;
                filler.gridy = 0;
                filler.gridheight =1;
                filler.gridwidth = GridBagConstraints.REMAINDER;
                title.gridx  = 0;
                title.gridy = 1;
                icon.gridx = 0;
                icon.gridy = 2;
                icon.anchor = GridBagConstraints.SOUTH;
                break;
            case NORTH:
                filler.gridx = 0;
                filler.gridy = 2;
                filler.gridheight =1;
                filler.gridwidth = GridBagConstraints.REMAINDER;
                title.gridx  = 0;
                title.gridy = 1;
                icon.gridx = 0;
                icon.gridy = 0;
                icon.anchor = GridBagConstraints.NORTH;
                break;
            case WEST:
                filler.gridx = 2;
                filler.gridy = 0;
                filler.gridheight =GridBagConstraints.REMAINDER;
                filler.gridwidth = 1;
                title.gridx  = 1;
                title.gridy = 0;              
                icon.gridx = 0;
                icon.gridy = 0;
                icon.anchor = GridBagConstraints.WEST;
                break;
            case EAST:
                filler.gridx = 0;
                filler.gridy = 0;
                filler.gridheight =GridBagConstraints.REMAINDER;
                filler.gridwidth = 1;
                title.gridx  = 1;
                title.gridy = 0;
                icon.gridx = 2;
                icon.gridy = 0;
                icon.anchor = GridBagConstraints.EAST;
                break;
        }
    }
    
    /**
     * Changes the side of the panel the dock is attached to
     * @param dockedTo Determines the side of the frame the dock is connected to 
     */
    public void setDockedTo(DockPanel.Side dockedTo) {
        this.dockedTo = dockedTo;
        initializeLayout();
        layout.setConstraints(spacer,filler);
        updateLayout(null);
        layout.layoutContainer(this);
    }
    
    /**
     * Determines the appropriate gridbag weight for the "book-end" spacers
     * at either end of the dock
     *
     * @return The weight
     */
    private double spacerWeightX(){
        switch (dockedTo){
            case NORTH:
            case SOUTH:
                return 1.0;
            case EAST:
            case WEST:
                return 0.0;
        }
        return 0.0;
    }
    
    /**
     * Determines the appropriate gridbag weight for the "book-end" spacers
     * at either end of the dock
     *
     * @return The weight
     */
    private double spacerWeightY(){
        return 1.0-spacerWeightX();
    }
    
    /**
     * Determines the appropriate size for the component based on the current "mouse over"
     * component, the location in the dock area relative to the overall dock panel, and a given component
     * This is protected so it can be over-ridden to apply a better alogrithm .
     *
     * @param p The location of the mouse relative to the overall dock
     * @param component The component to be sized
     * @param highlightedComponent The current component with the mouse over it
     * @return A Dimension object with the recommened size of the component
     */
    protected Dimension getComponentPreferedSize(Point p, Component component, Component highlightedComponent){
        //If we are hiding it should be small
        if (hiding){
            return new Dimension(insets*3,insets*3);
        }

        //determine it's distance based on half the size of the biggest component
        double compSize = component.getSize().height;
        double distanceFromCenter = 0;
        double delta = (double) (enlargedSize-normalSize);
                     
        if ((highlightedComponent!=null)){
            switch (dockedTo){
                case NORTH:
                case SOUTH:
                    distanceFromCenter = Math.abs((double) ((component.getLocation().x + compSize/2 )- p.x));
                    break;
                case EAST:
                case WEST:
                    distanceFromCenter = Math.abs((double) ((component.getLocation().y + compSize/2 )- p.y));
                    break;
            }

            
//            if (highlightedComponent==component){
            if (distanceFromCenter<enlargedSize/2){
                return new Dimension(enlargedSize,enlargedSize);
            }

            double cdy = 1.0 - distanceFromCenter/(double) (enlargedSize*2);
            double tSize = Math.max((double) normalSize + cdy * delta,(double) normalSize);
            int    size = (int) tSize;
            
            return new Dimension(size,size);
        } else {
            if (returnToNormal){
                return new Dimension(normalSize,normalSize);                
            } else {
                return component.getPreferredSize();
            }
        }
    }
    
    /**
     * Essentially removes the spacer that forces the dock to the bottom of the
     * panel, neat if you would like to use the panel as something like a
     * special tab top.
     *
     * @param fillSpace true if the panel should fill up any empty space above the dock,
     * false if it should not
     */
    public void fillSpace(boolean fillSpace){
        spacer.setVisible(fillSpace);
    }
    
    /**
     * Determines a value between the current and target including easing. Can
     * be over-ridden if desired to have different sizing behavior. Implementers 
     * should note that if the size is not clipped to ensure that the icons will
     * all fit in the dock, gridbag does Bad Things (tm). 
     *
     * @param current The current value
     * @param target  The target value
     * @return A value equal to or between target and current
     */
    protected double tweenValue(double current, double target){
        //Just make sure we do nothing if they are the same
        if (current==target){
            return target;
        }
        
        //Determine the difference
        double delta = (target-current)/8.0;
        
        //Tween in the right direction, but always by at least one pixel
        if (delta>0){
            delta = Math.max(1.0,delta);
        } else if (delta<0){
            delta = Math.min(-1.0,delta);
        }
        
        switch (dockedTo){
            case SOUTH:
            case NORTH:
                return Math.min(current+delta,( getWidth()-insets*iconLabels.size()*2)/iconLabels.size()-10);
            default:
                return Math.min(current+delta,(getHeight()-insets*iconLabels.size()*2)/iconLabels.size());
        }
    }
    
    /**
     * Determines if a component is in the dock
     * @param component The component you wish to test to see if its in the dock
     * @return True if it does, false if it doesn't
     */
    public boolean dockContains(Component component){
        if (iconLabels.get(component)!=null){
            return true;
        }
        return false;
    }
    
    /**
     * Takes a current size, and determines a new size setting up an animation
     * if needed.
     *
     * @param currentSize The current size of the component
     * @param newSize     The new size of the component
     * @return The in-between size
     */
    private Dimension tweenSize(Dimension currentSize, Dimension newSize){
        if ((newSize.width != currentSize.width) || (newSize.height != currentSize.height)){
            animating = true;
            newSize.width = (int) tweenValue(currentSize.width,newSize.width);
            newSize.height = (int) tweenValue(currentSize.height,newSize.height);
        }
        return newSize;
    }

    /**
     * 
     *
     */
    private void setReturnToNormal(){
        if (returnToNormal){
            return;
        }
        normalTimerRunning=true;
        SwingBugUtilities.invokeAfter(new Runnable() {
            public void run() {
                if (!overDockComponent){
                    returnToNormal=true;
                    normalTimerRunning = false;
                    validate();
                }
            }
        },100);
    }
    
    /** 
     * Debug method
     * 
     * @param component The component
     */
    protected void showComponentLabel(JComponent component,String text){
        //Debugging information
        if (DEBUG_MODE){
            ComponentEffect ce = effects.getEffectFor((JComponent) component);
            if ((ce!=null) && (ce instanceof ComponentTextEffect)){
                ((ComponentTextEffect) ce).setText(text);
            }
        }                
    }
    
    
    /**
     * Determines the new settings for the gridbag layout, and then applies them
     * to all components
     * @param p The point the mouse is at
     */
    private void updateLayout(Point p){
        Component highlightedComponent = null;
        animating = false;
        if (p!=null){
            highlightedComponent = getComponentAt(p);
            //Make sure it's at least in the dock zone
            switch (dockedTo){
                case NORTH:
                    if (p.y>spacer.getY()){
                        highlightedComponent=null;
                    }
                    break;
                case SOUTH:
                    if (p.y<spacer.getHeight()){
                        highlightedComponent=null;
                    }
                    break;
                case WEST:
                    if (p.x>spacer.getX()){
                        highlightedComponent=null;
                    }
                    break;
                case EAST:
                    if (p.x<spacer.getWidth()){
                        highlightedComponent=null;
                    }
                    break;
            }
            if ((highlightedComponent==firstSpacer) || (highlightedComponent==lastSpacer)){
                highlightedComponent = null;
            }
        }
        //Variables used for incrementing grid-x's and grid-y's
        int dx=0,dy=0;
        switch (dockedTo){
            case NORTH:
            case SOUTH:
                dx=1;
                break;
            case EAST:
            case WEST:
                dy=1;
                break;
        }
        
        //Set everything up as it should be
        initializeLayout();
        
        //Add first spacer and move to next position
        icon.weightx = spacerWeightX();
        icon.weighty = spacerWeightY();
        icon.fill = GridBagConstraints.BOTH;
        layout.setConstraints(firstSpacer,icon);
        icon.fill = GridBagConstraints.NONE;
        icon.gridx += dx;
        icon.gridy += dy;
        title.gridx += dx;
        title.gridy += dy;
        
        if (highlightedComponent==null){
            setReturnToNormal();
            overDockComponent=false;
        } else {
            overDockComponent = true;
            returnToNormal=false;
        }

            
        //Iterate through the components updating everything
        Component[] components = getComponents();
        for (Component component : components){
            //Make sure it's not a filler or a title
            if (!((component == spacer) || (component instanceof DockLabel) || (component == firstSpacer) || (component == lastSpacer))){
                //Set the weight
                icon.weightx = 0.0;
                icon.weighty = 0.0;
                //Icon first
                layout.setConstraints(component, icon);
                
                //Set the prefered size of the component
                component.setPreferredSize(tweenSize(component.getSize(),getComponentPreferedSize(p,component,highlightedComponent)));
                
                //Set the visibility of its label
                if (highlightedComponent==component){
                    showComponentLabel((JComponent) component, iconLabels.get(component));
                } else {
                    showComponentLabel((JComponent) component, "");
                }
                                
                //Move to next position
                icon.gridx += dx;
                icon.gridy += dy;
                title.gridx += dx;
                title.gridy += dy;
            }
        }
        
        //Add the last spacer
        icon.weightx = spacerWeightX();
        icon.weighty = spacerWeightY();
        icon.fill = GridBagConstraints.BOTH;
        layout.setConstraints(lastSpacer,icon);
    }
    
    /**
     * Adds a new item to the dock
     * @param component The component to add
     * @param label The label to display when the mouse is over the dock item
     */
    public void addDockElement(Component component, String label){
        if (iconLabels.get(component)!=null){
            return;
        }
                
        //We would like it to do something funcky as it adds them and make them grow
        component.setPreferredSize(new Dimension(0,0));
        iconLabels.put(component,label);
        add(component);
        updateLayout(mouseTracker.getPosition());
    }
    
    /**
     * Not interested
     * @param mouseEntered True if the mouse came in 
     */
    public void mouseCrossThreshold(boolean mouseEntered) {
    }
    
    /** 
     * Fires an auto-hide check after a delay to ensure the mouse didn't just
     * stray out of the dock on the way from one place to another. 
     */
    protected void delayedAutoHide(){
        if (delayedAutoHide){
            return;
        }
        delayedAutoHide=true;
        SwingBugUtilities.invokeAfter(new Runnable() {
            public void run() {
                hiding = couldAutoHide;
                if (hiding){
                    validate();
                }
                delayedAutoHide=false;
            }
        },1000);             
    }
    
    /**
     * When the mouse moves, update the layout. Should be optimized when the
     * highlighted component is the spacer to not do anything
     * @param position The location of the mouse
     */
    public void mouseMoved(Point position) {
        if (autoHide){
            Component mouseOver = getComponentAt(position);
            final Component self = this;
            if ((mouseOver == firstSpacer) || (mouseOver == lastSpacer) || (spacer==mouseOver) || (mouseOver == this)){
                couldAutoHide=true;
                if (hiding == false){
                    delayedAutoHide();
                }
            } else {
                hiding = false;
                couldAutoHide=false;
            }
        } else {
            hiding = false;
            couldAutoHide = false;
        }
        lastKnownMouse = position;
        updateLayout(position);
        layout.layoutContainer(this);
    }
    
    /**
     * Returns true if the mouse is over a dock component, but false at all other times. This will cause
     * swing to pass on the event to the next layer down.
     * @param x The frame relative dock location x-cordination
     * @param y The frame relative dock location y-coordinate
     * @return True if the mouse is over a dock icon, false otherwise
     */
    public boolean contains(int x, int y) {
        Rectangle rect=new Rectangle();
        //Have to implement our own componentAt here
        for (Component comp : getComponents()){
            rect=comp.getBounds(rect);
            if (rect.contains(x,y)){
                if (comp instanceof JSpacer){
//                    System.out.println("Out");
                    mouseMoved(new Point(-1,-1));
                    return false;
                }
//                System.out.println("In");
                return true;
            }
        }
        //A little non-intuitive. If it's not over a normal component or a spacer, it's in the dock area
        return true;
    }    

    protected void paintChildren(Graphics graphics) {
        super.paintChildren(graphics);
        if (DEBUG_MODE){
            effects.paintEffects(graphics);
        }
    }

    
    /**
     * Paints the component.
     *
     * @param graphics The graphics context
     */
    public void paintComponent(Graphics graphics) {
        graphics.setColor(new Color(0x89,0x5e,0x96,127));
        Rectangle dockSize = null;
        int oldNormalSize=normalSize;
        if (hiding){
            normalSize = Math.min(normalSize, ((dockedTo==Side.NORTH) || (dockedTo==Side.SOUTH)) ? firstSpacer.getHeight(): firstSpacer.getWidth());
        }
        //Draw the main dock background
        switch (dockedTo){
            case NORTH:
                dockSize = new Rectangle(firstSpacer.getX()+firstSpacer.getWidth(),0,getWidth()-(lastSpacer.getWidth()+firstSpacer.getWidth()),normalSize);
                break;
            case SOUTH:
                dockSize = new Rectangle(firstSpacer.getX()+firstSpacer.getWidth(),getHeight()-normalSize,getWidth()-(lastSpacer.getWidth()+firstSpacer.getWidth()),normalSize);
                break;
            case WEST:
                dockSize = new Rectangle(0,firstSpacer.getHeight(),normalSize,getHeight()-(lastSpacer.getHeight()+firstSpacer.getHeight()));               
                break;
            case EAST:
                dockSize = new Rectangle(getWidth()-normalSize,firstSpacer.getHeight(),normalSize,getHeight()-(lastSpacer.getHeight()+firstSpacer.getHeight()));
                break;
        }
        if (hiding){
            normalSize = oldNormalSize;
        }
        
        dockSize.x-=insets;
        dockSize.y-=insets;
        dockSize.width+=insets*2;
        dockSize.height+=insets*2;
        graphics.fillRect(dockSize.x,dockSize.y,dockSize.width,dockSize.height);
        
        
        //Draw the outline
        graphics.setColor(new Color(255,255,255,127));
        switch (dockedTo){
            case SOUTH:
                graphics.drawRect(dockSize.x,dockSize.y,dockSize.width-1,dockSize.height);
                break;
        }
        super.paintComponent(graphics);
    }
    
    /**
     * Called when the animation timer fires
     * @param actionEvent The action event
     */
    public void actionPerformed(ActionEvent actionEvent) {
        if (animating){
            updateLayout(lastKnownMouse);
            layout.layoutContainer(this);
        }
    }

    public ComponentEffect getEffect(JComponent forComponent, Container inComponent, EffectContainer effectEngine) {
        if (this.DEBUG_MODE){
            if (forComponent instanceof JSpacer){
                return null;
            } else {
                return new ComponentTextEffect(inComponent,forComponent,effectEngine, 
                        "", ComponentTextEffect.TextLocation.NORTH);
            }
        } 
        return null;
    }
    
    //For convenience
    /**
     * A utilitity class to fill the gaps around the dock contents
     */
    public class JSpacer extends JComponent {
        /**
         * Creates a new instance of the dock spacer
         */
        public JSpacer(){
            setBackground(null);
            setOpaque(false);
        }

        
        
    }
    
    //Temporary until I do something else
    /**
     * A utiliity class to represent the label of the currently mouse-over dock item
     */
    public class DockLabel extends StrokedLabel {
        /**
         * Creats a new instance of the dock label
         * @param title The text of the label
         */
        public DockLabel(String title){
            super(title);
        }
        
    };
}

