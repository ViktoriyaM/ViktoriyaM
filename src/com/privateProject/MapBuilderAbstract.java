package com.privateProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

public abstract class MapBuilderAbstract
{
protected static final String IDENTIFICATION_SCALE = "P207";
protected static final String IDENTIFICATION_OBJECT = ".OBJ";
protected static final String IDENTIFICATION_NUMBER_OBJECTS = ".DAT";
protected static final String IDENTIFICATION_END_FILE = ".END";

abstract boolean mapping(Set<String> objects, Set<String> filesNames, StringBuilder filesType, StringBuilder filesPath);

protected abstract void openCloseFile(Set<String> filesNames, StringBuilder filesType, StringBuilder filesPath);

protected abstract void readWriteFile(BufferedReader bufferedReader, FileWriter fileWriter, StringBuilder fileNameMapping, StringBuilder filesType, StringBuilder filesPath);

}
