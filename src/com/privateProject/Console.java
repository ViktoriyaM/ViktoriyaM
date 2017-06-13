package com.privateProject;

import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;

public class Console implements ConsoleInterface
{
private StringBuilder inputScale = null;
private Scanner inputLine = null;

Console()
{
    inputScale = new StringBuilder();
    inputLine = new Scanner(System.in);
}

@Override
public String readingConsoleData(List<String> scaleValue)
{
    int scaleSize = scaleValue.size() - 1;
    StringBuilder regularExpression = new StringBuilder();
    regularExpression.insert(0, "[0-"+scaleSize+"]");

    System.out.println("Введите номер, соответствующий масштабу карты, для удаления объектов:");
    for (int i = 0; i < scaleValue.size(); i++)
    {
        System.out.println("- масштаб " + scaleValue.get(i) + " - " + i);
    }
    System.out.println("- выход - quite");

    inputScale.delete(0, inputScale.length());
    inputScale.insert(0, inputLine.nextLine());

    boolean matcher = Pattern.matches(regularExpression.toString(), inputScale.toString());

    if (!QUITE.equals(inputScale.toString()) && matcher)
    {
        return scaleValue.get(Integer.parseInt(inputScale.toString()));
    }
    else if (QUITE.equals(inputScale.toString()))
    {
        return QUITE;
    }
    else
    {
        return CONTINUE;
    }

}

}
