package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaXReaderTest {
	
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("import.xml"));
		
		while(reader.hasNext()) {
			if(reader.next() == reader.START_ELEMENT) {
				if (reader.getName().toString().equals("imageAtt")) {
						if (reader.getAttributeValue(1).equalsIgnoreCase(
								"false")) {
							System.out.println("qqq");
						}
				}
			}
		}

	}

}
