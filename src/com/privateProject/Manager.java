package com.privateProject;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Manager implements ManagerInterface
{
private Set<Integer> selectedScales = null;
private Algorithm algorithm = null;
private FilesManager filesManager = null;
private Configuration configuration = null;
private Scanner inputLine = null;
private static final Logger LOGGER = LogManager.getLogger(Manager.class.getName());
Manager()
{
    selectedScales = new HashSet<>();
    algorithm = new Algorithm();
    configuration = new Configuration();
    filesManager = new FilesManager();
    inputLine = new Scanner(System.in);
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
    boolean status = configuration.initialize();
    if (!status)
    {
        System.out.println("Ошибка при инициализации файла XML");
        LOGGER.error("Error initialization XML file");
        LOGGER.info("Completion of the application");
        return;
    }
    List<String> configuredScales = configuration.getScaleValues();
    if (configuredScales.isEmpty())
    {
        System.out.println("Ошибка при формировании списка масштабов в файле XML");
        LOGGER.error("Error in the formation of the scale list in the XML file");
        LOGGER.info("Completion of the application");
        return;
    }
    String selectedScale;
    do
    {
        selectedScale = showMenu(configuredScales);
        if (!CONTINUE.equals(selectedScale) && !QUITE.equals(selectedScale))
        {
            if (!controlAlgorithm(selectedScale))
            {
                clearObjectsManager();
                break;
            }
            else
            {
                System.out.println("Файлы карты для масштаба " + selectedScale + " обработаны");
            }
        }
        else if (CONTINUE.equals(selectedScale) && !QUITE.equals(selectedScale))
        {
            System.out.println("Введено некорректное значение");
        }
    }
    while (!QUITE.equals(selectedScale));
    inputLine.close();
    LOGGER.info("Completion of the application");
}
/**
 * Выполняет вывод в консоль не обработанных значений масштабов и соответствующих
 * им порядковых номеров.
 *
 * @param configuredScales список всех масштабов из XML-документа
 *
 * @return масштаб, эквивалентный пользовательскому вводу
 */
@Override
public String showMenu(final List<String> configuredScales)
{    
    System.out.println("Введите номер, соответствующий масштабу карты, для удаления объектов:");
    for (int i = 0; i < configuredScales.size(); i++)
    {
        if (!selectedScales.contains(i))
        {
            System.out.println(i + " - " + "масштаб " + configuredScales.get(i));
        }
    }
    System.out.println("q - выход");
    String inputValue = inputValidation(configuredScales, inputLine);
    return inputValue;
}
/**
 * Выполняет проверку пользовательского ввода в консоль. Осуществялет проверку
 * пользовательского ввода на значение порядкового номера,
 * диапазон, а также на строку выхода из программы. Запрещает повторный ввод порядкового
 * номера для масштаба, ранее уже обработанного в программе.
 *
 * @param configuredScales список всех масштабов из XML-документа
 * @param inputLine        объект Scanner
 *
 * @return масштаб, эквивалентный пользовательскому вводу
 */
@Override
public String inputValidation(final List<String> configuredScales, final Scanner inputLine)
{
    boolean isNumber = inputLine.hasNextInt();
    if (isNumber)
    {
        int inputNumber = inputLine.nextInt();
        LOGGER.info("The value entered: " + inputNumber);
        //проверка на нахождение числа в диапазоне
        if (inputNumber >= 0 && inputNumber < configuredScales.size())
        {
            if (!selectedScales.contains(inputNumber))
            {
                selectedScales.add(inputNumber);
                return configuredScales.get(inputNumber);
            }
            else
            {
                return CONTINUE;
            }
        }
        else
        {
            return CONTINUE;
        }
    }
    else
    {
        String inputString = inputLine.next();
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
 * @param selectedScale масштаб, эквивалентный пользовательскому вводу
 *
 * @return true если файлы электронной карты обработаны успешно
 *         false в противном случае
 */
@Override
public boolean controlAlgorithm(final String selectedScale)
{
    boolean status = configuration.configurationAllParameters(selectedScale);
    if (!status)
    {
        System.out.println("Ошибка при формировании списка параметров в файле XML");
        return false;
    }
    status = filesManager.initializeDirectory(configuration);
    if (!status)
    {
        System.out.println("Ошибка при инициализации каталога " + filesManager.getCurrentDirectory());
        return false;
    }
    status = filesManager.checkingFilesInDirectory();
    if (!status)
    {
        System.out.println("Файл не найден: " + filesManager.getCurrentFullPath());
        return false;
    }
    status = algorithm.updateObjects(configuration);
    if (!status)
    {
        System.out.println("Ошибка при формировании списка параметров из файла XML");
        return false;
    }
    while (filesManager.hasNext())
    {
        try
        {
            status = algorithm.mapOverwriting(filesManager::next);
            if (!status)
            {
                System.out.println("Файл карты не обработан: " + filesManager.getCurrentFullPath());
            }
            else
            {
                System.out.println("Файл карты: " + filesManager.getCurrentFullPath() + " обработан");
                if (!filesManager.close())
                {
                    System.out.println("Файл карты: " + filesManager.getCurrentFullPath() + " закрыт с ошибкой");
                }
            }
        }
        catch (IOException ex)
        {
            System.out.println("Файл карты: " + filesManager.getCurrentFullPath() + " обработан с ошибкой");
            if (!filesManager.close())
            {
                System.out.println("Файл карты: " + filesManager.getCurrentFullPath() + " закрыт с ошибкой");
            }
        }
    }
    return true;
}
/**
 * Присваивает ссылкам на ненужные объекты в классе Manager значение null.
 */
private void clearObjectsManager()
{
    selectedScales = null;
    algorithm = null;
    configuration = null;
    filesManager = null;
}
}
