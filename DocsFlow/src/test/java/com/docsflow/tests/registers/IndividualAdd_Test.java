package com.docsflow.tests.registers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.docsflow.core.BaseTest;
import com.docsflow.core.database.DbQueries;
import com.docsflow.core.database.DbStatements;
import com.docsflow.core.web.pages.other.Handbooks_Page;
import com.docsflow.core.web.pages.other.LogInPage;
import com.docsflow.core.web.pages.other.MainPage;
import com.docsflow.core.web.pages.registers.Individuals_FilesPage;
import com.docsflow.core.web.pages.registers.Individuals_Page;
import com.docsflow.core.web.pages.registers.Individuals_RegPage;

public class IndividualAdd_Test extends BaseTest
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase_BeforeTest() throws Exception
	{
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String FoDeletion_ErrorMessage = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.FoDeletion_ErrorMessage;
	    String RegPlaceDeletion_ErrorMessage = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.RegPlaceDeletion_ErrorMessage;
	    
	    // Определение текста запроса
	    String FoDeletion_Statement = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.FoDeletion_Statement;
	    String RegPlaceDeletion_Statement = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.RegPlaceDeletion_Statement;
	    
	    // Выполнение запроса
	    new DbStatements().SimpleStatement(sqlConnection, FoDeletion_Statement, FoDeletion_ErrorMessage);
	    new DbStatements().SimpleStatement(sqlConnection, RegPlaceDeletion_Statement, RegPlaceDeletion_ErrorMessage);
	}
	
	@Test(groups = { "IndividualAdd_Test" })
	public void IndividualAdd_TestMethod()
	{	
		// Переход на главную
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		
		
		//Individuals_FilesPage filesPage  = mainPage.new goTo().direct_Redirect();
		
		
		// Добавление записи в словарь НДИ(для обновления кэша веб-сервера)
		Handbooks_Page handbooksPage = mainPage.new goTo().handbooksPage();
		handbooksPage.wait_ForPageReady();
		handbooksPage.dictionaryCache_Update(sqlConnection);
		
		// Переход к добавлению ФО
		mainPage = handbooksPage.backToMainPage();
		Individuals_Page individuals_Page = mainPage.new goTo().new CnapBlock().individuals_Page();
		individuals_Page.gridDownload_Wait();
		Individuals_RegPage addPage = individuals_Page.cardAdd_Call();
		
		// Заполнение + сохранение карточки
		// Проверка добавленной карточки
		addPage.country_Add();
		addPage.personInfo_Fill();
		addPage.docInfo_Fill();
		addPage.bornPlaceInfo_Fill();
		addPage.dictValue_SetInability_Check();
		addPage.residenceInfo_Fill();
		Individuals_RegPage editPage = addPage.card_Save();
		editPage.card_Check();
		
		// Проверка подсвечивания записи в гриде
	    individuals_Page = editPage.card_Close();
		individuals_Page.gridDownload_Wait();
		individuals_Page.rowHighlighted_Check();
		
		// Добавление 2го ФО
		addPage = individuals_Page.cardAdd_Call();
		addPage.secondCard_Fill();
		addPage.copyCardAdd_Inability_Check();
		addPage.card_Save();
		individuals_Page = addPage.card_Close();
		
		// Проверка фильтрации на страничке реестра ФО
		individuals_Page.gridDownload_Wait();
		individuals_Page.card_Search();
		individuals_Page.card_Check("add");	
		
		// Работа со вкладкой 'Связанные документы и файлы'(грид файлов)
		
		editPage = individuals_Page.card_Edit();
		editPage.placeValue_Change();
		editPage.card_Save();
		Individuals_FilesPage filesPage = editPage.goTo_Files_Page();		
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
	    individuals_Page = filesPage.card_Close();
		
		// Проверка просмотра
		
		individuals_Page.gridDownload_Wait();
		individuals_Page.card_Search();
		individuals_Page.card_Check("edit");	
		Individuals_RegPage viewPage = individuals_Page.card_View();
		viewPage.cardHeader_Check();
		filesPage = viewPage.goTo_Files_Page();
		filesPage.wait_ForPageReady();
		filesPage.filesGrid_Check("view");	
		filesPage.fileUnload_check();
		filesPage.addedDoc_Check();
		filesPage.docView_check();
		individuals_Page = filesPage.card_Close();
		
		// Проверка подсвечивания + выход из системы
		
		individuals_Page.gridDownload_Wait();
		individuals_Page.rowHighlighted_Check();
		individuals_Page.user_Out();
	}
	
	@AfterMethod(alwaysRun = true, groups = {"IndividualAdd_Test"})
	public void DeletionViaDatabase_AfterTest() throws Exception
	{
	    // Определение ошибки, которая будет появляться в случае падения запроса
	    String deletion_ErrorMessage = DbQueries.DictionaryTests.Deletion_Queries.cacheUpdateValueDeletion_ErrorMessage;
	    
	    // Определение текста запроса
	    String deletion_Statement = DbQueries.DictionaryTests.Deletion_Queries.cacheUpdateValueDeletion_Statement;
	    
	    // Выполнение запросов
	    new DbStatements().SimpleStatement(sqlConnection, deletion_Statement, deletion_ErrorMessage);
	}
}
