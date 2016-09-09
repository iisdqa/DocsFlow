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
import com.docsflow.core.web.pages.registers.Individuals_Page;
import com.docsflow.core.web.pages.registers.Individuals_RegPage;

public class IndividualAdd_Test extends BaseTest
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase_BeforeTest() throws Exception
	{
		
	}
	
	@Test(groups = { "IndividualAdd_Test" })
	public void IndividualAdd_TestMethod() throws Exception
	{	
		// ������� �� �������
		LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
		MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
		
		// ���������� ������ � ������� ���(��� ���������� ���� ���-�������)
		Handbooks_Page handbooksPage = mainPage.new goTo().handbooksPage();
		handbooksPage.wait_ForPageReady();
		handbooksPage.dictionaryCache_Update(sqlConnection);
		
		// ������� � ���������� ��
		mainPage = handbooksPage.backToMainPage();
		Individuals_Page individuals_Page = mainPage.new goTo().new CnapBlock().individuals_Page();
		individuals_Page.gridDownload_Wait();
		Individuals_RegPage addPage = individuals_Page.cardAdd_Call();
		
		// ���������� + ���������� ��������
		addPage.personInfo_Fill();
		addPage.docInfo_Fill();
		addPage.bornPlaceInfo_Fill();
		addPage.dictValue_SetInability_Check();
		addPage.residenceInfo_Fill();
		Individuals_RegPage editPage = addPage.card_Save();
		
		
		
		
	}
	
	@AfterMethod(alwaysRun = true, dependsOnMethods = {"IndividualAdd_TestMethod"})
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
