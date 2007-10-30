package de.fherfurt.imagecompare.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class SaveAsAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public SaveAsAction() {
		putValue(Action.NAME, "save_as");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("save_as")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("save_as"));
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
