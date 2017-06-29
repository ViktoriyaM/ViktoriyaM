package com.privateProject;

import javafx.util.Pair;
import java.io.*;
import java.util.LinkedHashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Algorithm extends AlgorithmAbstract
{

private static final Logger LOGGER = LogManager.getLogger(Algorithm.class.getName());

Algorithm()
{
    objects = new LinkedHashSet<>();
}

/**
 * Получает список объектов из сформированной конфигурации.
 *
 * @param configuration объект Configuration
 *
 * @return true если сформирован список объектов
 *         false в противном случае
 */
@Override
boolean getObjects(Configuration configuration)
{
    objects = configuration.getObjects();

    if (objects.isEmpty())
    {
        LOGGER.error("Error the get function is called before configuration of all: " + "objects is Empty: " + objects.isEmpty());

        return false;
    }

    return true;
}

/**
 * Выполняет чтение из входного буфера и запись в выходной буфер, полученных из {@link  FilesManager#next() }.
 *
 * @param filesManager объект FilesManager
 *
 * @return true если файл карт обработан успешно
 *         false в противном случае
 */
@Override
boolean readWriteFile(FilesManager filesManager)
{
    String line;
    Pair<BufferedReader, PrintWriter> pair = filesManager.next();
    if (pair == null)
    {
        LOGGER.error("Error in open file");
        return false;
    }

    BufferedReader bufferedReader = pair.getKey();
    PrintWriter printWriter = pair.getValue();

    try
    {
        while ((line = bufferedReader.readLine()) != null)
        {
            printWriter.println(line);
        }
    }
    catch (IOException ex)
    {
        LOGGER.error("Error write file " + ex);
        return false;
    }

    printWriter.close();
    try
    {
        bufferedReader.close();
    }
    catch (IOException ex)
    {
        LOGGER.error("Error close file " + ex);
        return false;
    }

    return true;
}



}
