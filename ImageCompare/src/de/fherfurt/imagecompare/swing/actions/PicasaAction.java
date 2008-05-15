package de.fherfurt.imagecompare.swing.actions;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.data.photos.UserFeed;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ResourceHandler;

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
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ArrayList<String> urls = new ArrayList<String>();
					PicasawebService myService = new PicasawebService(
							"exampleCo-exampleApp-1");
					
					myService.setUserCredentials(ResourceHandler.getInstance().getStrings().getString("user"), ResourceHandler.getInstance().getStrings().getString("pw"));
					
					URL albumAndPhotosUrl = new 
					  URL("http://picasaweb.google.com/data/feed/api/user/christian.pressler/album/CalvinJody?kind=photo");
					UserFeed myUserFeed = myService.getFeed(albumAndPhotosUrl, UserFeed.class);
//					System.out.println(myUserFeed.getAlbumEntries().get(0).getTitle().getPlainText());
//					for(AlbumEntry ae : myUserFeed.getAlbumEntries()) {
//						
//					}
					
					Query myQuery = new Query(albumAndPhotosUrl);
//					myQuery.setMaxResults(50);
					
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
	}
}

class PicasaFrame extends JFrame {
	
	public PicasaFrame() {
		
	}
	
}
