package com.privateProject;

import java.util.List;
import java.util.Set;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

public abstract class ConfigurationAbstract
{
protected static final String DEFAULT_XML_FILE = "mapProperties.xml";
protected Document document = null;
protected XPath xpath = null;
protected Set<String> objects = null;
protected Set<String> filesNames = null;
protected List<String> scaleValues = null;
protected String filesPath = null;
abstract boolean initialize();
abstract boolean initialize(String fileName);
protected abstract List<String> configurationScaleValues(Document document, XPath xpath);
protected abstract Set<String> configurationObjectsForDelete(Document document, XPath xpath, String scale);
protected abstract Set<String> configurationFilesNames(Document document, XPath xpath, String scale);
protected abstract String configurationFilesPath(Document document, XPath xpath, String scale);
abstract boolean configurationAllParameters(String scaleSelected);
abstract Set<String> getObjectsForDelete();
abstract Set<String> getFilesNames();
abstract String getFilesPath();
abstract List<String> getScaleValues();
}
