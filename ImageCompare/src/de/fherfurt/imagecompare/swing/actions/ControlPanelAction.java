package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.ResourceHandler;

public class ControlPanelAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	public ControlPanelAction() {
		putValue(Action.NAME, "ControlPanel");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("search")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("controlpanel"));
	}

	public void actionPerformed(ActionEvent arg0) {
		if(	((JFrame) SwingUtilities.getRoot( (JComponent)arg0.getSource() )).getGlassPane().isVisible()) {
			((JFrame) SwingUtilities.getRoot( (JComponent)arg0.getSource() )).getGlassPane().setVisible(false);
		} else {
			((JFrame) SwingUtilities.getRoot( (JComponent)arg0.getSource() )).getGlassPane().setVisible(true);
			((JFrame) SwingUtilities.getRoot( (JComponent)arg0.getSource() )).getGlassPane().setEnabled(false);
		}
	}

}
