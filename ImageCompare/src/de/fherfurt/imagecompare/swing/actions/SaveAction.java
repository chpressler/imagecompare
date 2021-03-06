package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class SaveAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public SaveAction() {
		putValue(Action.NAME, "save");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("save")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("save"));
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
