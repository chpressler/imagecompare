package de.fherfurt.imagecompare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.util.DOMUtil;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ImportXMLDomHandler {
	
	private static volatile ImportXMLDomHandler instance;
	
	private Document doc = null;

	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	public static synchronized ImportXMLDomHandler getInstance() {
		if(instance == null) {
			synchronized (ImportXMLDomHandler.class) {
				if (instance == null) {
					instance = new ImportXMLDomHandler();
				}
			}
		}
		return instance;
	}
	
	private ImportXMLDomHandler() {
		if (doc == null) {
			try {
				doc = dbf.newDocumentBuilder().parse(new File("import.xml"));
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			removeTextNodes(doc);
		}
	}
	
	public void save() {
		try {
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			XMLSerializer output = new XMLSerializer(new FileOutputStream(
					new File("import.xml")), format);
			output.serialize(doc);
		}
		catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	public void addImport(String absPath, HashMap<String, String> metadata) {
		
		Node image = doc.createElement("image");
		Attr id = doc.createAttribute("id");
		Attr path = doc.createAttribute("path");
		id.setNodeValue(Long.toString(getHighestId() + 1));
		path.setNodeValue(absPath);
		image.getAttributes().setNamedItem(id);
		image.getAttributes().setNamedItem(path);
		
		Node imageAttributes = doc.createElement("imageAttributes");
		
		Iterator iter = metadata.keySet().iterator();
		while(iter.hasNext()) {
			String s = iter.next().toString();
			Node imageAtt = doc.createElement("imageAtt");
			Attr name = doc.createAttribute("name");
			Attr value = doc.createAttribute("value");
			name.setNodeValue(s);
			try {
				value.setNodeValue(metadata.get(s).trim());
			} catch(Exception e) {
				value.setNodeValue("");
			}
			imageAtt.getAttributes().setNamedItem(name);
			imageAtt.getAttributes().setNamedItem(value);
			imageAttributes.appendChild(imageAtt);
		}
		
		image.appendChild(imageAttributes);
		doc.getDocumentElement().getFirstChild().appendChild(image);
	}
	
	private static void removeTextNodes(Node node) {
		if (!node.hasChildNodes()) {
			return;
		} else {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				if ((node.getChildNodes().item(i)).getNodeType() != Document.TEXT_NODE) {
					removeTextNodes((node.getChildNodes().item(i)));
				} else {
					node.removeChild((node.getChildNodes().item(i)));
					i--;
				}
			}
		}
	}

	private static void removeAllChildren(Node node) {
		if (!node.hasChildNodes()) {
			return;
		} else {
			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				removeTextNodes((node.getChildNodes().item(i)));
				node.removeChild((node.getChildNodes().item(i)));
				i--;
			}
		}
	}
	
	public long getHighestId() {
		long id = 0;
		ArrayList<Node> al = new ArrayList<Node>();
		getAllNodes(al, doc.getDocumentElement());
		for (int i = 0; i < al.size(); i++) {
			if (!getId((Node) al.get(i)).equalsIgnoreCase("")) {
				if (Long.parseLong(getId((Node) al.get(i))) > id) {
					id = Long.parseLong(getId((Node) al.get(i)));
				}
			}
		}
		return id;
	}
	
	public String getId(Node n) {
		if(n.getAttributes() == null) {
			return "";
		}
		for (int i = 0; i < n.getAttributes().getLength(); i++) {
			if (n.getAttributes().item(i).getNodeName().equalsIgnoreCase("id")) {
				return n.getAttributes().item(i).getNodeValue();
			}
		}
		return "";
	}
	
	public Node getNodeById(String id) {
		ArrayList<Node> al = new ArrayList<Node>();
		getAllNodes(al, doc.getDocumentElement());
		for (int i = 0; i < al.size(); i++) {
			if (getId((Node) al.get(i)).equals(id)) {
				return (Node) al.get(i);
			}
		}
		return null;
	}
	
	public Node getImageByPath(String path) {
		ArrayList<Node> al = new ArrayList<Node>();
		getAllNodes(al, doc.getDocumentElement());
		for (int i = 0; i < al.size(); i++) {
			if (((Node) al.get(i)).getNodeName().equals("image")) {
				if( ((Node) al.get(i)).getAttributes().getNamedItem("path").getNodeValue().equals(path) ) {
					return (Node) al.get(i);
				}
			}
		}
		return null;
	}

	private void getAllNodes(ArrayList<Node> results, Node root) {
		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			getAllNodes(results, root.getChildNodes().item(i));
		}
		results.add(root);
	}

	public ArrayList<Node> getAllNodes(Node root) {
		ArrayList<Node> al = new ArrayList<Node>();
		getAllNodes(al, root);
		return al;
	}

	public ArrayList<Node> getAllNodes() {
		ArrayList<Node> al = new ArrayList<Node>();
		getAllNodes(al, doc.getDocumentElement());
		return al;
	}

}
