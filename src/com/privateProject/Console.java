package com.privateProject;

import java.util.Scanner;
import java.util.List;

public class Console implements ConsoleInterface
{

private Scanner inputLine = null;

Console()
{
    inputLine = new Scanner(System.in);
}

@Override
public String readingConsoleData(List<String> scaleValues)
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
    inputScale = inputLine.next();

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
