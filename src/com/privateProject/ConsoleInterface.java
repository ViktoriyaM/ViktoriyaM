package com.privateProject;

import java.util.List;

public interface ConsoleInterface
{

public static final String QUITE = "q";
public static final String CONTINUE = "continue";

String readingConsoleData(List<String> scaleValue);

void closeConsole();
}
