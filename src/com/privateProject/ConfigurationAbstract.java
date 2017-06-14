package com.privateProject;

import java.io.FileInputStream;
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
protected List<String> scaleValues = null;
protected FileInputStream file = null;
        
protected abstract List<String> configurationScaleValues(List<String> scaleValues, Document document, XPath xpath);

protected abstract Set<String> configurationObjects(Set<String> objects, Document document, XPath xpath, String scale);

protected abstract Set<String> configurationFilesNames(Set<String> filesNames, Document document, XPath xpath, String scale);

protected abstract String configurationFilesPath(String filesPath, Document document, XPath xpath, String scale);

protected abstract String configurationFilesType(String filesType, Document document, XPath xpath);
        
abstract Set<String> getObjects(String scale);

abstract Set<String> getFilesNames(String scale);

abstract String getFilesPath(String scale);

abstract String getFilesType();
        
abstract List<String> getScaleValues();

abstract void closeFile();
}
