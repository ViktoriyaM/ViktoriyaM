/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privateProject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.util.Set;
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
public class AlgorithmTest
{

public AlgorithmTest()
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
}

@After
public void tearDown()
{
}

    /**
     * Test of initialize method, of class Algorithm.
     */
    @Test
    public void testInitialize()
    {
        System.out.println("initialize");
        Configuration configuration = null;
        String scaleSelected = "";
        Algorithm instance = new Algorithm();
        boolean expResult = false;
        boolean result = instance.initialize(configuration, scaleSelected);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of openCloseFile method, of class Algorithm.
     */
    @Test
    public void testOpenCloseFile()
    {
        System.out.println("openCloseFile");
        Set<String> filesNames = null;
        String filesPath = "";
        String filesType = "";
        Algorithm instance = new Algorithm();
        instance.openCloseFile(filesNames, filesPath, filesType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readWriteFile method, of class Algorithm.
     */
    @Test
    public void testReadWriteFile()
    {
        System.out.println("readWriteFile");
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        String fileNameMapping = "";
        String filesPath = "";
        String filesType = "";
        Algorithm instance = new Algorithm();
        instance.readWriteFile(bufferedReader, fileWriter, fileNameMapping, filesPath, filesType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}