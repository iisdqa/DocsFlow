package com.docsflow.core.web.pages.administration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.CheckBox;
import com.docsflow.core.web.elements.Custom;
import com.docsflow.core.web.elements.Link;
import com.docsflow.core.web.pages.other.LogInPage;

public class UserViewPage extends WebPage<UserViewPage> 
{
private static final String PAGE_URL = BASE_URL + "/User/EditUser/";
	
	public UserViewPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public UserViewPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getViewForm().isAvailable() && 
			   getBackToUsersPageLink().isAvailable();
	}
	
	/*_____________________________________________________________________________________________*/
	
	public void ViewPageCheck()
	{		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [10][];
		ExpectedValues[0] = new String[] {"�����:", "auto_user"};
		ExpectedValues[1] = new String[] {"�������:", "��������"};
		ExpectedValues[2] = new String[] {"���:", "�������"};
		ExpectedValues[3] = new String[] {"��������:", "��������"};
		ExpectedValues[4] = new String[] {"���������:", "����������"};
		ExpectedValues[5] = new String[] {"�������:", "(000) 111-22-33"};
		ExpectedValues[6] = new String[] {"����� ����������� �����:", "auto_user@email.com"};
		ExpectedValues[7] = new String[] {"������ ������������:", "�����"};
		ExpectedValues[8] = new String[] {"����:", "��������� �������������\n������� ������������ ������\n������� ���������� ������\n����"};
		ExpectedValues[9] = new String[] {"��������", "��"};
		
		// �������� �������� �� �����
		String[][] ActualValues = getValuesFromViewPage(getGridBody());
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// �������� ���������
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new RoleCheckBoxes().getAdminRole(), "checked", false);
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new RoleCheckBoxes().getCentralRole(), "checked", false);
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new RoleCheckBoxes().getLocalRole(), "checked", true);
	}
	
	public LogInPage userOut()
	{
		// ����� �� �������
		return new CommonActions().userOut(driver);
	}
	
	/*_____________________________________________________________________________________________*/
	
	private String[][] getValuesFromViewPage (WebElement table)
	{
		// ����������� ���������� �����
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		
		// �������� ������ ���������(������� + �� ����� '����')
		rows.remove(14);
		rows.remove(13);
		rows.remove(12);
		rows.remove(11);
		rows.remove(9);
		rows.remove(0);
		
		// ����������� ���������� ����� � ������������ �������
		String[][] GridValues = new String[rows.size()][];
		
		// ���������� ����
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// ���������� ���������� �������
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			int sizeBeforeCut = columns.size();
			
			// �������� ������ ����� ��� ����
			if(rnum == 8)
			{
				for(int cnum=sizeBeforeCut- 1; cnum > 1; cnum--)
				{
						columns.remove(cnum);
				}
			}
			// ����������� ������� ������� � ���������� �����
			String[] ColumnValues = new String[columns.size()];
			
			// ���������� �������� ����� � ������
			for(int cnum=0; cnum<columns.size(); cnum++)
			{			
				ColumnValues[cnum] = new CustomMethods().StringSpacesCut(columns.get(cnum).getText());
			}
			
			// �������� �������� ������� � ��� 
			GridValues[rnum] = ColumnValues;
		}
		return GridValues;
	}
	
	private Link getBackToUsersPageLink()
	{
		return new Link(driver, By.xpath("//a[@href='/User/Search']"));
	}
	
	private Custom getViewForm()
	{
		return new Custom(driver, By.id("frmEditUser"));
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='frmEditUser']/table/tbody"));
	}
	
	// ������������ ����
	private class RoleCheckBoxes
	{
		private CheckBox getLocalRole()
		{
			return new CheckBox(driver, By.id("Expert_Local_Level"));
		}
		
		private CheckBox getCentralRole()
		{
			return new CheckBox(driver, By.id("Expert_Central_Level"));
		}
		
		private CheckBox getAdminRole()
		{
			return new CheckBox(driver, By.id("System_Administrator"));
		}
	}
}
