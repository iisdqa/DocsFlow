package com.docsflow.core.web.pages.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.sql.Connection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.docsflow.core.database.DbQueries;
import com.docsflow.core.database.DbStatements;
import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Custom;
import com.docsflow.core.web.elements.TextInput;


public class IncomingDocs_RegistrationPage extends WebPage<IncomingDocs_RegistrationPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/";
	
	public IncomingDocs_RegistrationPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public IncomingDocs_RegistrationPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().getDocIndex_Input().isAvailable();
	}
	
	public void card_Generate(Connection sqlConnection)
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		WebElement numGenerate = new Elements().numGenerate_Button(driver);
		Custom docIndex = new Elements().getDocIndex_Input();
		TextInput regDate = new Elements().getRegDate_Date();
		TextInput caseName = new Elements().getCaseName_Input();
		//endregion
		
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		//assertThat(numGenerate.getAttribute("disabled"), is(equalTo("true")));
		
		// ����������� ���������� ������
		String index = expectedIndex_Define(sqlConnection);
		
		// ���������� ������������ �����
		regDate.click();
		regDate.inputText(new Elements().new Values().regDate);
		caseName.inputText(new Elements().new Values().caseName);
		
		// ����� �������� �� ������������ ��� '�������� �� ������������'
		new CommonActions().autoCompleteValue_Set(driver, caseName);
	
        // ������ �� ������ ��������� ������ ������ ��������
        numGenerate.click();
        new CommonActions().simpleWait(3);
        
        // �������� ����������� ��������
        waitUntilUnblocked(docIndex);
        
        // �������� ���������������� �������
        Integer indexLength = index.length();
        String expectedIndex = "";
        if(indexLength.equals(1)) expectedIndex = "01/0000" + index;
        else if(indexLength.equals(2)) expectedIndex = "01/000" + index;
        else if(indexLength.equals(3)) expectedIndex = "01/00" + index;
        else if(indexLength.equals(4)) expectedIndex = "01/0" + index;
        else if(indexLength > 4) Assert.fail("������! ������ ��������� ������ ����������� ��������.");
        
		assertThat(docIndex.getAttribute("value"), is(equalTo(expectedIndex)));
		
		// ������ ������� � ��������� ����������
		new CustomMethods().new WorkWith_TextFiles().file_Create(TextFiles_Path + "IncomingDoc_Index", expectedIndex);
	}
	
	private String expectedIndex_Define(Connection sqlConnection)
	{
		//region Variables	
		String ErrorMessage = DbQueries.DocsTests.Incoming_Docs.Select_Queries.IndexesCountDefine_ErrorMessage;
		String Statement = DbQueries.DocsTests.Incoming_Docs.Select_Queries.IndexesCount_Define;
		//endregion
		
		String count = "";
		
		try 
		{
			count = new DbStatements().Simple_SelectStatement(sqlConnection, Statement, ErrorMessage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// �������������� � int � �������� +1
		int expectedIndex = Integer.valueOf(count) + 1;
		
		return String.valueOf(expectedIndex);
	}
	
	public IncomingDocs_RegistrationPage cardInfo_Set()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		WebElement numGenerate = new Elements().numGenerate_Button(driver);
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		Select regulation = new Elements().getRegulation_Select();
		Select docType = new Elements().getDocType_Select();
		Select docForm = new Elements().getDocForm_Select();
		TextInput pages = new Elements().getPages_Input();
		TextInput additionsCount = new Elements().getAdditionsCount_Input();
		TextInput corrType = new Elements().getCorrType_Input();
		TextInput correspondent = new Elements().getCorrespondent_Input();
		TextInput corrInfo = new Elements().getCorrInfo_Input();
		TextInput corrDate = new Elements().getCorrDate_Input();
		TextInput �orrNum = new Elements().getCorrNum_Input();
		WebElement shortSummary = new Elements().getShortSummary_Text();
		WebElement notes = new Elements().getNotes_Text();
		//endregion
		
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		new CustomMethods().elementExistenceCheck(numGenerate, false);
		
		// ���������� 'Select' �����
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage);
		regulation.selectByVisibleText(new Elements().new Values().regulation);
		docType.selectByVisibleText(new Elements().new Values().docType);
		docForm.selectByVisibleText(new Elements().new Values().docForm);

		// ���������� �������������� �����
		corrType.inputText(new Elements().new Values().corrType);
		new CommonActions().autoCompleteValue_Set(driver, corrType);
		correspondent.inputText(new Elements().new Values().correspondent);
		new CommonActions().autoCompleteValue_Set(driver, correspondent);
		corrInfo.inputText(new Elements().new Values().corrInfo);
		new CommonActions().autoCompleteValue_Set(driver, corrInfo);
		
		// ���������� ������� ��������� �����
		pages.inputText(new Elements().new Values().pages);
		additionsCount.inputText(new Elements().new Values().additionsCount);
		corrDate.click();
		corrDate.inputText(new Elements().new Values().corrDate);
		�orrNum.inputText(new Elements().new Values().corrNum);
		
		// ���������� ���������(�������������) �����
		shortSummary.click();
		sendKeys(new Elements().new Values().shortSummary);
		notes.click();
		sendKeys(new Elements().new Values().notes);
		
		// ���������� ���������
		resolution_add();
		
		// �������� ����������� ���������
		resolution_grid_check("add");
		
		// ��������� ��������� ����
		save.click();
		new CommonActions().simpleWait(3);
				
		return new IncomingDocs_RegistrationPage(driver).waitUntilAvailable();		
	}
	
	private void resolution_add()
	{
		//region Variables
		String GridId = new Elements(). new Resolution_Elements().GridId;
		
		String projectType = new Elements().new Resolution_Elements().new Values().projectType;
		String author = new Elements().new Resolution_Elements().new Values().author;
		String resolutionDate = new Elements().new Resolution_Elements().new Values().resolutionDate;
		String resolution = new Elements().new Resolution_Elements().new Values().resolution;
		String deadlineDate =new Elements().new Resolution_Elements().new Values().deadlineDate;
		//endregion
	
		// ������� ���-�� ����������
		new Elements().new Resolution_Elements().add_Button(driver, GridId).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new Resolution_Elements().new Pop_Up().getResolution_Text());
		
		// ���������� �����
		sendKeys(resolution);
		sendKeys("\t");
		sendKeys(deadlineDate);
		new Elements().new Resolution_Elements().new Pop_Up().getProjectType_Select().selectByVisibleText(projectType);
		new Elements().new Resolution_Elements().new Pop_Up().getAuthor_Select().selectByVisibleText(author);
		sendKeys("\t");
		sendKeys(resolutionDate);
				
		// ���������
		new Elements().new Resolution_Elements().new Pop_Up().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new Resolution_Elements().download_Div(driver, GridId), false);
	}
	
	public void resolution_grid_check(String checkType)
	{
		//region Variables
		String GridId = new Elements(). new Resolution_Elements().GridId;
		WebElement grid = new Elements().new Resolution_Elements().grid_Body(driver, GridId);
		String resolution = "";
		if(checkType == "add") resolution = new Elements().new Resolution_Elements().new Values().resolution;
		else if(checkType == "edit") resolution = new Elements().new Resolution_Elements().new Values().resolution + " 2";
		String resolutionDate = new Elements().new Resolution_Elements().new Values().resolutionDate;
		String author = new Elements().new Resolution_Elements().new Values().author;
		String projectType = new Elements().new Resolution_Elements().new Values().projectType;
		String deadlineDate = new Elements().new Resolution_Elements().new Values().deadlineDate;
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  projectType,
										  author,
										  resolutionDate, 
										  resolution, 										   
										  deadlineDate};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void card_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		WebElement numGenerate = new Elements().numGenerate_Button(driver);
		TextInput regDate = new Elements().getRegDate_Date();
		Custom docIndex = new Elements().getDocIndex_Input();
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		Select regulation = new Elements().getRegulation_Select();
		Select docType = new Elements().getDocType_Select();
		Select docForm = new Elements().getDocForm_Select();
		TextInput pages = new Elements().getPages_Input();
		TextInput additionsCount = new Elements().getAdditionsCount_Input();
		TextInput corrType = new Elements().getCorrType_Input();
		TextInput correspondent = new Elements().getCorrespondent_Input();
		TextInput corrInfo = new Elements().getCorrInfo_Input();
		TextInput corrDate = new Elements().getCorrDate_Input();
		TextInput �orrNum = new Elements().getCorrNum_Input();
		WebElement shortSummary = new Elements().getShortSummary_Frame();
		WebElement notes = new Elements().getNotes_Frame();
		//endregion
		
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		new CustomMethods().elementExistenceCheck(numGenerate, false);
		
		// �������� ������� � ���� �����������
		assertThat(docIndex.getAttribute("value"), is(equalTo(new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index.txt"))));
		assertThat(regDate.getAttribute("value"), is(equalTo(new Elements().new Values().regDate)));
		
		// �������� 'Select' �����
		assertThat(docWorkStage.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage)));
		assertThat(regulation.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().regulation)));
		assertThat(docType.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docType)));
		assertThat(docForm.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docForm)));

		// �������� �������������� �����
		assertThat(corrType.getAttribute("value"), is(equalTo(new Elements().new Values().corrType)));
		assertThat(correspondent.getAttribute("value"), is(equalTo(new Elements().new Values().correspondent)));
		assertThat(corrInfo.getAttribute("value"), is(equalTo(new Elements().new Values().corrInfo)));
		
		// �������� ������� ��������� �����
		assertThat(pages.getAttribute("value"), is(equalTo(new Elements().new Values().pages)));
		assertThat(additionsCount.getAttribute("value"), is(equalTo(new Elements().new Values().additionsCount)));
		assertThat(corrDate.getAttribute("value"), is(equalTo(new Elements().new Values().corrDate)));
		assertThat(�orrNum.getAttribute("value"), is(equalTo(new Elements().new Values().corrNum)));
		
		// �������� ���������(�������������) �����
		String shortSummaryValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, shortSummary);
		assertThat(shortSummaryValue, is(equalTo(new Elements().new Values().shortSummary)));
		String notesValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, notes);
		assertThat(notesValue, is(equalTo(new Elements().new Values().notes)));
		
		// �������� ����������� ���������
		resolution_grid_check("add");
	}
	
	public void corr_ExistenceCheck()
	{
		//region Variables
		Button search = new Elements().new CorrespondentCheck_Elements().getSearch_Button();
		Custom info = new Elements().new CorrespondentCheck_Elements().getInfo_PopUp();
		Button close = new Elements().new CorrespondentCheck_Elements().getClose_Button();
		//endregion
		
		// ����� 
		search.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// �������� ��������
		assertThat(info.getText(), is(equalTo(new Elements().new CorrespondentCheck_Elements().new Values().noMatches_Info)));
		
		// �������� ���-���
		close.click();
		new CommonActions().simpleWait(1);
	}
	
	public void card_Edit()
	{
		//region Variables	
		String GridId = new Elements(). new Resolution_Elements().GridId;
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		Custom docIndex = new Elements().getDocIndex_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// �������������� ����
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage_Edit);
				
		// ������������� ���������
		resolution_edit();
		
		// �������� �������������� ���������
		resolution_grid_check("edit");
		
		// ��������� ��������� ����
