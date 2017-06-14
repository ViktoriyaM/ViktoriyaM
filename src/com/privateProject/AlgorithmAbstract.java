package com.privateProject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public abstract class AlgorithmAbstract
{

protected static final String IDENTIFICATION_SCALE = "P207";
protected static final String IDENTIFICATION_OBJECT = ".OBJ";
protected static final String IDENTIFICATION_NUMBER_OBJECTS = ".DAT";
protected static final String IDENTIFICATION_END_FILE = ".END";

Set<String> objects = null;
Set<String> filesNames = null;

abstract boolean initialize(String scaleSelected) throws ParserConfigurationException, SAXException, IOException;

protected abstract void openCloseFile(Set<String> filesNames, String filesPath, String filesType);

protected abstract void readWriteFile(BufferedReader bufferedReader, FileWriter fileWriter, String fileNameMapping, String filesPath, String filesType);

}
