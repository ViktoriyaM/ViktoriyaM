package com.privateProject;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

public class ManagerTest
{
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
    manager = new Manager();
}
@After
public void tearDown()
{
    manager = null;
}
/**
 * Test of inputValidation method, of class Manager.
 */
@Test
public void testInputValidation()
{
    System.out.println("inputValidation");
    List<String> scaleValues = new ArrayList<>();
    Collections.addAll(scaleValues, "50", "80 000", "150 000", "300 000");
//Test number scale from array
    assertEquals("50", manager.inputValidation(scaleValues, new Scanner("0")));
    assertEquals("80 000", manager.inputValidation(scaleValues, new Scanner("1")));
    assertEquals("300 000", manager.inputValidation(scaleValues, new Scanner("3")));
//Test Reentry
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("1")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("3")));
//Test Incorrect
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner(" 12Symbol")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("!Number")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("Character")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("Q")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner(" 2.")));
//Test Exit
    assertEquals("q", manager.inputValidation(scaleValues, new Scanner("q")));
}
/**
 * Test of controlAlgorithm method, of class Manager.
 */
@Rule
public ExpectedException expectedException = ExpectedException.none();
@Test
public void testControlAlgorithm() throws NoSuchElementException
{
    System.out.println("controlAlgorithm");
    String scale = "1 000 000";
////Test with bad initialize
    assertEquals(false, manager.controlAlgorithm(scale));
    tearDown();
    setUp();
//Test with good initialize
    expectedException.expect(NoSuchElementException.class);
    manager.managing();
    assertEquals(true, manager.controlAlgorithm(scale));
}
}
