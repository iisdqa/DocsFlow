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
		
		// Открыть поп-ап добавления
		new Elements().new PerformControl_Elements().getAdd_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getTask_Text());
		
		// Заполнение полей
		sendKeys(task);
		new Elements().new PerformControl_Elements().getNumber_Input().inputText(number);
		new Elements().new PerformControl_Elements().getInformTo_Input().inputText(informTo);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new PerformControl_Elements().getInformTo_Input());
		new Elements().new PerformControl_Elements().getСontroller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().getPerformer_Select().selectByVisibleText(performer);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getPerformerUnit_Input());		
		new Elements().new PerformControl_Elements().getPeriodicity_Select().selectByVisibleText(periodicity);
		new Elements().new PerformControl_Elements().getDeadline_Date().click();
		new Elements().new PerformControl_Elements().getDeadline_Date().inputText(deadlineDate);
		new Elements().new PerformControl_Elements().getCondition_Text().click();
		sendKeys(condition);
		
		// Проверка подтягивания отделения исполнителя
		assertThat(new Elements().new PerformControl_Elements().getPerformerUnit_Input().getAttribute("value"), is(equalTo(performerUnit)));
		
		// Сохранить
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
										  performerUnit,
										  deadlineDate,
										  periodicity,
										  performDate,
										  condition};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// Проверка этапа обработки
		assertThat(docWorkStatus.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage_Edit)));
	}
	
	public void inset_Save()
	{
		//region Variables
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// Сохранение вкладки
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
		
		// Открыть поп-ап редактирования
		new Elements().new PerformControl_Elements().getEdit_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getTask_Text());
		
		// Добавить дату выполнения
		new Elements().new PerformControl_Elements().getPerform_Date().inputText(performDate);
		
		// Сохранить
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
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// Проверка значений грида
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
		
		// Проверка недоступности кнопки добавления
		assertThat(addButton.getAttribute("disabled"), is(equalTo("true")));
		
		// Открыть поп-ап добавления
		new Elements().new PerformControl_Elements().getNumber_Cell("1").click();
		addButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().getReason_Text());
		
		// Заполнение полей
		sendKeys(reason);
		new Elements().new TermsChange_Elements().getNumber_Input().inputText(number);
		new Elements().new TermsChange_Elements().getPrevious_Date().click();
		new Elements().new TermsChange_Elements().getPrevious_Date().inputText(oldDate);
		new Elements().new TermsChange_Elements().getNext_Date().click();
		new Elements().new TermsChange_Elements().getNext_Date().inputText(newDate);
		
		// Сохранить
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
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  number, 
										  oldDate, 
										  newDate, 
										  reason};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void termsChange_Edit()
	{
		//region Variables
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// Открыть поп-ап редактирования
		new Elements().new TermsChange_Elements().getEdit_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new TermsChange_Elements().getReason_Text());
		
		// Редактировать причину
		sendKeys(" 2");
		
		// Сохранить
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
		
		// Открыть поп-ап добавления
		new Elements().new PerformControl_Elements().getAdd_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getTask_Text());
		
		// Заполнение полей
		sendKeys(task);
		new Elements().new PerformControl_Elements().getNumber_Input().inputText(number);
		new Elements().new PerformControl_Elements().getСontroller_Select().selectByVisibleText(controller);
		new Elements().new PerformControl_Elements().getPerformer_Select().selectByVisibleText(performer);
		waitUntilUnblocked(new Elements().new PerformControl_Elements().getPerformerUnit_Input());		
		new Elements().new PerformControl_Elements().getDeadline_Date().click();
		new Elements().new PerformControl_Elements().getDeadline_Date().inputText(deadlineDate);
		
		// Сохранить
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
		
		// Установить фокус для записи у которой нет связей
		new Elements().new PerformControl_Elements().getNumber_Cell("2").click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().getDownload_Div(), false);
	
		// Проверить, что не отображабться записи
		termsGrid_EmptinessCheck();
		
		// Отобрать все записи
		showAll.click();		
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new TermsChange_Elements().getDownload_Div(), false);
		
		// Проверить, что в гриде появились записи
		termsGrid_Check("edit");
	}
	
	public void termsGrid_EmptinessCheck()
	{
		//region Variables
		WebElement grid = new Elements().new TermsChange_Elements().getGridBody();
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [0][];
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void control_Delete(String rowToDelete)
	{
		//region Variables
		Button deleteButton = new Elements().new DeletionPopUp_Elements().getDelete_Button(rowToDelete);
		Custom info = new Elements().new DeletionPopUp_Elements().getInfo_PopUp();
		Button close = new Elements().new DeletionPopUp_Elements().getYes_Button();
		//endregion
		
		// Открытие поп-апа
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// Проверка сообщения
		assertThat(info.getText(), is(equalTo(new Elements().new DeletionPopUp_Elements().new Values().message)));
		
		// Закрытие поп-апа
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
		
		// Изменение этапа обработки
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage_New);
		
	 	// Клик по вкладке
		insetLink.click();
		
		//Отказ в сохранении данных
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(info);
		
		// Проверка сообщеия
		assertThat(info.getText(), is(equalTo(new IncomingDocs_RegistrationPage(driver).new Elements().new SaveOrNot_Elements().new Values().info)));
		
		// Закрытие поп-апа
		yes.click();
		new CommonActions().simpleWait(1);
		
		return new IncomingDocs_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	private class Elements
	{
		// Кнопка 'Зберегти'
		private Custom getSave_Button()   				{ return new Custom(driver, By.id("saveBtnTop")); }		
		
		// 'Этап обработки документа'
		private Select getDocWorkStage_Select() 		{ return new Select(driver.findElement(By.id("DocPhase"))); } 
		
		// Значения, которые будут использоваться для заполнения элементов
		private class Values
		{
			private String docWorkStage_Edit = "В обробці";   						// 'Этап обработки документа'
			private String docWorkStage_New = "На резолюції";   					// 'Этап обработки документа'
		}
		
		private class DeletionPopUp_Elements
		{
			// Кнопка удаления
			private Button getDelete_Button(String rowToDelete) { return new Button(driver, By.xpath("(//input[contains(@class, 'btn-delete')])[" + rowToDelete + "]")); }	
		
			// Поп-ап
			private Custom getInfo_PopUp()   					{ return new Custom(driver, By.id("add_edit_dialog")); }	
			
			// Кнопка закрытия
			private Button getYes_Button()   					{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Так')]")); }
		
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String message = "Ви впевнені, що бажаєте видалити запис?";
			}
		}
		
		// Блок элементов для грида 'Контроль исполнения'
		private class PerformControl_Elements
		{
			// Кнопка добавления
			private Button getAdd_Button()   				{ return new Button(driver, By.className("btn-add")); }	
			
			// Кнопка редактирования
			private Button getEdit_Button()   				{ return new Button(driver, By.className("btn-edit")); }			
			
			// № пункту
			private TextInput getNumber_Input()   			{ return new TextInput(driver, By.id("Tab1_1Grid_NumberPoint")); }	
			
			// Задание
			private Custom getTask_Text()   				{ return new Custom(driver, By.id("customTextEditor_Tab1_1Text1")); }
			
			// Кого информировать
			private TextInput getInformTo_Input()   		{ return new TextInput(driver, By.id("Tab1_1Grid_WhoInformName")); }	
			
			// Контролёр
			private Select getСontroller_Select()      		{ return new Select(driver.findElement(By.id("Tab1_1Grid_Controll"))); }
			
			// Исполнитель
			private Select getPerformer_Select()      		{ return new Select(driver.findElement(By.id("Tab1_1Grid_Executor"))); }
			
			// Регион исполнителя
			private Custom getPerformerUnit_Input()      	{ return new Custom(driver, By.id("Tab1_1Grid_UnitExecutor")); }
			
			// Срок выполнения
			private TextInput getDeadline_Date()      		{ return new TextInput(driver, By.id("Tab1_1Grid_TermExec")); }
			
			// Периодичность
			private Select getPeriodicity_Select()      	{ return new Select(driver.findElement(By.id("Tab1_1Grid_Periodicity"))); }			
			
			// Дата выполнения
			private TextInput getPerform_Date()      		{ return new TextInput(driver, By.id("Tab1_1Grid_DateExec")); }
			
			// 'Состояние'
			private Custom getCondition_Text()   			{ return new Custom(driver, By.id("customTextEditor_Tab1_1Text2")); }
			
			// Кнопка сохранения
			private Button getSave_Button()   				{ return new Button(driver, By.id("save_btn_grid1_1")); }	
			
			// Грид
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='grid_tab_1_1']/tbody")); }
			
			// TD 'Номер пункта'
			private Custom getNumber_Cell(String Number)  	{ return new Custom(driver, By.xpath("//td[(@aria-describedby='grid_tab_1_1_Number') and text()='" + Number + "']")); }
			
			// Div 'Завантаження'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_grid_tab_1_1")); }
			
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String number = "1";										// 'Номер пункта'
				private String task = "Тестовое задание";							// 'Задание'
				private String informTo = "Суд_1";									// 'Кого информировать'
				private String controller = "Іванов І.І";							// 'Контролирует'
				private String performer = "Сидорчук С.С";							// 'Исполнитель'
				private String performerUnit = "Відділ_2";							// 'Подразделение исполнителя'
				private String deadlineDate = new CustomMethods().getCurrentDate();	// 'Термин выполнения'
				private String periodicity = "Щотижня";								// 'Периодичность'
				private String performDate = new CustomMethods().getChangedDate(-1);// 'Дата выполнения'
				private String condition = "Тестова резолюція";						// 'Состояние'

			}
		}
		
		// Блок элементов грида 'История исполнения для периодического контроля'
		private class History_Elements
		{
			// Грид
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='grid_tab_1_2']/tbody")); }
			
	/*		// Div 'Завантаження'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_grid_tab_1_2")); }*/
		}
		
		// Блок элементов грида 'Перенос сроков'
		private class TermsChange_Elements
		{
			// Кнопка добавления
			private Button getAdd_Button()   				{ return new Button(driver, By.id("add_edit_tab")); }	
			
			// Кнопка 'Показать все записи'
			private Button getShowAll_Button()   			{ return new Button(driver, By.className("btn-all-records")); }	
			
			// Кнопка редактирования
			private Button getEdit_Button()   				{ return new Button(driver, By.xpath("(//input[contains(@class, 'btn-edit')])[2]")); }					
			
			// № пункту
			private TextInput getNumber_Input()   			{ return new TextInput(driver, By.id("Tab1_3Grid_NumberPoint")); }	
			
			// Срок выполнения
			private TextInput getPrevious_Date()      		{ return new TextInput(driver, By.id("Tab1_3Grid_PrevTerm")); }
			
			// Срок выполнения
			private TextInput getNext_Date()      			{ return new TextInput(driver, By.id("Tab1_3Grid_NextTerm")); }
			
			// Причина переноса
			private Custom getReason_Text()   				{ return new Custom(driver, By.id("customTextEditor_Tab1_3Text1")); }		
			
			// Кнопка сохранения
			private Button getSave_Button()   				{ return new Button(driver, By.id("save_btn")); }	
			
			// Грид
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='grid_tab_1_3']/tbody")); }
			
			// Div 'Завантаження'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_grid_tab_1_3")); }
			
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String number = "1";										// 'Номер пункта'				
				private String oldDate = new CustomMethods().getChangedDate(7);		// 'Сатарая дата'
				private String newDate = new CustomMethods().getChangedDate(8);		// 'Новая дата'
				private String reason = "Тестова причина";							// 'Причина переноса'

			}
		}
		
	}
}
