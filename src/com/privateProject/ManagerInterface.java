package com.privateProject;

import java.util.List;
import java.util.Scanner;

public interface ManagerInterface
{

public static final String QUITE = "q";
public static final String CONTINUE = "continue";

void managing();

String showMenu(List<String> scaleValues);

public String inputValidation(List<String> scaleValues, Scanner inputLine);


public boolean controlAlgorithm(String scaleSelected);
}
