/*
 * SplicedNavigator.java
 * 
 * Created on Oct 1, 2007, 3:51:52 AM
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author nigel
 */
public class SplicedNavigator extends SplicedPane{
    
    public static final String  SELECTED_NODE   = "SelectedNode";
        
    protected NavigationNode      folder;
    protected NavigationNode      selected = null;
    
    protected Thread              updatingThread  = null;
    
    protected JList               fileList = new JList();
    protected DefaultListModel    listModel = new DefaultListModel();
    protected SplicedNavigator    parentDirectory = null;
    
    protected JComponent          previewer = new DefaultNodeViewer(this);
    protected JScrollPane         scrollPanel = null;
    protected JLabel              directoryInfo = new JLabel();
    protected ListCellRenderer    cellRenderer = new DefaultNavigationNodeCellRenderer();
    
    /**
     * Constructs an empty Spliced Navigator with no folder. This is really 
     * only here to support graphical UI editors. 
     */
    public SplicedNavigator(){
        super();
        setSpliceLayout(Layout.HORIZONTAL_TOP);
        basicSetup();
        setNode(null);
    }
        
    /**
     * Creates a new Spliced Navigator that will list the entries in the first
     * node. 
     * 
     * @param node 
     */
    public SplicedNavigator(NavigationNode node){
        this();
        setNode(node);
        refreshContents();
    }    
       
    /**
     * Used when the the user selects another navigable node, which is also 
     * passed on in the constructor. Only one instance is ever in use.
     * 
     * @param node The node to show
     * @param parent The parent of this navigator
     * @param previewer The previewer used by the caller
     * @param nodeListRenderer The list cell renderer to use
     */
    protected SplicedNavigator(NavigationNode node, SplicedNavigator parent, 
            JComponent previewer, ListCellRenderer nodeListRenderer){
        this(node);
        this.parentDirectory = parent;
        setPreviewer(previewer);
    }

    /**
     * Sets the cell renderer used for the entries in the navigator list
     * 
     * @param cellRenderer The cell renderer
     */
    public void setCellRenderer(ListCellRenderer cellRenderer) {        
        SplicedNavigator    current = this;
        while (current.parentDirectory!=null){
            current = current.parentDirectory;
        }
        
        current.cellRenderer = cellRenderer;
        current.fileList.setCellRenderer(cellRenderer);
        while (current.getSecondComponent() instanceof SplicedNavigator){
            current = (SplicedNavigator) current.getSecondComponent();
            
            current.cellRenderer = cellRenderer;
            current.fileList.setCellRenderer(cellRenderer);
        }
        
        return;
    }

    /**
     * Gets the cell renderer to be used for all lists
     * 
     * @return The cell renderer from the top-level navigator
     */
    public ListCellRenderer getCellRenderer() {
        SplicedNavigator    current = this;
        while (current.parentDirectory!=null){
            current = current.parentDirectory;
        }
        
        return current.cellRenderer;
    }
    
    
    /**
     * Sets the node being shown
     * 
     * @param node The node to show
     */
    public void setNode(NavigationNode node){
        folder = node;
        if (folder!=null){
            refreshContents();
        }
    }
    
    /**
     * Used to change the selected node at this level, will roll up and call
     * firePropertyChanged on the top-level spliced navigator (so that the
     * previewer can be updated)
     * 
     * @param node The new node
     */
    protected void setSelectedNode(NavigationNode node){
        NavigationNode oldNode = selected;
        selected = node;
        SplicedNavigator current = this;
        while (current.parentDirectory!=null){
            current = current.parentDirectory;
        }

        current.firePropertyChange(SELECTED_NODE, oldNode, node);
    }
    
    /**
     * The previewer is used when a leaf node is selected, showing details about
     * the selected object. A default is supplied which show the name and 
     * description of the leaf.
     * 
     * @param previwer A JComponent that can preview the selection
     */
    public void setPreviewer(JComponent previewer){
        //Remove the old one as a property change listener
        if (parentDirectory==null){
            if (this.previewer instanceof PropertyChangeListener){
                removePropertyChangeListener((PropertyChangeListener) this.previewer);                
            }            
            previewer.setOpaque(true);
            previewer.setMinimumSize(new Dimension(180,100));
        } 
        //Removed an upward call as external apps should only call at the top level anyway        
        this.previewer = previewer;
        if (!(getSecondComponent() instanceof SplicedNavigator)){
            setSecondComponent(previewer);
        }
    }

