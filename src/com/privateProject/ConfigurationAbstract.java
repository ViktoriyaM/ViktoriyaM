package com.privateProject;

import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

public abstract class ConfigurationAbstract
{

protected static final String DEFAULT_XML_FILE = "mapProperties.xml";

protected DocumentBuilderFactory builderFactory = null;
protected Document document = null;
protected XPath xpath = null;

protected Set<String> objects = null;
protected Set<String> filesNames = null;
protected List<String> scaleValues = null;
protected String filesPath = null;

/**
 * Выполняет загрузку ресурса по умолчанию (XML-документа) для чтения.
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
 * @param document    представление всего XML-документа
 * @param xpath       XPath объект
 *
 * @return список всех масштабов из XML-файла
 */
protected abstract List<String> configurationScaleValues(Document document, XPath xpath);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска объектов, соответствующих выбранному пользователем значению масштаба.
 *
 * @param document представление всего XML-документа
 * @param xpath    XPath объект
 * @param scale    выбранное пользователем значение масштаба
 *
 * @return список объектов из XML-файла, соответствующих выбранному значению масштаба
 */
protected abstract Set<String> configurationObjects(Document document, XPath xpath, String scale);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска имен файлов, соответствующих выбранному пользователем значению масштаба.
 *
 * @param document   представление всего XML-документа
 * @param xpath      XPath объект
 * @param scale      выбранное пользователем значение масштаба
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
protected abstract Set<String> configurationFilesNames(Document document, XPath xpath, String scale);

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска пути к файлам, соответствующим выбранному пользователем значению масштаба.
 *
 * @param document  представление всего XML-документа
 * @param xpath     XPath объект
 * @param scale     выбранное пользователем значение масштаба
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
protected abstract String configurationFilesPath(Document document, XPath xpath, String scale);

/**
 * Выполняет формирование всех параметров по заданному масштабу из XML-документа
 * @param scaleSelected выбранное пользователем значение масштаба
 *
 * @return true при успешной загрузке всех параметров по заданному масштабу из XML-документа,
 *         false - в противном случае
 */
abstract boolean configurationAllParameters(String scaleSelected);

/**
 * Возвращает список объектов из XML-файла, соответствующих выбранному значению масштаба.
 *
 * @return список объектов из XML-файла, соответствующих выбранному значению масштаба
 */
abstract Set<String> getObjects();

/**
 * Возвращает список имен файлов электронной карты, соответствующих выбранному значению масштаба.
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
abstract Set<String> getFilesNames();

/**
 * Возвращает путь к файлам электронной карты, соответствующих выбранному значению масштаба.
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
abstract String getFilesPath();

/**
 * Возвращает список всех масштабов из XML-файла.
 *
 * @return список всех масштабов из XML-файла
 */
abstract List<String> getScaleValues();

}
