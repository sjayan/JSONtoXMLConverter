package com.xmljson.converters;

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

/**
 * Utilities Class contains methods for file read/write 
 * and Json reading and xml from json conversion.
 * 
 * @author Suranya 
 *
 */
public class JSONMethods {
	
	private static final Logger LOGGER = Logger.getLogger (JSONMethods.class.getName ());
	//String used for constructing xml in jsonValueToXml
	private StringBuilder xmlString = new StringBuilder ();
	
	/**
	 * xmlConverter reads json from an input string and converts it to
	 * JsonObject or JsonArray based on the initial token '[' or '{'
	 * 
	 * @param json String containing the json read from file
	 * @return String containing the xml converted from json
	 */
	public String xmlConverter (String json) {
		JsonStructure object = null;
		try {
			JsonReader jsonReader = Json.createReader(new StringReader (json));
			object = jsonReader.read();
		} catch (JsonException e) {
			LOGGER.log (Level.SEVERE, "Exception while parsing JSON. Error in syntax or unexpected beginning tokens, not [ or { ");
			return "";
		}
		xmlString.setLength(0);
		jsonValueToXml (object, null);
		return xmlString.toString();
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
	public void jsonValueToXml (JsonValue jsonValue, String key) {
		switch (jsonValue.getValueType ()) {		
			case OBJECT:
				if (key != null) {
					xmlString.append ("<object name=\"" + key + "\">");
				} else {
					xmlString.append ("<object>");
			    }
				JsonObject jsonObject = (JsonObject) jsonValue;
		        for (String name : jsonObject.keySet ())
		        	jsonValueToXml (jsonObject.get (name), name);
		        xmlString.append ("</object>");
		        break;
		        
			case ARRAY:
				if (key != null) {
					xmlString.append ("<array name=\"" + key + "\">");
			     } else {
			    	 xmlString.append ("<array>");
			     }
		         JsonArray array = (JsonArray) jsonValue;
		         for (JsonValue val : array)
		        	 jsonValueToXml (val, null);
		         xmlString.append ("</array>");
		         break;
		         
			case STRING:
		         JsonString string = (JsonString) jsonValue;
		         if (key != null) {
		        	 xmlString.append ("<string name=\"" + key + "\">" + string.getString () + "</string>");
		         } else {
		        	 xmlString.append("<string>" +string.getString () + "</string>");
		         }
		         break;
		         
			case NUMBER:
			     JsonNumber num = (JsonNumber) jsonValue;
			     if (key != null) {
			    	 xmlString.append ("<number name=\"" + key + "\">" + num.toString () + "</number>");
		         } else {
		        	 xmlString.append ("<number>" + num.toString () + "</number>");
		         }
		         break;
		         
			case TRUE:
				if (key != null) {
					xmlString.append ("<boolean name=\"" + key + "\">true</boolean>");
		         } else {
		        	xmlString.append ("<boolean>true</boolean>");
		         }
				break;
	    	  
			case FALSE:
				if (key != null) {
					xmlString.append ("<boolean name=\"" + key + "\">false</boolean>");
		        } else {
		        	xmlString.append ("<boolean>false</boolean>");
		        }
				break;
				
			case NULL:
				if (key != null) {
					xmlString.append ("<null name=\"" + key + "\"/>");
		        } else {
		        	xmlString.append ("<null/>");
		        }
				break;
		}
		//return xmlString.toString();
	}

}
