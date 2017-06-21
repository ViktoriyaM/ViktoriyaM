package com.privateProject;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Configuration extends ConfigurationAbstract
{

private static final Logger LOGGER = LogManager.getLogger(Configuration.class.getName());

Configuration()
{
    objects = new LinkedHashSet<>();
    filesNames = new LinkedHashSet<>();
    scaleValues = new ArrayList<>();
}

/**
 * Выполняет загрузку ресурса (XML-документа) для чтения, создает дерево документа из файла,
 * создает фабрику XPathFactory и использует ее для создания объека XPath.
 *
 * @return true при успешной загрузке и подключению к файловому ресурсу,
 *         false - в противном случае
 */
@Override
boolean initialize()
{
    try
    {
//        file = new File(getClass().getClassLoader().getResource(DEFAULT_XML_FILE).toURI());
        inputStream = getClass().getClassLoader().getResource(DEFAULT_XML_FILE);

//        System.out.println(getClass().getResourceAsStream("/resources/mapProperties.xml"));
//        System.out.println(getClass().getResource("/resources/mapProperties.xml"));
//        System.out.println(getClass().getClassLoader().getResource("resources/mapProperties.xml"));
//        System.out.println(getClass().getClassLoader().getResourceAsStream("resources/mapProperties.xml"));
        builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        document = builder.parse(inputStream.toString());

        XPathFactory xpathFactory = XPathFactory.newInstance();
        xpath = xpathFactory.newXPath();

        LOGGER.info("File " + inputStream + " is open successfully");

        return true;
    }
    catch (IOException | ParserConfigurationException | SAXException | IllegalArgumentException ex)
    {
        LOGGER.error("Error initialize() ", ex);
        return false;
    }
}

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
@Override
protected List<String> configurationScaleValues(List<String> scaleValues, Document document, XPath xpath)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale]");

        NodeList nodeList = (NodeList) xpathExpression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            scaleValues.add(nodeList.item(i).getAttributes().getNamedItem("scale").getNodeValue());
        }

    }
    catch (XPathExpressionException ex)
    {
        LOGGER.error("Error configurationScaleValues() ", ex);
    }

    return scaleValues;
}

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
@Override
protected Set<String> configurationObjects(Set<String> objects, Document document, XPath xpath, String scale)
{

    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/delete-objects");

        objects.addAll(Arrays.asList(xpathExpression.evaluate(document, XPathConstants.STRING).toString().split("\n| ")));
    }
    catch (XPathExpressionException ex)
    {
        LOGGER.error("Error configurationObjects() ", ex);
    }

    return objects;

}

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
@Override
protected Set<String> configurationFilesNames(Set<String> filesNames, Document document, XPath xpath, String scale)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-name");

        filesNames.addAll(Arrays.asList(xpathExpression.evaluate(document, XPathConstants.STRING).toString().split("\t|\n| ")));
    }
    catch (XPathExpressionException ex)
    {
        LOGGER.error("Error configurationFilesNames() ", ex);
    }

    return filesNames;
}

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
@Override
protected String configurationFilesPath(String filesPath, Document document, XPath xpath, String scale)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-path");

        filesPath = (String) xpathExpression.evaluate(document, XPathConstants.STRING);
    }
    catch (XPathExpressionException ex)
    {
        LOGGER.error("Error configurationFilesPath() ", ex);
    }

    return filesPath;
}

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
@Override
protected String configurationFilesType(String filesType, Document document, XPath xpath)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/file-type");

        filesType = (String) xpathExpression.evaluate(document, XPathConstants.STRING);
    }
    catch (XPathExpressionException ex)
    {
        LOGGER.error("Error configurationFilesType() ", ex);
    }

    return filesType;
}

/**
 * Возвращает список объектов из XML-файла, соответствующих выбранному значению масштаба.
 *
 * @param scale выбранное пользователем значение масштаба
 *
 * @return список объектов из XML-файла, соответствующих выбранному значению масштаба
 */
@Override
Set<String> getObjects(String scale)
{
    objects.clear();
    objects = configurationObjects(objects, document, xpath, scale);

    return objects;
}

/**
 * Возвращает список имен файлов электронной карты, соответствующих выбранному значению масштаба.
 *
 * @param scale выбранное пользователем значение масштаба
 *
 * @return список имен файлов электронной карты, соответствующих выбранному значению масштаба
 */
@Override
Set<String> getFilesNames(String scale)
{
    filesNames.clear();
    filesNames = configurationFilesNames(filesNames, document, xpath, scale);

    return filesNames;
}

/**
 * Возвращает путь к файлам электронной карты, соответствующих выбранному значению масштаба.
 *
 * @param scale выбранное пользователем значение масштаба
 *
 * @return путь к файлам электронной карты, соответствующих выбранному значению масштаба
 */
@Override
String getFilesPath(String scale)
{
    String filesPath = null;
    filesPath = configurationFilesPath(filesPath, document, xpath, scale);

    return filesPath;
}

/**
 * Возвращает тип файлов электронной карты.
 *
 * @return тип файлов электронной карты
 */
@Override
String getFilesType()
{
    String filesType = null;
    filesType = configurationFilesType(filesType, document, xpath);

    return filesType;
}

/**
 * Возвращает список всех масштабов из XML-файла.
 *
 * @return список всех масштабов из XML-файла
 */
@Override
List<String> getScaleValues()
{
    scaleValues.clear();
    scaleValues = configurationScaleValues(scaleValues, document, xpath);

    return scaleValues;
}

}
