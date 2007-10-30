package tutorials;

/*
 * @(#)FrontPage.java	1.22 99/08/16 15:40:36
 *
 * Copyright (c) 1999 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.awt.color.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.media.jai.*;
import javax.media.jai.widget.ImageCanvas;

import com.sun.media.jai.widget.DisplayJAI;

import java.net.URL;
import java.net.MalformedURLException;


public class FrontPage extends JPanel
                       implements ActionListener {

    private PlanarImage src_color = null;
    private PlanarImage src_gray = null;
    private DisplayJAI canvas = null;
    private JLabel label;
    private URL flasher = null;
    private double[] constants = { 10.0, 10.0, 10.0 };

    public FrontPage(String file,
                     String button_icon,
                     String flash_icon) {

        setBackground(new Color(200,200,255));
        setLayout(new BorderLayout());
        setOpaque(true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(200,200,255));
        panel.setBorder(new EmptyBorder(10,10,10,10));

        JButton button = new JButton("Smile");
	  try
	  {
        	label = new JLabel(new ImageIcon( new URL (button_icon) ));
        	flasher = new URL (flash_icon);

        	src_color = JAI.create("url", new URL (file) );
	  }
	  catch (MalformedURLException e)
	  {
		return;
	  }
//        src_gray = TutorUtils.convertColorToGray(src_color, 40);
//        canvas = new DisplayJAI(src_gray);
        canvas = new DisplayJAI(src_gray);
        canvas.setBackground(Color.yellow);

        canvas.setBorder( new CompoundBorder(
                             new EtchedBorder(),
                             new LineBorder(new Color(100,100,180), 20)
                        ) );

        canvas.setPreferredSize(new Dimension(src_gray.getWidth(),
                                src_gray.getHeight()));

        // brighten the color image a bit
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(src_color);
        pb.add(constants);
        src_color = JAI.create("addconst", pb, null);

        button.addActionListener(this);
        panel.add(button);
        panel.add(label);
        add(panel,  BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        // center the image (quick and dirty)
        canvas.setLocation(30, 50);
    }

    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        SampleModel sm = src_gray.getSampleModel();
        double[] pix   = new double[sm.getNumBands()];
        Raster srcras  = src_color.getData();
        int width      = src_color.getWidth();
        int height     = src_color.getHeight();
        Graphics g     = getGraphics();
        Point origin   = canvas.getLocation();
        Insets insets  = canvas.getInsets();
		
        label.setIcon( new ImageIcon(flasher) );

        // intentionally slow
        // red rose
/*
        for ( int i = 0; i < height; i++ ) {
            for ( int j = 0; j < width; j++ ) {
                srcras.getPixel(j, height-i-1, pix);

                g.setColor(new Color((int)pix[0],
                                     (int)pix[1],
                                     (int)pix[2]));

                g.drawLine(j+origin.x+insets.left,
                           origin.y+height-i-1+insets.top,
                           j+origin.x+insets.left,
                           origin.y+height-i-1+insets.top);
            }
        }
*/

        // synchronize graphics and image
        canvas.set(src_color);
        b.setEnabled(false);
        b.setBackground(Color.gray);
    }
}