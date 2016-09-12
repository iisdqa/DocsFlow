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
		return new Elements().close_Button(driver).isAvailable();
	}
	
	public void wait_ForPageReady()
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		//endregion
		
		new CustomMethods().simpleWait(1);
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
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
		
		// Открыть поп-ап добавления
		new Elements().new PerformControl_Elements().add_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new PerformControl_Elements().task_Text());
		
		// Заполнение полей
		sendKeys(task);
		new Elements().new PerformControl_Elements().number_Input().inputText(number);
		new Elements().new PerformControl_Elements().informTo_Input().selectByVisibleText(informTo);
		new Elements().new PerformControl_Elements().controller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().performer_Select().selectByVisibleText(performer);
		waitUntilClickable(new Elements().new PerformControl_Elements().performerUnit_Input());		
		new Elements().new PerformControl_Elements().periodicity_Select().selectByVisibleText(periodicity);
		new Elements().new PerformControl_Elements().deadline_Date().click();
		new Elements().new PerformControl_Elements().deadline_Date().inputText(deadlineDate);
		new Elements().new PerformControl_Elements().condition_Text().click();
		sendKeys(condition);
		
		// Проверка подтягивания отделения исполнителя
		assertThat(new Elements().new PerformControl_Elements().performerUnit_Input().getAttribute("value"), is(equalTo(performerUnit)));
		
		// Сохранить
		save.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new TermsChange_Elements().add_Button());
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
		if(checkType == "refreshed" || checkType == "view") deadlineDate = new CustomMethods().getChangedDate(7);
		String periodicity = new Elements().new PerformControl_Elements().new Values().periodicity;
		String performDate = " ";
		if(checkType == "edit")performDate = new Elements().new PerformControl_Elements().new Values().performDate;
		String condition = new Elements().new PerformControl_Elements().new Values().condition;
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "1",
										  number, 
										  task, 
										  informTo, 
										  controller, 						//Надо будет изменить, добавив sql скриптом автора
										  performer,						//Надо будет изменить, добавив sql скриптом автора
										  deadlineDate,
										  periodicity,
										  performDate,
										  condition};
		
		// Удалить лишние поля для просмотровой формы
		if(checkType == "view")
		{
			int[] elements_ToRemove = new int[]{ 0, 0};
			ExpectedValues[0] = new CustomMethods().new Grid().arrayElements_Remove(ExpectedValues[0], elements_ToRemove);
		}
		
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void inset_Save()
	{
		//region Variables
		Button save = new Elements().save_Button(driver);
		Custom termsChange_Add = new Elements(). new TermsChange_Elements().add_Button();
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		//endregion
		
		// Сохранение вкладки
		save.click();
		new CommonActions().simpleWait(3);
		waitUntilClickable(termsChange_Add);
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void control_Edit()
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		String performDate = new Elements().new PerformControl_Elements().new Values().performDate;
		Custom termsChange_Add = new Elements(). new TermsChange_Elements().add_Button();
		//endregion
		
		// Открыть поп-ап редактирования
		new Elements().new PerformControl_Elements().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new PerformControl_Elements().task_Text());
		
		// Добавить дату выполнения
		new Elements().new PerformControl_Elements().perform_Date().click();
		new Elements().new PerformControl_Elements().perform_Date().inputText(performDate);
		
		// Сохранить
		new Elements().new PerformControl_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(termsChange_Add);
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
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {number, 
										  task, 
										  informTo, 
										  controller, 						//Надо будет изменить, добавив sql скриптом автора
										  performer,						//Надо будет изменить, добавив sql скриптом автора
										  performerUnit,
										  deadlineDate,
										  periodicity,
										  performDate,
										  condition};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
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
		
		// Проверка недоступности кнопки добавления
		assertThat(addButton.getAttribute("disabled"), is(equalTo("true")));
		
		// Открыть поп-ап добавления
		new Elements().new PerformControl_Elements().new Grid().number_Cell("1").click();
		addButton.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new TermsChange_Elements().reason_Text());
		
		// Заполнение полей
		sendKeys(reason);
		new Elements().new TermsChange_Elements().number_Input().inputText(number);
		new Elements().new TermsChange_Elements().previous_Date().click();
		new Elements().new TermsChange_Elements().previous_Date().inputText(oldDate);
		new Elements().new TermsChange_Elements().next_Date().click();
		new Elements().new TermsChange_Elements().next_Date().inputText(newDate);
		
		// Сохранить
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
		if (checkType != "add") reason = new Elements().new TermsChange_Elements().new Values().reason + "2";
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  number, 
										  oldDate, 
										  newDate, 
										  reason};
		
		// Удалить лишние поля для просмотровой формы
		if(checkType == "view")
		{
			int[] elements_ToRemove = new int[]{ 0, 0};
			ExpectedValues[0] = new CustomMethods().new Grid().arrayElements_Remove(ExpectedValues[0], elements_ToRemove);
		}
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void termsChange_Edit()
	{
		//region Variables
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		Custom addButton = new Elements().new TermsChange_Elements().add_Button();
		//endregion
		
		// Открыть поп-ап редактирования
		new Elements().new TermsChange_Elements().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new TermsChange_Elements().reason_Text());
		
		// Редактировать причину
		sendKeys("2");
		
		// Сохранить
		new Elements().new TermsChange_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(addButton);
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
		
		// Открытие поп-апа
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(info);
		
		// Клик 'Так'
		yes.click();
		new CommonActions().simpleWait(1);
		
		// Проверка сообщения
		waitUntilClickable(info);
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().inability_Message)));
		
		// Закрытие поп-апа
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
		
		// Открыть поп-ап добавления
		new Elements().new PerformControl_Elements().add_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new PerformControl_Elements().task_Text());
		
		// Заполнение полей
		sendKeys(task);
		new Elements().new PerformControl_Elements().number_Input().inputText(number);
		new Elements().new PerformControl_Elements().controller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().performer_Select().selectByVisibleText(performer);
		waitUntilClickable(new Elements().new PerformControl_Elements().performerUnit_Input());		
		new Elements().new PerformControl_Elements().deadline_Date().click();
		new Elements().new PerformControl_Elements().deadline_Date().inputText(deadlineDate);
		
		// Сохранить
		new Elements().new PerformControl_Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(addButton);
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void termsGrid_ShowAll_Check()
	{
		//region Variables
		Button showAll = new Elements().new TermsChange_Elements().showAll_Button();
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		//endregion
		
		// Установить фокус для записи у которой нет связей
		new Elements().new PerformControl_Elements().new Grid().number_Cell("2").click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
	
		// Проверить, что не отображабться записи
		termsGrid_EmptinessCheck();
		
		// Отобрать все записи
		showAll.click();		
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
		
		// Проверить, что в гриде появились записи
		termsGrid_Check("edit");
	}
	
	public void termsGrid_EmptinessCheck()
	{
		//region Variables
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		WebElement grid = new Elements().new TermsChange_Elements().new Grid().grid_Body(driver, grid_Id);
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [0][];
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void control_Delete(String rowToDelete)
	{
		//region Variables
		String grid_Id = new Elements().new PerformControl_Elements().grid_Id;
		Button deleteButton = new Elements().new PerformControl_Elements().new Grid().delete_Button(rowToDelete);
		Custom info = new Elements().new DeletionPopUp_Elements().info_PopUp(driver);
		Button yes = new Elements().new DeletionPopUp_Elements().yes_Button(driver);
		Button add_Button = new Elements().new PerformControl_Elements().new Grid().add_Button(driver, grid_Id);
		//endregion
		
		// Открытие поп-апа
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(info);
		
		// Проверка сообщения
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().message)));
		
		// Закрытие поп-апа
		yes.click();
		new CommonActions().simpleWait(1);
		add_Button.waitUntilAvailable();
		waitForBlockStatus(new Elements().new PerformControl_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void termsChange_Delete()
	{
		//region Variables
		String grid_Id = new Elements().new TermsChange_Elements().grid_Id;
		Button deleteButton = new Elements().new TermsChange_Elements().new Grid().delete_Button(driver, grid_Id);
		Custom info = new Elements().new DeletionPopUp_Elements().info_PopUp(driver);
		Button yes = new Elements().new DeletionPopUp_Elements().yes_Button(driver);
		Button add_Button = new Elements().new TermsChange_Elements().new Grid().add_Button(driver, grid_Id);
		//endregion
		
		// Открытие поп-апа
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(info);
		
		// Проверка сообщения
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().message)));
		
		// Закрытие поп-апа
		yes.click();
		new CommonActions().simpleWait(1);
		add_Button.waitUntilAvailable();
		waitForBlockStatus(new Elements().new TermsChange_Elements().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public IncomingDocs_FilesPage goTo_Files_Page(String go_Type)
	{
		//region Variables	
		WebElement insetLink = new IncomingDocs_RegistrationPage(driver).new Elements().inset_Link(driver, "3");
		Custom info = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().info_PopUp(driver);
		Button yes = new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().yes_Button(driver);
		//endregion
				
	 	// Клик по вкладке
		insetLink.click();
		
		// С проверкой поп-апа при уходе с не сохраненными данными
		if(go_Type == "tricky")
		{
			//Отказ в сохранении данных
			new CommonActions().simpleWait(1);
			waitUntilClickable(info);
			
			// Проверка сообщеия
			assertThat(info.getText(), is(equalTo(new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().new Values().info)));
			
			// Закрытие поп-апа
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new IncomingDocs_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	private class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		private class DeletionPopUp_Elements extends CommonElements.Card_Elements.Pop_Ups
		{		
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String message = "Обраний документ буде видалено, продовжити?";
				private String inability_Message = "Видалення неможливе. Існують пов'язані записи в даному документі;";
			}
		}
		
		// Блок элементов для грида 'Контроль исполнения'
		private class PerformControl_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
			// [id] грида
			private String grid_Id = "539";				
			
			// Кнопка добавления
			private Button add_Button()   				{ return new Button(driver, By.id("btnAdd" + grid_Id)); }			
			
			// № пункту
			private TextInput number_Input()   			{ return new TextInput(driver, By.id("541")); }	
			
			// Задание
			private Custom task_Text()   				{ return new Custom(driver, By.id("customTextEditor_542")); }
			
			// Кого информировать
			private Select informTo_Input()   			{ return new Select(driver.findElement(By.id("543"))); }	
			
			// Контролёр
			private Select controller_Select()      	{ return new Select(driver.findElement(By.id("544"))); }
			
			// Исполнитель
			private Select performer_Select()      		{ return new Select(driver.findElement(By.id("545"))); }
			
			// Регион исполнителя
			private Custom performerUnit_Input()      	{ return new Custom(driver, By.id("545_82")); }
			
			// Срок выполнения
			private TextInput deadline_Date()      		{ return new TextInput(driver, By.id("546")); }
			
			// Периодичность
			private Select periodicity_Select()      	{ return new Select(driver.findElement(By.id("547"))); }			
			
			// Дата выполнения
			private TextInput perform_Date()      		{ return new TextInput(driver, By.id("548")); }
			
			// 'Состояние'
			private Custom condition_Text()   			{ return new Custom(driver, By.id("customTextEditor_549")); }
			
			private class Grid extends CommonElements.Card_Elements.Grid
			{
				// TD 'Номер пункта'
				private Custom number_Cell(String Number)  	{ return new Custom(driver, By.xpath("//td[(@aria-describedby='grid539_GRD_NUM1') and text()='" + Number + "']")); }
				
				// Кнопка удаления
				public Button delete_Button(String row)   	{ return new Button(driver, By.xpath("(//td[@aria-describedby='grid" + grid_Id + "_del'])[" + row + "]")); }
			}
			
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String number = "1";										// 'Номер пункта'
				private String task = "Задача_";									// 'Задание'
				private String informTo = "Суд_1";									// 'Кого информировать'
				private String controller = "Іванов І.І";							// 'Контролирует'
				private String performer = "Сидорчук С.С";							// 'Исполнитель'
				private String performerUnit = "Відділ_2";							// 'Подразделение исполнителя'
				private String deadlineDate = new CustomMethods().getCurrentDate();	// 'Термин выполнения'
				private String periodicity = "Щотижня";								// 'Периодичность'
				private String performDate = new CustomMethods().getChangedDate(-1);// 'Дата выполнения'
				private String condition = "Резолюція_";							// 'Состояние'

			}
		}
		
		// Блок элементов грида 'История исполнения для периодического контроля'
		private class History_Elements extends CommonElements.Card_Elements.Grid{}
		
		// Блок элементов грида 'Перенос сроков'
		private class TermsChange_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
			private String grid_Id = "562";					// [id] грида
			
			// Кнопка добавления
			private Custom add_Button()   					{ return new Custom(driver, By.id("btnAdd" + grid_Id)); }	
			
			// Кнопка 'Показать все записи'
			private Button showAll_Button()   				{ return new Button(driver, By.id("btnViewAll" + grid_Id)); }	
			
			// № пункту
			private TextInput number_Input()   				{ return new TextInput(driver, By.id("563")); }	
			
			// Срок выполнения
			private TextInput previous_Date()      			{ return new TextInput(driver, By.id("564")); }
			
			// Срок выполнения
			private TextInput next_Date()      				{ return new TextInput(driver, By.id("565")); }
			
			// Причина переноса	
			private Custom reason_Text()   					{ return new Custom(driver, By.id("customTextEditor_566")); }		
			
			// Элементы грида
			private class Grid extends CommonElements.Card_Elements.Grid	{}
			
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String number = "1";										// 'Номер пункта'				
				private String oldDate = new CustomMethods().getChangedDate(7);		// 'Сатарая дата'
				private String newDate = new CustomMethods().getChangedDate(8);		// 'Новая дата'
				private String reason = "Причина_";									// 'Причина переноса'

			}
		}
		
	}
}
