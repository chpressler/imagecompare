package de.fherfurt.imagecompare.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.actions.HelpAction;

public class ImageCompareMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	JFrame jframe = null;
	
	JMenu file = new JMenu(ResourceHandler.getInstance().getStrings().getString("file"));
	
	JMenuItem file_exit = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("exit"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("exit")));
	
	JMenuItem file_open = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("open"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("open")));
	
	JMenuItem file_save = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("save"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("save")));
	
	JMenuItem file_save_as = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("save_as"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("save_as")));
	
	JMenu edit = new JMenu(ResourceHandler.getInstance().getStrings().getString("edit"));

	JMenu laf = new JMenu(ResourceHandler.getInstance().getStrings().getString("laf"));
	
	JMenuItem edit_undo = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("undo"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("undo")));
	
	JMenuItem edit_cut = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("cut"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("cut")));
	
	JMenuItem edit_copy = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("copy"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("copy")));
	
	JMenuItem edit_paste = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("paste"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("paste")));
	
	JMenuItem edit_delete = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("delete"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("delete")));

	JMenuItem edit_properties = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("properties"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("properties")));
	
	JMenu help = new JMenu(ResourceHandler.getInstance().getStrings().getString("help"));
	
	JMenuItem help_help = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("help_help"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("help_help")));
	
	JMenuItem help_about = new JMenuItem(ResourceHandler.getInstance().getStrings().getString("about"), new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("about")));

	public ImageCompareMenu(final JFrame jf) {

		putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		
		file_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}});
		file.add(file_open);
		file.add(file_save);
		file.add(file_save_as);
		file.addSeparator();
		file.add(file_exit);

		JMenuItem jmi;
		int number = UIManager.getInstalledLookAndFeels().length;
		String[] plaf_names = new String[number];
		for (int a = 0; a < number; a++) {
			jmi = new JMenuItem(UIManager.getInstalledLookAndFeels()[a]
					.getName());
			final String LAFClassName = UIManager.getInstalledLookAndFeels()[a]
					.getClassName();
			jmi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						UIManager.setLookAndFeel(LAFClassName);
						SwingUtilities.updateComponentTreeUI(jf);
					} catch (Exception e3) {
						e3.printStackTrace();
						final JDialog jd = new JDialog();
						JOptionPane.showMessageDialog(jd,
							    e3.getMessage(),
							    "Exception",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			laf.add(jmi);
		}

		edit.add(edit_undo);
		edit.addSeparator();
		edit.add(edit_cut);
		edit.add(edit_copy);
		edit.add(edit_paste);
		edit.add(edit_delete);
		edit.addSeparator();
		edit.add(laf);
		edit.add(edit_properties);

		add(file);
		add(edit);
		
		help_help.addActionListener(new HelpAction());
		help.add(help_help);
		help.addSeparator();
		help_about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JDialog jd = new JDialog();
				JOptionPane.showMessageDialog(jd,
						"Sirius Client\nVersion 1.0\n\n(C) mokkafish 2007 - mokkafish.com", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		help.add(help_about);
		add(help);
	}

	public JFrame getJframe() {
		return jframe;
	}

	public void setJframe(JFrame jframe) {
		this.jframe = jframe;
	}

}
