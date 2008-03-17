package test;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.CommentEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.GphotoFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.data.photos.TagEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class PicasaTest {
	
//	private static final String API_PREFIX = "http://picasaweb.google.com/data/feed/api/user/";
//
//	private final PicasawebService service;
	
//	http://picasaweb.google.com/data/feed/api/user/userID/albumid/albumID/photoid/photoID?kind=kinds
	
//	public PicasaTest(PicasawebService service) {
//		this(service, null, null);
//	}

	public static void main(String[] args) throws Exception {
//		URL albumAndPhotosUrl = new 
//		URL("http://picasaweb.google.com/data/feed/api/user/christian.pressler/album/NetherfieldPark?kind=photo");
		PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
//		System.out.println(myService.getServiceVersion() + " - " + myService.getContentType());
		
//
//		// Send the request for the album's photos.
//		AlbumFeed myAlbumFeed = myService.getFeed(albumAndPhotosUrl, AlbumFeed.class);
//
//		// Print the title of the returned feed:
//		System.out.println(myAlbumFeed.getTitle().getPlainText());
		
		
//		URL feedUrl = new URL("http://picasaweb.google.com/data/feed/projection/all?kind=photo&q=sonne");
		URL feedUrl = new URL("http://picasaweb.google.com/data/feed/api/all?q=penguin");
		
		Query myQuery = new Query(feedUrl);
//		myQuery.setMaxResults(10);

		// Send the request and receive the response:
		
		PhotoFeed resultFeed = myService.query(myQuery, PhotoFeed.class);
		List<PhotoEntry> photos = new ArrayList<PhotoEntry>();
	    for (GphotoEntry entry : resultFeed.getEntries()) {
	      GphotoEntry adapted = entry.getAdaptedEntry();
	      if (adapted instanceof PhotoEntry) {
	        photos.add((PhotoEntry) adapted);
	      }
	    }
	    for(PhotoEntry pho : photos) {
	    	System.out.println( "??? " + pho.getMediaContents().get(0).getUrl()); 
//	    	System.out.println(pho.getSelfLink().getHref() + "/" + pho.getTitle().getPlainText());
//	    	try {
//	    		ImageIO.read(new URL("pho.getHtmlLink().getHref()"));
//	    	} catch (Exception ex) {
//	    		ex.printStackTrace();
//	    	}
	    }
		// Print the title of the returned feed:
		System.out.println(resultFeed.getTitle().getPlainText());
	}

}
