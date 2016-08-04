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
 * Class containing file access methods 
 * 
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
		LOGGER.log (Level.INFO, "Reading file : " + file.getName());
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader (new FileReader (file));
		} catch (FileNotFoundException e) {
			//if the input file (json file) is invalid, throw exception and exit
			LOGGER.log (Level.SEVERE, "File not found exception for " + file.getName());
			throw new IOException();
		}
		StringBuilder string = new StringBuilder ();
		try {
			String line = fileReader.readLine ();
			while (line != null) {
				string.append (line);
				string.append ("\n");
				line = fileReader.readLine ();
			}
		}catch (IOException e){
			//if the json file cannot be read, throw exception and exit
			LOGGER.log (Level.SEVERE, "Exception while reading file " + file.getName());
			throw new IOException();
		} finally{
			//in case file closing throws IOException, it will be caught in the calling class
			fileReader.close ();
		}
		//file contents returned as string
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
