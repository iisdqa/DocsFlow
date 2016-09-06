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
		// "������������"
		public Button add_Button(WebDriver driver)
		{
			return new Button(driver, By.id("btnAdd"));
		}
		
		// "������������"
		public Custom gridDownload_Div(WebDriver driver)
		{
			return new Custom(driver, By.id("load_grid"));
		}
	}
	
	// �������� ��������
	public static class Card_Elements
	{
		// ����� ��������
		public static class General_Elements
		{
			// ������ '��������'
			public Button save_Button(WebDriver driver)   						{ return new Button(driver, By.id("saveBtnTop")); }	
			
			// ������ '��������� ���������� ������'
			public WebElement numGenerate_Button(WebDriver driver) 				{ return driver.findElement(By.id("btnGenerateNum")); }
			
			// ������� ���� ��������
			public WebElement inset_Link(WebDriver driver, String inset_Number)	{ return driver.findElement(By.xpath("(//div[@class='item'])[" + inset_Number + "]")); }  
														  						// ���, 'InsetId': 56-'����������� ����', 57-'�������� ���������', 58-���'���� ��������� � ����� 
		}
		
		// �������� �������������� ����� ������ ��������
		public static class Grid
		{			
			// ������ ����������
			public Button add_Button(WebDriver driver, String GridId)   		{ return new Button(driver, By.id("btnAdd" + GridId)); }
		
			// ����
			public WebElement grid_Body(WebDriver driver, String grid_Id)		{ return driver.findElement(By.xpath("//*[@id='grid" + grid_Id + "']/tbody")); }
			
			// Div '������������'
			public Custom download_Div(WebDriver driver, String GridId)  		{ return new Custom(driver, By.id("load_grid" + GridId)); }
				
			// ������ ��������������
			public Button edit_Button(WebDriver driver, String GridId)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + GridId + "_edit']")); }
				
			// ������ ��������
			public Button delete_Button(WebDriver driver, String GridId)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + GridId + "_del']")); }
		}
		
		// ���-���
		public static class Pop_Ups
		{
			// �������������� ���-��
			public Custom info_PopUp(WebDriver driver)   				{ return new Custom(driver, By.cssSelector(".ui-dialog-content.ui-widget-content")); }	
			
			// ������ '���'
			public Button yes_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '���')]")); }
			
			// ������ 'ͳ'
			public Button no_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'ͳ')]")); }
			
			// ������ '�������'
			public Button close_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '�������')]")); }
			
			// ������ ����������
			public Button save_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[@class='ui-button-text' and text()='��������']")); }
		}
		
		// ���� ������
		public static class Card_Files_Elements
		{
			
		}
		
		// ���� ��������� ����������
		public static class Card_LinkedDocs_Elements
		{
			
		}
	}
}
