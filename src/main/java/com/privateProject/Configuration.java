package com.privateProject;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.*;

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
	 * false - в противном случае
	 */
	@Override
	boolean initialize()
	{
		return initialize(DEFAULT_XML_FILE);
	}
	
	/**
	 * Выполняет загрузку ресурса (XML-документа) для чтения, создает дерево документа из файла,
	 * выполняет синтаксический анализ XML-документа. Создает фабрику XPathFactory и использует ее для создания объека XPath.
	 *
	 * @param fileName имя XML-документа
	 * @return true при успешной загрузке и подключению к файловому ресурсу,
	 * false - в противном случае
	 */
	@Override
	boolean initialize(String fileName)
	{
		try
		{
			String pathToFile = getClass().getClassLoader().getResource(fileName).toString();
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setNamespaceAware(true);
			builderFactory.setIgnoringComments(true);
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			document = builder.parse(pathToFile);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			xpath = xpathFactory.newXPath();
			LOGGER.info("File " + pathToFile + " is open successfully");
			return true;
		}
		catch (IOException | ParserConfigurationException | SAXException | IllegalArgumentException | NullPointerException ex)
		{
			LOGGER.error("Error: initialize function is failed ", ex);
			return false;
		}
	}
	
	
	/**
	 * Выполняет компиляцию XPath-выражения. Применяет скомпилированный выриант
	 * к XML-документу для поиска объектов, которые необходимо удалить для выбранного масштаба.
	 *
	 * @param scale выбранное пользователем значение масштаба
	 */
	@Override
	protected final void configurationObjectsForDelete(String scale)
	{
		objects.clear();
		try
		{
			XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/objectsForDelete");
			String objectsString = xpathExpression.evaluate(document, XPathConstants.STRING).toString();
			if (!objectsString.isEmpty())
			{
				objects.addAll(Arrays.asList(objectsString.split("\\s+")));
				objects.remove("");
			}
		}
		catch (XPathExpressionException | NullPointerException ex)
		{
			LOGGER.error("Error: configuration objects function is failed ", ex);
		}
	}
	
	/**
	 * Выполняет компиляцию XPath-выражения. Применяет скомпилированный выриант
	 * к XML-документу для поиска имен файлов, которые необходимо удалить для выбранного масштаба.
	 *
	 * @param scale выбранное пользователем значение масштаба
	 */
	@Override
	protected final void configurationFilesNames(String scale)
	{
		filesNames.clear();
		try
		{
			XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/filesNames");
			String filesNamesString = xpathExpression.evaluate(document, XPathConstants.STRING).toString();
			if (!filesNamesString.isEmpty())
			{
				filesNames.addAll(Arrays.asList(filesNamesString.split("\\s+")));
			}
		}
		catch (XPathExpressionException | NullPointerException ex)
		{
			LOGGER.error("Error: configuration files names function is failed ", ex);
		}
	}
	
	/**
	 * Выполняет компиляцию XPath-выражения. Применяет скомпилированный выриант
	 * к XML-документу для поиска пути к файлам выбранного масштаба.
	 *
	 * @param scale выбранное пользователем значение масштаба
	 */
	@Override
	protected final void configurationFilesPath(String scale)
	{
		try
		{
			XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/filesPath");
			filesPath = xpathExpression.evaluate(document, XPathConstants.STRING).toString();
			if (filesPath.isEmpty())
			{
				filesPath = null;
			}
		}
		catch (XPathExpressionException | NullPointerException ex)
		{
			LOGGER.error("Error: configuration files path function is failed ", ex);
		}
	}
	
	
	/**
	 * Выполняет формирование всех параметров из XML-документа, соответствующих выбранному масштабу.
	 *
	 * @param scale выбранное пользователем значение масштаба
	 * @return true при успешной загрузке всех параметров из XML-документа,
	 * false - в противном случае
	 */
	@Override
	boolean configurationAllParameters(String scale)
	{
		configurationObjectsForDelete(scale);
		configurationFilesNames(scale);
		configurationFilesPath(scale);
		if (objects.isEmpty() || filesNames.isEmpty() || filesPath.isEmpty())
		{
			LOGGER.error("Error: initialize function is failed " + "objects is Empty: " + objects.isEmpty() + "filesNames is Empty: " + filesNames.isEmpty() + "filesPath is null: " + filesPath + "filesType is null: ");
			return false;
		}
		return true;
	}
	
	/**
	 * Возвращает список объектов из XML-документа, соответствующих выбранному масштабу.
	 *
	 * @return список объектов из XML-документа, соответствующих выбранному масштабу
	 */
	@Override
	Set<String> getObjectsForDelete()
	{
		return objects;
	}
	
	/**
	 * Возвращает список имен файлов электронной карты, соответствующих выбранному масштабу.
	 *
	 * @return список имен файлов электронной карты, соответствующих выбранному масштабу
	 */
	@Override
	Set<String> getFilesNames()
	{
		return filesNames;
	}
	
	/**
	 * Возвращает путь к файлам электронной карты, соответствующих выбранному масштабу.
	 *
	 * @return путь к файлам электронной карты, соответствующих выбранному масштабу
	 */
	@Override
	String getFilesPath()
	{
		return filesPath;
	}
	
	/**
	 * Возвращает список всех масштабов из XML-файла. Выполняет компиляцию XPath-выражения и применяет скомпилированный выриант
	 * к XML-документу для поиска всех масштабов.
	 *
	 * @return список всех масштабов из XML-файла
	 */
	@Override
	List<String> getScaleValues()
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
}
