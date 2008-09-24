package de.fherfurt.imagecompare.swing.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;

import de.fherfurt.imagecompare.ResourceHandler;
import de.fherfurt.imagecompare.swing.components.SettingsFrame;

public class ICPicasaComboBoxModel extends DefaultComboBoxModel {
	
	private static final long serialVersionUID = 1L;
	List<AlbumEntry> albums; 
	
	public ICPicasaComboBoxModel() throws Exception {
			ArrayList<String> urls = new ArrayList<String>();
		PicasawebService myService = new PicasawebService(
				"exampleCo-exampleApp-1");

		myService.setUserCredentials(SettingsFrame.getInstance().getProperties().getProperty("picasauser"), SettingsFrame.getInstance().getProperties().getProperty("picasapw"));

		URL albumAndPhotosUrl = new URL(
				"http://picasaweb.google.com/data/feed/api/user/"
						+ SettingsFrame.getInstance().getProperties().getProperty("picasauser") + "/?kind=album");
		UserFeed myUserFeed = myService.getFeed(albumAndPhotosUrl,
				UserFeed.class);

		albums = myUserFeed.getAlbumEntries();

		System.out.println(albums.size());
	}

	@Override
	public Object getElementAt(int index) {
		return albums.get(index).getName();
	}

	@Override
	public int getSize() {
		return albums.size();
	}
	
}
