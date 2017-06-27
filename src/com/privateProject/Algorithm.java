package com.privateProject;

import javafx.util.Pair;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Algorithm extends AlgorithmAbstract
{

private static final Logger LOGGER = LogManager.getLogger(Algorithm.class.getName());

Algorithm()
{
    objects = new LinkedHashSet<>();
}

@Override
boolean getObjects(Configuration configuration)
{
    objects = configuration.getObjects();
    
    if (objects.isEmpty())
    {
        LOGGER.error("Error the get function is called before configuration of all: " + "objects is Empty: " + objects.isEmpty());
        
        return false;
    }
    
    return true;
}

@Override
boolean readWriteFile(FilesManager filesManager)
{
    String line;
    Pair<BufferedReader, PrintWriter> pair = filesManager.next();
    if (pair == null)
    {
        LOGGER.error("Error in open file");
        return false;
    }
    
    BufferedReader bufferedReader = pair.getKey();
    PrintWriter printWriter = pair.getValue();
    
    try
    {
        while ((line = bufferedReader.readLine()) != null)
        {
            printWriter.println(line);
        }
    }
    catch (IOException ex)
    {
        LOGGER.error("Error in file " + ex);
        return false;
    }
    
    return true;
}

}
