/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privateProject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victoriya
 */
public class FilesManagerTest
{

private FilesManager filesManager = null;
private Configuration configuration = null;

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
    filesManager = new FilesManager();
    configuration = new Configuration();
}

@After
public void tearDown()
{
    filesManager = null;
    configuration = null;
}

/**
 * Test of initializeCatalog method, of class FilesManager.
 */
@Test
public void testInitializeCatalog()
{
    System.out.println("initializeCatalog");
    String scale = "200 000";
//Test with all files in catalog with good initialize
    assertEquals(true, configuration.initialize());
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(true, filesManager.initializeCatalog(configuration));

    tearDown();
    setUp();
//Test not with all files in catalog with good initialize
    assertEquals(true, configuration.initialize("mapProperties_nonexistent_file.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(false, filesManager.initializeCatalog(configuration));

    tearDown();
    setUp();
//Test without files names and path in XML-file with good initialize (before configuration All Parameters)
    assertEquals(true, configuration.initialize("mapProperties_without_scales.xml"));
    assertEquals(false, filesManager.initializeCatalog(configuration));

    tearDown();
    setUp();
//Test with arror files path in catalog with good initialize
    assertEquals(true, configuration.initialize("mapProperties_error_files_path.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(false, filesManager.initializeCatalog(configuration));
}

/**
 * Test of hasNext method, of class FilesManager.
 */
@Test
public void testHasNext()
{
    System.out.println("hasNext");
    String scale = "200 000";
//Test without all files names and after configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_without_files_names.xml"));
    assertEquals(false, configuration.configurationAllParameters(scale));
    assertEquals(false, filesManager.initializeCatalog(configuration));
    assertEquals(false, filesManager.hasNext());

    tearDown();
    setUp();

//Test with all files in catalog with good initialize and before configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_without_files_names.xml"));
    assertEquals(false, filesManager.initializeCatalog(configuration));
    assertEquals(false, filesManager.hasNext());

    //Test with all files in catalog with good initialize and after configuration All Parameters
    assertEquals(true, configuration.initialize("mapProperties_without_files_names.xml"));
    assertEquals(false, configuration.configurationAllParameters(scale));
    assertEquals(false, filesManager.initializeCatalog(configuration));
    assertEquals(false, filesManager.hasNext());

//Test with one file in catalog with good initialize
    assertEquals(true, configuration.initialize("mapProperties_one_file.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(true, filesManager.initializeCatalog(configuration));
    assertEquals(true, filesManager.hasNext());
    filesManager.next();
    assertEquals(false, filesManager.hasNext());
}

/**
 * Test of next method, of class FilesManager.
 */
@Test
public void testNext()
{
    System.out.println("next");
    String scale = "1 000 000";
//Test with one file in catalog with good initialize
    assertEquals(true, configuration.initialize("mapProperties_one_file.xml"));
    assertEquals(true, configuration.configurationAllParameters(scale));
    assertEquals(true, filesManager.initializeCatalog(configuration));
    assertEquals(true, filesManager.hasNext());
    assertNotNull(filesManager.next());
//NoSuchElementException    
    assertNull(filesManager.next());

}

/**
 * Test of getCurrentFile method, of class FilesManager.
 */
@Test
public void testGetCurrentFile()
{
    System.out.println("getCurrentFile");

}

}
