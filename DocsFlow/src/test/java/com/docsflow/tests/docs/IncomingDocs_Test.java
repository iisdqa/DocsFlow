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

public class IncomingDocs_Test extends BaseTest 
{
	/*@BeforeMethod(alwaysRun = true, dependsOnMethods = {"setUp"})
	public void DeletionViaDatabase() throws Exception
	{
	    // ����������� ������, ������� ����� ���������� � ������ ������� �������
	    String ErrorMessage = DbQueries.DocsTests.Incoming_Docs.Deletion_Queries.DocDeletion_ErrorMessage;
	    
	    // ����������� ������ �������
	    String DeletionStatement = DbQueries.DocsTests.Incoming_Docs.Deletion_Queries.DocDeletion_Statement;
	    
	    // ���������� �������
	    new DbStatements().SimpleStatement(sqlConnection, DeletionStatement, ErrorMessage);
	}*/
	
	@Test(groups = { "IncomingDocs_Test" })
	public void IncomingDocs_TestMethod() throws Exception
	{	
			// ������� �� �������
			LogInPage authorizationPage = new MainPage(driver).redirectToLogInPage();
			MainPage mainPage = authorizationPage.logInAs("admin_auto", "123456");
			
			IncomingDocs_PerformControlPage controlPage = mainPage.new goTo().direct_Redirect();
			
/*			// ������� �� ��������� �������������
			IncomingDocs_Page docsPage = mainPage.new goTo().new DocsBlock().IncomingDocs_Page(browser);
			docsPage.waitFor_PageReady();
			
			// ���������� ��������
			docsPage.tree_Open();
			IncomingDocs_RegistrationPage addPage = docsPage.card_add();
			addPage.card_Generate(sqlConnection);

			IncomingDocs_RegistrationPage editPage = addPage.cardInfo_Set();
			editPage.card_Check();

			// �������������� ��������				
			editPage.corr_ExistenceCheck();
			editPage.card_Edit();
			editPage.editedCard_Check();
			
			
			// ����������/�������������� �������� ����������
			// �������� ������������� ������ � ����� ������� 		
			IncomingDocs_PerformControlPage controlPage = editPage.goTo_PerformControl_Page();		
			controlPage.control_Add();
			controlPage.controlGrid_Check("add");
			controlPage.inset_Save();			
			controlPage.controlGrid_Check("add");
			controlPage.control_Edit();
			controlPage.controlGrid_Check("edit");
			controlPage.inset_Save();
			controlPage.controlGrid_Check("refreshed");
			controlPage.historyGrid_Check();
			
			// �������� ����� '������� ������' 	
			
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
			
			// �������� ������� �� ������ '������� ������' � '�������� ������'
						
			controlPage.control_Delete("1");
			controlPage.controlGrid_Check("refreshed");
			controlPage.inset_Save();
			controlPage.controlGrid_Check("refreshed");*/
			
			// ������ �� �������� '��������� ��������� � �����'
						
			IncomingDocs_FilesPage filesPage = controlPage.goTo_Files_Page();			
			filesPage.file_Add();
			filesPage.filesGrid_Check("add");
			filesPage.inset_Save();
			filesPage.filesGrid_Check("add");
			filesPage.file_Edit();
			filesPage.filesGrid_Check("edit");
			filesPage.inset_Save();
			filesPage.filesGrid_Check("edit");
			filesPage.fileUnload_check();
			
			// �������� ������ ��������
			// �������� ������ ��������������/���������
			
			/*docsPage = filesPage.card_Close();
			docsPage.waitFor_PageReady();
			docsPage.tree_Open();*/	
			
			
			
			
			
			
			
			
			
			//IncomingDocs_FilesPage filesPage = mainPage.new goTo().directRedirect();
	}
}
