package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DnDConstants;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.fherfurt.imagecompare.swing.controller.ImageComponentDragSource;
import de.fherfurt.imagecompare.swing.controller.ReflectedImageDragSource;

public class PreviewThumbnailComponent extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	private static JPanel panel = new JPanel();
	
	public PreviewThumbnailComponent() {
		super(panel);
		panel.setLayout(new FlowLayout());
		ImageIcon ii = new ImageIcon();
//		panel.setPreferredSize(new Dimension(800, 600));
		
		try {
//			addThumbnail(ImageIO.read(new File("pics/1.jpg")));
//			addThumbnail(ImageIO.read(new File("pics/2.jpg")));
//			addThumbnail(ImageIO.read(new File("pics/3.jpg")));
//			addThumbnail(ImageIO.read(new File("pics/4.jpg")));
//			addThumbnail(ImageIO.read(new File("pics/5.jpg")));
			File f = new File("C:/Dokumente und Einstellungen/All Users/Dokumente/Eigene Bilder/Beispielbilder");
			for(File file : f.listFiles()) {
				if(file.isFile()) {
					if(file.getName().endsWith(".jpg") || file.getName().endsWith(".gif") || file.getName().endsWith(".bmp")) {
						addThumbnail(ImageIO.read(file));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addThumbnail(BufferedImage image) {
		ImageComponent ic = new ImageComponent(image);
		ic.setThumbnail(80);
		new ImageComponentDragSource(ic, DnDConstants.ACTION_COPY);
		panel.add(ic);
	}

}
