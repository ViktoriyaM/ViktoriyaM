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
 * Test of showMenu method, of class Manager.
 */
@Test
public void testInputValidation()
{
    System.out.println("inputValidation");
    List<String> scaleValues = new ArrayList<>();
    Collections.addAll(scaleValues, "50", "80 000", "150 000", "300 000");

//    номер масштаба из массива
    assertEquals("50", manager.inputValidation(scaleValues, new Scanner("0")));
    assertEquals("80 000", manager.inputValidation(scaleValues, new Scanner("1")));
    assertEquals("300 000", manager.inputValidation(scaleValues, new Scanner("3")));
//    повторный ввод
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("1")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("3")));
//    некорректный ввод
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner(" 12Symbol")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("!Number")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("Character")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner("Q")));
    assertEquals("continue", manager.inputValidation(scaleValues, new Scanner(" 2.")));
//    выход
    assertEquals("q", manager.inputValidation(scaleValues, new Scanner("q")));
}

}
