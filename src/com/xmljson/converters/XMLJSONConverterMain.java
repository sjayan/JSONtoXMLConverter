package com.xmljson.converters;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for XMLJSONConverter
 *   
 * @author Suranya
 *
 */
public class XMLJSONConverterMain {
	
	private static final Logger LOGGER = Logger.getLogger (XMLJSONConverterMain.class.getName ());
	
	/**
	 * Main method where execution begins
	 *   
	 * @param args String array of command line arguments
	 *		  (Expected inputs - json file name, xml file name)
	 */
	public static void main (String []args) {
		// Check for command line arguments
		if (args.length != 2) {
			LOGGER.log (Level.SEVERE, "Unexpected number of arguments. Takes two arguments: json_file xml_file");
		} else {
			//Reading filenames from command line arguments
			String jsonFileName = args [0];
			String xmlFileName = args [1];
			LOGGER.log (Level.INFO, "Command line arguments : " + jsonFileName + " " + xmlFileName);
			File jsonFile = new File (jsonFileName);
			File xmlFile = new File (xmlFileName);
			//ConverterFactory returns implementation specific instance of XMLJSONConverterI
			XMLJSONConverterI xmljsonConverter = ConverterFactory.createXMLJSONConverter ();
			try {
				//convertJSONtoXML reads json from file, parses and converts to XML
				xmljsonConverter.convertJSONtoXML (jsonFile, xmlFile);
			} catch (IOException e) {
				LOGGER.log (Level.SEVERE, "Exception while reading/writing to files. Couldn't do conversion!");
			}
		}
	}
	
	

}
