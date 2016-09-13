package com.docsflow.tests.registers;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.docsflow.core.BaseTest;
import com.docsflow.core.database.DbQueries;
import com.docsflow.core.database.DbStatements;
import com.docsflow.core.web.pages.other.LogInPage;
import com.docsflow.core.web.pages.other.MainPage;
import com.docsflow.core.web.pages.registers.Entrepreneurs_Page;
import com.docsflow.core.web.pages.registers.Entrepreneurs_FilesPage;
import com.docsflow.core.web.pages.registers.Entrepreneurs_RegPage;

public class EntrepreneurAdd_Test extends BaseTest
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase_BeforeTest() throws Exception
	{
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String FoDeletion_ErrorMessage = DbQueries.CnapTests.Registers.Entrepreneurs.Deletion_Queries.FoDeletion_ErrorMessage;
	
	    // Определение текста запроса
	    String FoDeletion_Statement = DbQueries.CnapTests.Registers.Entrepreneurs.Deletion_Queries.FoDeletion_Statement;	  
	    
	    // Выполнение запроса
	    new DbStatements().SimpleStatement(sqlConnection, FoDeletion_Statement, FoDeletion_ErrorMessage);
	}
	
	@Test(groups = { "EntrepreneurAdd_Test" })
	public void IndividualAdd_TestMethod()
	{	
		// Переход на главную
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		
		
		//Individuals_FilesPage filesPage  = mainPage.new goTo().direct_Redirect();
		
		// Переход к добавлению ФО
		Entrepreneurs_Page entrepreneurs_Page = mainPage.new goTo().new CnapBlock().entrepreneurs_Page();
		entrepreneurs_Page.gridDownload_Wait();
		Entrepreneurs_RegPage addPage = entrepreneurs_Page.cardAdd_Call();
		
		// Заполнение + сохранение карточки
		// Проверка добавленной карточки
		addPage.personInfo_Fill();
		addPage.docInfo_Fill();
		addPage.bornPlaceInfo_Fill();
		addPage.dictValue_SetInability_Check();
		addPage.residenceInfo_Fill();
		Entrepreneurs_RegPage editPage = addPage.card_Save();
		editPage.card_Check();
		
		// Проверка подсвечивания записи в гриде
		entrepreneurs_Page = editPage.card_Close();
		entrepreneurs_Page.gridDownload_Wait();
		entrepreneurs_Page.rowHighlighted_Check();
		
		// Добавление 2го ФО
		addPage = entrepreneurs_Page.cardAdd_Call();
		addPage.secondCard_Fill();
		addPage.copyCardAdd_Inability_Check();
		addPage.card_Save();
		entrepreneurs_Page = addPage.card_Close();
		
		// Проверка фильтрации на страничке реестра ФО
		entrepreneurs_Page.gridDownload_Wait();
		entrepreneurs_Page.card_Search();
		entrepreneurs_Page.card_Check("add");	
		
		// Работа со вкладкой 'Связанные документы и файлы'(грид файлов)
		
		editPage = entrepreneurs_Page.card_Edit();
		editPage.placeValue_Change();
		editPage.card_Save();
		Entrepreneurs_FilesPage filesPage = editPage.goTo_Files_Page();		
		filesPage.file_Add();
		filesPage.filesGrid_Check("add");
		filesPage.inset_Save();
		filesPage.filesGrid_Check("add");
		filesPage.file_Edit();
		filesPage.filesGrid_Check("edit");
		filesPage.inset_Save();
		filesPage.filesGrid_Check("edit");	
		filesPage.fileUnload_check();
		
		// Работа со вкладкой 'Связанные документы и файлы'(блок документов)
		
		filesPage.doc_Add();
		filesPage.addedDoc_Check();
		filesPage.docView_check();
		filesPage.inset_Save();
		filesPage.addedDoc_Check();
		filesPage.docView_check();
		entrepreneurs_Page = filesPage.card_Close();
		
		// Проверка просмотра
		
		entrepreneurs_Page.gridDownload_Wait();
		entrepreneurs_Page.card_Search();
		entrepreneurs_Page.card_Check("edit");	
		Entrepreneurs_RegPage viewPage = entrepreneurs_Page.card_View();
		viewPage.cardHeader_Check();
		filesPage = viewPage.goTo_Files_Page();
		filesPage.wait_ForPageReady();
		filesPage.filesGrid_Check("view");	
		filesPage.fileUnload_check();
		filesPage.addedDoc_Check();
		filesPage.docView_check();
		entrepreneurs_Page = filesPage.card_Close();
		
		// Проверка подсвечивания + выход из системы
		
		entrepreneurs_Page.gridDownload_Wait();
		entrepreneurs_Page.rowHighlighted_Check();
		entrepreneurs_Page.user_Out();
	}
}
