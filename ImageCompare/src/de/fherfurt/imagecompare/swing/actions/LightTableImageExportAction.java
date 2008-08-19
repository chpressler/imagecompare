package de.fherfurt.imagecompare.swing.actions;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.LightTableComponent;

public class LightTableImageExportAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	public LightTableImageExportAction() {
		putValue(Action.NAME, "LightTableImageExport");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("lighttableexport")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("controlpanel"));
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
//		BufferedImage image = new BufferedImage(LightTableComponent.getInstance().getWidth(), LightTableComponent.getInstance().getHeight(), BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2D = (Graphics2D) image.getGraphics();
//		g2D = (Graphics2D) LightTableComponent.getInstance().getLayeredPane().getGraphics();
//		ImageIO.write(image, "jpg", new File("C:/expImage.jpg"));
//		
//		JPEGImageEncoder jie = (JPEGImageEncoder) JPEGCodec.createJPEGEncoder(new FileOutputStream(new File("C:/ep.jpg")));
			
		saveComponentAsJPEG(LightTableComponent.getInstance().getLayeredPane(), LightTableComponent.getInstance(), "C:/fot.jpg");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void saveComponentAsJPEG(Component cmp, Container cont, String jpegfile) {
	       Rectangle d = cmp.getBounds();
	       BufferedImage bi = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
	       Graphics2D g2d = bi.createGraphics();
	       SwingUtilities.paintComponent(g2d,cmp,cont, 0,0,d.width,d.height);
	       saveImageAsJPEG(bi, jpegfile);
	  }
	
	public void saveImageAsJPEG(BufferedImage bi, String filename) {
	      try {
	         ByteArrayOutputStream boutstream = new ByteArrayOutputStream();
	         JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(boutstream);
	         enc.encode(bi);
	         FileOutputStream fimage = new FileOutputStream(new File(filename));
	         boutstream.writeTo(fimage);
	         fimage.close();
	      } catch (Exception e) { System.out.println(e); }
	   }


}