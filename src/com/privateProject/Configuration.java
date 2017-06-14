package com.privateProject;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Configuration extends ConfigurationAbstract
{

private static Configuration instance = null;

private Configuration() throws ParserConfigurationException, SAXException, IOException
{
    objects = new LinkedHashSet<>();
    filesNames = new LinkedHashSet<>();
    scaleValues = new ArrayList<>();
  
    file = new FileInputStream(new File(DEFAULT_XML_FILENAME));

    builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    document = builder.parse(file);

    XPathFactory xpathFactory = XPathFactory.newInstance();
    xpath = xpathFactory.newXPath();

}

static Configuration getInstance() throws ParserConfigurationException, SAXException, IOException
{
    if (instance == null)
    {
        instance = new Configuration();
    }

    return instance;
}

@Override
void closeFile()
{
    try
    {
        file.close();
    }
    catch (IOException ex)
    {
        Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
    }
}

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
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return scaleValues;
}

@Override
protected Set<String> configurationObjects(Set<String> objects, Document document, XPath xpath, String scale)
{

    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/delete-objects");

        objects.addAll(Arrays.asList(xpathExpression.evaluate(document, XPathConstants.STRING).toString().split("\n| ")));
    }
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return objects;

}

@Override
protected Set<String> configurationFilesNames(Set<String> filesNames, Document document, XPath xpath, String scale)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-name");

        filesNames.addAll(Arrays.asList(xpathExpression.evaluate(document, XPathConstants.STRING).toString().split("\t|\n| ")));
    }
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return filesNames;
}

@Override
protected String configurationFilesPath(String filesPath, Document document, XPath xpath, String scale)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-path");

        filesPath = (String) xpathExpression.evaluate(document, XPathConstants.STRING);
    }
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return filesPath;
}

@Override
protected String configurationFilesType(String filesType, Document document, XPath xpath)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/file-type");

        filesType = (String) xpathExpression.evaluate(document, XPathConstants.STRING);
    }
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return filesType;
}

@Override
Set<String> getObjects(String scale)
{
    objects.clear();
    objects = configurationObjects(objects, document, xpath, scale);

    return objects;
}

@Override
Set<String> getFilesNames(String scale)
{
    filesNames.clear();
    filesNames = configurationFilesNames(filesNames, document, xpath, scale);

    return filesNames;
}

@Override
String getFilesPath(String scale)
{
    String filesPath = null;
    filesPath = configurationFilesPath(filesPath, document, xpath, scale);

    return filesPath;
}

@Override
String getFilesType()
{
    String filesType = null;
    filesType = configurationFilesType(filesType, document, xpath);

    return filesType;
}

@Override
List<String> getScaleValues()
{
    scaleValues.clear();
    scaleValues = configurationScaleValues(scaleValues, document, xpath);

    return scaleValues;
}

}
