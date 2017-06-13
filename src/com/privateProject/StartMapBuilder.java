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
    String scaleSelected = new String();
    Configuration configuration = new Configuration();

    List<String> scaleValues = configuration.getScaleValues();
    Console console = new Console();
    MapBuilder mapBuilder = new MapBuilder();

    boolean condition = true;

    while (!Console.QUITE.equals(scaleSelected))
    {
        scaleSelected = console.readingConsoleData(scaleValues);

        if (!Console.CONTINUE.equals(scaleSelected) && !Console.QUITE.equals(scaleSelected))
        {
            Set<String> objects = configuration.getObjects(scaleSelected);
            Set<String> filesNames = configuration.getFilesNames(scaleSelected);
            StringBuilder filesPath = configuration.getFilesPath(scaleSelected);
            StringBuilder filesType = configuration.getFilesType();

//            condition = mapBuilder.mapping(objects, filesNames, filesType, filesPath);
        }
    }

    console.closeConsole();
}

}
