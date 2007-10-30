/*
 * SwingBugUtilities.java
 *
 * Created on March 30, 2007, 12:27 AM
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

package com.blogofbug.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.Timer;

/**
 * Contains some utility methods applicable to any swing application. 
 *
 * @author nigel
 */
public class SwingBugUtilities{
    
    private static Timer    timer;
        
    /** Creates a new instance of SwingBugUtilities */
    private SwingBugUtilities() {
    }

    private static void createTimer(){
        if (timer!=null){
            return;
        }
        timer = new Timer(20,new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
        timer.setDelay(20);
        timer.setInitialDelay(20);
        timer.setCoalesce(true);
        timer.setRepeats(true);
        timer.start();
    }
    
    public static boolean isTimerListening(ActionListener testListener){
        createTimer();
        ActionListener[] listeners = timer.getActionListeners();
        for (ActionListener candidate : listeners){
            if (candidate == testListener){
                return true;
            }
        }
        return false;
    }
    
    public static void addTimerListener(ActionListener newListener){
        if (!isTimerListening(newListener)){
            timer.addActionListener(newListener);
            reportListeners();
        }    
    }
    
    public static int getTimerCount(){
        if (timer==null){
            return 0;
        }
        return timer.getActionListeners().length;
    }
    
    public static void reportListeners(){
        System.out.println(timer.getActionListeners().length);
    }
    
    public static void removeTimerListener(ActionListener oldListener){
        timer.removeActionListener(oldListener);
        reportListeners();
    }
    
    /** 
     * Runs the supplied class after a certain period of time, the thread
     * will be executed in the EDT. 
     *
     * @param execute The runnable object whose method will be called after the
     * specified delay
     * @param after The delay in ms before the event will be called
     */
    public static Timer invokeAfter(final Runnable execute, int after){
        Timer timer = new Timer(after,new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                execute.run();
            }
        });
        timer.setRepeats(false);
        timer.start();
        return timer;
    }

    
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
        }
        
    }
}
