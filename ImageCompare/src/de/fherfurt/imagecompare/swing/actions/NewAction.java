package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class NewAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public NewAction() {
		putValue(Action.NAME, "new");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("newT")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("new"));
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
