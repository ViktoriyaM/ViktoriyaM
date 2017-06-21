package com.privateProject;

import java.util.Set;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Algorithm extends AlgorithmAbstract
{

private static final Logger LOGGER = LogManager.getLogger(Algorithm.class.getName());

Algorithm()
{
    objects = new LinkedHashSet<>();
    filesNames = new LinkedHashSet<>();
}

@Override
boolean initialize(Configuration configuration, String scaleSelected)
{
    objects = configuration.getObjects(scaleSelected);
    filesNames = configuration.getFilesNames(scaleSelected);
    String filesPath = configuration.getFilesPath(scaleSelected);
    String filesType = configuration.getFilesType();

    if (objects.isEmpty() || filesNames.isEmpty() || filesPath == null || filesType == null)
    {
        LOGGER.error("Error initialize() " + " " + objects.isEmpty() + " " + filesNames.isEmpty() + " " + filesPath + " " + filesType);
        return false;
    }
    else
    {
//    openCloseFile(filesNames, filesType, filesPath);
        objects.clear();
        filesNames.clear();
        return true;
    }

}

@Override
protected void openCloseFile(Set<String> filesNames, String filesPath, String filesType)
{
    Iterator<String> iterator = filesNames.iterator();
    String fileNameMapping = null;
    String fileName = null;

    while (iterator.hasNext())
    {
        fileName = iterator.next();
        fileNameMapping = fileName + "_optimal";

        try
        {
            FileReader fileReader = new FileReader(new File(filesPath + fileName + filesType));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            FileWriter fileWriter = new FileWriter(new File(filesPath + fileNameMapping + filesType));
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            readWriteFile(bufferedReader, fileWriter, fileNameMapping, filesPath, filesType);

            bufferedReader.close();
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }

}

@Override
protected void readWriteFile(BufferedReader bufferedReader, FileWriter fileWriter, String fileNameMapping, String filesPath, String filesType)
{
    StringBuilder lineFile = new StringBuilder();

    try
    {
        while ((lineFile.insert(0, bufferedReader.readLine())) != null)
        {
//            fileWriter.write(lineFile.toString());
//            fileWriter.flush();
            System.out.println(lineFile);
            lineFile.delete(0, lineFile.length());
        }

    }
    catch (IOException e)
    {
        System.out.println(e);
    }
}

}
