package com.xmljson.converters;
/**
 * Factory class for creating instances of {@link XMLJSONConverterI}.
 */
public final class ConverterFactory {

    /**
     * Returns an instance of the {@link XMLJSONConverter}.
     * Implementation class for the interface {@link XMLJSONConverterI}
     *
     * @return {@link XMLJSONConverter} 
     */
    public static final XMLJSONConverterI createXMLJSONConverter() {
       return new XMLJSONConverter ();
    }
}
