package com.privateProject;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final public class Configuration extends ConfigurationAbstract
{

private static final Logger LOGGER = LogManager.getLogger(Configuration.class.getName());

Configuration()
{
    objects = new LinkedHashSet<>();
    filesNames = new LinkedHashSet<>();
    scaleValues = new ArrayList<>();
}

/**
 * Выполняет загрузку ресурса по умолчанию (XML-документа) для чтения.
 *
 * @return true при успешной загрузке и подключению к файловому ресурсу,
 *         false - в противном случае
 */
@Override
boolean initialize()
{

    boolean resultInit = initialize(DEFAULT_XML_FILE);
    return resultInit;
}

/**
 * Выполняет загрузку ресурса (XML-документа) для чтения, создает дерево документа из файла,
 * выполняет синтаксический анализ XML-документа, полученного из заданного потока ввода,
 * создает фабрику XPathFactory и использует ее для создания объека XPath.
 *
 * @param fileName имя XML-документа
 *
 * @return true при успешной загрузке и подключению к файловому ресурсу,
 *         false - в противном случае
 */
@Override
boolean initialize(String fileName)
{
    URL inputStream = getClass().getClassLoader().getResource(fileName);

    try
    {
        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        builderFactory.setIgnoringComments(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        document = builder.parse(inputStream.toString());

        XPathFactory xpathFactory = XPathFactory.newInstance();
        xpath = xpathFactory.newXPath();

        LOGGER.info("File " + inputStream + " is open successfully");

        return true;
    }
    catch (IOException | ParserConfigurationException | SAXException | IllegalArgumentException | NullPointerException ex)
    {
        LOGGER.error("Error initialize() ", ex);
        return false;
    }
}

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска всех масштабов.
 *
 * @param document представление всего XML-документа
 * @param xpath    XPath объект
 *
 * @return список всех масштабов из XML-файла
 */
@Override
protected List<String> configurationScaleValues(Document document, XPath xpath)
{
    scaleValues.clear();

    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale]");

        NodeList nodeList = (NodeList) xpathExpression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            scaleValues.add(nodeList.item(i).getAttributes().getNamedItem("scale").getNodeValue().trim());
        }

    }
    catch (XPathExpressionException | NullPointerException ex)
    {
        LOGGER.error("Error configurationScaleValues() ", ex);
    }

    return scaleValues;
}

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
@Override
protected Set<String> configurationObjects(Document document, XPath xpath, String scale)
{
    objects.clear();

    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/delete-objects");

        String objectsString = xpathExpression.evaluate(document, XPathConstants.STRING).toString();
        if (!("".equals(objectsString)))
        {
            objects.addAll(Arrays.asList(objectsString.split("\n| ")));
            objects.remove("");
        }
    }
    catch (XPathExpressionException | NullPointerException ex)
    {
        LOGGER.error("Error configurationObjects() ", ex);
    }

    return objects;

}

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска имен файлов, соответствующих выбранному пользователем значению масштаба.
 *
 * @param document представление всего XML-документа
 * @param xpath    XPath объект
 * @param scale    выбранное пользователем значение масштаба
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
@Override
protected Set<String> configurationFilesNames(Document document, XPath xpath, String scale)
{
    filesNames.clear();

    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-name");

        String filesNamesString = xpathExpression.evaluate(document, XPathConstants.STRING).toString();

        if (!("".equals(filesNamesString)))
        {
            filesNames.addAll(Arrays.asList(filesNamesString.split("\t|\n| ")));
            filesNames.remove("");
        }
    }
    catch (XPathExpressionException | NullPointerException ex)
    {
        LOGGER.error("Error configurationFilesNames() ", ex);
    }

    return filesNames;
}

/**
 * Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
 * к XML-документу для поиска пути к файлам, соответствующим выбранному пользователем значению масштаба.
 *
 * @param document представление всего XML-документа
 * @param xpath    XPath объект
 * @param scale    выбранное пользователем значение масштаба
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
@Override
protected String configurationFilesPath(Document document, XPath xpath, String scale)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-path");

        filesPath = xpathExpression.evaluate(document, XPathConstants.STRING).toString();
        if ("".equals(filesPath))
        {
            filesPath = null;
        }
    }
    catch (XPathExpressionException | NullPointerException ex)
    {
        LOGGER.error("Error configurationFilesPath() ", ex);
    }

    return filesPath;
}

@Override
/**
 * Выполняет формирование всех параметров из XML-документа, соответствующих выбранному пользователем значению масштаба.
 *
 * @param scaleSelected выбранное пользователем значение масштаба
 *
 * @return true при успешной загрузке всех параметров из XML-документа,
 *         false - в противном случае
 */
boolean configurationAllParameters(String scaleSelected)
{
    objects = configurationObjects(document, xpath, scaleSelected);
    filesNames = configurationFilesNames(document, xpath, scaleSelected);
    filesPath = configurationFilesPath(document, xpath, scaleSelected);

    if (objects.isEmpty() || filesNames.isEmpty() || filesPath.isEmpty())
    {
        LOGGER.error("Error initialize() " + "objects is Empty: " + objects.isEmpty() + "filesNames is Empty: " + filesNames.isEmpty()
                     + "filesPath is null: " + filesPath + "filesType is null: ");
        return false;
    }

    return true;
}

/**
 * Возвращает список объектов из XML-документа, соответствующих выбранному значению масштаба.
 *
 * @return список объектов из XML-документа, соответствующих выбранному значению масштаба
 */
@Override
Set<String> getObjects()
{
    return objects;
}

/**
 * Возвращает список имен файлов электронной карты, соответствующих выбранному значению масштаба.
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
@Override
Set<String> getFilesNames()
{
    return filesNames;
}

/**
 * Возвращает путь к файлам электронной карты, соответствующих выбранному значению масштаба.
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
@Override
String getFilesPath()
{
    return filesPath;
}

/**
 * Возвращает список всех масштабов из XML-файла.
 *
 * @return список всех масштабов из XML-файла
 */
@Override
List<String> getScaleValues()
{
    scaleValues = configurationScaleValues(document, xpath);

    return scaleValues;
}

}
