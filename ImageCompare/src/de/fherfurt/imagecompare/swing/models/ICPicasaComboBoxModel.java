package de.fherfurt.imagecompare.swing.models;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;

import de.fherfurt.imagecompare.ResourceHandler;

public class ICPicasaComboBoxModel extends DefaultComboBoxModel {
	
	private static final long serialVersionUID = 1L;
	List<AlbumEntry> albums; 
	
	public ICPicasaComboBoxModel() throws Exception {
			ArrayList<String> urls = new ArrayList<String>();
		PicasawebService myService = new PicasawebService(
				"exampleCo-exampleApp-1");

		myService.setUserCredentials(ResourceHandler.getInstance().getStrings()
				.getString("user"), ResourceHandler.getInstance().getStrings()
				.getString("pw"));

		URL albumAndPhotosUrl = new URL(
				"http://picasaweb.google.com/data/feed/api/user/"
						+ ResourceHandler.getInstance().getStrings().getString(
								"user") + "/?kind=album");
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
