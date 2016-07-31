javac -d bin -cp lib/javax.json-1.0.4.jar src/com/xmljson/converters/Utilities.java
javac -d bin src/com/xmljson/converters/XMLJSONConverterI.java
javac -d bin -cp bin src/com/xmljson/converters/XMLJSONConverter.java
javac -d bin -cp bin src/com/xmljson/converters/ConverterFactory.java
javac -d bin -cp bin src/com/xmljson/converters/TestXMLJSONConverter.java
java -cp lib/javax.json-1.0.4.jar:bin com/xmljson/converters/TestXMLJSONConverter files/json_object files/xml_object

