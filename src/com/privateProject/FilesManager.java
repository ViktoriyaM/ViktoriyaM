package com.privateProject;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import javafx.util.Pair;
import java.io.*;
import java.net.URISyntaxException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FilesManager
{

private static final Logger LOGGER = LogManager.getLogger(FilesManager.class.getName());
private String catalogName = null;
private String fileReaderPath = null;
private String fileWriterPath = null;
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
    iteratorFilesNames = filesNames.iterator();
    String filesPath = configuration.getFilesPath();
    HashSet<String> filesNamesFromCatalog = new HashSet<>();
    String folderName = "mapping";
    Path pathReader = null;
    Path pathWriter = null;

    if (filesNames.isEmpty() || filesPath.isEmpty())
    {
        LOGGER.error("Error the get function is called before configuration of all: " + "filesNames is Empty: " + filesNames.isEmpty()
                     + "filesPath is null: " + filesPath + "filesType is null: ");
        return false;
    }

    Path directory = Paths.get(System.getProperty("user.dir"), folderName, filesPath);
    try
    {
        pathWriter = Files.createDirectories(directory);
    }
    catch (IOException ex)
    {
        setCurrentCatalog(folderName);
        LOGGER.error("Error create Directorty " + ex);
        return false;
    }

    try
    {
        pathReader = Paths.get(getClass().getClassLoader().getResource(filesPath).toURI());

        setFilePath(pathReader.toString(), pathWriter.toString());

        DirectoryStream<Path> entries = Files.newDirectoryStream(pathReader);

        for (Path entry : entries)
        {
            filesNamesFromCatalog.add(entry.getFileName().toString());
        }
    }
    catch (IOException | NullPointerException | URISyntaxException ex)
    {
        setCurrentCatalog(filesPath);
        LOGGER.error("Error files directory not found " + ex);
        return false;
    }

    return checkCatalog(filesNamesFromCatalog);
}

/**
 * Выполняет сравнение имен файлов из каталога и имен файлов из XML-документа
 *
 * @param filesNamesFromCatalog имена фалов из каталога
 *
 * @return true если все файлы из XML-документа найдены в каталоге
 *         false в противном случае
 */
private boolean checkCatalog(Set<String> filesNamesFromCatalog)
{
    ArrayList<String> entries = new ArrayList<>(filesNames);

    for (String entry : entries)
    {
        if (!filesNamesFromCatalog.contains(entry))
        {
            setCurrentCatalog(entry);
            LOGGER.error("Error file not found " + entry);
            return false;
        }
    }

    return true;
}

/**
 * Проверяет наличие следующего файла для чтения в каталоге.
 *
 * @return true если файл для чтения есть
 *         false в противном случае
 */
boolean hasNext()
{
    if (filesNames.isEmpty())
    {
        return false;
    }

    return iteratorFilesNames.hasNext();
}

/**
 * Формирует выходной буфер для чтения и записи файлов.
 *
 * @return буфер для чтения и записи
 */
Pair<BufferedReader, RandomAccessFile> next()
{
    Pair<BufferedReader, RandomAccessFile> pair = null;
    String fileName = null;
    String currentFileName = null;
    StringBuilder newFileName = new StringBuilder(fileWriterPath);

    try
    {
        fileName = iteratorFilesNames.next();
    }
    catch (NoSuchElementException ex)
    {
        LOGGER.error("Error in iterator Files Names " + ex);
        return pair;
    }

    currentFileName = fileReaderPath + System.getProperty("file.separator") + fileName;
    newFileName.append(System.getProperty("file.separator")).append(fileName).insert(newFileName.indexOf(".txf"), "_optimal");

    try
    {
        setCurrentCatalog(currentFileName);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(currentFileName), "windows-1251"));

//        PrintWriter randomAccessFile = new PrintWriter(newFileName.toString());
        RandomAccessFile randomAccessFile = new RandomAccessFile(newFileName.toString(), "rw");

        pair = new Pair<>(bufferedReader, randomAccessFile);
    }
    catch (UnsupportedEncodingException | FileNotFoundException ex)
    {
        LOGGER.error("Error in encoding file " + ex);
    }

    return pair;
}

/**
 * Устанавливает путь и имя текущего обрабатываемого файла.
 *
 * @param catalogName путь и имя текущего обрабатываемого файла
 */
private void setCurrentCatalog(String catalogName)
{
    this.catalogName = catalogName;
}

/**
 * Возвращает имя текущего обрабатываемого файла.
 *
 * @return имя текущего обрабатываемого файла
 */
String getCurrentCatalog()
{
    return catalogName;
}

/**
 * Устанавливает путь к файлам для чтения и для записи.
 *
 * @param fileReaderPath путь к файлам для чтения
 *
 * @param fileWriterPath путь к файлам для записи
 */
private void setFilePath(String fileReaderPath, String fileWriterPath)
{
    this.fileReaderPath = fileReaderPath;
    this.fileWriterPath = fileWriterPath;
}

/**
 * Закрывает поток чтения и поток записи
 *
 * @param bufferedReader   буфферизированный поток для чтения данных из файла
 * @param randomAccessFile поток для записи данных в файл с произвольным доступом
 *
 * @return результат закрытия потоков чтения и записи
 */
boolean close(BufferedReader bufferedReader, RandomAccessFile randomAccessFile)
{
    try
    {
        bufferedReader.close();
        randomAccessFile.close();
    }
    catch (IOException ex)
    {
        LOGGER.error("Error close file " + ex);
        return false;
    }
    return true;
}
}
