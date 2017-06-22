package com.privateProject;

import java.net.URL;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

public abstract class ConfigurationAbstract
{

protected static final String DEFAULT_XML_FILE = "resources/mapProperties.xml";

protected DocumentBuilderFactory builderFactory = null;
protected Document document = null;
protected XPath xpath = null;

protected Set<String> objects = null;
protected Set<String> filesNames = null;
protected List<String> scaleValues = null;
protected URL inputStream = null;

/**
 * Выполняет загрузку ресурса (XML-документа) для чтения, создает дерево документа из файла,
 * создает фабрику XPathFactory и использует ее для создания объека XPath.
 *
 * @return true при успешной загрузке и подключению к файловому ресурсу,
 *         false - в противном случае
 */
abstract boolean initialize();

/**
 * Выполняет загрузку ресурса (XML-документа) для чтения, создает дерево документа из файла,
 * создает фабрику XPathFactory и использует ее для создания объека XPath.
 *
 * @param fileName имя XML-документа
 * @return true при успешной загрузке и подключению к файловому ресурсу,
 *         false - в противном случае
 */
abstract boolean initialize(String fileName);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска всех масштабов.
 *
 * @param scaleValues список всех масштабов из XML-файла
 * @param document    представление всего XML-документа
 * @param xpath       XPath объект
 *
 * @return список всех масштабов из XML-файла
 */
protected abstract List<String> configurationScaleValues(List<String> scaleValues, Document document, XPath xpath);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска объектов, соответствующих выбранному пользователем значению масштаба.
 *
 * @param objects  список объектов из XML-файла, соответствующих выбранному значению масштаба
 * @param document представление всего XML-документа
 * @param xpath    XPath объект
 * @param scale    выбранное пользователем значение масштаба
 *
 * @return список объектов из XML-файла, соответствующих выбранному значению масштаба
 */
protected abstract Set<String> configurationObjects(Set<String> objects, Document document, XPath xpath, String scale);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска имен файлов, соответствующих выбранному пользователем значению масштаба.
 *
 * @param filesNames список файлов электронной карты, соответствующих выбранному значению масштаба
 * @param document   представление всего XML-документа
 * @param xpath      XPath объект
 * @param scale      выбранное пользователем значение масштаба
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
protected abstract Set<String> configurationFilesNames(Set<String> filesNames, Document document, XPath xpath, String scale);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска пути к файлам, соответствующим выбранному пользователем значению масштаба.
 *
 * @param filesPath путь к файлам электронной карты, соответствующих выбранному значению масштаба
 * @param document  представление всего XML-документа
 * @param xpath     XPath объект
 * @param scale     выбранное пользователем значение масштаба
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
protected abstract String configurationFilesPath(String filesPath, Document document, XPath xpath, String scale);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска типа файлов электронной карты.
 *
 * @param filesType тип файлов электронной карты
 * @param document  представление всего XML-документа
 * @param xpath     XPath объект
 *
 * @return тип файлов электронной карты
 */
protected abstract String configurationFilesType(String filesType, Document document, XPath xpath);

/**
 * Возвращает список объектов из XML-файла, соответствующих выбранному значению масштаба.
 *
 * @param scale выбранное пользователем значение масштаба
 *
 * @return список объектов из XML-файла, соответствующих выбранному значению масштаба
 */
abstract Set<String> getObjects(String scale);

/**
 * Возвращает список имен файлов электронной карты, соответствующих выбранному значению масштаба.
 *
 * @param scale выбранное пользователем значение масштаба
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
abstract Set<String> getFilesNames(String scale);

/**
 * Возвращает путь к файлам электронной карты, соответствующих выбранному значению масштаба.
 *
 * @param scale выбранное пользователем значение масштаба
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
abstract String getFilesPath(String scale);

/**
 * Возвращает тип файлов электронной карты.
 *
 * @return тип файлов электронной карты
 */
abstract String getFilesType();

/**
 * Возвращает список всех масштабов из XML-файла.
 *
 * @return список всех масштабов из XML-файла
 */
abstract List<String> getScaleValues();

}
