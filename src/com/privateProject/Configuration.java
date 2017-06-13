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

public final class Configuration extends ConfigurationAbstract
{

Configuration()
{
    builderFactory = DocumentBuilderFactory.newInstance();
    
    objects = new LinkedHashSet<>();
    filesNames = new LinkedHashSet<>();
    filesPath = new StringBuilder();
    filesType = new StringBuilder();
    scaleValue = new ArrayList<>();

    try
    {
        FileInputStream file = new FileInputStream(new File(DEFAULT_XML_FILENAME));

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        document = builder.parse(file);

        XPathFactory xpathFactory = XPathFactory.newInstance();
        xpath = xpathFactory.newXPath();
    }
    catch (ParserConfigurationException | SAXException | IOException e)
    {
        System.out.println(e);
    }
}

@Override
protected List<String> configurationScaleValue(List<String> scaleValue, Document document, XPath xpath)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale]");

        NodeList nodeList = (NodeList) xpathExpression.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            scaleValue.add(nodeList.item(i).getAttributes().getNamedItem("scale").getNodeValue());
        }

    }
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return scaleValue;
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
protected StringBuilder configurationFilesPath(StringBuilder filesPath, Document document, XPath xpath, String scale)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/scale[@scale='" + scale + "']/file-path");

        filesPath.insert(0, (String) xpathExpression.evaluate(document, XPathConstants.STRING));
    }
    catch (XPathExpressionException e)
    {
        System.out.println(e);
    }

    return filesPath;
}

@Override
protected StringBuilder configurationFilesType(StringBuilder filesType, Document document, XPath xpath)
{
    try
    {
        XPathExpression xpathExpression = xpath.compile("/maps/file-type");

        filesType.insert(0, (String) xpathExpression.evaluate(document, XPathConstants.STRING));
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
StringBuilder getFilesPath(String scale)
{
    filesPath.delete(0, filesPath.length());
    filesPath = configurationFilesPath(filesPath, document, xpath, scale);

    return filesPath;
}

@Override
StringBuilder getFilesType()
{
    filesType.delete(0, filesType.length());
    filesPath = configurationFilesType(filesType, document, xpath);

    return filesPath;
}

@Override
List<String> getScaleValues()
{
    scaleValue.clear();
    scaleValue = configurationScaleValue(scaleValue, document, xpath);

    return scaleValue;
}

}
