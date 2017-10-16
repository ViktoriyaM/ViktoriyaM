package com.privateProject;

import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author victoriya
 */
public class AlgorithmTest
{
private Algorithm algorithm = null;
private Configuration configuration = null;
private Manager manager = null;
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
    algorithm = new Algorithm();
    configuration = new Configuration();
    manager = new Manager();
}
@After
public void tearDown()
{
    algorithm = null;
    configuration = null;
    manager = null;
}
/**
 * Test of getObjects method, of class Algorithm.
 */
@Rule
public ExpectedException expectedException = ExpectedException.none();
@Test
public void testUpdateObjects()
{
    System.out.println("getObjects");
//Test without before configuration All Parameters
    assertEquals(false, algorithm.updateObjects(configuration));
    tearDown();
    setUp();
//Test after configuration All Parameters    
    expectedException.expect(NoSuchElementException.class);
    String scale = "1 000 000";
    String fileName = "mapProperties_test_correct.xml";
    configuration.initialize(fileName);
    assertEquals(true, manager.controlAlgorithm(scale));
    assertEquals(true, algorithm.updateObjects(configuration));
}
/**
 * Test of readWriteFile method, of class Algorithm.
 */
@Test
public void testReadWriteFile()
{
    System.out.println("readWriteFile");
}
}