    /**
     * Gets the current selected node (deepest)
     * 
     * @return The deepest selected node
     */
    protected NavigationNode getSelectedNode() {
        if (this.getSecondComponent() instanceof SplicedNavigator){
            return ((SplicedNavigator) getSecondComponent()).getSelectedNode();
        } else {
            return selected;            
        }
    }
    
    /** 
     * Changes the second component to preview the selected object
     */
    private void showPreviewer(){
        if (getSecondComponent() instanceof SplicedNavigator){                
            setSecondComponent(previewer); 
        }        
    }
    
    /**
     * Gets the total width of all of the folders
     * 
     * @return The width of all of the folders
     */
    private int getFolderWidths(){
        if (getSecondComponent() instanceof JLabel){
            return getFirstComponent().getWidth();
        } else {
            int width = ((SplicedNavigator)getSecondComponent()).getFolderWidths()+
                    getFirstComponent().getWidth();
            return width;
        }
    }
    
    /**
     * Either increases or reduces the size of the of the panel that the directories
     * are in, reducing if necessary
     */
    private void optimizeSize(){
        if (getParent() instanceof SplicedNavigator){
            //Pass it on up
            ((SplicedNavigator) getParent()).optimizeSize();
        } else if (getParent() instanceof JViewport){
            JViewport parent = (JViewport) getParent();
            //Just going to use a magic number for the label width, as it will
            //end up being as big as it needs to be
            int labelWidth = 180;
            //Now we need to know the total size of all folder lists
            int folderWidths = getFolderWidths();
            //Size it
            setSize(Math.max(parent.getWidth(),labelWidth+folderWidths),getHeight()); 

            //Now we can auto-scroll to the correct place given the newly resized
            //component
            JScrollPane scrollPane = (JScrollPane) parent.getParent();
            int sX = 0;
            SplicedNavigator sd = this;
            while (sd.getSecondComponent() instanceof SplicedNavigator){
                sX+=sd.getFirstComponent().getWidth();
                sd = (SplicedNavigator) sd.getSecondComponent();
            }
            scrollPane.getHorizontalScrollBar().setValue(sX);
        }
    }
     
    /**
     * Called when the selection changes, adding a new level if needed
     * 
     * @param selectionEvent The event
     */
    private void selectionChanged(ListSelectionEvent selectionEvent){
        int sel = fileList.getSelectedIndex();
        if (sel<0){
            return;
        }
        NavigationNode selectedFile = (NavigationNode) listModel.getElementAt(fileList.getSelectedIndex());
        
        setSelectedNode(selectedFile);
        
        if (selectedFile==null){
            showPreviewer();
        } else if (selectedFile.isLeaf()){
            showPreviewer();
        } else {
            SplicedNavigator newSpliceD = new SplicedNavigator(selectedFile,this,previewer,getCellRenderer());
            newSpliceD.previewer.setSize(previewer.getWidth(), previewer.getHeight());
            setSecondComponent(newSpliceD);            
        }
        optimizeSize();

        repaint();
    }
    
    /**
     * Does some basic setup, configuring the cell renders etc
     */
    private void basicSetup(){
        fileList.setCellRenderer(new DefaultNavigationNodeCellRenderer());
        fileList.setModel(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPanel = new JScrollPane(fileList);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        fileList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()){
                    selectionChanged(arg0);
                }
            }
        });

        directoryInfo.setText("Not Set");

        addBarComponent(directoryInfo);
        
        setBarHeight(20);

        setFirstComponent(scrollPanel);
        setSecondComponent(previewer);
        setSizes(180);
    }
    
    /** 
     * Causes the list of nodes to be refreshed
     */
    @SuppressWarnings("deprecation")
    public void refreshContents(){
        if (updatingThread!=null){
                updatingThread.stop();
        }
                
        updatingThread = new Thread(new Runnable() {
            public void run() {
                final List<NavigationNode> nodes = folder.getChildren();

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        listModel.removeAllElements();
                        for (NavigationNode node : nodes){
                            listModel.addElement(node);
                        }               
                        setFirstComponent(scrollPanel);
                        directoryInfo.setText(folder.getName());
                        if (!(getSecondComponent() instanceof JLabel)){
                            setSecondComponent(previewer);                
                        }
                    }
                });
            }
        });
        
        updatingThread.run();
        
    }

    /**
     * Changes the current node.
     * 
     * @param node the new node
     */
    private void setCurrentNode(NavigationNode node) {
        this.folder = node;
        refreshContents();
    }
}
