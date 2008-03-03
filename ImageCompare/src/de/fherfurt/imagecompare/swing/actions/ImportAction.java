package de.fherfurt.imagecompare.swing.actions;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

import de.fherfurt.imagecompare.ImageAnalyser;
import de.fherfurt.imagecompare.ImportDBMySQLHandler;
import de.fherfurt.imagecompare.ResourceHandler;

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
//							ImportXMLStaXHandler.getInstance().startDoc();
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
//							ImportXMLStaXHandler.getInstance().closeDoc();
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
				if (file.getName().endsWith(".jpg") || file.getName().endsWith(".gif") || file.getName().endsWith(".JPG") || file.getName().endsWith(".bmp") || file.getName().endsWith(".png") || file.getName().endsWith(".PNG") || file.getName().endsWith(".GIF")) {
//					System.out.println(file);
//					ImportXMLStaXHandler.getInstance().addImage(file.getAbsolutePath(), getMetadata(file));
//					if(ImportXMLDomHandler.getInstance().getImageByPath(file.getAbsolutePath()) != null) {
//						System.out.println("Image " + file.getAbsolutePath() + " schon vorhanden");
//						continue;
//					}
//					ImportXMLDomHandler.getInstance().addImport(file.getAbsolutePath(), getMetadata(file));
					if(ImportDBMySQLHandler.getInstance().isImported(file.getAbsolutePath())) {
						System.out.println(file.getAbsolutePath() + " schon importiert");
						continue;
					}
					ImportDBMySQLHandler.getInstance().addImport(file.getAbsolutePath(), ImageAnalyser.getInstance().getImageAttributes(file));
				}
			}
		}
	}
	
	
}
