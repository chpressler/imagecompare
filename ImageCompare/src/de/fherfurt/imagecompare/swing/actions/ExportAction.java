package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;


public class ExportAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public ExportAction() {
		putValue(Action.NAME, "export");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/" + ResourceHandler.getInstance().getIcons().getString("export")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance().getStrings().getString("export"));
	}

	public void actionPerformed(ActionEvent arg0) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				//JOptionPane mit DropDown FS, FTP, Email...
				//if(FS) {
				//FSExport fse = new FSExport();
				//fse.exportImageBase();
				//}...
				ImageBase.getInstance().exportToDir("C:/export");
			}}).start();
	}

}
