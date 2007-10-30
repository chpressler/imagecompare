package de.fherfurt.imagecompare.components;

import javax.swing.JToolBar;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;

import de.fherfurt.imagecompare.actions.ExportAction;
import de.fherfurt.imagecompare.actions.HelpAction;
import de.fherfurt.imagecompare.actions.ImportAction;
import de.fherfurt.imagecompare.actions.NewAction;
import de.fherfurt.imagecompare.actions.OpenAction;
import de.fherfurt.imagecompare.actions.SaveAction;
import de.fherfurt.imagecompare.actions.SaveAsAction;
import de.fherfurt.imagecompare.actions.UndoAction;

public class ImageCompareToolBar extends JToolBar {
	
	private static final long serialVersionUID = 1L;

	public ImageCompareToolBar() {
		putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		setFloatable(false);
        putClientProperty("JToolBar.isRollover", Boolean.TRUE);
        
		this.add(new NewAction());
		this.add(new OpenAction());
		this.addSeparator();
		this.add(new SaveAction());
		this.add(new SaveAsAction());
		this.addSeparator();
		this.add(new ImportAction());
		this.add(new ExportAction());
		this.addSeparator();
		this.add(new UndoAction());
		this.addSeparator();
		this.add(new HelpAction());
	}

}
