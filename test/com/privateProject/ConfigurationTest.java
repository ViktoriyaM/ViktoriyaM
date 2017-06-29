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

//Test without file NullPointerException IOException
    assertEquals(false, configuration.initialize("Test.xml"));
//Test with bad file SAXException
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
//Test with good file
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
}

/**
 * Test of configurationScaleValues method, of class Configuration.
 */
@Test
public void testConfigurationScaleValues()
{
    System.out.println("configurationScaleValues");
    List<String> expectedList = Collections.emptyList();
//Test without scales with good initialize
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(expectedList, configuration.getScaleValues());

    tearDown();
    setUp();
//Test without scales with bad initialize
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertEquals(expectedList, configuration.getScaleValues());

    tearDown();
    setUp();
//Test with scale with good initialize   
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
//Test without objects with good initialize
    assertEquals(true, configuration.initialize("mapProperties_without_objects.xml"));
    assertEquals(expectedSet, configuration.getObjects());

    tearDown();
    setUp();
//Test without objects with bad initialize
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertEquals(expectedSet, configuration.getObjects());

    tearDown();
    setUp();
//Test with objects with good initialize but before configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedSet, configuration.getObjects());

    tearDown();
    setUp();
//Test without objects with good initialize and after configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_without_objects.xml"));
    assertEquals(false, configuration.configurationAllParameters(scale));
    assertEquals(expectedSet, configuration.getObjects());

    tearDown();
    setUp();
//Test with objects with good initialize and after configuration All Parameters
    Collections.addAll(expectedSet, "32330000", "51141200");
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(expectedSet, configuration.getObjects());
//New entry    
    scale = "1 000 000";
    expectedSet.clear();
    Collections.addAll(expectedSet, "31134000", "23100000");
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(expectedSet, configuration.getObjects());
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
//Test without Files Names with good initialize
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(expectedSet, configuration.getFilesNames());

    tearDown();
    setUp();
//Test without Files Names with bad initialize  
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertEquals(expectedSet, configuration.getFilesNames());

    tearDown();
    setUp();
//Test with Files Names with good initialize but before configuration All Parameters    
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(expectedSet, configuration.getFilesNames());

    tearDown();
    setUp();
//Test without Files Names with good initialize and after configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(false, configuration.configurationAllParameters(scale));
    assertEquals(expectedSet, configuration.getFilesNames());

    tearDown();
    setUp();
//Test with Files Names with good initialize and after configuration All Parameters
    Collections.addAll(expectedSet, "L-37.txf");
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(expectedSet, configuration.getFilesNames());
//New entry    
    scale = "300 000";
    expectedSet.clear();
    Collections.addAll(expectedSet, "M-37-08.txf");
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(expectedSet, configuration.getFilesNames());
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
//Test without Files Path with good initialize
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertNull(configuration.getFilesPath());

    tearDown();
    setUp();
    //Test without Files Path with bad initialize   
    assertEquals(false, configuration.initialize("mapProperties_without_all.xml"));
    assertNull(configuration.getFilesPath());

    tearDown();
    setUp();
//Test with Files Path with good initialize but before configuration All Parameters      
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertNull(configuration.getFilesPath());
    
    tearDown();
    setUp();
//Test without Files Path with good initialize and after configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(false, configuration.configurationAllParameters(scale));
    assertNull(configuration.getFilesPath());

    tearDown();
    setUp();
    
//Test with Files Path with good initialize and after configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_test_correct.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(expectedString, configuration.getFilesPath());
//New entry    
    scale = "1 000 000";
    expectedString = "1 000 000/";
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(expectedString, configuration.getFilesPath());

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
 * Test of getScaleValues method, of class Configuration.
 */
@Test
public void testGetScaleValues()
{
    System.out.println("getScaleValues");

    testConfigurationScaleValues();
}

}
