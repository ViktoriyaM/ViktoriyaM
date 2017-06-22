package com.privateProject;

import java.util.Scanner;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Manager implements ManagerInterface
{

private Set<Integer> inputScales = null;
private Algorithm algorithm = null;
private Configuration configuration = null;
private static final Logger LOGGER = LogManager.getLogger(Manager.class.getName());

Manager()
{
    inputScales = new HashSet<>();
    algorithm = new Algorithm();
    configuration = new Configuration();
}

/**
 * Вызывает методы для открытия XML-документа {@link Configuration#initialize()},
 * получения списка масштабов из XML-документа {@link Configuration#getScaleValues()},
 * запуска пользовательского меню для обработки первого пользовательского выбора {@link #showMenu(java.util.List)}
 * и последующего {@link #showMenu(java.util.List, java.util.Set) },
 * формирования и обработки объектов электронной карты {@link Algorithm#initialize(com.privateProject.Configuration, java.lang.String)} .
 *
 */
@Override
public void managing()
{
    String scaleSelected = null;
    List<String> scaleValues = null;
    boolean condition = false;

    boolean resultInit = configuration.initialize();

    if (!resultInit)
    {
        System.out.println("Ошибка при инициализации файла XML");
        LOGGER.error("Error initialization XML file");
    }
    else
    {
        scaleValues = configuration.getScaleValues();

        if (scaleValues.isEmpty())
        {
            System.out.println("Ошибка при формировании списка масштабов в файле XML");
            LOGGER.error("Error in the formation of the scale list in the XML file");
        }
        else
        {
            do
            {

                scaleSelected = showMenu(scaleValues);

                if (!CONTINUE.equals(scaleSelected) && !QUITE.equals(scaleSelected))
                {
                    condition = algorithm.initialize(configuration, scaleSelected);

                    if (!condition)
                    {
                        System.out.println("Ошибка при формировании списка параметров в файле XML");
                        LOGGER.error("Error building the list of parameters in the XML file");
                        break;
                    }
                }
                else if (CONTINUE.equals(scaleSelected) && !QUITE.equals(scaleSelected))
                {
                    System.out.println("Введено некорректное значение");
                }
            }
            while (!QUITE.equals(scaleSelected));

            LOGGER.info("Completion of the application");
        }
    }
}

/**
 * Выполняет вывод в консоль еще не обработанных значений масштабов и соответствующих
 * им порядковых номеров.
 *
 * @param scaleValues список всех масштабов из XML-документа
 *
 * @return масштаб, эквивалентный пользовательскому вводу
 */
@Override
public String showMenu(List<String> scaleValues)
{
    Scanner inputLine = new Scanner(System.in);
    String inputValue = null;

    System.out.println("Введите номер, соответствующий масштабу карты, для удаления объектов:");
    for (int i = 0; i < scaleValues.size(); i++)
    {
        if (!inputScales.isEmpty())
        {
            if (!inputScales.contains(i))
            {
                System.out.println(i + " - " + "масштаб " + scaleValues.get(i));
            }
        }
        else
        {
            System.out.println(i + " - " + "масштаб " + scaleValues.get(i));
        }
    }
    System.out.println("q - выход");

    inputValue = inputValidation(scaleValues, inputLine);
    
    return inputValue;
}

/**
 * Выполняет проверку пользовательского ввода в консоль. Осуществялет проверку
 * пользовательского ввода на значение порядкового номера,
 * диапазон, а также на строку выхода из программы. Запрещает повторный ввод порядкового
 * номера для масштаба, ранее уже обработанного в программе.
 *
 * @param scaleValues список всех масштабов из XML-документа
 * @param inputLine   объект Scanner
 *
 * @return масштаб, эквивалентный пользовательскому вводу
 */
@Override
public String inputValidation(List<String> scaleValues, Scanner inputLine)
{
    String inputString = null;
    boolean isNumber = false;
    int inputNumber = 0;

    isNumber = inputLine.hasNextInt();

    if (isNumber)
    {
        inputNumber = inputLine.nextInt();
        LOGGER.info("The value entered: " + inputNumber);

        //проверка на нахождение числа в диапазоне
        if (inputNumber >= 0 && inputNumber < scaleValues.size())
        {
            if (!inputScales.isEmpty())
            {
                if (!inputScales.contains(inputNumber))
                {
                    inputScales.add(inputNumber);
                    return scaleValues.get(inputNumber);
                }
                else
                {
                    return CONTINUE;
                }
            }
            else
            {
                inputScales.add(inputNumber);
                return scaleValues.get(inputNumber);
            }
        }
        else
        {

            return CONTINUE;
        }
    }
    else
    {
        inputString = inputLine.next();
        LOGGER.info("The value entered: " + inputString);

        if (QUITE.equals(inputString))
        {
            return QUITE;
        }
        else
        {
            return CONTINUE;
        }
    }
}

}
