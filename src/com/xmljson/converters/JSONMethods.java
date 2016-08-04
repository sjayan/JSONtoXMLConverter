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
 * Utilities Class contains methods for 
 * JSON reading and JSON to XML conversion.
 * 
 * @author Suranya 
 *
 */
public class JSONMethods {
	
	private static final Logger LOGGER = Logger.getLogger (JSONMethods.class.getName ());
	
	//Needed for constructing XML Document
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder; 
	private Document doc;
	
	/**
	 * xmlConverter reads json from an input string and converts it to
	 * JsonObject or JsonArray based on the initial token '[' or '{'
	 * Creates XML Document from this JSON
	 * Writes XML Document ot file
	 * 
	 * @param json String containing the json read from file
	 * @param xml Output file for writing XML to
	 * 
	 */
	public void xmlConverter (String json, File xml) {
		JsonStructure object = null;
		try {
			JsonReader jsonReader = Json.createReader(new StringReader (json));
			object = jsonReader.read();
		} catch (JsonException e) {
			//Exceptions occur if the json string doesnt follow json grammer or
			//if the json doesnt start with a json array or json object
			LOGGER.log (Level.SEVERE, "Exception while parsing JSON. Error in syntax or unexpected beginning tokens, not [ or { ");
			LOGGER.log (Level.SEVERE, "Cannot convert file ");
			return;
		}
		//Instantiating classes need for creating an XML Document
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			//in case exception occurs xml document cannot be created
			LOGGER.log (Level.SEVERE, "ParserConfigurationException while making XML Document builder");
			LOGGER.log (Level.SEVERE, "Cannot convert file ");
			return;
		}
		doc = docBuilder.newDocument();	
		//Method recursively goes throw a json adds corresponding elements to a XML Document
		jsonValueToXml (object, null, null);		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		DOMSource source = new DOMSource(doc);
		//Setting the result to be the xml file given as input
		StreamResult result = new StreamResult(xml);
		try {
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);		
		} catch (TransformerException e) {
			//if the file path is invalid, it will be caught here
			LOGGER.log (Level.SEVERE, "Exception while making file from XML");
			LOGGER.log (Level.SEVERE, "Cannot write XML file ");
			return;
		}
		//XML file created successfully
		LOGGER.log (Level.INFO, "XML written to file : " + xml.getName());
	}
	
	/**
	 * jsonValueToXml recursively traverses through the given Json
	 * and add it as the corresponding element to the XML document 
	 * 
	 * @param jsonValue JsonValue object which can be a json object, array, string, number,
	 * 		  true, false or null. 
	 * @param key Key corresponding to jsonValue
	 * @param parent Parent element for the current element in XML
	 * 
	 */
	private void jsonValueToXml (JsonValue jsonValue, String key, Element parent) {
		switch (jsonValue.getValueType ()) {		
			case OBJECT:
				//creating an element in the XML file
				Element obj = doc.createElement("object");
				//If parent is null, this object will be the root element
				if (parent == null) {
					doc.appendChild(obj);
				} else {
					parent.appendChild(obj);
				}
				//creating attribute name with value key, if key is provided
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					attr.setValue(key);
					obj.setAttributeNode(attr);
				} 
				//Recursively calling this method for all of obj's children
				JsonObject jsonObject = (JsonObject) jsonValue;
		        for (String name : jsonObject.keySet ())
		        	jsonValueToXml (jsonObject.get (name), name, obj);
		        break;
		        
			case ARRAY:
				//creating element array
				Element arr = doc.createElement("array");
				//if parent is null, this array is the root element
				if (parent == null) {
					doc.appendChild(arr);
				} else {
					parent.appendChild(arr);
				}
				//making attribute name for the key
				if (key != null) {
					Attr attr = doc.createAttribute("name");
					attr.setValue(key);
					arr.setAttributeNode(attr);
			     }
		         JsonArray array = (JsonArray) jsonValue;
		         //Recursively calling this method for all of array's children
		         for (JsonValue val : array)
		        	 jsonValueToXml (val, null, arr);
		         break;
		         
			case STRING:
				//creating element string and attribute if key is provided
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
				//creating element number and attribute if key is provided
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
				//creating element boolean true and attribute if key is provided
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
				//creating element boolean false and attribute if key is provided
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
				//creating element null and attribute if key is provided
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
