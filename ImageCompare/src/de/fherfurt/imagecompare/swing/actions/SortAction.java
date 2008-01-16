package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.SortComponent;

public class SortAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	public SortAction() {
		putValue(Action.NAME, "sort");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("sort")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("sort"));
	}

	public void actionPerformed(ActionEvent arg0) {
		new SortComponent();
	}

}
