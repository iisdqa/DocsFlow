package com.docsflow.core.web.pages.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Custom;
import com.docsflow.core.web.elements.TextInput;

public class IncomingDocs_PerformControlPage extends WebPage<IncomingDocs_PerformControlPage>
{
	private static final String PAGE_URL = BASE_URL + "/IODocs/InputDocs/List";
	
	public IncomingDocs_PerformControlPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public IncomingDocs_PerformControlPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new PerformControl_Elements().getAdd_Button().isAvailable();
	}
	
	public void control_Add()
	{
		//region Variables
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String informTo = new Elements().new PerformControl_Elements().new Values().informTo;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String performerUnit = new Elements().new PerformControl_Elements().new Values().performerUnit;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		String periodicity = new Elements().new PerformControl_Elements().new Values().periodicity;
		String condition = new Elements().new PerformControl_Elements().new Values().condition;
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// ������� ���-�� ����������
		new Elements().new PerformControl_Elements().getAdd_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getTask_Text());
		
		// ���������� �����
		sendKeys(task);
		new Elements().new PerformControl_Elements().getNumber_Input().inputText(number);
		new Elements().new PerformControl_Elements().getInformTo_Input().inputText(informTo);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new PerformControl_Elements().getInformTo_Input());
		new Elements().new PerformControl_Elements().get�ontroller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().getPerformer_Select().selectByVisibleText(performer);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getPerformerUnit_Input());		
		new Elements().new PerformControl_Elements().getPeriodicity_Select().selectByVisibleText(periodicity);
		new Elements().new PerformControl_Elements().getDeadline_Date().click();
		new Elements().new PerformControl_Elements().getDeadline_Date().inputText(deadlineDate);
		new Elements().new PerformControl_Elements().getCondition_Text().click();
		sendKeys(condition);
		
		// �������� ������������ ��������� �����������
		assertThat(new Elements().new PerformControl_Elements().getPerformerUnit_Input().getAttribute("value"), is(equalTo(performerUnit)));
		
		// ���������
		new Elements().new PerformControl_Elements().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new PerformControl_Elements().getDownload_Div(), false);
	}
	
	public void controlGrid_Check(String checkType)
	{
		//region Variables
		WebElement grid = new Elements().new PerformControl_Elements().getGridBody();
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String informTo = new Elements().new PerformControl_Elements().new Values().informTo;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String performerUnit = new Elements().new PerformControl_Elements().new Values().performerUnit;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		if(checkType == "refreshed") deadlineDate = new CustomMethods().getChangedDate(7);
		String periodicity = new Elements().new PerformControl_Elements().new Values().periodicity;
		String performDate = " ";
		if(checkType == "edit")performDate =	new Elements().new PerformControl_Elements().new Values().performDate;
		String condition = new Elements().new PerformControl_Elements().new Values().condition;
		Select docWorkStatus = new Elements().getDocWorkStage_Select();
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "1",
										  number, 
										  task, 
										  informTo, 
										  controller, 						//���� ����� ��������, ������� sql �������� ������
										  performer,						//���� ����� ��������, ������� sql �������� ������
										  performerUnit,
										  deadlineDate,
										  periodicity,
										  performDate,
										  condition};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// �������� ����� ���������
		assertThat(docWorkStatus.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage_Edit)));
	}
	
	public void inset_Save()
	{
		//region Variables
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// ���������� �������
		save.click();
		new CommonActions().simpleWait(3);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new PerformControl_Elements().getDownload_Div(), false);
	}
	
	public void control_Edit()
	{
		//region Variables
		String performDate = new Elements().new PerformControl_Elements().new Values().performDate;
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// ������� ���-�� ��������������
		new Elements().new PerformControl_Elements().getEdit_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getTask_Text());
		
		// �������� ���� ����������
		new Elements().new PerformControl_Elements().getPerform_Date().inputText(performDate);
		
		// ���������
		new Elements().new PerformControl_Elements().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new PerformControl_Elements().getDownload_Div(), false);
	}		
	
	public void historyGrid_Check()
	{
		//region Variables
		WebElement grid = new Elements().new History_Elements().getGridBody();
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String informTo = new Elements().new PerformControl_Elements().new Values().informTo;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String performerUnit = new Elements().new PerformControl_Elements().new Values().performerUnit;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		String periodicity = new Elements().new PerformControl_Elements().new Values().periodicity;
		String performDate = new Elements().new PerformControl_Elements().new Values().performDate;
		String condition = new Elements().new PerformControl_Elements().new Values().condition;
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {number, 
										  task, 
										  informTo, 
										  controller, 						//���� ����� ��������, ������� sql �������� ������
										  performer,						//���� ����� ��������, ������� sql �������� ������
										  performerUnit,
										  deadlineDate,
										  periodicity,
										  performDate,
										  condition};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void termsChange_Add()
	{
		//region Variables
		Button addButton = new Elements().new TermsChange_Elements().getAdd_Button();
		String number = new Elements().new TermsChange_Elements().new Values().number;
		String oldDate = new Elements().new TermsChange_Elements().new Values().oldDate;
		String newDate = new Elements().new TermsChange_Elements().new Values().newDate;
		String reason = new Elements().new TermsChange_Elements().new Values().reason;
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// �������� ������������� ������ ����������
		assertThat(addButton.getAttribute("disabled"), is(equalTo("true")));
		
		// ������� ���-�� ����������
		new Elements().new PerformControl_Elements().getNumber_Cell("1").click();
		addButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().getReason_Text());
		
		// ���������� �����
		sendKeys(reason);
		new Elements().new TermsChange_Elements().getNumber_Input().inputText(number);
		new Elements().new TermsChange_Elements().getPrevious_Date().click();
		new Elements().new TermsChange_Elements().getPrevious_Date().inputText(oldDate);
		new Elements().new TermsChange_Elements().getNext_Date().click();
		new Elements().new TermsChange_Elements().getNext_Date().inputText(newDate);
		
		// ���������
		new Elements().new TermsChange_Elements().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new TermsChange_Elements().getDownload_Div(), false);
	}
	
	public void termsGrid_Check(String checkType)
	{
		//region Variables
		WebElement grid = new Elements().new TermsChange_Elements().getGridBody();
		String number = new Elements().new TermsChange_Elements().new Values().number;
		String oldDate = new Elements().new TermsChange_Elements().new Values().oldDate;
		String newDate = new Elements().new TermsChange_Elements().new Values().newDate;
		String reason = new Elements().new TermsChange_Elements().new Values().reason;
		if (checkType == "edit") reason = new Elements().new TermsChange_Elements().new Values().reason + " 2";
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  number, 
										  oldDate, 
										  newDate, 
										  reason};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void termsChange_Edit()
	{
		//region Variables
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// ������� ���-�� ��������������
		new Elements().new TermsChange_Elements().getEdit_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().getReason_Text());
		
		// ������������� �������
		sendKeys(" 2");
		
		// ���������
		new Elements().new TermsChange_Elements().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new TermsChange_Elements().getDownload_Div(), false);
	}
	
	public void secondControl_Add()
	{
		//region Variables
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// ������� ���-�� ����������
		new Elements().new PerformControl_Elements().getAdd_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getTask_Text());
		
		// ���������� �����
		sendKeys(task);
		new Elements().new PerformControl_Elements().getNumber_Input().inputText(number);
		new Elements().new PerformControl_Elements().get�ontroller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().getPerformer_Select().selectByVisibleText(performer);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getPerformerUnit_Input());		
		new Elements().new PerformControl_Elements().getDeadline_Date().click();
		new Elements().new PerformControl_Elements().getDeadline_Date().inputText(deadlineDate);
		
		// ���������
		new Elements().new PerformControl_Elements().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new PerformControl_Elements().getDownload_Div(), false);
	}
	
	public void termsGrid_ShowAll_Check()
	{
		//region Variables
		Button showAll = new Elements().new TermsChange_Elements().getShowAll_Button();
		//endregion
		
		// ���������� ����� ��� ������ � ������� ��� ������
		new Elements().new PerformControl_Elements().getNumber_Cell("2").click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().getDownload_Div(), false);
	
		// ���������, ��� �� ������������� ������
		termsGrid_EmptinessCheck();
		
		// �������� ��� ������
		showAll.click();		
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().getDownload_Div(), false);
		
		// ���������, ��� � ����� ��������� ������
		termsGrid_Check("edit");
	}
	
	public void termsGrid_EmptinessCheck()
	{
		//region Variables
		WebElement grid = new Elements().new TermsChange_Elements().getGridBody();
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [0][];
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void control_Delete(String rowToDelete)
	{
		//region Variables
		Button deleteButton = new Elements().new DeletionPopUp_Elements().getDelete_Button(rowToDelete);
		Custom info = new Elements().new DeletionPopUp_Elements().getInfo_PopUp();
		Button close = new Elements().new DeletionPopUp_Elements().getYes_Button();
		//endregion
		
		// �������� ���-���
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// �������� ���������
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().message)));
		
		// �������� ���-���
		close.click();
		new CommonActions().simpleWait(1);
	}
	
	public IncomingDocs_FilesPage goTo_Files_Page()
	{
		//region Variables	
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		WebElement insetLink = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().getInset_Link("58");
		Custom info = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().getInfo_PopUp();
		Button yes = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().getYes_Button();
		//endregion
		
		// ��������� ����� ���������
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage_New);
		
	 	// ���� �� �������
		insetLink.click();
		
		//����� � ���������� ������
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// �������� ��������
		assertThat(info.getText(), is(equalTo(new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().new Values().info)));
		
		// �������� ���-���
		yes.click();
		new CommonActions().simpleWait(1);
		
		return new IncomingDocs_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ �������� _______________________________________________________*/	
	
	private class Elements
	{
		// ������ '��������'
		private Custom getSave_Button()   				{ return new Custom(driver, By.id("saveBtnTop")); }		
		
		// '���� ��������� ���������'
		private Select getDocWorkStage_Select() 		{ return new Select(driver.findElement(By.id("DocPhase"))); } 
		
		// ��������, ������� ����� �������������� ��� ���������� ���������
		private class Values
		{
			private String docWorkStage_Edit = "� �������";   						// '���� ��������� ���������'
			private String docWorkStage_New = "�� ���������";   					// '���� ��������� ���������'
		}
		
		private class DeletionPopUp_Elements
		{
			// ������ ��������
			private Button getDelete_Button(String rowToDelete) { return new Button(driver, By.xpath("(//input[contains(@class, 'btn-delete')])[" + rowToDelete + "]")); }	
		
			// ���-��
			private Custom getInfo_PopUp()   					{ return new Custom(driver, By.id("add_edit_dialog")); }	
			
			// ������ ��������
			private Button getYes_Button()   					{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), '���')]")); }
		
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String message = "�� �������, �� ������ �������� �����?";
			}
		}
		
		// ���� ��������� ��� ����� '�������� ����������'
		private class PerformControl_Elements
		{
			// ������ ����������
			private Button getAdd_Button()   				{ return new Button(driver, By.className("btn-add")); }	
			
			// ������ ��������������
			private Button getEdit_Button()   				{ return new Button(driver, By.className("btn-edit")); }			
			
			// � ������
			private TextInput getNumber_Input()   			{ return new TextInput(driver, By.id("Tab1_1Grid_NumberPoint")); }	
			
			// �������
			private Custom getTask_Text()   				{ return new Custom(driver, By.id("customTextEditor_Tab1_1Text1")); }
			
			// ���� �������������
			private TextInput getInformTo_Input()   		{ return new TextInput(driver, By.id("Tab1_1Grid_WhoInformName")); }	
			
			// ��������
			private Select get�ontroller_Select()      		{ return new Select(driver.findElement(By.id("Tab1_1Grid_Controll"))); }
			
			// �����������
			private Select getPerformer_Select()      		{ return new Select(driver.findElement(By.id("Tab1_1Grid_Executor"))); }
			
			// ������ �����������
			private Custom getPerformerUnit_Input()      	{ return new Custom(driver, By.id("Tab1_1Grid_UnitExecutor")); }
			
			// ���� ����������
			private TextInput getDeadline_Date()      		{ return new TextInput(driver, By.id("Tab1_1Grid_TermExec")); }
			
			// �������������
			private Select getPeriodicity_Select()      	{ return new Select(driver.findElement(By.id("Tab1_1Grid_Periodicity"))); }			
			
			// ���� ����������
			private TextInput getPerform_Date()      		{ return new TextInput(driver, By.id("Tab1_1Grid_DateExec")); }
			
			// '���������'
			private Custom getCondition_Text()   			{ return new Custom(driver, By.id("customTextEditor_Tab1_1Text2")); }
			
			// ������ ����������
			private Button getSave_Button()   				{ return new Button(driver, By.id("save_btn_grid1_1")); }	
			
			// ����
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='grid_tab_1_1']/tbody")); }
			
			// TD '����� ������'
			private Custom getNumber_Cell(String Number)  	{ return new Custom(driver, By.xpath("//td[(@aria-describedby='grid_tab_1_1_Number') and text()='" + Number + "']")); }
			
			// Div '������������'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_grid_tab_1_1")); }
			
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String number = "1";										// '����� ������'
				private String task = "�������� �������";							// '�������'
				private String informTo = "���_1";									// '���� �������������'
				private String controller = "������ �.�";							// '������������'
				private String performer = "�������� �.�";							// '�����������'
				private String performerUnit = "³���_2";							// '������������� �����������'
				private String deadlineDate = new CustomMethods().getCurrentDate();	// '������ ����������'
				private String periodicity = "�������";								// '�������������'
				private String performDate = new CustomMethods().getChangedDate(-1);// '���� ����������'
				private String condition = "������� ���������";						// '���������'

			}
		}
		
		// ���� ��������� ����� '������� ���������� ��� �������������� ��������'
		private class History_Elements
		{
			// ����
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='grid_tab_1_2']/tbody")); }
			
	/*		// Div '������������'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_grid_tab_1_2")); }*/
		}
		
		// ���� ��������� ����� '������� ������'
		private class TermsChange_Elements
		{
			// ������ ����������
			private Button getAdd_Button()   				{ return new Button(driver, By.id("add_edit_tab")); }	
			
			// ������ '�������� ��� ������'
			private Button getShowAll_Button()   			{ return new Button(driver, By.className("btn-all-records")); }	
			
			// ������ ��������������
			private Button getEdit_Button()   				{ return new Button(driver, By.xpath("(//input[contains(@class, 'btn-edit')])[2]")); }					
			
			// � ������
			private TextInput getNumber_Input()   			{ return new TextInput(driver, By.id("Tab1_3Grid_NumberPoint")); }	
			
			// ���� ����������
			private TextInput getPrevious_Date()      		{ return new TextInput(driver, By.id("Tab1_3Grid_PrevTerm")); }
			
			// ���� ����������
			private TextInput getNext_Date()      			{ return new TextInput(driver, By.id("Tab1_3Grid_NextTerm")); }
			
			// ������� ��������
			private Custom getReason_Text()   				{ return new Custom(driver, By.id("customTextEditor_Tab1_3Text1")); }		
			
			// ������ ����������
			private Button getSave_Button()   				{ return new Button(driver, By.id("save_btn")); }	
			
			// ����
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='grid_tab_1_3']/tbody")); }
			
			// Div '������������'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_grid_tab_1_3")); }
			
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String number = "1";										// '����� ������'				
				private String oldDate = new CustomMethods().getChangedDate(7);		// '������� ����'
				private String newDate = new CustomMethods().getChangedDate(8);		// '����� ����'
				private String reason = "������� �������";							// '������� ��������'

			}
		}
		
	}
}
