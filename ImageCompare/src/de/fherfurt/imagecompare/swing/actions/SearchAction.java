package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.SearchComponent;

public class SearchAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	public SearchAction() {
		putValue(Action.NAME, "search");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("search")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("search"));
	}

	public void actionPerformed(ActionEvent arg0) {
		new SearchComponent();
	}

}
