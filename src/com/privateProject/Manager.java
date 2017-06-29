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
private FilesManager filesManager = null;
private Configuration configuration = null;
private static final Logger LOGGER = LogManager.getLogger(Manager.class.getName());

Manager()
{
    inputScales = new HashSet<>();
    algorithm = new Algorithm();
    configuration = new Configuration();
    filesManager = new FilesManager();
}

/**
 * Вызывает методы для открытия XML-документа {@link Configuration#initialize()},
 * получения списка масштабов из XML-документа {@link Configuration#getScaleValues()},
 * запуска пользовательского меню для обработки пользовательского выбора {@link #showMenu(java.util.List) },
 * запуска алгоритма обработки объектов электронной карты {@link #controlAlgorithm(java.lang.String) } .
 *
 */
@Override
public void managing()
{
    String scaleSelected = null;
    List<String> scaleValues = null;

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
                    if (!controlAlgorithm(scaleSelected))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Файлы карты для масштаба " + scaleSelected + " обработаны");
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
 * Выполняет вывод в консоль не обработанных значений масштабов и соответствующих
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

/**
 * Вызывает функцию конфигурации списка параметров из XML-документа для выбранного масштаба {@link Configuration#configurationAllParameters(java.lang.String) },
 * вызывает функцию проверки наличия всех файлов из XML-документа в указанном каталоге {@link  FilesManager#initializeCatalog(com.privateProject.Configuration) },
 * вызывает функцию получения списка объектов из сформированной конфигурации {@link Algorithm#getObjects(com.privateProject.Configuration) },
 * проверяет есть ли файлы для чения и вызывает алгоритм для обработки файлов карты {@link Algorithm#readWriteFile(com.privateProject.FilesManager) }.
 *
 * @param scaleSelected масштаб, эквивалентный пользовательскому вводу
 *
 * @return true если файлы электронной карты обработаны успешно
 *         false в противном случае
 */
@Override
public boolean controlAlgorithm(String scaleSelected)
{
    boolean isCheckConfiguration = false;
    boolean isCheckCatalog = false;
    boolean isCheckReadWriteFile = false;
    boolean isCheckGetObjects = false;

    isCheckConfiguration = configuration.configurationAllParameters(scaleSelected);
    if (!isCheckConfiguration)
    {
        System.out.println("Ошибка при формировании списка параметров в файле XML");
        return false;
    }

    isCheckCatalog = filesManager.initializeCatalog(configuration);
    if (!isCheckCatalog)
    {
        System.out.println("Каталог не найден: " + filesManager.getCurrentCatalog());
        return false;
    }

    isCheckGetObjects = algorithm.getObjects(configuration);
    if (!isCheckGetObjects)
    {
        System.out.println("Ошибка при формировании списка параметров из файла XML");
        return false;
    }

    while (filesManager.hasNext())
    {
        isCheckReadWriteFile = algorithm.readWriteFile(filesManager);
        if (!isCheckReadWriteFile)
        {
            System.out.println("Файл карты не обработан: " + filesManager.getCurrentCatalog());
        }
        else
        {
            System.out.println("Файл карты: " + filesManager.getCurrentCatalog() + " обработан");
        }
    }

    return true;
}

}
