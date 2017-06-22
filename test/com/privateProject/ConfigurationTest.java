package com.privateProject;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigurationTest
{

private Configuration configuration = null;

public ConfigurationTest()
{
}

@BeforeClass
public static void setUpClass()
{
}

@AfterClass
public static void tearDownClass()
{
}

@Before
public void setUp()
{
    configuration = new Configuration();
}

@After
public void tearDown()
{
    configuration = null;
}

/**
 * Test of initialize method, of class Configuration.
 */
@Test
public void testInitialize()
{
    System.out.println("initialize");

    assertEquals(true, configuration.initialize());
}

/**
 * Test of initialize method with argument, of class Configuration.
 */
@Test
public void testInitializeWithArgument()
{
    System.out.println("initialize with argument");

//NullPointerException
    assertEquals(false, configuration.initialize("Test.xml"));
//    SAXException
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
}

/**
 * Test of configurationScaleValues method, of class Configuration.
 */
@Test
public void testConfigurationScaleValues()
{
    System.out.println("configurationScaleValues");
    List<String> expectedList = Collections.emptyList();

    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(expectedList, configuration.getScaleValues());
    tearDown();
    setUp();
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertEquals(expectedList, configuration.getScaleValues());
    tearDown();
    setUp();
    expectedList = new ArrayList<>();
    Collections.addAll(expectedList, "300 000", "1 000 000");
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedList, configuration.getScaleValues());
}

/**
 * Test of configurationObjects method, of class Configuration.
 */
@Test
public void testConfigurationObjects()
{
    System.out.println("configurationObjects");
    String scale = "300 000";
    LinkedHashSet<String> expectedSet = new LinkedHashSet<>();

    assertEquals(true, configuration.initialize("mapProperties_without_objects.xml"));
    assertEquals(expectedSet, configuration.getObjects(scale));
    tearDown();
    setUp();
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertEquals(expectedSet, configuration.getObjects(scale));
    tearDown();
    setUp();
    Collections.addAll(expectedSet, "32330000", "51141200");
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedSet, configuration.getObjects(scale));
}

/**
 * Test of configurationFilesNames method, of class Configuration.
 */
@Test
public void testConfigurationFilesNames()
{
    System.out.println("configurationScaleValues");
    String scale = "1 000 000";
    LinkedHashSet<String> expectedSet = new LinkedHashSet();

    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(expectedSet, configuration.getFilesNames(scale));
    tearDown();
    setUp();
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertEquals(expectedSet, configuration.getFilesNames(scale));
    tearDown();
    setUp();
    Collections.addAll(expectedSet, "L-37");
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedSet, configuration.getFilesNames(scale));
}

/**
 * Test of configurationFilesPath method, of class Configuration.
 */
@Test
public void testConfigurationFilesPath()
{
    System.out.println("configurationFilesPath");
    String scale = "300 000";
    String expectedString = "300 000/";

    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertNull(configuration.getFilesPath(scale));
    tearDown();
    setUp();
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertNull(configuration.getFilesPath(scale));
    tearDown();
    setUp();
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedString, configuration.getFilesPath(scale));
}

/**
 * Test of configurationFilesType method, of class Configuration.
 */
@Test
public void testConfigurationFilesType()
{
    System.out.println("configurationFilesType");
    String expectedString = ".txf";

    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertNull(configuration.getFilesType());
    tearDown();
    setUp();
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertNull(configuration.getFilesType());
    tearDown();
    setUp();
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedString, configuration.getFilesType());
}

/**
 * Test of getObjects method, of class Configuration.
 */
@Test
public void testGetObjects()
{
    System.out.println("getObjects");

    testConfigurationObjects();
}

/**
 * Test of getFilesNames method, of class Configuration.
 */
@Test
public void testGetFilesNames()
{
    System.out.println("getFilesNames");

    testConfigurationFilesNames();
}

/**
 * Test of getFilesPath method, of class Configuration.
 */
@Test
public void testGetFilesPath()
{
    System.out.println("getFilesPath");

    testConfigurationFilesPath();
}

/**
 * Test of getFilesType method, of class Configuration.
 */
@Test
public void testGetFilesType()
{
    System.out.println("getFilesType");

    testConfigurationFilesType();

}

/**
 * Test of getScaleValues method, of class Configuration.
 */
@Test
public void testGetScaleValues()
{
    System.out.println("getScaleValues");

    testConfigurationScaleValues();
}

}
