/*
 * ImageRectangleEffect.java
 *
 * Created on March 27, 2007, 9:11 AM
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

package com.blogofbug.swing.components.effects;

import com.blogofbug.swing.borders.AbstractImageBorder;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author nigel
 */
public class ImageRectangleEffect extends AbstractImageBorder implements BoundEffect {
    Rectangle       bounds;
    /** Creates a new instance of ImageRectangleEffect */
    public ImageRectangleEffect(Rectangle bounds, BufferedImage image, Insets insets) {
        super(image, insets);
        setBounds(bounds);
    }

    public void setBounds(Rectangle rectangle) {
        this.bounds = (Rectangle) rectangle.clone();
    }

    public Rectangle getBounds() {
        return (Rectangle) bounds.clone();
    }

    public boolean isLocalEffect() {
        return true;
    }

    public void paintEffect(Graphics2D graphics) {
        graphics.translate(bounds.x,bounds.y);
        paintBorder(bounds.width,bounds.height,graphics,bounds.x,bounds.y,bounds.width,bounds.height);
        paintCenter(graphics,bounds.width,bounds.height);
    }

    public long update() {
        return EFFECT_INACTIVE;
    }
    
}
