package de.fherfurt.imagecompare.swing.actions;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

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
import javax.swing.filechooser.FileSystemView;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import de.fherfurt.imagecompare.ImportXMLDomHandler;
import de.fherfurt.imagecompare.ImportXMLStaXHandler;
import de.fherfurt.imagecompare.ResourceHandler;

public class ImportAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	boolean b = true;

	public ImportAction() {
		putValue(Action.NAME, "import");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/"
				+ ResourceHandler.getInstance().getIcons().getString("import")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance()
				.getStrings().getString("import"));
	}

	public void actionPerformed(ActionEvent arg0) {
		JFrame scan = new JFrame("Scan for Import");
		scan.setAlwaysOnTop(true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JCheckBox dc;
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
			public void actionPerformed(ActionEvent e) {
				if (b) {
					b = false;
					ImportXMLStaXHandler.getInstance().startDoc();
					for (final Component c : ((JButton) e.getSource()).getParent()
							.getComponents()) {
						if (c instanceof JCheckBox && ((JCheckBox) c).isSelected()) {
							for (final File f : File.listRoots()) {
								if (FileSystemView.getFileSystemView()
										.getSystemDisplayName(f).equals(
												((JCheckBox) c).getText())) {
									new Thread(new Runnable() {
										@Override
										public void run() {
											scan(f);
//											ImportXMLDomHandler.getInstance().save();
											System.out.println("finished import for " + ((JCheckBox) c).getText());
										}
									}).start();
								}
							}
						}
					}
					ImportXMLStaXHandler.getInstance().closeDoc();
					b = true;
				}
			}
		});
		panel.add(button);
		JProgressBar bar = new JProgressBar();
		panel.add(bar);
		scan.getContentPane().add(panel);
		scan.pack();
		scan.setVisible(true);
		scan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void scan(File f) {
		if (f.isDirectory() && f.listFiles().length != 0) {
			for (File file : f.listFiles()) {
				if (file.isDirectory()) {
					scan(file);
				}
				if (file.getName().endsWith(".jpg")) {
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
		Iterator directories = metadata.getDirectoryIterator();
		while (directories.hasNext()) {
			final Directory directory = (Directory) directories.next();
			Iterator tags = directory.getTagIterator();
			while (tags.hasNext()) {
				final Tag tag = (Tag) tags.next();
				final int tt = tag.getTagType();
				metadatamap.put(tag.getTagName(), directory.getString(tt));
			}
		} 
		//Kontrast mit in die HashMap!!! ohne Angabae von Unit
		//Dynamik mit in die HashMap!!! ohne Angaba von Unit
		//Anzahl der Pixel zusätzlich mit rein -> aus Histogramm, nicht aus h * b!!!
		return metadatamap;
	}

}
