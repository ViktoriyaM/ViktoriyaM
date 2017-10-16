package com.privateProject;

import java.util.List;
import java.util.Scanner;

public interface ManagerInterface
{
static final String QUITE = "q";
static final String CONTINUE = "continue";
void managing();
String showMenu(List<String> scaleValues);
String inputValidation(List<String> scaleValues, Scanner inputLine);
boolean controlAlgorithm(String scaleSelected);
}
