/*
 * CaroselFinal.java
 *
 * Created on December 29, 2006, 10:57 AM
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

package com.blogofbug.examples;

import com.blogofbug.examples.yahooimagesearch.YahooImageSearch;
import com.blogofbug.swing.components.ImageLabel;
import com.blogofbug.swing.components.JCarosel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author  nigel
 */
public class CaroselFinal extends javax.swing.JFrame {
    
    /** Creates new form CaroselFinal */
    public CaroselFinal() {
        initComponents();
        JCarosel carosel = new JCarosel(128);
        carosel.setBackground(Color.BLACK,Color.DARK_GRAY);
        getContentPane().add(carosel, BorderLayout.CENTER);
        setSize(600,400);
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/bug256x256.png").toString(),"Swing-Bug");
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/swingpp.png").toString(),"Duke's Den");
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/Full Carousel.png").toString(),"JCarousel 0.4");
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/Cascade.png").toString(),"Cascade Demonstration");
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/Dock.png").toString(),"Docks Rock");
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/Quit.png").toString(),"Quit It Bozo");    
        carosel.add(CaroselFinal.class.getResource("/com/blogofbug/examples/images/Acknowledgements.png").toString(),"Thanks to You!");    
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("JCarosel Final Demo");
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CaroselFinal().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
