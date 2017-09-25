package com.privateProject;

import java.io.*;
import java.util.regex.*;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.function.Supplier;

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
boolean updateObjects(Configuration configuration)
{
    objects = configuration.getObjectsForDelete();
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
 *
 * @throws IOException
 */
@Override
boolean mapOverwriting(Supplier<FilesManager.GenerateNext> pair) throws IOException
{
    if (pair == null)
    {
        LOGGER.error("Error in open file");
        return false;
    }
    BufferedReader bufferedReader = pair.get().getReader();
    RandomAccessFile randomAccessFile = pair.get().getWriter();
    try
    {
        String readLine;
        while ((readLine = bufferedReader.readLine()) != null)
        {
            mapProcessing(randomAccessFile, readLine);
        }
    }
    catch (IOException ex)
    {
        LOGGER.error("Error write file " + ex);
        throw ex;
    }
    return true;
}
/**
 * Выполняет обработку файлов электронной карты, удаляя все объекты, соответствующие массиву из XML-документа.
 *
 * @param randomAccessFile поток для записи данных в файл с произвольным доступом
 * @param readLine         строка, считанная из буфферизированного входного потока
 *
 * @throws IOException возникает исключение при ошибке записи в файл
 */
@Override
protected void mapProcessing(RandomAccessFile randomAccessFile, String readLine) throws IOException
{
    Matcher matcherObject = Pattern.compile("\\d+").matcher(readLine);
    if (readLine.matches(IDENTIFICATION_OBJECT))
    {
        if (matcherObject.find())
        {
            String currentObject = matcherObject.group();
            if (objects.contains(currentObject))
            {
                isWrite = false;
                currentNumberDeletedObjects++;
            }
            else
            {
                isWrite = true;
            }
        }
    }
    if (isWrite && !readLine.matches(IDENTIFICATION_END_FILE) && !readLine.matches(IDENTIFICATION_NUMBERS_OBJECTS))
    {
        randomAccessFile.write(readLine.getBytes());
        randomAccessFile.writeBytes(System.getProperty("line.separator"));
    }
    if (readLine.matches(IDENTIFICATION_NUMBERS_OBJECTS))
    {
        if (matcherObject.find())
        {
            Matcher matcherDat = Pattern.compile("\\D+").matcher(readLine);
            if (matcherDat.find())
            {
                String lineWithDat = matcherDat.group();
                randomAccessFile.write(lineWithDat.getBytes());
            }
            currentNumberObjects = Integer.parseInt(matcherObject.group());
            pointerLineWithNumberObjects = randomAccessFile.getFilePointer();
            randomAccessFile.writeBytes(System.getProperty("line.separator"));
        }
    }
    if (readLine.matches(IDENTIFICATION_END_FILE))
    {
        randomAccessFile.write(readLine.getBytes());
        int currentObjects = currentNumberObjects - currentNumberDeletedObjects;
        randomAccessFile.seek(pointerLineWithNumberObjects);
        randomAccessFile.write(Integer.toString(currentObjects).getBytes());
        randomAccessFile.writeBytes(System.getProperty("line.separator"));
        currentNumberDeletedObjects = 0;
    }
}
}
