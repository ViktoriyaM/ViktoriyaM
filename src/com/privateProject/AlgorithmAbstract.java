package com.privateProject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Set;

public abstract class AlgorithmAbstract
{

protected static final String IDENTIFICATION_SCALE = "P207";
protected static final String IDENTIFICATION_OBJECT = ".OBJ";
protected static final String IDENTIFICATION_NUMBER_OBJECTS = ".DAT";
protected static final String IDENTIFICATION_END_FILE = ".END";

Set<String> objects = null;
Set<String> filesNames = null;

abstract boolean initialize(Configuration configuration, String scaleSelected);

protected abstract void openCloseFile(Set<String> filesNames, String filesPath, String filesType);

protected abstract void readWriteFile(BufferedReader bufferedReader, FileWriter fileWriter, String fileNameMapping, String filesPath, String filesType);

}
