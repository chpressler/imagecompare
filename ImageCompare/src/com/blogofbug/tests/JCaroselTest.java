/*
 * Version:   $Revision: 1.1 $
 * Modified:  $Date: 2007/04/06 08:48:12 $
 * By:        $Author: bugfaceuk $
 */
package com.blogofbug.tests;

import com.blogofbug.examples.CaroselComponentExample;
import com.blogofbug.swing.components.ImageLabel;
import com.blogofbug.swing.components.JCarosel;
import com.blogofbug.swing.components.ReflectedImageLabel;
import com.blogofbug.utility.ImageUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author bug
 */
public class JCaroselTest {
    static public void main(String[] args) {
        //new CaroselComponentExample().setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               
                
                BufferedImage lBufferedImage = ImageUtilities.loadCompatibleImage(JCaroselTest.class.getResource("info.png").toString());
                ImageIcon     imageIcon = new ImageIcon(lBufferedImage);
                JCarosel carosel = new JCarosel();
                carosel.setBackground(Color.BLACK, Color.GRAY);
                for (int i = 0; i < 5; i++) {
                    carosel.add( new ReflectedImageLabel( lBufferedImage, "image " + i));
                }
                
                JPanel jpanel = new JPanel();
                jpanel.setName("JPanel");
                jpanel.setBorder(BorderFactory.createTitledBorder("Panel"));
                jpanel.setLayout(new BorderLayout());
                jpanel.add(new JButton("Test"),BorderLayout.CENTER);
                jpanel.validate();
                carosel.add("Panel Test",jpanel);
                
                JButton button=new JButton("Go Figure");
                button.setOpaque(false);
                button.setName("JButton");
                button.setForeground(Color.BLACK);                
                carosel.add("Button Test",button);
                
                ImageLabel imageLabel = new ImageLabel(imageIcon);
                imageLabel.setName("ImageLabel");
                carosel.add("Image Label",imageLabel);
                
                JFrame lJFrame = new JFrame();
                lJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                lJFrame.add(carosel);
                lJFrame.setSize(600,400);
                lJFrame.setVisible(true);
            }
        });
    }
}

