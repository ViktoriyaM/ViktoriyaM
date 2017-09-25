package com.privateProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;
import java.util.function.Supplier;
import javafx.util.Pair;

public abstract class AlgorithmAbstract
{
protected static final String IDENTIFICATION_SCALE = "P207(.*)";
protected static final String IDENTIFICATION_OBJECT = ".OBJ(.*)";
protected static final String IDENTIFICATION_NUMBERS_OBJECTS = ".DAT(.*)";
protected static final String IDENTIFICATION_END_FILE = ".END(.*)";
protected Set<String> objects = null;
protected long pointerLineWithNumberObjects = 0;
protected int currentNumberObjects = 0;
protected int currentNumberDeletedObjects = 0;
protected boolean isWrite = true;
abstract boolean updateObjects(Configuration configuration);
abstract boolean mapOverwriting(Supplier<FilesManager.GenerateNext> pair) throws IOException;
abstract protected void mapProcessing(RandomAccessFile randomAccessFile, String readLine) throws IOException;
}
