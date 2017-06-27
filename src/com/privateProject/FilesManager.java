package com.privateProject;

import java.util.Set;
import java.util.Iterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import javafx.util.Pair;
import java.io.*;
import java.util.logging.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FilesManager
{

private static final Logger LOGGER = LogManager.getLogger(FilesManager.class.getName());
private Path path = null;
String fileName = null;
private Set<String> filesNames = null;
private Iterator<String> iteratorFilesNames = null;

/**
 * Выполняет проверку наличия всех файлов из XML-документа в указанном каталоге.
 *
 * @param configuration объект класса Configuration для получения имен и пути к
 *                      файлам из XML-документа
 *
 * @return true если все файлы в каталоге найдены
 *         false в противном случае
 */
boolean initializeCatalog(Configuration configuration)
{
    filesNames = configuration.getFilesNames();
    String filesPath = configuration.getFilesPath();

    if (filesNames.isEmpty() || filesPath == null)
    {
        LOGGER.error("Error the get function is called before configuration of all: " + "filesNames is Empty: " + filesNames.isEmpty()
                     + "filesPath is null: " + filesPath + "filesType is null: ");
        return false;
    }

    Path path = Paths.get(filesPath);
    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path))
    {
        for (Path entry : entries)
        {
            if (!filesNames.contains(entry.getFileName().toString()))
            {
                setPath(entry);
                LOGGER.error("Error file not found " + entry);
                return false;
            }
        }
    }
    catch (IOException ex)
    {
        LOGGER.error("Error files directory not found " + ex);
        return false;
    }

    return true;
}

boolean hasNext()
{
    if (filesNames.isEmpty())
    {
        return false;
    }

    iteratorFilesNames = filesNames.iterator();

    return iteratorFilesNames.hasNext();
}

Pair<BufferedReader, PrintWriter> next()
{
    String fileName = iteratorFilesNames + "_optimal";
    Pair<BufferedReader, PrintWriter> pair = null;

    try
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(iteratorFilesNames.toString()), "windows-1251"));

        PrintWriter printWriter = new PrintWriter(fileName);

        pair = new Pair<>(bufferedReader, printWriter);
        setCurrentFile(iteratorFilesNames.toString());
    }
    catch (UnsupportedEncodingException | FileNotFoundException ex)
    {
        LOGGER.error("Error in encoding file " + ex);
    }

    return pair;
}

/**
 * Устанавливает значение пути к файлу, не найденному в каталоге при инициализации
 *
 * @param path путь к отсутствующему файлу
 */
private void setPath(Path path)
{
    this.path = path;
}

/**
 * Возвращает путь к отсутствующему файлу
 *
 * @return путь к отсутствующему файлу
 */
Path getPath()
{
    return path;
}

private void setCurrentFile(String fileName)
{
    this.fileName = fileName;
}

String getCurrentFile()
{
    return fileName;
}
}
