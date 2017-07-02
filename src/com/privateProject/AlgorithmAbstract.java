package com.privateProject;

import java.util.Set;

public abstract class AlgorithmAbstract
{

protected static final String IDENTIFICATION_SCALE = "P207(.*)";
protected static final String IDENTIFICATION_OBJECT = ".OBJ(.*)";
protected static final String IDENTIFICATION_NUMBERS_OBJECTS = ".DAT(.*)";
protected static final String IDENTIFICATION_END_FILE = ".END(.*)";

protected Set<String> objects = null;
protected long numberLineWithNumberObjects = 0;
protected int currentNumberObjects = 0;
protected int currentNumberDeletedObjects = 0;
protected boolean isWrite = true;

abstract boolean getObjects(Configuration configuration);

abstract boolean readWriteFile(FilesManager filesManager);

}
