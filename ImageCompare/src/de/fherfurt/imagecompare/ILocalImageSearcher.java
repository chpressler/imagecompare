package de.fherfurt.imagecompare;

import java.util.ArrayList;

public interface ILocalImageSearcher {

	public ArrayList<String> getResults(String pattern);
	
	public void deleteImage(String path);
	
}
