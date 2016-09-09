package com.docsflow.core.web.pages.registers;

import org.openqa.selenium.WebDriver;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;

public class Individuals_Page extends WebPage<Individuals_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Dictionaries/DictionariesUDB/List";
	
	public Individuals_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Individuals_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Grid().add_Button(driver).isAvailable();
	}

	/*__________________________________________________ Actions ________________________________________________________*/
	
	// �������� ��������� �����
	public void gridDownload_Wait()
	{
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
		new CommonActions().simpleWait(1);
	}
	
	// ������� � ���������� �������� ���������
	public Individuals_RegPage cardAdd_Call()
	{
		// ������ �� ������ ����������
		new Elements().new Grid().add_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Individuals_RegPage(driver).waitUntilAvailable();	
	}
	
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	
	private class Elements
	{		
		// ����
		private class Grid extends CommonElements.BaseGrid_Elements{}
		
		// ������������ �������� ���������� ����������
		private class Filtration_Accordion extends CommonElements.FiltrationControl_Elements 
		{
			// ������������ ��������
			private class Values
			{
				private String fieldName = "������ ���������";
				private String matchType = "�������";
				private String value = new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index");
			}
		}	
	}
	
	/*___________________________________________________________________________________________________________________*/
}
