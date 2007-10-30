package de.fherfurt.imagecompare.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class OpenAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public OpenAction() {
		putValue(Action.NAME, "open");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("open")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("open"));
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
