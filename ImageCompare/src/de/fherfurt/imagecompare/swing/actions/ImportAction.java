package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;

public class ImportAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public ImportAction() {
		putValue(Action.NAME, "import");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("import")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("import"));
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
