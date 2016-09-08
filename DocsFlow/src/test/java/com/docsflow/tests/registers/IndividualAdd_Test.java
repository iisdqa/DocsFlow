package com.docsflow.tests.registers;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.docsflow.core.BaseTest;
import com.docsflow.core.database.DbQueries;
import com.docsflow.core.database.DbStatements;
import com.docsflow.core.web.pages.other.Handbooks_Page;
import com.docsflow.core.web.pages.other.LogInPage;
import com.docsflow.core.web.pages.other.MainPage;

public class IndividualAdd_Test extends BaseTest
{
	@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String FoDeletion_ErrorMessage = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.FoDeletion_ErrorMessage;
	    String RegPlaceDeletion_ErrorMessage = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.RegPlaceDeletion_ErrorMessage;
	    
	    // ����������� ������ �������
	    String FoDeletion_Statement = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.FoDeletion_Statement;
	    String RegPlaceDeletion_Statement = DbQueries.CnapTests.Registers.Individuals.Deletion_Queries.RegPlaceDeletion_Statement;
	    
	    // ���������� ��������
	    new DbStatements().SimpleStatement(sqlConnection, FoDeletion_Statement, FoDeletion_ErrorMessage);
	    new DbStatements().SimpleStatement(sqlConnection, RegPlaceDeletion_Statement, RegPlaceDeletion_ErrorMessage);
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
		handbooksPage.dictionaryCache_Update();
		
		
		
		
		
		
	}
}
