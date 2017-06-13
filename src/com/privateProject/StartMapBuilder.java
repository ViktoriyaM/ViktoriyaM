package com.privateProject;

import java.util.List;
import java.util.Set;

public class StartMapBuilder
{

/**
 @param args the command line arguments
 */
public static void main(String[] args)
{
    StringBuilder scaleSelected = new StringBuilder();
    Configuration configuration = new Configuration();

    List<String> scaleValue = configuration.getScaleValue();
    Console console = new Console();
    MapBuilder mapBuilder = new MapBuilder();

    boolean condition = true;

    while (!Console.QUITE.equals(scaleSelected.toString()))
    {
        scaleSelected.delete(0, scaleSelected.length());
        scaleSelected.insert(0, console.readingConsoleData(scaleValue));

        if (!Console.CONTINUE.equals(scaleSelected.toString()) && !Console.QUITE.equals(scaleSelected.toString()))
        {
            Set<String> objects = configuration.getObjects(scaleSelected.toString());
            Set<String> filesNames = configuration.getFilesNames(scaleSelected.toString());
            StringBuilder filesPath = configuration.getFilesPath(scaleSelected.toString());
            StringBuilder filesType = configuration.getFilesType();

            condition = mapBuilder.mapping(objects, filesNames, filesType, filesPath);
        }
    }

}

}
