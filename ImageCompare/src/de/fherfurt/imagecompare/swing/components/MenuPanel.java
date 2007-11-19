package de.fherfurt.imagecompare.swing.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lowagie.text.pdf.TextField;

import de.fherfurt.imagecompare.ImageBase;

public class MenuPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tf;
	
	private JButton b;

	public MenuPanel() {
		tf = new JTextField(10);
		b = new JButton("choos Folder");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fc = new JFileChooser();
					int ret = fc.showOpenDialog(null);
//					fc.addChoosableFileFilter(new FileFilter() {
//						public boolean accept(File f) {
//							if (f.isDirectory())
//								return true;
//							return f.getName().toLowerCase().endsWith(".jpg");
//						}
//
//						public String getDescription() {
//							return "JPG Images";
//						}
//					});
					fc.setMultiSelectionEnabled(false);
					//fc.setCurrentDirectory(new File("resources/"));
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (ret == JFileChooser.APPROVE_OPTION) {
						ImageBase.getInstance().setImageBase(new File("C:/Dokumente und Einstellungen/Christian/Desktop/swingx/swingx/images"));//fc.getSelectedFile().getAbsolutePath()));
					}
					if (ret == JFileChooser.CANCEL_OPTION) {
						
					}

				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(new JDialog(), e1.getMessage(),
							"Exception", JOptionPane.ERROR_MESSAGE);
				}
			}});
		add(tf);
		add(b);
	}

}
