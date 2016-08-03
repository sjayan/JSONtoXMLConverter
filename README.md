XMLJSONConverter

Converts JSON to XML form with the JSON type as the tag and JSON name as an attribute.
Most libraries convert JSON to standard XML form, so I made one to construct XML in a free form.
Porject build using Ant.
 
BUILD

Run "Ant" from the project folder to build all the class files and 
make an executable jar called "XMLJSONConverter.jar". 

RUN

Run the executable jar with the command 
"java -jar XMLJSONConverter.jar json_file xml_file"
Sample input and output files are provided in the "files" folder
"java -jar XMLJSONConverter.jar files/json_object files/xml_object"

EXTERNAL LIBRARIES

javax.json-1.0.4.jar : JavaEE API available for processing JSON

PROJECT FILES

	XMLJSONConverter
	 |______ bin : for class files
	 |______ src : source files
	 |______ lib : external libraries
	 |______ doc : javadocs for the source files
	 |______ files : Sample input/output files
	 |______ build.xml : Ant build configurations
	 |______ autorun.sh : Shell script that can compile and test the project 
	 |______ README
