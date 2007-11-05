/*
 * JCarosel.java
 *
 * Created on November 22, 2006, 7:27 PM
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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import com.blogofbug.swing.SwingBugUtilities;
import com.blogofbug.swing.components.effects.ComponentEffect;
import com.blogofbug.swing.components.effects.ContainerEffectManager;
import com.blogofbug.swing.components.effects.EffectContainer;
import com.blogofbug.swing.delegates.MouseTracker;
import com.blogofbug.swing.delegates.MouseTrackerListener;
import com.blogofbug.swing.layout.CaroselLayout;
import com.blogofbug.utility.ImageUtilities;
import com.blogofbug.utility.PerformanceMonitor;

import de.fherfurt.imagecompare.swing.controller.ReflectedImageDragSource;

/**
 * A carousel component which lays out components around a carousel, moving each to the front
 * as it is clicked on. Double clicking will fire the action associated with the compnent if it has one, or give it the focus if it does not.
 *
 * Note this will be changed to only allow RichComponents in the future.
 * @author bug
 */
public class JCarosel extends GradientPanel implements  MouseListener,
        MouseWheelListener,
        ContainerEffectManager.ComponentEffectSupplier,
        KeyListener
        {
    /**
     * Set to true if the performance should be measured. Debugging purposes only
     */
    private static final boolean MEASURE_PERFORMANCE    = true;
    /**
     * Performance monitor object allowing the profiling of the rendering performance
     */
    private static PerformanceMonitor   performanceMonitor = new PerformanceMonitor("JCarousel.paint()",200);
    /**
     * The property that is set when a new component comes to the front. To use it add a property change listener for it, useful for keeping animations synchronized.
     */
    public static final String  FRONT_COMPONENT_CHANGE  = "frontComponentChanged";
    /**
     * The layout being used for the carousel
     */
    protected   CaroselLayout   layout;
    /**
     * The last component the wheel selected. Used to ensure we don't move too far around why they are wheeling and they turn back on themselves creating a nasty oscilation in the animation (everything works fine, it's just not purty)
     */
    protected   Component       lastWheeledTo = null;
    
    /**
     * The prefered width of the components in the container, everything will be scaled to this width for neutral scaling (1.0)
     */
    protected int DEFAULT_CONTENT_WIDTH = 64;
    
    /**
     * Delay in milliseconds from the first click to the start of the spin, this
     * gives implementors using a double click anywhere to fire an event a chance
     * for their users to get the double click message before the component spins
     * around.
     */
    protected int doubleClickDelay  = 200;
    protected int hoverDelay        = 1000;
    
    protected ContainerEffectManager    componentEffects=new ContainerEffectManager(this,this,true);
    
    /**
     * Creates a new instance of JCarosel
     */
    public JCarosel() {
        layout = new CaroselLayout(this, componentEffects);
        this.setLayout(layout);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        setContentWidth(DEFAULT_CONTENT_WIDTH);
    }
    
    /**
     * Returns the effect manager for the carousel
     *
     * @return The effects manager (a ConatainerEffectManager)
     */
    public ContainerEffectManager getComponentEffects() {
        return componentEffects;
    }

    /**
     * Creates a new Carousel specifying the prefered width up front
     * @param contentWidth The prefered width of component at neutral scale (3 or 9 o'clock)
     */
    public JCarosel(int contentWidth){
        this();
        setContentWidth(contentWidth);
    }
    
    /**
     * Sets the prefered width of the components inside the carousel, this is the neutral width that will change as the component is scaled, but at 9 and 6 o'clock where the scale is one they will be this width.
     * @param contentWidth The prefered width.
     */
    public void setContentWidth(int contentWidth){
        layout.setNeutralContentWidth(contentWidth);
    }
    
    /**
     * If set to true the carousel will fade out components as they move away from the front of the carousel (6 o'clock)
     * @param useDepthBased True to fade components as they move to the back, false to not do it
     */
    public void setDepthBasedAlpha(boolean useDepthBased){
        layout.setDepthBasedAlpha(useDepthBased);
    }
    
    /**
     * Specifies which type of CarouselLayout to be used to lay the component out around the carousel
     * Any looping layout can be used. Mobius strip layout anyone?
     * @param layout The carousel layout to use
     */
    public void setLayout(CaroselLayout layout){
        this.layout = layout;
        super.setLayout(layout);
    }
    
    /**
     * Adds a component to the carousel
     * @param component The component to add to the carousel
     * @return The component
     */
    public Component add(final Component component){
        //Before adding set it to an idealized size
        component.setSize(layout.getNeutralContentWidth(), 
                layout.getNeutralContentWidth());
        //Now safe to add
        add("",component);
        component.setForeground(Color.WHITE);
        component.setBackground(Color.BLACK);
        bringToFront(getComponent(0));
        validate();
        component.addMouseListener(this);
        component.addKeyListener(this);
        return component;
    }
    
    /**
     * Adds an image to the carousel
     * @param image The image to add
     * @param text The text label
     * @return The component created, normally a reflected image label
     */
    public Component add(Image image, String text) {
        ReflectedImageLabel component = new ReflectedImageLabel(image, text);
        return add(component);
    }
    
    public Component add(String name, final Component comp) {
    	comp.addMouseListener(this);
    	comp.addKeyListener(this);
    	return super.add(name, comp);
    }
    
    /**
     * Removes the component from the carousel
     * @param component The component to remove
     */
    public void remove(Component component) {
        super.remove(component);
        component.removeMouseListener(this);
        component.removeKeyListener(this);
        
        if (getComponentCount()>0){
            bringToFront(getComponent(0));
        }
        invalidate();
        validate();
    }
    
    /**
     * The image to add and it's width and height
     * @param imageURL The URL
     * @param width The desired rendering width
     * @param height The desired rendering height
     * @return The component created
     * @deprecated This function will be removed, use add(String imageURL) instead.
     */
    public Component add(String imageURL, int width, int height){
        ReflectedImageLabel component = new ReflectedImageLabel(imageURL,width,height);
        return add(component);
    }
    
    /**
     * Add the image, and it's label to the carousel
     * @param imageURL The image URL
     * @param text The text label
     * @param width The width
     * @param height The height
     * @return The component created to hold the image
     * @deprecated This function will be removed, use setNeutralWidth() on JCarousel instead.
     */
    public Component add(String imageURL, String text, int width, int height){
        ReflectedImageLabel component = new ReflectedImageLabel(imageURL,text,width,height);
        return add(component);
    }
    
    /**
     * Brings the specified component to the front of the carousel
     * @param component The component to bring to the front
     */
    public void bringToFront(Component component){
        firePropertyChange(FRONT_COMPONENT_CHANGE,getComponent(0),component);
        layout.setFrontMostComponent(component);
    }
    
    /**
     * Which component is at the front
     * @return The component at the front (by default 6 o'clock)
     */
    public Component getFrontmost(){
        return getComponent(0);
    }
    
    /**
     * Bring the "clicked" component to the front. Delays by 200ms to allow for a
     * double click
     * @param mouseEvent Brings the component clicked on by the mouse to the front
     */
    public void mouseClicked(final MouseEvent mouseEvent) {
    	if (hoverDelayTimer != null && hoverDelayTimer.isRunning()) { hoverDelayTimer.stop(); }//System.out.println("hover stop - mouse click" + mouseEvent.getSource().getClass().getName() + "/" + ((Component)mouseEvent.getSource()).getName()); }
        if (mouseEvent.getClickCount()==1){
        	if (iDoubleClickDelayTimer != null && iDoubleClickDelayTimer.isRunning()) { iDoubleClickDelayTimer.stop(); System.out.println("click stop"); }
        	iDoubleClickDelayTimer = SwingBugUtilities.invokeAfter(new Runnable() {
                public void run() {
                	System.out.println("click run");
                    bringToFront((Component) mouseEvent.getSource());
                    System.out.println(mouseEvent.getSource());
                }
            },doubleClickDelay);
        	System.out.println("click start");
        }
    }
    Timer iDoubleClickDelayTimer = null;
    
    /**
     * Sets the delay between clicking on a component in the carousel, and the
     * spin starting to move that component to the front. The longer it is, the
     * easier it is to double click on a non-front component
     *
     * @param spinStartDelay
     */
    public void setSpinStartDelay(int value) {
        this.doubleClickDelay = value;
    }
    
    /**
     * Returns the spin start delay
     *
     * @return The delay in ms between the click and the spin
     */
    public int getSpinStartDelay() {
        return doubleClickDelay;
    }
    
    
    
    /**
     * Inserts a component at the specified index
     *
     * @param i The index
     * @param comp The component
     */
    public void insertComponentAt(int i, Component comp){
        add(comp);
        layout.moveComponentTo(i,comp);
    }
    
    /**
     * Inserts a new object at a specific location
     * @param i The position on the carousel
     * @param imageURL The URL of the image
     * @param width The width of the image
     * @param height The height of the image
     * @return The component created to show the image (usually a ReflectedImageLabel but this may change)
     */
    public Component insertAt(int i,String imageURL, int width, int height){
        Component comp = add(imageURL,width,height);
        layout.moveComponentTo(i,comp);
        return comp;
    }
    
    /**
     * Inserts a new object at a specific location
     * @param i The position on the carousel
     * @param imageURL The URL of the image
     * @param text The text label
     * @param width The prefered width of the image
     * @param height The prefered height of the image
     * @return The component created to represent the image, currently reflected image label but may change
     */
    public Component insertAt(int i,String imageURL, String text, int width, int height){
        Component comp = add(imageURL,text,width,height);
        layout.moveComponentTo(i,comp);
        return comp;
    }
    
    
    /**
     * Moves everything to their final positions
     *
     */
    public void finalizeLayoutImmediately(){
        layout.layoutContainer(this);
        layout.finalizeLayoutImmediately();
        repaint();
    }
    
    /**
     * Needed to paint the highlights only after the children have been done
     *
     */
    protected void paintChildren(Graphics graphics) {
        super.paintChildren(graphics);
        int component = getComponentCount()-1;
        for (int c=component;c>-1;c--){
            Component comp = getComponent(c);
            if (comp.isVisible()){
                CarouselComponentEffect cce = (CarouselComponentEffect) this.componentEffects.getEffectFor((JComponent) comp);                
                cce.getHighlight().paintEffect((Graphics2D) graphics);
            }
        }
    }
    
    
    /**
     * Over-rides the component's paintComponent
     * @param g The graphics context
     */
    public void paintComponent(Graphics g){
        performanceMonitor.operationStarted();
        super.paintComponent(g);
        componentEffects.paintEffects(g);
        performanceMonitor.operationStopped();
        //System.out.println(performanceMonitor.generateMetrics());
    }
    
    /**
     * Not interested
     * @param mouseEvent Ignored
     */
    public void mousePressed(MouseEvent mouseEvent) {}
    
    /**
     * Not interested
     * @param mouseEvent Ignored
     */
    public void mouseReleased(MouseEvent mouseEvent) {}
    
    /**
     * We entered with the mouse,
     * if nothing happens for a X amount of time, the icon is moved to front.
     * @param mouseEvent Ignored
     */
    public void mouseEntered(final MouseEvent mouseEvent) {
    	if (mouseEvent.getSource() == getFrontmost()) return;
    	if (hoverDelayTimer != null && hoverDelayTimer.isRunning() && hoverDelayComponent != mouseEvent.getSource()) { hoverDelayTimer.stop(); } //System.out.println("hover stop - mouse enter " + mouseEvent.getSource().getClass().getName() + "/" + ((Component)mouseEvent.getSource()).getName()); }
        if (!layout.isAnimating() && (hoverDelayTimer == null || !hoverDelayTimer.isRunning()))
    	{
    		hoverDelayComponent = (Component)mouseEvent.getSource();
	    	hoverDelayTimer = SwingBugUtilities.invokeAfter(new Runnable() {
	            public void run() {
//	            	System.out.println("hover run " + mouseEvent.getSource().getClass().getName() + "/" + ((Component)mouseEvent.getSource()).getName());
	                bringToFront((Component) mouseEvent.getSource());
	            }
	        },hoverDelay);
	    	//System.out.println("hover start " + mouseEvent.getSource().getClass().getName() + "/" + ((Component)mouseEvent.getSource()).getName());
    	}
    }
	Timer hoverDelayTimer = null;
	Component hoverDelayComponent = null;
	
    /**
     * Stop the hover delay timer
     * @param mouseEvent 
     */
    public void mouseExited(MouseEvent mouseEvent){
    	if (hoverDelayTimer != null && hoverDelayTimer.isRunning()) { hoverDelayTimer.stop(); }//System.out.println("hover stop - mouse exit " + mouseEvent.getSource().getClass().getName() + "/" + ((Component)mouseEvent.getSource()).getName()); }
   	}
    
    /**
     * When event received will spin the carousel to select the next object. Because the wheel can be spun quicker than the carousel animates it keeps track of the target (so the user may have selected something three components away, althought the animation has not yet finished moving past the first comopnent)
     * @param mouseWheelEvent The event object
     */
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        
        if (mouseWheelEvent.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            int amount = mouseWheelEvent.getWheelRotation();
            if (lastWheeledTo==null){
                lastWheeledTo=getFrontmost();
            }
            int lastPosition = layout.getComponentIndex(lastWheeledTo);
            int frontMostPosition = layout.getComponentIndex(getComponent(0));
            //Don't over spin
            if (Math.abs(lastPosition-frontMostPosition)>layout.getComponentCount()/4){
                return;
            }
            if (amount > 0) {
                lastWheeledTo=layout.getPreviousComponent(lastWheeledTo);
            } else {
                lastWheeledTo=layout.getNextComponent(lastWheeledTo);
            }
            bringToFront(lastWheeledTo);
        }
    }
    
    /**
     * Adds a new image to the carousel
     *
     * @param imageURL The image
     * @return The component created
     */
    public Component add(String imageURL) {
        ReflectedImageLabel component = new ReflectedImageLabel(imageURL);
        return add(component);
    }
    
    /**
     * Adds a new image to the carousel
     *
     * @param imageURL The image
     * @param textLabel The label
     * @return The component created
     */
    public Component add(String imageURL, String textLabel) {
        ReflectedImageLabel component = new ReflectedImageLabel(ImageUtilities.loadCompatibleImage(imageURL),textLabel);
        new ReflectedImageDragSource(component, DnDConstants.ACTION_COPY);
        return add(component);
    }
    
    /**
     * Returns a RelectComponent effect for the specified object, to change the
     * exact effect, just over-ride this method
     *
     * @param forComponent The component to create the effect for
     * @param inComponent The container
     * @param effectEngine The engine creatingit
     * @return The effect, by default a ReflectComponent effect
     */
    public ComponentEffect getEffect(JComponent forComponent, Container inComponent, EffectContainer effectEngine) {
        String label = "";
        if (forComponent instanceof ReflectedImageLabel){
            label = ((ReflectedImageLabel) forComponent).getRichText();
        } else {
            if (forComponent.getToolTipText()!=null){
                label = forComponent.getToolTipText();
            } else {
                label = forComponent.getName();
                if (label==null){
                    label = "";
                }
            }
        }
        return new CarouselComponentEffect(inComponent,forComponent,effectEngine,label);
    }
    

    /** 
     * Spins the carousel based on key-presses. The left cursor keys rotates right, and the
     * right rotates left (makes sense when you look at it!). 
     *
     * @param e The key event
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) bringToFront(layout.getNextClockwise()); // left
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) bringToFront(layout.getNextAntiClockwise()); // right
    }
    
    /** 
     * Not used 
     * 
     * @param e The keyEvent
     */
    public void keyReleased(KeyEvent e) {
    }
    
    /** 
     * Not used 
     * 
     * @param e The keyEvent
     */
    public void keyTyped(KeyEvent e) {
    }

}
