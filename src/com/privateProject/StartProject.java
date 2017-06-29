package com.privateProject;

public class StartProject
{

/**
 * Создает объект класса Manager. Вызывает управляющий метод для открытия XML-файла,
 * получения списка масштабов в файле, запуска пользовательского меню.
 *
 * @param args аргументы командной строки
 */
public static void main(String[] args)
{
    Manager manager = new Manager();
    manager.managing();
}

}
