package com.xmljson.converters;

import java.io.File;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Utilities Class contains methods for file read/write 
 * and Json reading and xml from json conversion.
 * 
 * @author Suranya 
 *
 */
public class JSONMethods {
	
	private static final Logger LOGGER = Logger.getLogger (JSONMethods.class.getName ());
	
	//XML Dom
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder; 
	private Document doc;
	
	/**
	 * xmlConverter reads json from an input string and converts it to
	 * JsonObject or JsonArray based on the initial token '[' or '{'
	 * 
	 * @param json String containing the json read from file
	 * @return String containing the xml converted from json
	 */
	public void xmlConverter (String json, File xml) {
		JsonStructure object = null;
		try {
			JsonReader jsonReader = Json.createReader(new StringReader (json));
			object = jsonReader.read();
		} catch (JsonException e) {
			LOGGER.log (Level.SEVERE, "Exception while parsing JSON. Error in syntax or unexpected beginning tokens, not [ or { ");
			LOGGER.log (Level.SEVERE, "Cannot convert file ");
			return;
		}
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOGGER.log (Level.SEVERE, "ParserConfigurationException while making XML Document builder");
			LOGGER.log (Level.SEVERE, "Cannot convert file ");
			return;
		}
		doc = docBuilder.newDocument();		
		jsonValueToXml (object, null, null);		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xml);
		try {
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);		
		} catch (TransformerException e) {
			LOGGER.log (Level.SEVERE, "Exception while making file from XML");
			LOGGER.log (Level.SEVERE, "Cannot write XML file ");
			return;
		}
		
	}
	
	/**
	 * jsonValueToXml recursively traverses through the given JsonValue
	 * and converts it into an xml string 
	 * 
	 * @param jsonValue JsonValue object which can be a json object, array, string, number,
	 * 		  true, false or null. 
	 * @param key Key corresponding to jsonValue
	 * @return String containing the xml converted from json
	 */
	private void jsonValueToXml (JsonValue jsonValue, String key, Element parent) {
		switch (jsonValue.getValueType ()) {		
			case OBJECT:
				Element obj = doc.createElement("object");
				if (parent == null) {
					doc.appendChild(obj);
				} else {
					parent.appendChild(obj);
				}
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					attr.setValue(key);
					obj.setAttributeNode(attr);
				} 
				JsonObject jsonObject = (JsonObject) jsonValue;
		        for (String name : jsonObject.keySet ())
		        	jsonValueToXml (jsonObject.get (name), name, obj);
		        break;
		        
			case ARRAY:
				Element arr = doc.createElement("array");
				if (parent == null) {
					doc.appendChild(arr);
				} else {
					parent.appendChild(arr);
				}				
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					attr.setValue(key);
					arr.setAttributeNode(attr);
			     }
		         JsonArray array = (JsonArray) jsonValue;
		         for (JsonValue val : array)
		        	 jsonValueToXml (val, null, arr);
		         break;
		         
			case STRING:
				Element str = doc.createElement("string");
				JsonString string = (JsonString) jsonValue;
				str.appendChild(doc.createTextNode(string.getString ()));
				parent.appendChild(str);		         
		         if (key != null) {
		        	 Attr attr = doc.createAttribute("name");
					 attr.setValue(key);
					 str.setAttributeNode(attr); 
		         } 
		         break;
		         
			case NUMBER:
				 Element number = doc.createElement("number");
			     JsonNumber num = (JsonNumber) jsonValue;
			     number.appendChild(doc.createTextNode(num.toString ()));
				 parent.appendChild(number);
			     if (key != null) {
			    	 Attr attr = doc.createAttribute("name");
					 attr.setValue(key);
					 number.setAttributeNode(attr); 
		         } 
		         break;
		         
			case TRUE:
				Element bool = doc.createElement("boolean");
				bool.appendChild(doc.createTextNode("true"));
				parent.appendChild(bool);
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					 attr.setValue(key);
					 bool.setAttributeNode(attr); 
		         } 
				break;
	    	  
			case FALSE:
				Element boolFalse = doc.createElement("boolean");
				boolFalse.appendChild(doc.createTextNode("false"));
				parent.appendChild(boolFalse);
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					 attr.setValue(key);
					 boolFalse.setAttributeNode(attr); 
		        } 
				break;
				
			case NULL:
				Element nullValue = doc.createElement("null");
				parent.appendChild(nullValue);
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					 attr.setValue(key);
					 nullValue.setAttributeNode(attr); 
		        } 
				break;
		}
	}

}
