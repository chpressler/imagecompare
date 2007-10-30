/*
 * ReflectedImageLabel.java
 *
 * Created on November 22, 2006, 11:34 PM
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

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import com.blogofbug.swing.components.effects.ContainedEffect;
import com.blogofbug.swing.components.effects.Effect;
import com.blogofbug.swing.components.effects.EffectEngine;
import com.blogofbug.swing.components.effects.ReflectionEffect;
import com.blogofbug.utility.ImageUtilities;

/**
 * A RichComponent which takes the supplied image, adds on 50% to the height of the image
 * and draws a graduated alpha-blended reflection below the top aligned original image. The
 * text (set by setRichText()) is drawn dynamically over the reflection, below the original image.
 * @author nigel
 */
public class ReflectedImageLabel extends JButton{
    /**
     * The richtext associated with this component
     */
    private String  text = "";
    /**
     * The image with reflection
     */
    private BufferedImage   bufferedImage = null;
        
    /**
     * The desired alpha composite
     */
    private AlphaComposite  alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f);
    
    /**
     * An effect engine to render the background effects
     */
    private EffectEngine    backgroundEffects=new EffectEngine(this,true);
    
    /**
     * An effect engine to render the foreground effects
     */
    private EffectEngine    foregroundEffects=new EffectEngine(this,true);
    
    private ReflectionEffect   reflectionEffect = null;
    
    /**
     * Creates a new instance from the supplied image object
     * @param image The Image object
     * @param width The prefered width of the image when rendered by a rich container
     * @param height The prefered heightof the image when rendered by a rich container
     * @deprecated This function will be removed, use setNeutralWidth() on JCarousel instead.
     */
    public ReflectedImageLabel(Image image, int width, int height) {
        setRichImage(image);
        construct();
    }
    
    /**
     * Creates a new instance of a reflected label for the supplied image, also setting the text
     * to be associated with the image.
     * @param image The image
     * @param text The text label
     * @param width The prefered width of the image when rendered by a rich container
     * @param height The prefered height of the image when rendered by a rich container
     * @deprecated This function will be removed, use setNeutralWidth() on JCarousel instead.
     */
    public ReflectedImageLabel(Image image, String text, int width, int height) {
        setRichImage(image);
        setRichText(text);
        construct();
    }
    
    /**
     * Creates a new instance of a reflected label using the supplied image and text
     * @param image The image to be used
     * @param text The text to be displayed
     */
    public ReflectedImageLabel(Image image, String text) {
        this(image, text, image.getWidth(null), image.getHeight(null));
    }
    
    /**
     * See constructor for image label, this version of the constructor takes an image URL instead
     * of the image object (the URL can be in string format).
     * @param imageURL A URL (in string form) of the image.
     */
    public ReflectedImageLabel(String imageURL){
        try {
            setRichImage(new URL(imageURL));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        construct();
    }
    
    /**
     * Creates a new instance, setting the width and the height that may be used by the RichContainer
     * @param imageURL The URL of the image (String form)
     * @param width The prefered width of the image when rendered by a rich container
     * @param height The prefered height of the image when rendered by a rich container
     * @deprecated This function will be removed, use setNeutralWidth() on JCarousel instead.
     */
    public ReflectedImageLabel(String imageURL, int width, int height){
        try {
            setRichImage(new URL(imageURL));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        construct();
    }
    
    /**
     * Creates a new instance, using the image specified in the URL string, the prefered dimensions and sets the Rich text as well
     * @param imageURL The URL of the image in text form
     * @param text The RichText to be displayed
     * @param width The prefered width of the image when rendered by a rich container
     * @param height The prefered height of the image when rendered by a rich container
     * @deprecated This function will be removed, use setNeutralWidth() on JCarousel instead.
     */
    public ReflectedImageLabel(String imageURL, String text, int width, int height){
        this(imageURL,width,height);
        this.text=text;
        construct();
    }
    
    /**
     * generic code for all constructors
     *
     */
    private void construct()
    {
    	// this should look like an image button
    	setBorderPainted(false);
    }

    
    /**
     * Depricated.
     * @deprecated Use setRichText() instead
     * @param text The rich text
     */
    public void setLabel(String text){
        this.text = text;
    }
    

    
    /**
     * Will accept a string URL for loading the image before calling the normal setupImage function after loading it.
     * @param imageURL The URL of the image (in a string)
     */
    private void setupImage(String imageURL) {
        Image image = null;
        try {
            image = ImageIO.read(new URL(imageURL));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.setupImage(image);
    }
    
    /**
     * Adds a reflection to the supplied image
     * @param image The image object to use to pre-render the reflection
     */
    protected void setupImage(Image image){
        if (image == null) {
            return;
        }
                
        bufferedImage = (BufferedImage) image;
        setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        if ((getWidth()==0) && (getHeight()==0)){
            setSize(getPreferredSize());
        }
       
    }
    
    /**
     * Sets the transparency of the component
     * @param alphaLevel The alpha level of the object
     * @see RichComponent
     */
    public void setAlpha(float alphaLevel){
        alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaLevel);
    }
  
    /**
     * Deprecated
     * @param image An image object to use (reflection will be added)
     * @see RichComponent
     * @deprecated Please use setRichImage() instead from the RichComponent interface
     */
    public void setImage(Image image) {
        this.setupImage(image);
    }    
    
    
    /**
     * Overrides the default getPreferedSize() which has been controlled by the created image and adds 50% onto the height to allow for the reflection.
     * @return The prefered dimensions of the component
     */
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        //d.height = (int) ((double) d.height * 1.5);
        
        return d;
    }
    
    /** 
     * Paints the component
     * 
     * @param graphics The graphics context
     */
    public void paintComponent(Graphics graphics) {
        // Don't paint if I'm off screen
        if ((getX() + getWidth() < 0) && (getY() + getHeight() < 0)) {
            return;
        }

        Graphics2D g = (Graphics2D) graphics;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        backgroundEffects.paintEffects(graphics);        
        g.drawImage(bufferedImage,0,0,getWidth(),getHeight(),null);
        foregroundEffects.paintEffects(g);
    }


    /** 
     * Assigns an image to the component, the width and height taken from the supplied image
     *
     * @param image         The URL of the image
     */
    public void setRichImage(URL image) {
        setRichImage(ImageUtilities.loadCompatibleImage(image.toString()));
    }

    /**
     * See interface definition
     * @param image See interface definition
     * @see RichComponent
     */
    public void setRichImage(File image) {
        try {
            setRichImage(image.toURL());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * See interface definition
     * @param image See interface definition
     * @see RichComponent
     */
    public void setRichImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        setSize(image.getWidth(null),image.getHeight(null));
        setImage(image);
    }

    /**
     * See interface definition
     * @see RichComponent
     */
    public void prePaintImage() {
        //I do all my pre-rendering earlier...
    }

    /**
     * See interface definition
     * @param text See interface definition
     * @see RichComponent
     */
    public void setRichText(String text) {
        setLabel(text);
    }

    /**
     * See interface definition
     * @return See interface definition
     * @see RichComponent
     */
    public String getRichText() {
        return this.text;
    }

    /**
     * Adds an effect which should be rendered before the component paints itself
     *
     * @param The effect
     */
    public void addBackgroundEffect(Effect effect) {
        if (effect instanceof ContainedEffect){
            ((ContainedEffect) effect).setEffectContainer(backgroundEffects);
        }
        backgroundEffects.addEffect(effect);
    }

    /**
     * Adds an effect which should be rendered after the component paints itself
     *
     * @param The effect
     */
    public void addForegroundEffect(Effect effect) {
        if (effect instanceof ContainedEffect){
            ((ContainedEffect) effect).setEffectContainer(foregroundEffects);
        }
        foregroundEffects.addEffect(effect);
    }

    /**
     * Removes an effect from the background effects
     *
     * @param The effect
     */
    public void removeBackgroundEffect(Effect effect) {
        backgroundEffects.removeEffect(effect);
    }

    /**
     * Removes an effect from the foreground effects
     *
     * @param The effect
     */
    public void removeForegroundEffect(Effect effect) {
        foregroundEffects.removeEffect(effect);
    }

}
