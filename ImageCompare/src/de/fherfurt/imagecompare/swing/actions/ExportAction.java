package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.ExportFrame;

public class ExportAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public ExportAction() {
		putValue(Action.NAME, "export");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("export")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("export"));
	}

	public void actionPerformed(ActionEvent arg0) {
		new ExportFrame();
	}

}
