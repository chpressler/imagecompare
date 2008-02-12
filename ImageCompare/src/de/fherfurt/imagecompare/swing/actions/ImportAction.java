package de.fherfurt.imagecompare.swing.actions;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import de.fherfurt.imagecompare.ImportXMLDomHandler;
import de.fherfurt.imagecompare.ImportXMLStaXHandler;
import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.util.ICUtil;

public class ImportAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private JProgressBar bar;

	boolean b = true;

	public ImportAction() {
		putValue(Action.NAME, "import");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/"
				+ ResourceHandler.getInstance().getIcons().getString("import")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance()
				.getStrings().getString("import"));
		bar = new JProgressBar();
	}

	public void actionPerformed(ActionEvent arg0) {
		JFrame scan = new JFrame("Scan for Import");
		scan.setAlwaysOnTop(true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JCheckBox dc;
		//TODO -> add Radiobutton for overwrite import (new), or append (add) 
		for (File f : File.listRoots()) {
			dc = new JCheckBox(FileSystemView.getFileSystemView()
					.getSystemDisplayName(f), true);
			panel.add(new JLabel(FileSystemView.getFileSystemView()
					.getSystemIcon(f)));
			panel.add(dc);
		}
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		JButton button = new JButton("scan");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (b) {
							b = false;
							SwingUtilities.getRoot(bar).setCursor(new Cursor(Cursor.WAIT_CURSOR));
							bar.setIndeterminate(true);
							ImportXMLStaXHandler.getInstance().startDoc();
							for (final Component c : ((JButton) e.getSource()).getParent()
									.getComponents()) {
								if (c instanceof JCheckBox && ((JCheckBox) c).isSelected()) {
									for (final File f : File.listRoots()) {
										if (FileSystemView.getFileSystemView()
												.getSystemDisplayName(f).equals(
														((JCheckBox) c).getText())) {
//											new Thread(new Runnable() {
//												@Override
//												public void run() {
													scan(f);
//													ImportXMLDomHandler.getInstance().save();
													System.out.println("finished import for " + ((JCheckBox) c).getText());
//												}
//											}).start();
										}
									}
								}
							}
							ImportXMLStaXHandler.getInstance().closeDoc();
							bar.setIndeterminate(false);
							SwingUtilities.getRoot(bar).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							b = true;
						}
					}}).start();
			}
		});
		panel.add(button);
		panel.add(bar);
		scan.getContentPane().add(panel);
		scan.pack();
		scan.setVisible(true);
		scan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void scan(File f) {
		if (f.isDirectory() && f.listFiles() != null) {
			for (File file : f.listFiles()) {
				if (file.isDirectory()) {
					scan(file);
				}
				if (file.getName().endsWith(".jpg")) {
//					System.out.println(file);
					ImportXMLStaXHandler.getInstance().addImage(file.getAbsolutePath(), getMetadata(file));
//					if(ImportXMLDomHandler.getInstance().getImageByPath(file.getAbsolutePath()) != null) {
//						System.out.println("Image " + file.getAbsolutePath() + " schon vorhanden");
//						continue;
//					}
//					ImportXMLDomHandler.getInstance().addImport(file.getAbsolutePath(), getMetadata(file));
				}
			}
		}
	}
	
	public HashMap<String, String> getMetadata(File f) {
		HashMap<String, String> metadatamap = new HashMap<String, String>();
		Metadata metadata = new Metadata();
		try {
			metadata = JpegMetadataReader.readMetadata(f);
		} catch (JpegProcessingException e) {
			e.printStackTrace();
		}
		
//		Iterator directories = metadata.getDirectoryIterator();
//		while (directories.hasNext()) {
//			final Directory directory = (Directory) directories.next();
//			Iterator tags = directory.getTagIterator();
//			while (tags.hasNext()) {
//				final Tag tag = (Tag) tags.next();
//				final int tt = tag.getTagType();
//				metadatamap.put(tag.getTagName(), directory.getString(tt));
//			}
//		}
		
		try {
			BufferedImage bi = ICUtil.getInstance().getThumbnal(ImageIO.read(f));
			metadatamap.put("faceCount", Integer.toString(ICUtil.getInstance().getFaceCount(f.getAbsolutePath())));
//			metadatamap.put("lastModofied", Long.toString(f.lastModified()));
//			metadatamap.put("size", Long.toString(f.length()));
//			metadatamap.put("imageWidth", Integer.toString(bi.getWidth()) );
//			metadatamap.put("imageHeight", Integer.toString(bi.getHeight()) );
//			metadatamap.put("pixelcount", Integer.toString(bi.getWidth() * bi.getHeight()) );
//			metadatamap.put("colored", Boolean.toString( ICUtil.getInstance().isColored(bi) ) );
//			metadatamap.put("dynamic", Integer.toString( ICUtil.getInstance().getDynamic(bi, true) ));
//			metadatamap.put("contrast", Integer.toString( ICUtil.getInstance().getContrast(bi, false) ));
//			metadatamap.put("averageLum", Integer.toString(ICUtil.getInstance().getAverageLum(bi, false)));
//			metadatamap.put("averageSat", Integer.toString(ICUtil.getInstance().getAverageSat(bi)));
		} catch (Exception e1) {
			return metadatamap;
		}
		return metadatamap;
	}
		
}
