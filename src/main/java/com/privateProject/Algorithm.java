package com.privateProject;

import java.io.*;
import java.util.regex.*;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public final class Algorithm extends AlgorithmAbstract
{
	private static final Logger LOGGER = LogManager.getLogger(Algorithm.class.getName());
	
	Algorithm()
	{
		objects = new LinkedHashSet<>();
	}
	
	/**
	 * Получает из конфигуратора список объектов, которые должны быть удалены.
	 *
	 * @param configuration объект Configuration
	 * @return true если сформирован список объектов
	 * false в противном случае
	 */
	@Override
	boolean updateObjects(Configuration configuration)
	{
		objects = configuration.getObjectsForDelete();
		if (objects.isEmpty())
		{
			LOGGER.error("Error: the function is called before configuration of all parameters. Objects is Empty ");
			return false;
		}
		return true;
	}
	
	/**
	 * Выполняет чтение из входного буфера и запись в выходной буфер, полученных из {@link  FilesManager#next() }.
	 *
	 * @param bufferReadWrite буфер для чтения и записи
	 * @return true если файл карт обработан успешно
	 * false в противном случае
	 * @throws IOException
	 */
	@Override
	boolean reader(Supplier<FilesManager.GenerateNext> bufferReadWrite) throws IOException
	{
		
		if (bufferReadWrite == null)
		{
			LOGGER.error("Error: function argument is null");
			return false;
		}
		BufferedReader reader = bufferReadWrite.get().getReader();
		RandomAccessFile writer = bufferReadWrite.get().getWriter();
		try
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				processingMap(writer, line);
			}
		}
		catch (IOException ex)
		{
			LOGGER.error("Error: write to file failed " + ex);
			throw ex;
		}
		return true;
	}
	
	/**
	 * Выполняет обработку файлов электронной карты, удаляя все объекты, соответствующие массиву из XML-документа.
	 * Перезаписывает число объектов в новом файле.
	 *
	 * @param writer поток для записи данных в файл с произвольным доступом
	 * @param line   строка, считанная из буфферизированного входного потока
	 * @throws IOException возникает исключение при ошибке записи в файл
	 */
	@Override
	protected void processingMap(RandomAccessFile writer, String line) throws IOException
	{
		Matcher matcherObject = Pattern.compile("\\d+").matcher(line);
		if (line.matches(IDENTIFICATION_OBJECT))
		{
			if (matcherObject.find())
			{
				String currentObject = matcherObject.group();
				if (objects.contains(currentObject))
				{
					isWrite = false;
					currentNumberDeletedObjects++;
				}
				else
				{
					isWrite = true;
				}
			}
		}
		if (isWrite && !line.matches(IDENTIFICATION_END_FILE) && !line.matches(IDENTIFICATION_NUMBERS_OBJECTS))
		{
			writer.write(line.getBytes());
			writer.writeBytes(System.getProperty("line.separator"));
		}
		if (line.matches(IDENTIFICATION_NUMBERS_OBJECTS))
		{
			if (matcherObject.find())
			{
				Matcher matcherDat = Pattern.compile("\\D+").matcher(line);
				if (matcherDat.find())
				{
					String lineWithDat = matcherDat.group();
					writer.write(lineWithDat.getBytes());
				}
				currentNumberObjects = Integer.parseInt(matcherObject.group());
				pointerLineWithNumberObjects = writer.getFilePointer();
				writer.writeBytes(System.getProperty("line.separator"));
			}
		}
		if (line.matches(IDENTIFICATION_END_FILE))
		{
			writer.write(line.getBytes());
			int currentObjects = currentNumberObjects - currentNumberDeletedObjects;
			writer.seek(pointerLineWithNumberObjects);
			writer.write(Integer.toString(currentObjects).getBytes());
			writer.writeBytes(System.getProperty("line.separator"));
			currentNumberDeletedObjects = 0;
		}
	}
}
