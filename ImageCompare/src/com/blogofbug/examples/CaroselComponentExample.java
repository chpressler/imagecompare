/*
 * CaroselLayoutManager.java
 *
 * Created on December 29, 2006, 12:04 PM
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

import com.blogofbug.swing.components.JCarosel;
import com.blogofbug.swing.layout.CaroselLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author bug
 */
public class CaroselComponentExample extends JFrame{
    
    /** Creates a new instance of CaroselLayoutManager */
    public CaroselComponentExample() {
        super("Carosel Component");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,400);
        getContentPane().setLayout(new BorderLayout());
        JCarosel    carosel = new JCarosel();
        carosel.setBackground(Color.BLACK,Color.DARK_GRAY);
        getContentPane().add(carosel);
        carosel.add("Label Example",new JLabel("Example with text"));
        carosel.add("Button Example", new JButton("Oh, and a button too!"));
        carosel.add("Text Field", new JTextField("Edit me!"));
        carosel.add("Image example",new JLabel(new ImageIcon(CaroselLayoutManager.class.getResource("/com/blogofbug/examples/images/Acknowledgements.png"))));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CaroselLayoutManager().setVisible(true);
            }
        });
    }    
}
