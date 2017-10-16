package com.privateProject;

public class StartProject
{
	/**
	 * Создает объект класса Manager. Вызывает управляющий метод для запуска пользовательского меню.
	 *
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args)
	{
		Manager manager = new Manager();
		manager.managing();
	}
}
