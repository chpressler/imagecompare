/*
 * JBookPanelTest.java
 *
 * Created on April 5, 2007, 3:38 PM
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

package com.blogofbug.tests;

import com.blogofbug.examples.TimerTestPanel;
import com.blogofbug.examples.bookdemopages.FrontPage;
import com.blogofbug.examples.bookdemopages.NastyPage;
import com.blogofbug.examples.bookdemopages.TimerIntro;
import com.blogofbug.swing.components.ImageLabel;
import com.blogofbug.swing.components.ReflectedImageLabel;
import com.blogofbug.swing.components.incubator.JBookPanel;
import com.blogofbug.utility.ImageUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 *
 * @author nigel
 */
public class JBookPanelTest {
    static public void main(String[] args) {
        //new CaroselComponentExample().setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               
                
                BufferedImage lBufferedImage = ImageUtilities.loadCompatibleImage(JCaroselTest.class.getResource("/com/blogofbug/examples/images/swingpp.png").toString());
                ImageIcon     imageIcon = new ImageIcon(lBufferedImage);
                JBookPanel    book = new JBookPanel();
                
                LinkedList<JComponent> components = new LinkedList<JComponent>();

                components.add(new FrontPage());
                
                components.add(new ReflectedImageLabel(lBufferedImage,"Reflected Image"));

                components.add(new TimerIntro());
                
                components.add(new NastyTestPanel());                
                
                
                components.add(new NastyPage());
                
                components.add(new TimerTestPanel());
                
                                                              
                book.setPages(components.toArray(new JComponent[]{}),256,314);
                JFrame lJFrame = new JFrame();
                lJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                lJFrame.getContentPane().setLayout(new BorderLayout());
                                
                lJFrame.getContentPane().add(book,BorderLayout.CENTER);
                lJFrame.setSize(600,380);
                lJFrame.setVisible(true);
            }
        });
    }
}
