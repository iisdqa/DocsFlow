package com.docsflow.tests.docs;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.docsflow.core.BaseTest;
import com.docsflow.core.database.DbQueries;
import com.docsflow.core.database.DbStatements;
import com.docsflow.core.web.pages.docs.IncomingDocs_Page;
import com.docsflow.core.web.pages.docs.IncomingDocs_FilesPage;
import com.docsflow.core.web.pages.docs.IncomingDocs_PerformControlPage;
import com.docsflow.core.web.pages.docs.IncomingDocs_RegistrationPage;
import com.docsflow.core.web.pages.other.LogInPage;
import com.docsflow.core.web.pages.other.MainPage;

public class IncomingDocsAdd_Test extends BaseTest 
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String ErrorMessage = DbQueries.DocsTests.Incoming_Docs.Deletion_Queries.DocDeletion_ErrorMessage;
	    
	    // Определение текста запроса
	    String DeletionStatement = DbQueries.DocsTests.Incoming_Docs.Deletion_Queries.DocDeletion_Statement;
	    
	    // Выполнение запроса
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "IncomingDocsAdd_Test" })
	public void IncomingDocsAdd_TestMethod() throws Exception
	{	
			// Переход на главную
			LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			// Переход на страничку пользователей
			IncomingDocs_Page docsPage = mainPage.new goTo().new DocsBlock().IncomingDocs_Page(browser);
			docsPage.waitFor_PageReady();
			
			// Добавление карточки
			docsPage.tree_Open();
			IncomingDocs_RegistrationPage addPage = docsPage.card_add();
			addPage.card_Generate(sqlConnection);

			IncomingDocs_RegistrationPage editPage = addPage.cardInfo_Set();
			editPage.card_Check();

			// Редактирование карточки				
			editPage.corr_ExistenceCheck();
			editPage.card_Edit();
			editPage.editedCard_Check();
			
			
			// Добавление/редактирование контроля выполнения
			// Проверка автогенерации записи в гриде истории 		
			IncomingDocs_PerformControlPage controlPage = editPage.goTo_PerformControl_Page("tricky");		
			controlPage.control_Add();
			controlPage.controlGrid_Check("add");
			controlPage.inset_Save();			
			controlPage.controlGrid_Check("add");
			controlPage.control_Edit();
			controlPage.controlGrid_Check("edit");
			controlPage.inset_Save();
			controlPage.controlGrid_Check("refreshed");
			controlPage.historyGrid_Check();
			
			// Проверка грида 'Перенос сроков' 	
			
			controlPage.termsChange_Add();
			controlPage.termsGrid_Check("add");
			controlPage.inset_Save();
			controlPage.controlGrid_Check("refreshed");
			controlPage.historyGrid_Check();
			controlPage.termsGrid_Check("add");	
			controlPage.termsChange_Edit();
			controlPage.termsGrid_Check("edit");
			controlPage.control_DeleteInability_Check();
			controlPage.secondControl_Add();
			controlPage.inset_Save();
			controlPage.termsGrid_Check("edit");
			controlPage.termsGrid_ShowAll_Check();
			
			// Удаление записей из гридов 'Перенос сроков' и 'Контроль версий'
						
			controlPage.control_Delete("1");
			controlPage.controlGrid_Check("refreshed");
			controlPage.inset_Save();
			controlPage.controlGrid_Check("refreshed");
			controlPage.termsChange_Delete();
			
			// Работа со вкладкой 'Связанные документы и файлы'
						
			IncomingDocs_FilesPage filesPage = controlPage.goTo_Files_Page("tricky");		
			filesPage.file_Add();
			filesPage.filesGrid_Check("add");
			filesPage.inset_Save();
			filesPage.filesGrid_Check("add");
			filesPage.file_Edit();
			filesPage.filesGrid_Check("edit");
			filesPage.inset_Save();
			filesPage.filesGrid_Check("edit");	
			filesPage.fileUnload_check();
			filesPage.file_Delete();
			
			// Проверка поиска карточки
			// Проверка редактирования
			
			docsPage = filesPage.card_Close("tricky");
			docsPage.waitFor_PageReady();
			docsPage.tree_Open();
			docsPage.card_Search();
			docsPage.card_Check("add");
			editPage = docsPage.card_Edit();
			editPage.cardHeader_Check();
			editPage.resolution_grid_check("edit");
			editPage = editPage.shortSummary_Edit();
			docsPage = editPage.card_Close();
			
			// Проверка просмотра
			docsPage.waitFor_PageReady();
			docsPage.tree_Open();
			docsPage.card_Search();
			docsPage.card_Check("edit");
			IncomingDocs_RegistrationPage viewPage = docsPage.card_View();
			viewPage.cardHeader_Check();
			viewPage.resolution_grid_check("view");
			IncomingDocs_PerformControlPage view_controlPage = viewPage.goTo_PerformControl_Page("simple");
			view_controlPage.wait_ForPageReady();
			view_controlPage.controlGrid_Check("view");
			view_controlPage.historyGrid_Check();
			view_controlPage.termsGrid_Check("view");
			IncomingDocs_FilesPage view_filesPage = view_controlPage.goTo_Files_Page("simple");
			view_filesPage.wait_ForPageReady();
			view_filesPage.filesGrid_Check("view");
			view_filesPage.fileUnload_check();
			
			// Проверка посдвечивания строки в гриде
			docsPage = view_filesPage.card_Close("simple");
			//docsPage.card_Check("edit");
			// Дописать проверку подсвечивания
			
			// Выход из системы
			docsPage.user_Out();
	}
}