/*		datePicker.click();
		new CommonActions().simpleWait(1);
		docIndex.click();
		new CommonActions().simpleWait(1);*/
		save.click();
		new CommonActions().simpleWait(3);
		
		// �������� �������� ���������
		waitUntilUnblocked(docIndex);
		waitForBlockStatus(new Elements().new Resolution_Elements().download_Div(driver, GridId), false);
	}
	
	private void resolution_edit()
	{			
		// ������� ���-�� ����������
		String GridId = new Elements(). new Resolution_Elements().GridId;
		new Elements().new Resolution_Elements().edit_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new Resolution_Elements().new Pop_Up().getResolution_Text());
		
		// ���������� �����
		sendKeys(" 2");
		sendKeys("\t");
				
		// ���������
		new Elements().new Resolution_Elements().new Pop_Up().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new Resolution_Elements().download_Div(driver, GridId), false);
	}
	
	public void editedCard_Check()
	{
		//region Variables	
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		//endregion
		
		// �������� ����� ���������
		assertThat(docWorkStage.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage_Edit)));
		
		// �������� �������������� ���������
		resolution_grid_check("edit");
	}
	
	public IncomingDocs_PerformControlPage goTo_PerformControl_Page()
	{
		//region Variables	
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		WebElement insetLink = new Elements().new SaveOrNot_Elements().getInset_Link("57");
		Custom info = new Elements().new SaveOrNot_Elements().getInfo_PopUp();
		Button no = new Elements().new SaveOrNot_Elements().getNo_Button();
		//endregion
		
		// ��������� ����� ���������
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage);
		
	 	// ���� �� �������
		insetLink.click();
		
		//����� � ���������� ������
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// �������� ��������
		assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().new Values().info)));
		
		// �������� ���-���
		no.click();
		new CommonActions().simpleWait(1);
		
		return new IncomingDocs_PerformControlPage(driver).waitUntilAvailable();
	}
	
	
	/*__________________________________________________ �������� _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{	
	
		
		// '���� ��������� ���������'
		private Select getDocWorkStage_Select() 		{ return new Select(driver.findElement(By.id("509"))); } 
			
		// '������ ���������'
		private Custom getDocIndex_Input()  			{ return new Custom(driver, By.id("510")); }
		
		// '���� �����������'
		private TextInput getRegDate_Date()   			{ return new TextInput(driver, By.id("511")); }	
		
		// '��������'
		private Select getRegulation_Select()   		{ return new Select(driver.findElement(By.id("512"))); }
	
		// '��� ���������'
		private Select getDocType_Select()      		{ return new Select(driver.findElement(By.id("513"))); }
		
		// '����� ����������� ���������'
		private Select getDocForm_Select()      		{ return new Select(driver.findElement(By.id("514"))); }
				
		// '������'
		private TextInput getPages_Input()     	    	{ return new TextInput(driver, By.id("515")); }
		
		// '����������'
		private TextInput getAdditionsCount_Input() 	{ return new TextInput(driver, By.id("516")); }
		
		// '��� �������������'
		private TextInput getCorrType_Input()   		{ return new TextInput(driver, By.id("517_auto")); }
		
		// '�������������'
		private TextInput getCorrespondent_Input()  	{ return new TextInput(driver, By.id("518_auto")); }
		
		// '��� ��������������'
		private TextInput getCorrInfo_Input()   		{ return new TextInput(driver, By.id("519_auto")); }
		
		// '���� ��������������'
		private TextInput getCorrDate_Input()   		{ return new TextInput(driver, By.id("520")); }
		
		// '� ��������������'
		private TextInput getCorrNum_Input()   			{ return new TextInput(driver, By.id("521")); }
		
		// '�������� ���� �� ������������'
		private TextInput getCaseName_Input()   		{ return new TextInput(driver, By.id("522_auto")); }		
		
		// '������� ����������'
		private WebElement getShortSummary_Text()   	{ WebElement element = driver.findElement(By.id("customTextEditor_523")); return element; }
	
		// '������� ����������' �����
		private WebElement getShortSummary_Frame()   	{ return driver.findElement(By.id("customTextEditor_523_DesignIFrame")); }
			
		// '�������'
		private WebElement getNotes_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_524")); return element; }
		
		// '�������' �����
		private WebElement getNotes_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_524_DesignIFrame")); }	
		
		// ��������, ������� ����� �������������� ��� ���������� ���������
		private class Values
		{
			private String docWorkStage = "�����";   								// '���� ��������� ���������'
			private String docWorkStage_Edit = "� �������";   						// '���� ��������� ���������'
			private String regDate = "01.01.2020"; 									// '���� �����������'
			private String regulation = "�����������"; 							// '��������'
			private String docType = "���"; 										// '��� ���������'
			private String docForm = "���������� �����"; 							// '����� ����������� ���������'
			private String pages = "1"; 											// '������'
			private String additionsCount = "2"; 									// '����������'
			private String corrType = "����"; 										// '��� �������������'
			private String correspondent = "���_2"; 								// '�������������'
			private String corrInfo = "������� �.�"; 								// '��� ��������������'
			private String corrDate = new CustomMethods().getCurrentDate(); 		// '���� ��������������'
			private String corrNum = "222111"; 										// '� ��������������'
			private String caseName = "������������ ����_1"; 						// '�������� ���� �� ������������'
			private String shortSummary = "�������� ����"; 						// '������� ����������'
			private String notes = "������� �������"; 								// '�������'
		}
		
		private class Resolution_Elements extends CommonElements.Card_Elements.Grid
		{
			private String GridId = "525";
		
			private class Pop_Up extends CommonElements.Card_Elements.Pop_Ups
			{
				private Select getProjectType_Select()      	{ return new Select(driver.findElement(By.id("526"))); }
				
				// �����
				private Select getAuthor_Select()      			{ return new Select(driver.findElement(By.id("527"))); }
				
	/*			// ���� ���������
				private TextInput getResolutionDate_Input() 	{ return new TextInput(driver, By.id("Tab0_KFullNameStr")); }*/
				
				// '���������'
				private Custom getResolution_Text()   			{ return new Custom(driver, By.id("customTextEditor_529")); }
				
	/*			// ������ ����������
				private TextInput getDeadlineDate_Input() 		{ return new TextInput(driver, By.id("Tab_FrontPage_Grid_DateTermExec")); }*/
			}
			
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String projectType = "������";					// '������'
				private String author = "������ �.�";					// '�����'
				private String resolutionDate = "02.01.2020";			// '���� ���������'
				private String resolution = "������� ���������";		// '���������'
				private String deadlineDate = "03.01.2020";				// '������ ����������'
			}
		}
		
		private class CorrespondentCheck_Elements
		{
			// ������ ����������
			private Button getSearch_Button()   				{ return new Button(driver, By.id("chckBtn")); }	
		
			// ���-��
			private Custom getInfo_PopUp()   					{ return new Custom(driver, By.id("add_edit_dialog")); }	
			
			// ������ ��������
			private Button getClose_Button()   					{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '�������')]")); }
		
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String noMatches_Info = "�� ���������� ����� �� ������� � ��� ����� �� ���� ����������� ������ ���������";
			}
		}
		
		public class SaveOrNot_Elements
		{
			// ������� ���� ��������
			public WebElement getInset_Link(String InsetId){ return driver.findElement(By.xpath("//div[@onclick=\"RedirectTo('"+ InsetId +"\')\"]")); }  
														  // ���, 'InsetId': 56-'����������� ����', 57-'�������� ���������', 58-���'���� ��������� � ����� 
		
			// ���-��
			public Custom getInfo_PopUp()   					{ return new Custom(driver, By.id("add_edit_dialog")); }	
			
			// ������ ��������
			public Button getNo_Button()   						{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'ͳ')]")); }
		
			// ������ ���
			public Button getYes_Button()   					{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '���')]")); }
			
			// ��������, ������� ����� �������������� ��� ���������� ���������
			public class Values
			{
				public String info = "��� ���� ������. ������ �������� ���?";
			}
		}
	}	
}
