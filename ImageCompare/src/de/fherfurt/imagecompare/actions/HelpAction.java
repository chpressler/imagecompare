package de.fherfurt.imagecompare.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class HelpAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public HelpAction() {
		putValue(Action.NAME, "help");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("helpT")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("help_help"));
	}

	public void actionPerformed(ActionEvent arg0) {
		try {
			Runtime.getRuntime().exec("cmd.exe /c start http://www.mokkafish.com");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
