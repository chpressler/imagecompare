/*
 * YahooImageSearcher.java
 *
 * Created on April 12, 2007, 11:32 AM
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

package com.blogofbug.examples.yahooimagesearch;

import com.blogofbug.swing.SwingBugUtilities;
import com.blogofbug.swing.components.LayeredDockPanel;
import com.blogofbug.swing.components.ReflectedImageLabel;
import com.blogofbug.utility.ImageUtilities;
import com.yahoo.search.ImageSearchResult;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author  nigel
 */
public class YahooImageSearcher extends javax.swing.JFrame implements ActionListener,
                                                                      ContainerListener,
                                                                      MouseListener{
    
    YahooImageSearch    imageSearch;
    ImageIcon           busyIcon = new ImageIcon(YahooImageSearcher.class.getResource("/com/blogofbug/examples/images/busy.gif"));
    
    /** Creates new form YahooImageSearcher */
    public YahooImageSearcher() {
        initComponents();
        
        carousel = new ImageCarousel();
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        previewPanel.setOpaque(false);
        setGlassPane(previewPanel);
        carousel.setBackground(Color.BLACK,Color.DARK_GRAY);
        carousel.addContainerListener(this);
        getContentPane().add(carousel, BorderLayout.CENTER);
        setSize(800,600);
        
        LayeredDockPanel layeredPane = new LayeredDockPanel(this);
        layeredPane.setVisible(true);
        this.getRootPane().getLayeredPane().add(layeredPane, LayeredDockPanel.DOCK_LAYER);
        layeredPane.setBounds(0,0,400,400);
        
        SwingBugUtilities.addTimerListener(this);
        
        
        //For the demo we add a copule of items
        ReflectedImageLabel nextPageButton = new ReflectedImageLabel(
                ImageUtilities.loadCompatibleImageResource(this.getClass(),"/com/blogofbug/examples/images/next-page.png"),"Next Page");
        nextPageButton.setBorderPainted(true);
        nextPageButton.setOpaque(false);
        nextPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                nextPage();
            }
        });
        
        ReflectedImageLabel prevPageButton = new ReflectedImageLabel(
                ImageUtilities.loadCompatibleImageResource(this.getClass(),"/com/blogofbug/examples/images/prev-page.png"),"Prev. Page");
        prevPageButton.setBorderPainted(true);
        prevPageButton.setOpaque(false);
        prevPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                prevPage();
            }
        });
        
        ReflectedImageLabel changeSearchTerm = new ReflectedImageLabel(
                ImageUtilities.loadCompatibleImageResource(this.getClass(),"/com/blogofbug/examples/images/search.png"),"New Search");
        changeSearchTerm.setBorderPainted(true);
        changeSearchTerm.setOpaque(false);
        changeSearchTerm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                searchFor();
            }
        });
        
        
        layeredPane.addDockElement(prevPageButton,"Previous Page");
        layeredPane.addDockElement(nextPageButton,"Next Page");
        layeredPane.addDockElement(changeSearchTerm,"Searh For...");
        
        imageSearch = new YahooImageSearch(carousel,15);
        imageSearch.searchFor("\"Java Duke\"");
    }
    
    private void searchFor(){
        String s = JOptionPane.showInputDialog(this,"Enter search term");
        if (s!=null){
            imageSearch.searchFor(s);
        }
    }
    
    private void prevPage(){
        if (imageSearch.getPage()>0){
            imageSearch.showPage(imageSearch.getPage()-1);
        }
    }
    
    private void nextPage(){
        imageSearch.showPage(imageSearch.getPage()+1);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JLabel jLabel2;
        javax.swing.JLabel jLabel3;
        javax.swing.JLabel jLabel5;

        previewPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        imagePreview = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        format = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dimensions = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        size = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        spacer = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        spacer1 = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        searchInformation = new javax.swing.JLabel();
        activity = new javax.swing.JLabel();
        carousel = new com.blogofbug.examples.yahooimagesearch.ImageCarousel();

        previewPanel.setLayout(new java.awt.GridBagLayout());

        previewPanel.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setOpaque(false);
        imagePreview.setBackground(new java.awt.Color(0, 0, 0));
        imagePreview.setForeground(new java.awt.Color(255, 255, 255));
        imagePreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagePreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/blogofbug/examples/images/busy.gif")));
        imagePreview.setText("Loading...");
        jScrollPane1.setViewportView(imagePreview);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Format");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        format.setForeground(new java.awt.Color(255, 255, 255));
        format.setText("JPEG");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(format, gridBagConstraints);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Dimensions");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        dimensions.setForeground(new java.awt.Color(255, 255, 255));
        dimensions.setText("Width x Height");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(dimensions, gridBagConstraints);

        saveButton.setText("Save");
        saveButton.setOpaque(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(saveButton, gridBagConstraints);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        size.setForeground(new java.awt.Color(255, 255, 255));
        size.setText("xxxbytes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(size, gridBagConstraints);

        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Title");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(title, gridBagConstraints);

        spacer.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(spacer, gridBagConstraints);

        closeButton.setText("Close");
        closeButton.setOpaque(false);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel1.add(closeButton, gridBagConstraints);

        spacer1.setForeground(new java.awt.Color(255, 255, 255));
        spacer1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(spacer1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(48, 14, 14, 14);
        previewPanel.add(jPanel1, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Image Searcher - WebServices by Yahoo!");
        setLocationByPlatform(true);
        topPanel.setLayout(new java.awt.GridBagLayout());

        topPanel.setBackground(new java.awt.Color(0, 0, 0));
        searchInformation.setBackground(new java.awt.Color(0, 0, 0));
        searchInformation.setFont(new java.awt.Font("Arial", 0, 18));
        searchInformation.setForeground(new java.awt.Color(255, 255, 255));
        searchInformation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/blogofbug/examples/images/websrv-by-yahoo.gif")));
        searchInformation.setText("Search Info");
        searchInformation.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        topPanel.add(searchInformation, gridBagConstraints);

        activity.setBackground(new java.awt.Color(0, 0, 0));
        activity.setFont(new java.awt.Font("Arial", 0, 18));
        activity.setForeground(new java.awt.Color(255, 255, 255));
        activity.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        activity.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/blogofbug/examples/images/busy.gif")));
        activity.setText("Activity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        topPanel.add(activity, gridBagConstraints);

        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);

        getContentPane().add(carousel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
            //Create a file chooser
            final JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File file) {
                    if (file.isDirectory()){
                        return true;
                    }
                    String fileName = file.getName();
                    if (fileName.endsWith("png")){
                        return true;
                    } else if (fileName.endsWith("gif")){
                        return true;
                    } else if (fileName.endsWith("jpg")){
                        return true;
                    } else {
                        return false;
                    }
                }
                public String getDescription() {
                    return "Image Files (png, gif, jpg)";
                }
            });
            int result = chooser.showSaveDialog(this);
            
            if (result==JFileChooser.APPROVE_OPTION){
                boolean doSave=true;
                String extension = null;
                File    selectedFile = chooser.getSelectedFile();
                String  name = selectedFile.getName();
                try {
                    extension = name.substring(name.lastIndexOf(".")+1,name.length());
                } catch (Exception e){};
                
                if (extension==null){
                    name=name+".png";
                    extension="png";
                    selectedFile = new File(selectedFile.getAbsolutePath()+"."+extension);
                }
                
                if (chooser.getSelectedFile().exists()){
                    if (JOptionPane.showConfirmDialog(this,"File "+chooser.getSelectedFile().getAbsolutePath()+" exists. Overwrite?","Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
                        doSave =false;
                    }
                }
                if (doSave){
                    try {
                        ImageIO.write((BufferedImage) ((ImageIcon)imagePreview.getIcon()).getImage(),extension,selectedFile);
                        closeButtonActionPerformed(null);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,"Could not save to file: "+chooser.getSelectedFile().getAbsolutePath(),"Save Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        
    }//GEN-LAST:event_saveButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        carousel.fadeBack();
        previewPanel.setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new YahooImageSearcher().setVisible(true);
            }
        });
    }
    
    private void setIfDifferent(JLabel label, String candidateChange){
        if (label.getText().equals(candidateChange)){
            return;
        }
        label.setText(candidateChange);
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        setIfDifferent(searchInformation,"Search: "+imageSearch.getSearchString());
        
        if (carousel.getComponentCount()<imageSearch.getResultsOnPage()){
            setIfDifferent(activity,imageSearch.getStatus());
            if (activity.getIcon()!=busyIcon){
                activity.setIcon(busyIcon);
            }
        } else {
            if (carousel.getComponentCount()>0){
                Component component = carousel.getComponent(0);
                ImageSearchResult result = imageSearch.getResultFor(component);
                if (result!=null){
                    setIfDifferent(activity,result.getTitle());
                } else {
                    setIfDifferent(activity,"Unable to find results!");
                }
                if (activity.getIcon()!=null){
                    activity.setIcon(null);
                }
            } else {
                setIfDifferent(activity,imageSearch.getStatus());
                if (activity.getIcon()!=busyIcon){
                    activity.setIcon(busyIcon);
                }
            }
        }
    }

    public void componentAdded(ContainerEvent containerEvent) {
        containerEvent.getChild().addMouseListener(this);
    }

    public void componentRemoved(ContainerEvent containerEvent) {
        containerEvent.getChild().removeMouseListener(this);
    }

    public void showImage(final ImageSearchResult result){
        carousel.fadeOut();
        format.setText(result.getFileFormat());
        title.setText(result.getUrl());
        dimensions.setText(""+result.getWidth().intValue()+"x"+result.getHeight().intValue());
        size.setText(result.getFileSize().intValue()/1000+"k");
        previewPanel.setVisible(true);
        imagePreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/blogofbug/examples/images/busy.gif")));
        imagePreview.setText("Loading...");
        imagePreview.setOpaque(false);
        imagePreview.setBackground(null);
        imagePreview.setForeground(Color.WHITE);
        final JFrame thisFrame = (JFrame) this;
        saveButton.setEnabled(false);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                BufferedImage image = ImageUtilities.loadCompatibleImage(result.getUrl());
                if (image == null){
                    imagePreview.setIcon(null);
                imagePreview.setForeground(Color.RED);
                    imagePreview.setText("Host Failed to Respond");
                } else {
                    imagePreview.setIcon(new ImageIcon(image));
                    imagePreview.setText("");                    
                    saveButton.setEnabled(true);
                }
            }
        });
        thread.start();
    }
    
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            showImage(imageSearch.getResultFor(mouseEvent.getComponent()));
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activity;
    private com.blogofbug.examples.yahooimagesearch.ImageCarousel carousel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel dimensions;
    private javax.swing.JLabel format;
    private javax.swing.JLabel imagePreview;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel previewPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel searchInformation;
    private javax.swing.JLabel size;
    private javax.swing.JPanel spacer;
    private javax.swing.JPanel spacer1;
    private javax.swing.JLabel title;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
    
}
