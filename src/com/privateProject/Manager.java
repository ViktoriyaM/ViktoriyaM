package com.privateProject;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Manager implements ManagerInterface
{

private Scanner inputLine = null;
Algorithm algorithm = null;
Configuration configuration = null;

Manager() throws ParserConfigurationException, SAXException, IOException
{
    inputLine = new Scanner(System.in);
    algorithm = new Algorithm();
    configuration = Configuration.getInstance();
}

@Override
public void managing() throws ParserConfigurationException, SAXException, IOException
{
    String scaleSelected = null;
    boolean condition = false;
    List<String> scaleValues = configuration.getScaleValues();

    do
    {
        scaleSelected = showMenu(scaleValues);
        if (!CONTINUE.equals(scaleSelected) && !QUITE.equals(scaleSelected))
        {
            condition = algorithm.initialize(scaleSelected);
        }
    }
    while (!QUITE.equals(scaleSelected));

    closeConsole();
}

@Override
public String showMenu(List<String> scaleValues)
{
    String inputScale = null;
    int scaleSize = scaleValues.size();
    boolean isNumber = false;

    System.out.println("Введите номер, соответствующий масштабу карты, для удаления объектов:");
    for (int i = 0; i < scaleSize; i++)
    {
        System.out.println(i + " - " + "масштаб " + scaleValues.get(i));
    }
    System.out.println("q - выход");

    isNumber = inputLine.hasNextInt();
    inputScale = inputLine.nextLine();

    //если введено число
    if (isNumber)
    {
        //проверка на нахождение числа в диапазоне
        if (Integer.parseInt(inputScale) >= 0 && Integer.parseInt(inputScale) < scaleSize)
        {
            return scaleValues.get(Integer.parseInt(inputScale));
        }
        else
        {
            return CONTINUE;
        }
    }
    else
    {
        if (QUITE.equals(inputScale))
        {
            return QUITE;
        }
        else
        {
            return CONTINUE;
        }
    }

}

@Override
public void closeConsole()
{
    inputLine.close();
}

}
