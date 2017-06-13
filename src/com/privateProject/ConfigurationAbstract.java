package com.privateProject;

import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

public abstract class ConfigurationAbstract
{
protected static final String DEFAULT_XML_FILENAME = "mapProperties.xml";

protected DocumentBuilderFactory builderFactory = null;
protected Document document = null;
protected XPath xpath = null;

protected Set<String> objects = null;
protected Set<String> filesNames = null;
protected StringBuilder filesPath = null;
protected StringBuilder filesType = null;
protected List<String> scaleValue = null;

protected abstract List<String> configurationScaleValue(List<String> scaleValue, Document document, XPath xpath);

protected abstract Set<String> configurationObjects(Set<String> objects, Document document, XPath xpath, String scale);

protected abstract Set<String> configurationFilesNames(Set<String> filesNames, Document document, XPath xpath, String scale);

protected abstract StringBuilder configurationFilesPath(StringBuilder filePath, Document document, XPath xpath, String scale);

protected abstract StringBuilder configurationFilesType(StringBuilder filesType, Document document, XPath xpath);
        
abstract Set<String> getObjects(String scale);

abstract Set<String> getFilesNames(String scale);

abstract StringBuilder getFilesPath(String scale);

abstract StringBuilder getFilesType();
        
abstract List<String> getScaleValues();
}
