package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class UndoAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public UndoAction() {
		putValue(Action.NAME, "undo");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("undo")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("undo"));
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
