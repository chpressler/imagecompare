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
import javax.swing.filechooser.FileFilter;

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
					JFileChooser fileChooser = new JFileChooser(".");
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
			}});
		add(tf);
		add(b);
	}

}
