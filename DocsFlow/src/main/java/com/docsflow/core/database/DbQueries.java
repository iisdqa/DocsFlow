package com.docsflow.core.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import com.docsflow.core.web.CustomMethods;

public class DbQueries 
{	
	private static String queriesPath = "C:\\Selenium_TestData\\Projects\\DocsFlow\\SQL\\SQL_Queries\\";
	
	public static class DocsTests
	{
		public static class Incoming_Docs
		{
			public static class Select_Queries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static final String IndexesCountDefine_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке выборки количества документов из БД за определенный период.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String IndexesCount_Define = "select dc.DNM_NUM" + "\r\n" +
			    								           "from [dbo].[DocNum] dc"  + "\r\n" +
			    								           "join [dbo].Tree_Staff tf on dc.STT_IDP = tf.STT_IDP"  + "\r\n" +
			    								           "where dc.DNM_YER = 2020 and tf.STT_LNAM = 'Автоматизация'";
			}
			
			public static class Deletion_Queries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static final String DocDeletion_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления входящего документа.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String DocDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "incoming_doc_deletion.sql");
			}	
		}
	}
	
	public static class DictionaryTests
	{
	
	}
	
	public static class AdministrationTests
	{
		public static class UsersAndAuditQueries
		{
			public static class DeletionQueries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления пользователя.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String UserDeletionStatement = readFile(queriesPath + "user_deletion.sql");
			}
		}
	}	
	
	// Прочитать текст из файла
	private static String readFile(String path)
	{
		File file = new File(path);
		Reader input = null;
		StringWriter output = new StringWriter();
		try 
		{
			input = new FileReader(file);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
			Assert.fail("Не удалось найти файл при попытке определения SQL запроса.");
		}
		try 
		{
		  IOUtils.copy(input, output);
		  input.close();
		}	  
		catch (Exception e) 
	    {
			e.printStackTrace();
	    }
		String fileContents = output.toString();
		return fileContents;
	}
}
