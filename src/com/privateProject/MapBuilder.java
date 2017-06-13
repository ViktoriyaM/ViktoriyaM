package com.privateProject;

import java.util.Set;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public final class MapBuilder extends MapBuilderAbstract
{

@Override
boolean mapping(Set<String> objects, Set<String> filesNames, StringBuilder filesType, StringBuilder filesPath)
{
    openCloseFile(filesNames, filesType, filesPath);
    return true;
}

@Override
protected void openCloseFile(Set<String> filesNames, StringBuilder filesType, StringBuilder filesPath)
{
    Iterator<String> iterator = filesNames.iterator();
    StringBuilder fileNameMapping = new StringBuilder();
    StringBuilder fileName = new StringBuilder();
    
    while (iterator.hasNext())
    {
        fileNameMapping.delete(0, fileNameMapping.length());
        fileName.delete(0, fileName.length());
        
        fileName.insert(0, iterator.next());
        fileNameMapping.insert(0, fileName.toString() + "_optimal");
        
        try
        {
            FileReader fileReader = new FileReader(new File(filesPath.toString() + fileName.toString() + filesType.toString()));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            FileWriter fileWriter = new FileWriter(new File(filesPath.toString() + fileNameMapping.toString() + filesType.toString()));
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            readWriteFile(bufferedReader, fileWriter, fileNameMapping, filesType, filesPath);
            
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
protected void readWriteFile(BufferedReader bufferedReader, FileWriter fileWriter, StringBuilder fileNameMapping, StringBuilder filesType, StringBuilder filesPath)
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
