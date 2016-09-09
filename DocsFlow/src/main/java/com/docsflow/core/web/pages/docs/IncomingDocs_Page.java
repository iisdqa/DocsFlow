package com.docsflow.core.web.pages.docs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;

public class IncomingDocs_Page extends WebPage<IncomingDocs_Page>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/List/63";
	
	public IncomingDocs_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public IncomingDocs_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Units_Tree().units_Accordion(driver).isAvailable();
	}

	
	/*________________________________________________________ Actions ___________________________________________________*/
	
	// �������� ���������� ���������
	public void waitFor_PageReady()
	{
		waitUntilClickable(new Elements().new Units_Tree().tree_Div(driver));
	}
	
	// ������� ������ �������������
	public void tree_Open()
	{
		// ���� � �������������
		int[] tree_Path = {0, 5, 1};
		
		// ������� � �������������
		new CommonActions().tree_Handler(driver, tree_Path);
		
		// �������� ��������� �����
		gridDownload_Wait();
	}
	
	// �������� ��������� �����
	public void gridDownload_Wait()
	{
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
		new CommonActions().simpleWait(1);
	}
	
	// ������� � ���������� �������� ���������
	public IncomingDocs_RegistrationPage card_add()
	{
		// ������ �� ������ ����������
		new Elements().new Grid().add_Button(driver).click();
		new CommonActions().simpleWait(2);
		
/*		// ������� ����. ������� + ����������� ������� �� �����
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(tabs.get(1));*/
		
		return new IncomingDocs_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// ����� ��������
	public void card_Search()
	{
		//region Variables	
		String fieldName = new Elements().new Filtration_Accordion().new Values().fieldName;
		String matchType = new Elements().new Filtration_Accordion().new Values().matchType;
		String value = new Elements().new Filtration_Accordion().new Values().value;
		//endregion
		
		// ����������
		new CommonActions().grid_Filtration(driver, fieldName, matchType, value);
		
		// �������� ��������� �����
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
	}
	
	// �������� ��������� ��������
	public void card_Check(String checkType)
	{
		//region Variables
		WebElement grid = new Elements().new Grid().grid_Body(driver);
		String index = new Elements().new Filtration_Accordion().new Values().value;
		String number  = index.substring(index.lastIndexOf('0') + 1);
		String regDate = new IncomingDocs_RegistrationPage(driver).new Elements().new Values().regDate;
		String correspondent = new IncomingDocs_RegistrationPage(driver).new Elements().new Values().correspondent;
		String corrNum = new IncomingDocs_RegistrationPage(driver).new Elements().new Values().corrNum;
		String corrDate = new IncomingDocs_RegistrationPage(driver).new Elements().new Values().corrDate;
		String shortSummary = new IncomingDocs_RegistrationPage(driver).new Elements().new Values().shortSummary;
		if (checkType == "edit") shortSummary = shortSummary + "2";
		String regulation = new IncomingDocs_RegistrationPage(driver).new Elements().new Values().regulation;
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "",
										  number, 
										  index, 
										  regDate, 
										  correspondent,
										  corrNum,
										  corrDate,
										  shortSummary,
										  regulation};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// ������� � �������������� ��������
	public IncomingDocs_RegistrationPage card_Edit()
	{
		// ������ �� ������ ��������������
		new Elements().new Grid().edit_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new IncomingDocs_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// ������� � ��������� ��������
	public IncomingDocs_RegistrationPage card_View()
	{
		// ������ �� ������ ���������
		new Elements().new Grid().view_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new IncomingDocs_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	/*____________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements ________________________________________________________*/
	
	private class Elements
	{
		// ������������ �������� ������
		private class Units_Tree extends CommonElements.UnitsTree_Elements {}
		
		// ������������ �������� �����
		private class Grid extends CommonElements.BaseGrid_Elements {}
		
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
	/*____________________________________________________________________________________________________________________*/
}
