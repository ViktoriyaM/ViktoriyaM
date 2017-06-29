/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privateProject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.NoSuchElementException;
import java.util.Set;
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
private FilesManager filesManager = null;

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
    filesManager = new FilesManager();
}

@After
public void tearDown()
{
    algorithm = null;
    configuration = null;
    manager = null;
    filesManager = null;
}

/**
 * Test of getObjects method, of class Algorithm.
 */
@Rule
public ExpectedException expectedException = ExpectedException.none();

@Test
public void testGetObjects()
{
    System.out.println("getObjects");
    String scale = "200 000";
//Test without before configuration All Parameters
    assertEquals(false, algorithm.getObjects(configuration));

    tearDown();
    setUp();
//Test after configuration All Parameters    
    expectedException.expect(NoSuchElementException.class);
    manager.managing();
    assertEquals(true, manager.controlAlgorithm(scale));
    assertEquals(true, algorithm.getObjects(configuration));

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
