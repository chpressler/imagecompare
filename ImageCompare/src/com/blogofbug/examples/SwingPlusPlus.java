/*
 * SwingPlusPlus.java
 *
 * Created on March 27, 2007, 10:58 PM
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

import com.blogofbug.swing.SwingBugUtilities;
import com.blogofbug.swing.components.EffectsPanel;
import com.blogofbug.swing.components.GradientPanel;
import com.blogofbug.swing.components.effects.Effect;
import com.blogofbug.swing.components.effects.ListCellBurst;
import com.blogofbug.swing.components.effects.ParticleEffect;
import com.blogofbug.swing.components.effects.ReflectComponent;
import com.blogofbug.swing.components.effects.ReflectEffect;
import com.blogofbug.swing.delegates.MouseTracker;
import com.blogofbug.swing.delegates.MouseTrackerListener;
import com.blogofbug.utility.ImageUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author  nigel
 */
public class SwingPlusPlus extends javax.swing.JFrame implements MouseTrackerListener{
    EffectsPanel    effectsPanel = new EffectsPanel(false);
    BufferedImage   sparkle      = ImageUtilities.loadCompatibleImage(SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/glowing_particle.png").toString());
    BufferedImage   sparkleSmall      = ImageUtilities.loadCompatibleImage(SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/glowing_particle_small.png").toString());
    ReflectComponent listReflection;

    DefaultListModel    model = new DefaultListModel();
    /** Creates new form SwingPlusPlus */
    public SwingPlusPlus() {
        //Do some initial tweaking
        setGlassPane(effectsPanel);
        effectsPanel.setVisible(true);
        GradientPanel gradPanel = new GradientPanel();
        gradPanel.setBackground(Color.BLACK,Color.DARK_GRAY);
        this.setContentPane(gradPanel);
        
        //Netbeans normal setup
        initComponents();
        
        //Change the cell renderer etc
        list.setCellRenderer(new ExampleCellRender());
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        model.addElement("Java Desktop");
        model.addElement("Rules");
        model.addElement("The");
        model.addElement("Cross Platform");
        model.addElement("Desktop");
        model.addElement("World");
        model.addElement("and");
        model.addElement("Does More");
        model.addElement("Than You Might");
        model.addElement("Think");
        model.addElement("Love, Bug");

        listActions.addAction(new ClearListSelectionAction());
        listActions.addAction(new AddListItem());
        listActions.addAction(new RemoveListItem());
        
        setSize(600,400);
        jPanel2.setBackground(null);
        listReflection= new ReflectComponent(this.getContentPane(),jPanel1,effectsPanel);
        effectsPanel.addEffect(listReflection);
        setupReflectionListeners();
        new MouseTracker(this.getContentPane()).addListener(this);
    }
    
    public void setupReflectionListeners(){
        this.addComponentListener(new ComponentListener() {
            public void componentHidden(ComponentEvent componentEvent) {
            }
            public void componentMoved(ComponentEvent componentEvent) {
            }
            public void componentResized(ComponentEvent componentEvent) {
                listReflection.reinitializeEffect();
                getGlassPane().repaint();
            }
            public void componentShown(ComponentEvent componentEvent) {
                listReflection.reinitializeEffect();
                getGlassPane().repaint();
            }
        });
        
                
        jScrollPane1.getViewport().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                listReflection.reinitializeEffect();
                getGlassPane().repaint();
            }
        });
        
