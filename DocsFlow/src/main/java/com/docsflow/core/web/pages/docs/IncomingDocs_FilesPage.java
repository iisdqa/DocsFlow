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

public class IncomingDocs_FilesPage extends WebPage<IncomingDocs_FilesPage> 
{
private static final String PAGE_URL = BASE_URL + "/IODocs/InputDocs/List";
	
	public IncomingDocs_FilesPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public IncomingDocs_FilesPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Files().getAdd_Button().isAvailable();
	}
	
	public void file_Add()
	{
		//region Variables
		String date = new Elements().new Files().new Values().date;
		String user = new Elements().new Files().new Values().user;
		String fileName = new Elements().new Files().new Values().fileName;
		String fileWay = new Elements().new Files().new Values().fileWay;
		String comment = new Elements().new Files().new Values().comment;
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// Открыть поп-ап добавления
		new Elements().new Files().getAdd_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new Files().getComment_Text());
		
		// Заполнение полей
		sendKeys(comment);
		new Elements().new Files().get_Date().click();
		new Elements().new Files().get_Date().inputText(date);
		
		// Добавление файл + проверка подстановки в текстовое поле
		new Elements().new Files().getFileUpload_Button().inputText(fileWay);
		new CommonActions().simpleWait(2);
		assertThat(new Elements().new Files().getFile_Input().getAttribute("value"), is(equalTo(fileName)));
		//assertThat(new Elements().new Files().getFile_Input().getAttribute("readonly"), is(equalTo("true")));
		
		// Проверка подтягивания пользователя
		assertThat(new Elements().new Files().getUser_Input().getAttribute("value"), is(equalTo(user)));
		//assertThat(new Elements().new Files().getUser_Input().getAttribute("readonly"), is(equalTo("true")));
		
		// Сохранить
		new Elements().new Files().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new Files().getDownload_Div(), false);
	}
	
	public void filesGrid_Check(String checkType)
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
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "",
										  fileName, 
										  date, 
										  user, 
										  comment};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
		
		// Проверка этапа обработки
		if (checkType == "add") assertThat(docWorkStatus.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage)));
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
		waitForBlockStatus(new Elements().new Files().getDownload_Div(), false);
	}
	
	public void file_Edit()
	{
		//region Variables
		Custom save = new Elements().getSave_Button();
		//endregion
		
		// Открыть поп-ап редактирования
		new Elements().new Files().getEdit_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(new Elements().new Files().getComment_Text());
		
		// Изменить комментарий
		sendKeys(" 2");
		
		// Сохранить
		new Elements().new Files().getSave_Button().click();
		new CommonActions().simpleWait(1);
		waitUntilUnblocked(save);
		waitForBlockStatus(new Elements().new Files().getDownload_Div(), false);
	}	
	
	public void fileUnload_check()
	{
		//region Variables
		Button download = new Elements().new Files().getFileDownload_Button();
		String fileName = new Elements().new Files().new Values().fileName;
		//endregion
		
		new CustomMethods().new WorkWith_TextFiles().fileDownload_Check(download, fileName);
	}
	
	public IncomingDocs_Page card_Close()
	{
		// Закрытие редактирования карточки
		new Elements().get_CloseButton().click();
		new CommonActions().simpleWait(1);
		
		return new IncomingDocs_Page(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	private class Elements
	{
		// Кнопка 'Зберегти'
		private Custom getSave_Button()   				{ return new Custom(driver, By.id("saveBtnTop")); }		
		
		// Кнопка 'Зберегти'
		private Custom get_CloseButton()   				{ return new Custom(driver, By.id("cancelBtnTop")); }		
		
		
		// 'Этап обработки документа'
		private Select getDocWorkStage_Select() 		{ return new Select(driver.findElement(By.id("DocPhase"))); } 
		
		// Значения, которые будут использоваться для заполнения элементов
		private class Values
		{
			private String docWorkStage = "На резолюції";   					// 'Этап обработки документа'
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
		
		private class Files
		{
			// Кнопка добавления
			private Button getAdd_Button()   				{ return new Button(driver, By.id("add_edit_tab2_1")); }	
			
			// Кнопка редактирования
			private Button getEdit_Button()   				{ return new Button(driver, By.className("btn-edit")); }
			
			// Кнопка редактирования
			private Button getFileDownload_Button()   		{ return new Button(driver, By.className("btn-filedownload-s")); }
						
			// Дата
			private TextInput get_Date()      				{ return new TextInput(driver, By.id("Tab2_1Grid_DocumentsDate")); }
			
			// Пользователь
			private TextInput getUser_Input()      			{ return new TextInput(driver, By.id("Tab2_1Grid_DocumentsUser")); }
			
			// ФАйл
			private TextInput getFile_Input()      			{ return new TextInput(driver, By.id("Tab2_1Grid_DocumentFileName")); }
			
			// Кнопка загрузки файла
			private TextInput getFileUpload_Button()		{return new TextInput(driver, By.id("file_source"));}
			
			// 'Комментарий'
			private Custom getComment_Text()   				{ return new Custom(driver, By.id("customTextEditor_Tab2_1Text1")); }
			
			// Кнопка сохранения
			private Button getSave_Button()   				{ return new Button(driver, By.id("save_btn")); }	
			
			// Грид
			private WebElement getGridBody()				{ return driver.findElement(By.xpath("//*[@id='list_tab_2_1']/tbody")); }			
			
			// Div 'Завантаження'
			private Custom getDownload_Div()  				{ return new Custom(driver, By.id("load_list_tab_2_1")); }
			
			private class Values
			{	private String date = "05.01.2020";	  									// Дата
				private String user = "admin_auto";						     			// Пользователь
				private String fileWay = "C:\\Selenium_TestData\\Other\\ForDocAdd.txt";	// Ссылка на файл(реальная)
				private String fileName = "ForDocAdd.txt";								// Название файла
				private String comment = "Тестовий коментар";			     			// Ссылка на файл
			}
		}
	
	}
}
