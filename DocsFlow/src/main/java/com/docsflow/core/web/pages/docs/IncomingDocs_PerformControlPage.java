package com.docsflow.core.web.pages.docs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
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
		return new Elements().new PerformControl_Elements().add_Button().isAvailable();
	}
	
	public void control_Add()
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String informTo = new Elements().new PerformControl_Elements().new Values().informTo;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String performerUnit = new Elements().new PerformControl_Elements().new Values().performerUnit;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		String periodicity = new Elements().new PerformControl_Elements().new Values().periodicity;
		String condition = new Elements().new PerformControl_Elements().new Values().condition;
		Button save = new Elements().new PerformControl_Elements().save_Button(driver);
		//endregion
		
		// ������� ���-�� ����������
		new Elements().new PerformControl_Elements().add_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().task_Text());
		
		// ���������� �����
		sendKeys(task);
		new Elements().new PerformControl_Elements().number_Input().inputText(number);
		new Elements().new PerformControl_Elements().informTo_Input().selectByVisibleText(informTo);
		new Elements().new PerformControl_Elements().controller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().performer_Select().selectByVisibleText(performer);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().performerUnit_Input());		
		new Elements().new PerformControl_Elements().periodicity_Select().selectByVisibleText(periodicity);
		new Elements().new PerformControl_Elements().deadline_Date().click();
		new Elements().new PerformControl_Elements().deadline_Date().inputText(deadlineDate);
		new Elements().new PerformControl_Elements().condition_Text().click();
		sendKeys(condition);
		
		// �������� ������������ ��������� �����������
		assertThat(new Elements().new PerformControl_Elements().performerUnit_Input().getAttribute("value"), is(equalTo(performerUnit)));
		
		// ���������
		save.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().add_Button());
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void controlGrid_Check(String checkType)
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		WebElement grid = new Elements().new PerformControl_Elements().new Grid().grid_Body(driver, grid_Id);
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String informTo = new Elements().new PerformControl_Elements().new Values().informTo;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		if(checkType == "refreshed") deadlineDate = new CustomMethods().getChangedDate(7);
		String periodicity = new Elements().new PerformControl_Elements().new Values().periodicity;
		String performDate = " ";
		if(checkType == "edit")performDate =	new Elements().new PerformControl_Elements().new Values().performDate;
		String condition = new Elements().new PerformControl_Elements().new Values().condition;
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
										  deadlineDate,
										  periodicity,
										  performDate,
										  condition};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void inset_Save()
	{
		//region Variables
		Button save = new Elements().save_Button(driver);
		Custom termsChange_Add = new Elements(). new TermsChange_Elements().add_Button();
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		//endregion
		
		// ���������� �������
		save.click();
		new CommonActions().simpleWait(3);
		waitUntilUnblocked(termsChange_Add);
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void control_Edit()
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		String performDate = new Elements().new PerformControl_Elements().new Values().performDate;
		Custom termsChange_Add = new Elements(). new TermsChange_Elements().add_Button();
		//endregion
		
		// ������� ���-�� ��������������
		new Elements().new PerformControl_Elements().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().task_Text());
		
		// �������� ���� ����������
		new Elements().new PerformControl_Elements().perform_Date().click();
		new Elements().new PerformControl_Elements().perform_Date().inputText(performDate);
		
		// ���������
		new Elements().new PerformControl_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(termsChange_Add);
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}		
	
	public void historyGrid_Check()
	{
		//region Variables
		WebElement grid = new Elements().new History_Elements().grid_Body(driver, "550");
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
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		Custom addButton = new Elements().new TermsChange_Elements().add_Button();
		String number = new Elements().new TermsChange_Elements().new Values().number;
		String oldDate = new Elements().new TermsChange_Elements().new Values().oldDate;
		String newDate = new Elements().new TermsChange_Elements().new Values().newDate;
		String reason = new Elements().new TermsChange_Elements().new Values().reason;
		//endregion
		
		// �������� ������������� ������ ����������
		assertThat(addButton.getAttribute("disabled"), is(equalTo("true")));
		
		// ������� ���-�� ����������
		new Elements().new PerformControl_Elements().new Grid().number_Cell("1").click();
		addButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().reason_Text());
		
		// ���������� �����
		sendKeys(reason);
		new Elements().new TermsChange_Elements().number_Input().inputText(number);
		new Elements().new TermsChange_Elements().previous_Date().click();
		new Elements().new TermsChange_Elements().previous_Date().inputText(oldDate);
		new Elements().new TermsChange_Elements().next_Date().click();
		new Elements().new TermsChange_Elements().next_Date().inputText(newDate);
		
		// ���������
		new Elements().new TermsChange_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		addButton.waitUntilAvailable();
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void termsGrid_Check(String checkType)
	{
		//region Variables
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		WebElement grid = new Elements().new TermsChange_Elements().new Grid().grid_Body(driver, grid_Id);
		String number = new Elements().new TermsChange_Elements().new Values().number;
		String oldDate = new Elements().new TermsChange_Elements().new Values().oldDate;
		String newDate = new Elements().new TermsChange_Elements().new Values().newDate;
		String reason = new Elements().new TermsChange_Elements().new Values().reason;
		if (checkType == "edit") reason = new Elements().new TermsChange_Elements().new Values().reason + "2";
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
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		Custom addButton = new Elements().new TermsChange_Elements().add_Button();
		//endregion
		
		// ������� ���-�� ��������������
		new Elements().new TermsChange_Elements().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().reason_Text());
		
		// ������������� �������
		sendKeys("2");
		
		// ���������
		new Elements().new TermsChange_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(addButton);
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void control_DeleteInability_Check()
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		Button deleteButton = new Elements().new PerformControl_Elements().new Grid().delete_Button(driver, grid_Id);
		Custom info = new Elements().new DeletionPopUp_Elements().info_PopUp(driver);
		Button yes = new Elements().new DeletionPopUp_Elements().yes_Button(driver);
		Button close = new Elements().new DeletionPopUp_Elements().close_Button(driver);
		//endregion
		
		// �������� ���-���
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// ���� '���'
		yes.click();
		new CommonActions().simpleWait(1);
		
		// �������� ���������
		waitUntilUnblocked(info);
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().inability_Message)));
		
		// �������� ���-���
		close.click();
		new CommonActions().simpleWait(1);
	}
	
	public void secondControl_Add()
	{
		//region Variables
		String number = new Elements().new PerformControl_Elements().new Values().number;
		String task = new Elements().new PerformControl_Elements().new Values().task;
		String controller = new Elements().new PerformControl_Elements().new Values().controller;
		String performer = new Elements().new PerformControl_Elements().new Values().performer;
		String deadlineDate = new Elements().new PerformControl_Elements().new Values().deadlineDate;
		Custom addButton = new Elements().new TermsChange_Elements().add_Button();
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		//endregion
		
		// ������� ���-�� ����������
		new Elements().new PerformControl_Elements().add_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().task_Text());
		
		// ���������� �����
		sendKeys(task);
		new Elements().new PerformControl_Elements().number_Input().inputText(number);
		new Elements().new PerformControl_Elements().controller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().performer_Select().selectByVisibleText(performer);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().performerUnit_Input());		
		new Elements().new PerformControl_Elements().deadline_Date().click();
		new Elements().new PerformControl_Elements().deadline_Date().inputText(deadlineDate);
		
		// ���������
		new Elements().new PerformControl_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(addButton);
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void termsGrid_ShowAll_Check()
	{
		//region Variables
		Button showAll = new Elements().new TermsChange_Elements().showAll_Button();
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		//endregion
		
		// ���������� ����� ��� ������ � ������� ��� ������
		new Elements().new PerformControl_Elements().new Grid().number_Cell("2").click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
	
		// ���������, ��� �� ������������� ������
		termsGrid_EmptinessCheck();
		
		// �������� ��� ������
		showAll.click();		
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
		
		// ���������, ��� � ����� ��������� ������
		termsGrid_Check("edit");
	}
	
	public void termsGrid_EmptinessCheck()
	{
		//region Variables
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		WebElement grid = new Elements().new TermsChange_Elements().new Grid().grid_Body(driver, grid_Id);
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
		Button deleteButton = new Elements().new PerformControl_Elements().new Grid().delete_Button(rowToDelete);
		Custom info = new Elements().new DeletionPopUp_Elements().info_PopUp(driver);
		Button yes = new Elements().new DeletionPopUp_Elements().yes_Button(driver);
		//endregion
		
		// �������� ���-���
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// �������� ���������
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().message)));
		
		// �������� ���-���
		yes.click();
		new CommonActions().simpleWait(1);
	}
	
	public IncomingDocs_FilesPage goTo_Files_Page()
	{
		//region Variables	
		WebElement insetLink = new IncomingDocs_RegistrationPage(driver).new Elements().inset_Link(driver, "58");
		Custom info = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().info_PopUp(driver);
		Button yes = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().yes_Button(driver);
		//endregion
				
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
	
	private class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		private class DeletionPopUp_Elements extends CommonElements.Card_Elements.Pop_Ups
		{		
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String message = "������� �������� ���� ��������, ����������?";
				private String inability_Message = "��������� ���������. ������� ���'���� ������ � ������ ��������;";
			}
		}
		
		// ���� ��������� ��� ����� '�������� ����������'
		private class PerformControl_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
			private String grid_Id = "539";				// [id] �����
			
			// ������ ����������
			private Button add_Button()   				{ return new Button(driver, By.id("btnAdd" + grid_Id)); }			
			
			// � ������
			private TextInput number_Input()   			{ return new TextInput(driver, By.id("541")); }	
			
			// �������
			private Custom task_Text()   				{ return new Custom(driver, By.id("customTextEditor_542")); }
			
			// ���� �������������
			private Select informTo_Input()   			{ return new Select(driver.findElement(By.id("543"))); }	
			
			// ��������
			private Select controller_Select()      	{ return new Select(driver.findElement(By.id("544"))); }
			
			// �����������
			private Select performer_Select()      		{ return new Select(driver.findElement(By.id("545"))); }
			
			// ������ �����������
			private Custom performerUnit_Input()      	{ return new Custom(driver, By.id("545_82")); }
			
			// ���� ����������
			private TextInput deadline_Date()      		{ return new TextInput(driver, By.id("546")); }
			
			// �������������
			private Select periodicity_Select()      	{ return new Select(driver.findElement(By.id("547"))); }			
			
			// ���� ����������
			private TextInput perform_Date()      		{ return new TextInput(driver, By.id("548")); }
			
			// '���������'
			private Custom condition_Text()   			{ return new Custom(driver, By.id("customTextEditor_549")); }
			
			private class Grid extends CommonElements.Card_Elements.Grid
			{
				// TD '����� ������'
				private Custom number_Cell(String Number)  	{ return new Custom(driver, By.xpath("//td[(@aria-describedby='grid539_GRD_NUM1') and text()='" + Number + "']")); }
				
				// ������ ��������
				public Button delete_Button(String row)   	{ return new Button(driver, By.xpath("(//td[@aria-describedby='grid" + grid_Id + "_del'])[" + row + "]")); }
			}
			
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String number = "1";										// '����� ������'
				private String task = "������_";									// '�������'
				private String informTo = "���_1";									// '���� �������������'
				private String controller = "������ �.�";							// '������������'
				private String performer = "�������� �.�";							// '�����������'
				private String performerUnit = "³���_2";							// '������������� �����������'
				private String deadlineDate = new CustomMethods().getCurrentDate();	// '������ ����������'
				private String periodicity = "�������";								// '�������������'
				private String performDate = new CustomMethods().getChangedDate(-1);// '���� ����������'
				private String condition = "���������_";							// '���������'

			}
		}
		
		// ���� ��������� ����� '������� ���������� ��� �������������� ��������'
		private class History_Elements extends CommonElements.Card_Elements.Grid{}
		
		// ���� ��������� ����� '������� ������'
		private class TermsChange_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
			private String grid_Id = "562";					// [id] �����
			
			// ������ ����������
			private Custom add_Button()   					{ return new Custom(driver, By.id("btnAdd" + grid_Id)); }	
			
			// ������ '�������� ��� ������'
			private Button showAll_Button()   				{ return new Button(driver, By.id("btnViewAll" + grid_Id)); }	
			
			// � ������
			private TextInput number_Input()   				{ return new TextInput(driver, By.id("563")); }	
			
			// ���� ����������
			private TextInput previous_Date()      			{ return new TextInput(driver, By.id("564")); }
			
			// ���� ����������
			private TextInput next_Date()      				{ return new TextInput(driver, By.id("565")); }
			
			// ������� ��������	
			private Custom reason_Text()   					{ return new Custom(driver, By.id("customTextEditor_566")); }		
			
			// �������� �����
			private class Grid extends CommonElements.Card_Elements.Grid	{}
			
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String number = "1";										// '����� ������'				
				private String oldDate = new CustomMethods().getChangedDate(7);		// '������� ����'
				private String newDate = new CustomMethods().getChangedDate(8);		// '����� ����'
				private String reason = "�������_";									// '������� ��������'

			}
		}
		
	}
}
