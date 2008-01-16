package de.fherfurt.imagecompare;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class ImportXMLStaXHandler {
	
	private static volatile ImportXMLStaXHandler instance;
	
	XMLOutputFactory factory = XMLOutputFactory.newInstance(); 
	
	FileOutputStream fos;
	
	XMLStreamWriter writer;
	
	private ImportXMLStaXHandler() {
		
	}
	
	public static ImportXMLStaXHandler getInstance() {
		if(instance == null) {
			synchronized (ImportXMLStaXHandler.class) {
				if(instance == null) {
					instance = new ImportXMLStaXHandler();
				}
			}
		}
		return instance;
	}
	
	public void startDoc() {
		try {
//			File f = new File(p.getProperty("jarpath") + "/" + pa + "/" + serial + "/protocol_run.xml");
//			f.createNewFile();
//			System.out.println(f.getAbsolutePath());
//			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(
//					new FileOutputStream(f), "UTF-8");
//
//			writer.writeStartDocument("UTF-8", "1.0");
			File file = new File("import.xml");
			if(file.exists()) {
				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file, false);
			writer = factory.createXMLStreamWriter(fos, "UTF-8");

			writer.writeStartDocument("UTF-8", "1.0");
			
//			writer = factory.createXMLStreamWriter(fos);
//			
//			writer.writeStartDocument();
			writer.writeStartElement("images");
			
//			writer.close();
		} catch (Exception e) {

		}
	}
	
	public void closeDoc() {
		try {
//			File file = new File("import.xml");
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file, true);
//			XMLStreamWriter writer = factory.createXMLStreamWriter(fos);
			
			writer.writeEndElement();
			writer.writeEndDocument();
			
			writer.flush();
			writer.close();
			fos.close();
		} catch (Exception e) {

		}
	}
	
	public void addImage(String abs_path, HashMap<String, String> metadata) {
		try {
//			File file = new File("import.xml");
//			file.createNewFile();
//			FileOutputStream fos = new FileOutputStream(file, true);
//			XMLStreamWriter writer = factory.createXMLStreamWriter(fos);
			
			writer.writeStartElement("image");
			writer.writeAttribute("path", abs_path);
			
			writer.writeStartElement("attributes");
			
			Iterator<String> iter = metadata.keySet().iterator();
			while(iter.hasNext()) {
				String s = iter.next().toString();
				writer.writeStartElement("imageAtt");
				writer.writeAttribute("name", s);
				if(!s.contains("humbnail")) {
					try {
						writer.writeAttribute("value", metadata.get(s).trim());
					} catch(Exception e) {
						writer.writeAttribute("value", "");
					}
				}
				writer.writeEndElement();
			}
			
			writer.writeEndElement();
			writer.writeEndElement();
			
//			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("scheisse");
		}
	}
}
