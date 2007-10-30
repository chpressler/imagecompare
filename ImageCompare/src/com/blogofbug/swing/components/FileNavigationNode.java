/*
 * FileNavigationNode.java
 * 
 * Created on Oct 19, 2007, 1:07:18 AM
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author nigel
 */
public class FileNavigationNode implements NavigationNode{
    File                        theFile = null;
    FileSystemNavigable         context = null;

    public FileNavigationNode(FileSystemNavigable context, File theFile) {
        this.theFile=theFile;
        this.context = context;
    }
    
    public boolean isLeaf() {
        return theFile.isFile();
    }

    public boolean isChild() {
        return (theFile.getParentFile()!=null);
    }

    public boolean isVisitable() {
        return true;
    }

    public List<NavigationNode> getChildren() {
        FileSystemView fileSystem = context.getFileSystemView();
        File[] files = fileSystem.getFiles(theFile, true);
        ArrayList<NavigationNode> children = new ArrayList<NavigationNode>(files.length);
        for (File file : files){
            children.add(new FileNavigationNode(context, file));
        }
        return children;
    }

    public NavigationNode getChild(String name) {
        FileSystemView fileSystem = context.getFileSystemView();
        File[] files = fileSystem.getFiles(theFile, true);
        for (File file : files){
            if (file.getName().equals(name)){
                return new FileNavigationNode(context, file);
            }
        }
        return null;
    }

    public NavigationNode getParent() {
        File parent = context.getFileSystemView().getParentDirectory(theFile);
        if (parent!=null){
            return new FileNavigationNode(context, parent);
        }
        return null;
    }

    public String getName() {
        return theFile.getName();
    }

    public Icon getIcon() {
        return context.getFileSystemView().getSystemIcon(theFile);
    }

    public String getDescription() {
        return context.getFileSystemView().getSystemTypeDescription(theFile);
    }

    public Object getObject() {
        return theFile;
    }
}
