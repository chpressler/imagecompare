/*
 * ScaledPanel.java
 *
 * Created on April 4, 2007, 7:48 AM
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

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author nigel
 */
public class ScaledPanel extends JPanel{
    Dimension           scaleFrom = new Dimension(100,100);
    /** Creates a new instance of ScaledPanel */
    public ScaledPanel(Dimension scaleFrom) {
        this.scaleFrom = scaleFrom;
    }

    protected void paintComponent(Graphics graphics) {
    }



}
