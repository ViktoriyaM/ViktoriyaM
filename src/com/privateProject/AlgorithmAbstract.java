package com.privateProject;

import java.util.Set;

public abstract class AlgorithmAbstract
{

protected static final String IDENTIFICATION_SCALE = "P207";
protected static final String IDENTIFICATION_OBJECT = ".OBJ";
protected static final String IDENTIFICATION_NUMBER_OBJECTS = ".DAT";
protected static final String IDENTIFICATION_END_FILE = ".END";

Set<String> objects = null;

abstract boolean getObjects(Configuration configuration);
abstract boolean readWriteFile(FilesManager filesManager);

}
