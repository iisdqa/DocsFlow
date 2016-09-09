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
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String ErrorMessage = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.FoDeletion_ErrorMessage;
	    
	    // ����������� ������ �������
	    String DeletionStatement = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.FoDeletion_Statement;
	    
	    // ���������� �������
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}
	
	@Test(groups = { "IndividualAdd_Test" })
	public void IndividualAdd_TestMethod() throws Exception
	{	
		// ������� �� �������
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		
		
		Individuals_RegPage addPage = mainPage.new goTo().direct_Redirect();
		
		
/*		// ���������� ������ � ������� ���(��� ���������� ���� ���-�������)
		Handbooks_Page handbooksPage = mainPage.new goTo().handbooksPage();
		handbooksPage.wait_ForPageReady();
		handbooksPage.dictionaryCache_Update(sqlConnection);
		
		// ������� � ���������� ��
		mainPage = handbooksPage.backToMainPage();
		Individuals_Page individuals_Page = mainPage.new goTo().new CnapBlock().individuals_Page();
		individuals_Page.gridDownload_Wait();
		Individuals_RegPage addPage = individuals_Page.cardAdd_Call();*/
		
		// ���������� + ���������� ��������
		// �������� ����������� ��������
		addPage.country_Add();
		addPage.personInfo_Fill();
		addPage.docInfo_Fill();
		addPage.bornPlaceInfo_Fill();
		addPage.dictValue_SetInability_Check();
		addPage.residenceInfo_Fill();
		Individuals_RegPage editPage = addPage.card_Save();
		editPage.card_Check();
		
		// �������� ������������� ������ � �����
		Individuals_Page individuals_Page = editPage.card_Close();
		individuals_Page.gridDownload_Wait();
		individuals_Page.rowHighlighted_Check();
		
		// ���������� 2�� ��
		addPage = individuals_Page.cardAdd_Call();
		addPage.secondCard_Fill();
		addPage.card_Save();
		individuals_Page = editPage.card_Close();
		
		// �������� ���������� �� ��������� ������� ��
		individuals_Page.gridDownload_Wait();
		individuals_Page.card_Search();
		individuals_Page.card_Check("add");
		
		
		
		// ������ �� �������� '��������� ��������� � �����'
		
		/*Individuals_FilesPage filesPage = editPage.goTo_Files_Page();		
		filesPage.file_Add();
		filesPage.filesGrid_Check("add");
		filesPage.inset_Save();
		filesPage.filesGrid_Check("add");
		filesPage.file_Edit();
		filesPage.filesGrid_Check("edit");
		filesPage.inset_Save();
		filesPage.filesGrid_Check("edit");	
		filesPage.fileUnload_check();
		filesPage.file_Delete();*/
		
		
	}
	
	@AfterMethod(alwaysRun = true, groups = {"DrugChanges_Test"})
	public void DeletionViaDatabase_AfterTest() throws Exception
	{
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String deletion_ErrorMessage = DbQueries.DictionaryTests.Deletion_Queries.cacheUpdateValueDeletion_ErrorMessage;
	    
	    // ����������� ������ �������
	    String deletion_Statement = DbQueries.DictionaryTests.Deletion_Queries.cacheUpdateValueDeletion_Statement;
	    
	    // ���������� ��������
	    new DbStatements().SimpleStatement(sqlConnection, deletion_Statement, deletion_ErrorMessage);
	}
}
