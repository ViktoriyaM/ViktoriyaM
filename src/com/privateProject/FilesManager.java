package com.privateProject;

import java.util.Set;
import java.util.HashSet;
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
private String fullFileName = null;
String filesPath = null;
private String filesReaderPath = null;
private String filesWriterPath = null;
private Set<String> filesNames = null;
private Set<String> filesNamesFromDirectory = new HashSet<>();
private Iterator<String> iteratorFilesNames = null;
private Pair<BufferedReader, RandomAccessFile> pair = null;
/**
 * Выполняет проверку наличия каталога для чтения по пути из XML-документа, создание каталога для записи.
 * Выполняет инициализацию всех файлов в каталоге для чтения.
 *
 * @param configuration объект класса Configuration для получения имен и пути к
 *                      файлам из XML-документа
 *
 * @return true если каталоги для чтения и для записи созданы успешно
 *         false в противном случае
 */
boolean initializeDirectory(Configuration configuration)
{
    filesNames = configuration.getFilesNames();
    iteratorFilesNames = filesNames.iterator();
    filesPath = configuration.getFilesPath();
    String folderName = "mapping";
    if (filesNames.isEmpty() || filesPath.isEmpty())
    {
        LOGGER.error("Error the get function is called before configuration of all: " + "filesNames is Empty: " + filesNames.isEmpty()
                     + "filesPath is null: " + filesPath + "filesType is null: ");
        return false;
    }
    try
    {
        Path directory = Paths.get(System.getProperty("user.dir"), folderName, filesPath);
        Path pathWriter = Files.createDirectories(directory);
        Path pathReader = Paths.get(getClass().getClassLoader().getResource(filesPath).toURI());
        setFilesPath(pathReader.toString(), pathWriter.toString());
        DirectoryStream<Path> entries = Files.newDirectoryStream(pathReader);
        for (Path entry : entries)
        {
            filesNamesFromDirectory.add(entry.getFileName().toString());
        }
    }
    catch (IOException | NullPointerException | URISyntaxException ex)
    {
        LOGGER.error("Error files directory not found " + ex);
        return false;
    }
    return true;
}
/**
 * Выполняет сравнение имен файлов из каталога для чтения и имен файлов из XML-документа.
 *
 * @return true если все файлы из XML-документа найдены в каталоге
 *         false в противном случае
 */
boolean checkingFilesInDirectory()
{
    for (String fileName : filesNames)
    {
        if (!filesNamesFromDirectory.contains(fileName))
        {
            setCurrentFullPath(filesPath + fileName);
            LOGGER.error("Error file not found " + fileName);
            return false;
        }
    }
    clearObjectsFilesManager();
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
    StringBuilder newFullFileName = new StringBuilder(filesWriterPath);
    String fileName = null;
    try
    {
        fileName = iteratorFilesNames.next();
    }
    catch (NoSuchElementException ex)
    {
        LOGGER.error("Error in iterator Files Names " + ex);
        return pair;
    }
    String currentFullFileName = filesReaderPath + System.getProperty("file.separator") + fileName;
    newFullFileName.append(System.getProperty("file.separator")).append(fileName).insert(newFullFileName.indexOf(".txf"), "_optimal");
    setCurrentFullPath(currentFullFileName);
    try
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(currentFullFileName), "windows-1251"));
        RandomAccessFile randomAccessFile = new RandomAccessFile(newFullFileName.toString(), "rw");
        pair = new Pair<>(bufferedReader, randomAccessFile);
    }
    catch (UnsupportedEncodingException | FileNotFoundException ex)
    {
        LOGGER.error("Error in encoding file " + ex);
    }
    return pair;
}
/**
 * Устанавливает путь или имя текущего обрабатываемого файла.
 *
 * @param fullFileName путь или имя текущего обрабатываемого файла
 */
private void setCurrentFullPath(String fullFileName)
{
    this.fullFileName = fullFileName;
}
/**
 * Возвращает путь или имя текущего обрабатываемого файла.
 *
 * @return путь или имя текущего обрабатываемого файла
 */
String getCurrentFullPath()
{
    return fullFileName;
}
/**
 * Возвращает путь к текущему каталогу, в котором находятся файлы для чтения.
 *
 * @return путь к текущему каталогу
 */
String getCurrentDirectory()
{
    return filesPath;
}
/**
 * Устанавливает путь к файлам для чтения и для записи.
 *
 * @param filesReaderPath путь к файлам для чтения
 * @param filesWriterPath путь к файлам для записи
 */
private void setFilesPath(String filesReaderPath, String filesWriterPath)
{
    this.filesReaderPath = filesReaderPath;
    this.filesWriterPath = filesWriterPath;
}
/**
 * Закрывает поток чтения и поток записи
 *
 * @param bufferedReader   буфферизированный поток для чтения данных из файла
 * @param randomAccessFile поток для записи данных в файл с произвольным доступом
 *
 * @return результат закрытия потоков чтения и записи
 */
boolean close()
{
    try
    {
        pair.getKey().close();
        pair.getValue().close();
    }
    catch (IOException ex)
    {
        LOGGER.error("Error close file " + ex);
        return false;
    }
    return true;
}
/**
 * Присваивает ссылкам на ненужные объекты в классе FilesManager значение null.
 */
private void clearObjectsFilesManager()
{
    filesNamesFromDirectory = null;
    filesPath = null;
}
}
