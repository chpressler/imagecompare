/*
 * EffectEngine.java
 *
 * Created on March 19, 2007, 10:10 PM
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

package com.blogofbug.swing.components.effects;

import com.blogofbug.swing.SwingBugUtilities;
import com.jhlabs.composite.AddComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * Deligate implementation of an effects engine. All a component needs to do is create an instance and
 * ensure that paint() method is called by their paintComponent method.
 *
 * @author nigel
 */
public class EffectEngine implements EffectContainer, ActionListener{
     /**
     * The id for the main timer event
     */
    protected  static final int TIMER_IDENTIFIER = 1;
    /**
     * The delay used for the timer, sub-classes that want to provide a higher resolution can 
     * override this
     */
    protected  int              delay   = 20;
    /**
     * A list of the currently running effects
     */
    protected LinkedList<EffectState> effects      = new LinkedList<EffectState>();
    /**
     * A temporary list of effects that are finsihed use for clean-up after the rest as has been 
     * used.
     */
    protected LinkedList<Effect>      finishedEffects = new LinkedList<Effect>();
    
    /**
     * The timer used to trigger effects
     */
    protected Timer              timer        = new Timer(TIMER_IDENTIFIER, this);
    
    /**
     * The component the engine is attached to
     */
    protected JComponent               component = null;
    
    /** 
     * Counts the number of timers created
     */
    private static int                 timerCount = 0;
    
    /**
     * Remembers if this is a paint only engine or not
     */
    private boolean paintOnly = true;
    
    /**
     * Creates a new instance of EffectEngine that can be tied to a component.
     * @param component The component to be tied to. 
     * @param paintOnly setting this to true will create a "paint only" effects
     * layer where effects are not updated by a timer, but relys on external 
     * updates and repaints. paintOnly effect engines are preferable if possible, 
     * but please note there a defects with Swing timer that cause it to be slow
     * if you have many of them, so it is better to use the timer in the effect
     * engine if you have to
     */
    public EffectEngine(JComponent component,boolean paintOnly) {
        this.component = component;
        this.paintOnly = paintOnly;
        if (!paintOnly){
            //Start this timer if it's visible
            if (component.isVisible()){
                startTimer();
            }
            //Controls when the timer is started and stopped
            component.addComponentListener(new ComponentListener() {
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
        }
    }

    /**
     * Adds an effect to the effects engine
     * @param effect The effect to add
     */
   public void addEffect(Effect effect){
        effects.addLast(new EffectState(effect,System.currentTimeMillis()));
    }    
    
    /** 
     * Called when the comonent needs paintin'
     *
     * @param graphics The graphics context
     */
    public void paintEffects(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        
        for (EffectState effectState : effects){
            Effect effect = effectState.getTheEffect();
            
            Graphics2D effectContext=g2d;
            if (effect.isLocalEffect()){
                effectContext = (Graphics2D) g2d.create();
            } 
            
            effect.paintEffect(effectContext);
        }
    }
    
    /** 
     * Starts the timer
     */
    private void startTimer(){
        if (!paintOnly){
            SwingBugUtilities.addTimerListener(this);
        }
    }
    
    /**
     * stops the timer
     */
    private void stopTimer(){
        if (!paintOnly){
            SwingBugUtilities.removeTimerListener(this);
        }
    }
    
    /** 
     * Called when the timer fires. It updates any effects that need updating,
     * removes any that have finished, and then if at leat one has updated 
     * stimulates a repaint. 
     * 
     * @param actionEvent The action event
     */
    public void actionPerformed(ActionEvent actionEvent) {
        //Stop the timer if it's no longer valid
        if (!component.isValid()){
            stopTimer();
        }
        if (update()){
            component.repaint();
        }
    }    
    
    
    /**
     * Called when the timer fires. It updates any effects that need updating,
     * removes any that have finished, and then if at leat one has updated 
     * stimulates a repaint. 
     *
     * @return A boolean, if true, then a repaint should occur
     *
     */
    public boolean update(){
        long time = System.currentTimeMillis();
        finishedEffects.clear();
        boolean oneUpdated = false;
        
        //Update any effects that need updating
        for (EffectState effectState : effects){
            long fireNext = effectState.getFireNext();
            if (!((fireNext==Effect.EFFECT_INACTIVE) || (fireNext==Effect.EFFECT_FINISHED))){
                if (effectState.getFireNext()<time){
                    fireNext = effectState.getTheEffect().update();
                    oneUpdated = true;
                    if (fireNext==Effect.EFFECT_FINISHED){
                        finishedEffects.add(effectState.getTheEffect());
                    } else if (fireNext==Effect.EFFECT_INACTIVE){
                        effectState.setFireNext(fireNext);
                    } else {
                        effectState.setFireNext(time+fireNext);
                    }
                }
            } 
        }
        
        //Remove any finished effects
        for (Effect deadEffect : finishedEffects){
            for (EffectState effectState : effects){
                if (effectState.getTheEffect()==deadEffect){
                    effects.remove(effectState);
                    break;
                }
            }
        }
        
        //If we updated any of them, we need to fire a repaint
        if (oneUpdated){
            return true;
        } 
        
        return false;
    }    

    
    /** 
     * Determines the width of the container
     *
     * @return The width in pixels of the container
     */
    public int getWidth() {
        return component.getWidth();
    }

    /** 
     * Determines the height of the container
     * 
     * @return The height in pixels of the container
     */
    public int getHeight() {
        return component.getHeight();
    }

    /**
     * Wakes an inactive effect
     *
     * @param effect The effect to wake
     */
    public void wakeEffect(Effect effect) {
        for (EffectState effectState : effects){
            if (effectState.getTheEffect()==effect){
                effectState.setFireNext(0);
                return;
            }
        }
    }

    /**
     * Removes the effect
     *
     * @param effect The effect to remove
     */
    public void removeEffect(Effect effect) {
        for (EffectState effectState : effects){
            if (effectState.getTheEffect()==effect){
                effects.remove(effectState);
                return;
            }
        }
    }

    /** 
     * Utility class to hold the effects, and their state. 
     * 
     */
    protected class EffectState {
        /**
         * The effect itself
         */
        protected Effect    theEffect;
        
        /**
         * When the effect should fire next
         */
        protected long       fireNext;
        
        /** 
         * Constructs a new instance of the effect
         * @param effect The effect
         * @param fireNext The next time it should be updated
         */
        protected EffectState(Effect effect, long fireNext){
            theEffect = effect;
            this.fireNext = fireNext;
        }

        /**
         * Gets the time the effect should next fire.
         * @return The next fire time
         */
        public long getFireNext() {
            return fireNext;
        }

        /**
         * Gets the effect
         * @return The effect object
         */
        public Effect getTheEffect() {
            return theEffect;
        }
        
        /**
         * Sets the next fire time
         * @param fireNext The next time it should fire
         */
        public void setFireNext(long fireNext) {
            this.fireNext = fireNext;
        }

        /**
         * Sets the effect
         * @param theEffect The new effect
         */
        public void setTheEffect(Effect theEffect) {
            this.theEffect = theEffect;
        }
    }    
    
}
