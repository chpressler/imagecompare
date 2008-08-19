package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.event.ListDataListener;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.data.photos.UserFeed;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.PicasaFrame;
import de.fherfurt.imagecompare.swing.models.ICPicasaComboBoxModel;

public class PicasaAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public PicasaAction() {
		putValue(Action.NAME, "Picasa");
		putValue(Action.SMALL_ICON, new ImageIcon("resources/icons/"
				+ ResourceHandler.getInstance().getIcons().getString("picasa")));
		putValue(Action.SHORT_DESCRIPTION, ResourceHandler.getInstance()
				.getStrings().getString("picasa"));
	}

	public void actionPerformed(ActionEvent arg0) {
		PicasaFrame.getInstance().setVisible(true);
	}
}