        mouseTrailOn.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                listReflection.reinitializeEffect();
                getGlassPane().repaint();
            }
        });
        
        final JList menuList = list;
        menuList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (!listSelectionEvent.getValueIsAdjusting()){
                    effectsPanel.addEffect(new ListCellBurst(menuList,menuList.getSelectedIndex()));
                }
                SwingBugUtilities.invokeAfter(new Runnable() {
                    public void run() {
                        listReflection.reinitializeEffect();
                        getGlassPane().repaint();
                    }
                },500);
            }
        });        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JLabel jLabel1;

        jPanel1 = new javax.swing.JPanel();
        listActions = new com.blogofbug.swing.components.JMultiButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new com.blogofbug.swing.components.JAnimatedList();
        jLabel2 = new javax.swing.JLabel();
        mouseTrailOn = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(296, 300));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 300));
        listActions.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel1.add(listActions, gridBagConstraints);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("List Action");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(list);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 4, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("<html>Select items in the list to see a selection effect");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 4, 10);
        jPanel1.add(jLabel2, gridBagConstraints);

        mouseTrailOn.setBackground(new java.awt.Color(0, 0, 0));
        mouseTrailOn.setForeground(new java.awt.Color(255, 255, 255));
        mouseTrailOn.setSelected(true);
        mouseTrailOn.setText("Mouse Trail Effect");
        mouseTrailOn.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mouseTrailOn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        mouseTrailOn.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 10, 4, 10);
        jPanel1.add(mouseTrailOn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setMinimumSize(new java.awt.Dimension(10, 100));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public class ClearListSelectionAction extends AbstractAction{
        public ClearListSelectionAction(){
            putValue(Action.NAME,"Clear Selection");
            putValue(Action.SMALL_ICON,new ImageIcon(SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/glowing_particle.png")));
        }

        public void actionPerformed(ActionEvent actionEvent) {
            list.clearSelection();
        }
    }

    public class RemoveListItem extends AbstractAction{
        public RemoveListItem(){
            putValue(Action.NAME,"Remove List Item");
            putValue(Action.SMALL_ICON, new ImageIcon(SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/glowing_particle.png")));
        }

        public void actionPerformed(ActionEvent actionEvent) {
            int index = list.getSelectedIndex();
            if (index>=0){
                ((DefaultListModel) list.getModel()).removeElementAt(index);
            }
            if (model.getSize()==0){
                return;
            }
            list.setSelectedIndex(Math.min(model.size()-1,index));
        }
    }    
    
    public class AddListItem extends AbstractAction{
        public AddListItem(){
            putValue(Action.NAME,"Add Item");
            putValue(Action.SMALL_ICON, new ImageIcon(SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/glowing_particle.png")));
        }

        public void actionPerformed(ActionEvent actionEvent) {
            ((DefaultListModel) list.getModel()).addElement("A new item");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SwingPlusPlus().setVisible(true);
            }
        });
    }

    public void mouseCrossThreshold(boolean mouseEntered) {
    }

    public void mouseMoved(Point position) {
//        System.out.println(this.getComponentAt(position));
        if (mouseTrailOn.isSelected()){
            Effect theEffect = null;
            if (Math.random()>0.5){
                theEffect = new ParticleEffect(sparkleSmall,(int)position.getX(),(int)position.getY(),40,(effectsPanel.getHeight()/3)*2);
            } else {
                theEffect = new ParticleEffect(sparkle,(int) position.getX(),(int)position.getY(),40,(effectsPanel.getHeight()/3)*2);
            }   
            
            effectsPanel.addEffect(new ReflectEffect(theEffect,effectsPanel));
        }
    }
    
    public class SimpleCellRenderer extends JLabel implements ListCellRenderer{
        public SimpleCellRenderer(){
            setOpaque(true);
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        }

        public Component getListCellRendererComponent(JList jList, Object object, int i, boolean b, boolean b0) {
            setText((String) object);
            if (i % 2==1){
                setBackground(new Color (0xEE,0xEE,0xEE));
            } else {
                setBackground(Color.WHITE);
            }
            return this;
        }
    }
    
    
    public class ExampleCellRender extends JLabel implements ListCellRenderer{
            ImageCache images = new ImageCache();
            public ExampleCellRender(){
                setOpaque(true);
                setHorizontalTextPosition(JLabel.LEFT);
                setHorizontalAlignment(JLabel.RIGHT);
                images.put("Java Desktop",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Quit.png").toString()),32,32)
                            )
                        );
                images.put("Rules",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Dock.png").toString()),32,32)
                            )
                        );
                images.put("The",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Quit.png").toString()),32,32)
                            )
                        );
                images.put("Cross Platform",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Dock.png").toString()),32,32)
                            )
                        );
                images.put("Desktop",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Quit.png").toString()),32,32)
                            )
                        );
                images.put("World",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Dock.png").toString()),32,32)
                            )
                        );
                images.put("and",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Acknowledgements.png").toString()),32,32)
                            )
                        );
                images.put("Does More",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Cascade.png").toString()),32,32)
                            )
                        );
                images.put("Than You Might",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Full Carousel.png").toString()),32,32)
                            )
                        );
                images.put("Think",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/Quit.png").toString()),32,32)
                            )
                        );
                images.put("Love, Bug",new ImageIcon(
                        ImageUtilities.scaledImage(ImageUtilities.loadCompatibleImage(
                            SwingPlusPlus.class.getResource("/com/blogofbug/examples/images/bug256x256.png").toString()),32,32)
                            )
                        );

            }
            public Component getListCellRendererComponent(JList list, Object object, int i, boolean isSelected, boolean hasFocus) {
                
                if (!isSelected){
                    setForeground(list.getForeground());
                    if ((i & 1) == 0){
                        setBackground(list.getBackground());
                    } else {
                        setBackground(new Color(0xEE,0xEE,0xEE));
                    }
                } else {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                }
                
                setIcon(images.get(object));
                setText((String) object);
                return this;
            }
        
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.blogofbug.swing.components.JAnimatedList list;
    private com.blogofbug.swing.components.JMultiButton listActions;
    private javax.swing.JCheckBox mouseTrailOn;
    // End of variables declaration//GEN-END:variables
    
}
