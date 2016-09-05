package com.docsflow.core.web.pages.docs;

import org.openqa.selenium.WebDriver;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.WebPage;

public class IncomingDocs_Page extends WebPage<IncomingDocs_Page>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/List/55";
	
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

	public void waitFor_PageReady()
	{
		waitUntilUnblocked(new Elements().new Units_Tree().tree_Div(driver));
	}
	
	public void tree_Open()
	{
		// ���� � �������������
		int[] tree_Path = {0, 5, 1};
		
		// ������� � �������������
		new CommonActions().tree_Handler(driver, tree_Path);
		
		// �������� ��������� �����
		waitForBlockStatus(new Elements().new Grid().gridDownload_Div(driver), false);
		new CommonActions().simpleWait(1);
	}
	
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
	
/*	public void grid_Check()
	{
		//region Variables
		WebElement grid = new Elements().new Files().getGridBody();
		String date = new Elements().new Files().new Values().date;
		String user = new Elements().new Files().new Values().user;
		String fileName = new Elements().new Files().new Values().fileName;
		String comment = new Elements().new Files().new Values().comment;
		if (checkType == "edit") comment = new Elements().new Files().new Values().comment + " 2";
		Select docWorkStatus = new Elements().getDocWorkStage_Select();
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "",
										  fileName, 
										  date, 
										  user, 
										  comment};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
		
		// �������� ����� ���������
		if (checkType == "add") assertThat(docWorkStatus.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage)));
	}*/
	
	/*__________________________________________________ �������� _______________________________________________________*/
	
	private class Elements
	{
		// ������������ �������� ������
		private class Units_Tree extends CommonElements.UnitsTree_Elements {}
		
		// ������������ �������� �����
		private class Grid extends CommonElements.BaseGrid_Elements {}
	}
}
