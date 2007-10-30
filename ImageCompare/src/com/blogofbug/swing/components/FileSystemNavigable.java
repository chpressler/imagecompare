/*
 * FileSystemNavigable.java
 * 
 * Created on Oct 19, 2007, 1:05:16 AM
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
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author nigel
 */
public class FileSystemNavigable implements NavigableSystem{
    FileSystemView  fileSystem = FileSystemView.getFileSystemView();

    public List<NavigationNode> getRoots() {
        File[] rootFiles = fileSystem.getRoots();
        ArrayList<NavigationNode> roots = new ArrayList<NavigationNode>(rootFiles.length);
        
        for (File root : rootFiles){
            roots.add(new FileNavigationNode(this, root));
        }
        return roots;
    }

    public NavigationNode getDefaultRoot() {
        return new FileNavigationNode(this, fileSystem.getHomeDirectory());
    }
    
    FileSystemView  getFileSystemView(){
        return fileSystem;
    }
}
