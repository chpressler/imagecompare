package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.fherfurt.imagecompare.ResourceHandler;

public class SavePropertiesAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	Properties props;

	public SavePropertiesAction(Properties props) {
		this.props = props;
		putValue(Action.NAME, "save");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("save")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("save"));
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			Date d = new Date();
			props.store(new FileOutputStream(new File("resources/preferences")), "edited in SettingsFrame: " + d.toString());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

}