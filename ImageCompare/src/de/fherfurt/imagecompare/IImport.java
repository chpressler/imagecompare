package de.fherfurt.imagecompare;

import java.util.HashMap;

public interface IImport {
	
	void addImport(String absolutePath, HashMap<String, String> metadata);

}
