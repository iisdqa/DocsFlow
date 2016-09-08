package com.docsflow.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Cell;
import com.docsflow.core.web.elements.Custom;

public class Handbooks_Page extends WebPage<Handbooks_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Dictionaries/DictionariesUDB/List";
	
	public Handbooks_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Handbooks_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new DictValues().new Grid().add_Button().isAvailable();
	}

	/*__________________________________________________ Actions ________________________________________________________*/
	// �������� �������� �����
	public void wait_ForPageReady()
	{		
		new CustomMethods().simpleWait(1);
		waitForBlockStatus(new Elements().new DictValues().new Grid().download_Div(), false);
	}
	
	// ���������� ������ ��� ���������� ����
	public void dictionaryCache_Update()
	{
		// ����� �������
		new Elements().new DictNames().new Grid().dictName_Cell("������").click();
		wait_ForPageReady();
		
		// ���������� ��������
		new Elements().new DictValues().new Grid().add_Button().click();
		
		
		
		// �������� ����� ��
		
	}
	
	
	
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	private class Elements
	{
		private class DictNames
		{
			// �������� ����� ��������
			private class Grid
			{
				// "������������"
				public Cell dictName_Cell(String dictName)	{return new Cell(driver, By.xpath("//td[@aria-describedby='list_dict_DIC_PARENT_NAME' and text()='" + dictName + "']"));}
			}
		}
		
		private class DictValues
		{
			private class PopUps
			{
				private class AddEdit
				{
					
				}
			}
			
			// �������� ����� �������� ��������
			private class Grid
			{
				// ������ ����������
				private Button add_Button()					{return new Button(driver, By.id("add_value_dict"));}
				
				// "������������"
				public WebElement grid_Body()				{ return driver.findElement(By.xpath("//table[@id='list_value_dict']/tbody")); }
				
				// "������������"
				public Custom download_Div()				{ return new Custom(driver, By.id("load_list_value_dict")); }

				// ������ ��������������
				public Button edit_Button()   				{ return new Button(driver, By.xpath("//td[@aria-describedby='list_value_dict_edit']")); }	
			
				// ������������ ��������
				private class Values
				{
				
				}
			}
		}
	}
	/*___________________________________________________________________________________________________________________*/
}
