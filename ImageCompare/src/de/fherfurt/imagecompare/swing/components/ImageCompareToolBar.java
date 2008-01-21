package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;

import javax.swing.JToolBar;

import com.jgoodies.looks.HeaderStyle;
import com.jgoodies.looks.Options;

import de.fherfurt.imagecompare.swing.actions.ExportAction;
import de.fherfurt.imagecompare.swing.actions.HelpAction;
import de.fherfurt.imagecompare.swing.actions.ImportAction;
import de.fherfurt.imagecompare.swing.actions.NewAction;
import de.fherfurt.imagecompare.swing.actions.OpenAction;
import de.fherfurt.imagecompare.swing.actions.SaveAction;
import de.fherfurt.imagecompare.swing.actions.SaveAsAction;
import de.fherfurt.imagecompare.swing.actions.SearchAction;
import de.fherfurt.imagecompare.swing.actions.SortAction;

public class ImageCompareToolBar extends JToolBar {
	
	private static final long serialVersionUID = 1L;

	public ImageCompareToolBar() {
		setBackground(new Color(80, 30, 30));
		putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.BOTH);
		setFloatable(true);
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
		this.add(new SearchAction());
		this.add(new SortAction());
		this.addSeparator();
		this.add(new HelpAction());
	}

}
