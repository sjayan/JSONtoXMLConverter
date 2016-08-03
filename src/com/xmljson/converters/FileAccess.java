package com.xmljson.converters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author suranya
 *
 */
public class FileAccess {

	private static final Logger LOGGER = Logger.getLogger (XMLJSONConverter.class.getName ());
	
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
