package com.privateProject;

import javafx.util.Pair;
import java.io.*;
import java.util.regex.*;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;
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

    Pair<BufferedReader, RandomAccessFile> pair = filesManager.next();
    if (pair == null)
    {
        LOGGER.error("Error in open file");
        return false;
    }

    BufferedReader bufferedReader = pair.getKey();
    RandomAccessFile randomAccessFile = pair.getValue();

    try
    {
        while ((line = bufferedReader.readLine()) != null)
        {
            calculation(randomAccessFile, line);
        }
    }
    catch (IOException ex)
    {
        LOGGER.error("Error write file " + ex);
        return false;
    }

    if (!filesManager.close(bufferedReader, randomAccessFile))
    {
        return false;
    }

    return true;
}

/**
 * Выполняет обработку файлов электронной карты, удаляя все объекты, соответствующие массиву из XML-документа.
 *
 * @param randomAccessFile поток для записи данных в файл с произвольным доступом
 * @param line строка, считанная из буфферизированного входного потока
 *
 * @return результат обработки файла
 *
 * @throws IOException возникает исключение при ошибке записи в файл
 */
private boolean calculation(RandomAccessFile randomAccessFile, String line) throws IOException
{
    Matcher matcherObject = Pattern.compile("\\d+").matcher(line);
    String lineDat = "";

    if (line.matches(IDENTIFICATION_OBJECT))
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
                randomAccessFile.write(line.getBytes());
                randomAccessFile.writeBytes(System.getProperty("line.separator"));
            }
        }
    }
    else
    {
        if (isWrite && !line.matches(IDENTIFICATION_END_FILE) && !line.matches(IDENTIFICATION_NUMBERS_OBJECTS))
        {
            randomAccessFile.write(line.getBytes());
        }

        if (line.matches(IDENTIFICATION_NUMBERS_OBJECTS))
        {
            if (matcherObject.find())
            {
                Matcher matcherDat = Pattern.compile("\\D+").matcher(line);

                if (matcherDat.find())
                {
                    lineDat = matcherDat.group();
                }
                currentNumberObjects = Integer.parseInt(matcherObject.group());
                randomAccessFile.write(lineDat.getBytes());
                numberLineWithNumberObjects = randomAccessFile.getFilePointer();
                randomAccessFile.writeBytes(System.getProperty("line.separator"));
            }
        }

        if (line.matches(IDENTIFICATION_END_FILE))
        {
            randomAccessFile.write(line.getBytes());

            int currentObjects = currentNumberObjects - currentNumberDeletedObjects;
            randomAccessFile.seek(numberLineWithNumberObjects);
            randomAccessFile.write(Integer.toString(currentObjects).getBytes());
        }

        randomAccessFile.writeBytes(System.getProperty("line.separator"));
    }

    return true;
}

}
