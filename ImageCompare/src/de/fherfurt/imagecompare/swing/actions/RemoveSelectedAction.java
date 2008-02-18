package de.fherfurt.imagecompare.swing.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;
import de.fherfurt.imagecompare.swing.components.PreviewThumbnailComponent;

public class RemoveSelectedAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		for(Component c : PreviewThumbnailComponent.getInstance().getComponents()) {
			if(((ImageThumbnailComponent) c).isSelected()) {
				ImageBase.getInstance().remove(((ImageThumbnailComponent) c).getPath());
			}
		}
	}

}
