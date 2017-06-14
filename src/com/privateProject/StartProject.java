package com.privateProject;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class StartProject
{

/**
 @param args the command line arguments
 */
public static void main(String[] args)
{
    try
    {
        Manager manager = new Manager();
        manager.managing();
    }
    catch (IOException | ParserConfigurationException | SAXException e)
    {
        System.out.println(e);
    }
}

}
