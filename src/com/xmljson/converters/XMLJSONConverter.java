
package com.xmljson.converters;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the {@link XMLJSONConverterI} Interface
 * Used for converting JSON to XML
 * 
 * @author suranya
 *
 */
public class XMLJSONConverter implements XMLJSONConverterI{
	
	private static final Logger LOGGER = Logger.getLogger (XMLJSONConverter.class.getName ());

	/**
    * Reads in the JSON from the given file and outputs the data, converted to
    * XML, to the given file. Exceptions are thrown by this method so that the
    * caller can clean up the before exiting. Uses {@link JSONMethods} class to 
    * read/write to file, read JSON and convert to XML.
    *
    * @param json {@link java.io.File} from which to read JSON data.
    * @param xml {@link java.io.File} from which to write XML data.
    * @throws IOException {@link java.io.IOException} 
    */
	public void convertJSONtoXML (File json, File xml) throws IOException {
		String jsonString = FileAccess.readFile (json);
		if (jsonString.isEmpty() || jsonString == null) {
			LOGGER.log (Level.SEVERE, "json string read from file is empty");	
			return;
		}
		LOGGER.log (Level.INFO, "json string read from file: \n" + jsonString);
		JSONMethods jsonMethods = new JSONMethods();
		String xmlString = jsonMethods.xmlConverter (jsonString);
		if (xmlString.isEmpty() || xmlString == null) return;
		LOGGER.log (Level.INFO, "Converted xml string: \n" + xmlString);
		FileAccess.writeFile (xml, xmlString);		
	}

}
