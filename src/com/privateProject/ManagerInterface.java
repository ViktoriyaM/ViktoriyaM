package com.privateProject;

import java.util.List;
import java.util.Set;

public interface ManagerInterface
{

public static final String QUITE = "q";
public static final String CONTINUE = "continue";

/**
 * Вызывает методы для открытия XML-файла {@link Configuration#initialize()},
 * получения списка масштабов из XML-файла {@link Configuration#getScaleValues()}, 
 * запуска пользовательского меню для обработки первого пользовательского выбора {@link #showMenu(java.util.List)} 
 * и последующего {@link #showMenu(java.util.List, java.util.Set) }, 
 * формирования и обработки объектов электронной карты {@link Algorithm#initialize(com.privateProject.Configuration, java.lang.String)} .
 * 
 */
void managing();

/**
 * Выполняет вывод в консоль значений масштабов и соответствующих им порядковых номеров. 
 * Осуществялет проверку пользовательского ввода на значение порядкового номера, 
 * диапазон, а также на строку выхода из программы.
 * @param scaleValues список всех масштабов из XML-файла
 * @return параметр, введенный пользователем с клавиатуры
 */
String showMenu(List<String> scaleValues);

/**
 * Выполняет вывод в консоль еще не обработанных значений масштабов и соответствующих им порядковых номеров. 
 * Осуществялет проверку пользовательского ввода на значение порядкового номера, 
 * диапазон, а также на строку выхода из программы. Запрещает повторный ввод порядкового номера для масштаба,
 * ранее уже обработанного в программе.
 * @param scaleValues список всех масштабов из XML-файла
 * @param inputScales список допустимых параметров, введенных пользователем с клавиатуры
 * @return параметр, введенный пользователем с клавиатуры
 */
String showMenu(List<String> scaleValues, Set<Integer> inputScales);

/**
 * Закрывает поток ввода из консоли.
 */
void closeMenu();
}
