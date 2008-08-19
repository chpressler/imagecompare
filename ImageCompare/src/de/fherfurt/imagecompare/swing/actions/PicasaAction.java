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
		new PicasaFrame();
	}
}

class PicasaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JComboBox cb;
	
	public PicasaFrame() {
		super("PicasaWebAlbums");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(300, 50);
		try {
			cb = new JComboBox(new ICPicasaComboBoxModel());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		cb.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				System.out.println(e.getItem());
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							ArrayList<String> urls = new ArrayList<String>();
							PicasawebService myService = new PicasawebService(
									"exampleCo-exampleApp-1");
							
							myService.setUserCredentials(ResourceHandler.getInstance().getStrings().getString("user"), ResourceHandler.getInstance().getStrings().getString("pw"));
							
							URL albumAndPhotosUrl = new URL("http://picasaweb.google.com/data/feed/api/user/christian.pressler/album/" + e.getItem() + "?kind=photo");
							UserFeed myUserFeed = myService.getFeed(albumAndPhotosUrl, UserFeed.class);
//							System.out.println(myUserFeed.getAlbumEntries().get(0).getTitle().getPlainText());
//							for(AlbumEntry ae : myUserFeed.getAlbumEntries()) {
//								
//							}
							
							Query myQuery = new Query(albumAndPhotosUrl);
//							myQuery.setMaxResults(50);
							
							PhotoFeed resultFeed = myService.query(myQuery,
									PhotoFeed.class);
							
							List<PhotoEntry> photos = new ArrayList<PhotoEntry>();
							
							for (GphotoEntry entry : resultFeed
									.getEntries()) {
								
								GphotoEntry adapted = entry
										.getAdaptedEntry();
								if (adapted instanceof PhotoEntry) {
									photos.add((PhotoEntry) adapted);
								}
							}
							for (PhotoEntry pho : photos) {
								urls.add(pho.getMediaContents().get(0).getUrl().toString());
							}
							ImageBase.getInstance().setImageBase(urls);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}}).start();
			}});
		getContentPane().add(cb);
//		pack();
		setVisible(true);
	}
	
}