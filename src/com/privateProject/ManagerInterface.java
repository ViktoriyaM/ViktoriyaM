package com.privateProject;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface ManagerInterface
{

public static final String QUITE = "q";
public static final String CONTINUE = "continue";

void managing() throws ParserConfigurationException, SAXException, IOException;
String showMenu(List<String> scaleValue);
void closeConsole();
}
