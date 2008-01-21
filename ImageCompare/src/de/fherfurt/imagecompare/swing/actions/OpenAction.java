package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;

public class OpenAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	public OpenAction() {
		putValue(Action.NAME, "set ImageBase");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("open")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("open"));
	}

	public void actionPerformed(ActionEvent arg0) {
		new Thread(new Runnable() {
			public void run() {
				try {
					JFileChooser fileChooser = new JFileChooser(".");
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					//Filter
//					fileChooser.addChoosableFileFilter(new FileFilter() {
//						@Override
//						public boolean accept(File f) {
//							if(f.getName().endsWith("jpg")) {
//								return true;
//							} else {
//								return false;
//							}
//						}
//						@Override
//						public String getDescription() {
//							return "JPG only";
//						}});
		            int status = fileChooser.showOpenDialog(null);
		            if (status == JFileChooser.APPROVE_OPTION) {
//		            	System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
		            	File f = new File(fileChooser.getSelectedFile().getAbsolutePath());
//		            	System.out.println(f);
		                ImageBase.getInstance().setImageBase(f);
		            }
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JDialog(), e1.getMessage(),
							"Exception", JOptionPane.ERROR_MESSAGE);
				}
			}}).start();
	}

}
