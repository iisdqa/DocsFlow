package com.docsflow.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Custom;

public abstract class CommonElements 
{
	public static class UnitsTree_Elements
	{
		public Custom units_Accordion(WebDriver driver)
		{
			return new Custom(driver, By.id("departments-accordion"));
		}
		
		public Custom tree_Div(WebDriver driver)
		{
			return new Custom(driver, By.id("tree"));
		}
	}

	public static class FiltrationControl_Elements
	{
		
	}
	
	public static class BaseGrid_Elements
	{
		// "Завантаження"
		public Button add_Button(WebDriver driver)
		{
			return new Button(driver, By.id("btnAdd"));
		}
		
		// "Завантаження"
		public Custom gridDownload_Div(WebDriver driver)
		{
			return new Custom(driver, By.id("load_grid"));
		}
	}
	
	// Элементы карточек
	public static class Card_Elements
	{
		// Общие элементы
		public static class General_Elements
		{
			// Кнопка 'Зберегти'
			public Button save_Button(WebDriver driver)   						{ return new Button(driver, By.id("saveBtnTop")); }	
			
			// Кнопка 'Генерація наступного номера'
			public WebElement numGenerate_Button(WebDriver driver) 				{ return driver.findElement(By.id("btnGenerateNum")); }
			
			// Єлемент меню карточки
			public WebElement inset_Link(WebDriver driver, String inset_Number)	{ return driver.findElement(By.xpath("(//div[@class='item'])[" + inset_Number + "]")); }  
														  						// Где, 'InsetId': 56-'Реэстраційні данні', 57-'Контроль виконання', 58-Пов'язані документи і файли 
		}
		
		// Элементы универсального грида внутри карточки
		public static class Grid
		{			
			// Кнопка добавления
			public Button add_Button(WebDriver driver, String GridId)   		{ return new Button(driver, By.id("btnAdd" + GridId)); }
		
			// Грид
			public WebElement grid_Body(WebDriver driver, String grid_Id)		{ return driver.findElement(By.xpath("//*[@id='grid" + grid_Id + "']/tbody")); }
			
			// Div 'Завантаження'
			public Custom download_Div(WebDriver driver, String GridId)  		{ return new Custom(driver, By.id("load_grid" + GridId)); }
				
			// Кнопка редактирования
			public Button edit_Button(WebDriver driver, String GridId)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + GridId + "_edit']")); }
				
			// Кнопка удаления
			public Button delete_Button(WebDriver driver, String GridId)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + GridId + "_del']")); }
		}
		
		// Поп-апы
		public static class Pop_Ups
		{
			// Информационный поп-ап
			public Custom info_PopUp(WebDriver driver)   				{ return new Custom(driver, By.cssSelector(".ui-dialog-content.ui-widget-content")); }	
			
			// Кнопка 'Так'
			public Button yes_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Так')]")); }
			
			// Кнопка 'Ні'
			public Button no_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Ні')]")); }
			
			// Кнопка 'Закрити'
			public Button close_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Закрити')]")); }
			
			// Кнопка сохранения
			public Button save_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[@class='ui-button-text' and text()='Зберегти']")); }
		}
		
		// Блок файлов
		public static class Card_Files_Elements
		{
			
		}
		
		// Блок связанных документов
		public static class Card_LinkedDocs_Elements
		{
			
		}
	}
}
