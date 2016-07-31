package com.xmljson.converters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class Utilities {
	
	private static final Logger LOGGER = Logger.getLogger (Utilities.class.getName ());
	//String used for constructing xml in jsonValueToXml
	static StringBuilder xmlString = new StringBuilder ();
	
	/**
	 * xmlConverter reads json from an input string and converts it to
	 * JsonObject or JsonArray based on the initial token '[' or '{'
	 * 
	 * @param json String containing the json read from file
	 * @return String containing the xml converted from json
	 */
	public static String xmlConverter (String json) {
		JsonStructure object = null;
		try {
			JsonReader jsonReader = Json.createReader(new StringReader (json));
			object = jsonReader.read();
		} catch (JsonException e) {
			LOGGER.log (Level.SEVERE, "Exception while parsing JSON. Error in syntax or unexpected beginning tokens, not [ or { ");
			return "";
		}
		return jsonValueToXml(object, null);
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
	public static String jsonValueToXml (JsonValue jsonValue, String key) {
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
		return xmlString.toString();
	}
	
	/**
	 * readFile reads from the given file and returns a String containing the 
	 * contents of the file 
	 * 
	 * @param file Input file to be read from. 
	 * @return String containing the contents of the input file.
	 * @throws IOException {@link java.io.IOException} 
	 */
	public static String readFile (File file) throws IOException {
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader (new FileReader (file));
		} catch (FileNotFoundException e) {
			LOGGER.log (Level.SEVERE, "File not found exception for " + file.getName());
			return "";
		}
		StringBuilder string = new StringBuilder ();
		String line = fileReader.readLine ();
		while (line != null) {
			string.append (line);
			string.append ("\n");
			line = fileReader.readLine ();
		}
		fileReader.close ();
		return string.toString();
	}
	
	/**
	 * writeFile writes to the given file from an input String
	 * 
	 * @param file Output file to write to. 
	 * @param text String containing the contents to write to file.
	 * @throws IOException {@link java.io.IOException} 
	 */
	public static void writeFile (File file, String text) throws IOException  {
		if (text == null || text.isEmpty()) return;
		BufferedWriter fileWriter = null;
		fileWriter = new BufferedWriter (new FileWriter (file));			
		fileWriter.write (text);
		fileWriter.close ();
	}

}
