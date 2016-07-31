package com.xmljson.converters;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test class for XMLJSONConverter
 *   
 * @author Suranya
 *
 */
public class TestXMLJSONConverter {
	
	private static final Logger LOGGER = Logger.getLogger (TestXMLJSONConverter.class.getName ());
	
	/**
	 * Main method where testing begins
	 *   
	 * @param args String array of command line arguments
	 *		  (Expected inputs - json file name, xml file name)
	 */
	public static void main (String []args) {
		// Check for command line arguments
		if (args.length != 2) {
			LOGGER.log (Level.SEVERE, "Unexpected number of arguments. Takes two arguments: json_file xml_file");
		} else {
			String jsonFileName = args [0];
			String xmlFileName = args [1];
			LOGGER.log (Level.INFO, "Command line arguments : " + jsonFileName + " " + xmlFileName);
			File jsonFile = new File (jsonFileName);
			File xmlFile = new File (xmlFileName);
			XMLJSONConverterI xmljsonConverter = ConverterFactory.createXMLJSONConverter ();
			try {
				xmljsonConverter.convertJSONtoXML (jsonFile, xmlFile);
			} catch (IOException e) {
				LOGGER.log (Level.SEVERE, "IOException while reading/writing to files");
				//e.printStackTrace();
			}
		}
	}
	
	

}
