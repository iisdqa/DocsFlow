package com.docsflow.core.web.pages.registers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.TextInput;

public class Individuals_RegPage extends WebPage<Individuals_RegPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/Add/63";
	
	public Individuals_RegPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Individuals_RegPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().close_Button(driver).isAvailable();
	}

	

	/*__________________________________________________ Actions ________________________________________________________*/

	public void country_Add()
	{
		//region Variables
		Button countryAdd = new Elements().new PersonInfo().countryAdd_Button();
		TextInput citizenshipInput = new Elements().new PersonInfo().citizenship_TextInput();
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		String askMessage = new Elements().new PersonInfo().dictValueAdd_AskMessage(citizenship);
		String successMessage = new Elements().new PersonInfo().dictValueAdd_SuccessMessage("������������");
		//endregion
		
		// �������� ������������� ������ ���������� ���� �� ������� ��������
		assertThat(countryAdd.getAttribute("disabled"), is(equalTo("true")));
		
		// ������ ��������
		citizenshipInput.inputText(citizenship);
		
		// ������� � �������
		new CommonActions().dictValue_Add(driver, countryAdd, askMessage, successMessage);
		
		// �������� ���������
		driver.navigate().refresh();
		countryAdd.waitUntilAvailable();
	}
	
	// ���������� ����� � ����� '�����'
	public void personInfo_Fill()
	{
		//region Variables	
		String idCode = new Elements().new PersonInfo().new Values().idCode;
		String surname = new Elements().new PersonInfo().new Values().surname;
		String name = new Elements().new PersonInfo().new Values().name;
		String patronymic = new Elements().new PersonInfo().new Values().patronymic;
		String gender = new Elements().new PersonInfo().new Values().gender;
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		String bornDate = new Elements().new PersonInfo().new Values().bornDate;
		String phone = new Elements().new PersonInfo().new Values().phone;
		String �ellPhone = new Elements().new PersonInfo().new Values().�ellPhone;
		String email = new Elements().new PersonInfo().new Values().email;
		//endregion
		
		// ���������� �����
		new Elements().new PersonInfo().idCode_TextInput().inputText(idCode);
		new Elements().new PersonInfo().surname_TextInput().inputText(surname);
		new Elements().new PersonInfo().name_TextInput().inputText(name);
		new Elements().new PersonInfo().patronymic_TextInput().inputText(patronymic);
		new Elements().new PersonInfo().gender_Select().selectByVisibleText(gender);
		new Elements().new PersonInfo().citizenship_TextInput().inputText(citizenship);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new PersonInfo().citizenship_TextInput(), 1);
		new Elements().new PersonInfo().bornDate_TextInput().inputText(bornDate);
		new Elements().new PersonInfo().phone_TextInput().inputText(phone);
		new Elements().new PersonInfo().cellPhone_TextInput().inputText(�ellPhone);
		new Elements().new PersonInfo().email_TextInput().inputText(email);
	}
	
	public void docInfo_Fill()
	{
		
	}
	
	public void bornPlaceInfo_Fill()
	{
		
	}
	
	public void residenceInfo_Fill()
	{
		
	}
	
	public void card_Save()
	{
		
	}
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	
	private class Elements extends CommonElements.Card_Elements.General_Elements
	{
		// ���� '�����'
		private class PersonInfo extends CommonElements.Card_Elements.Pop_Ups
		{
			// '������������'
			private Button countryAdd_Button() 			{ return new Button(driver,By.id("705_btn_add")); } 
			
			// '���'
			private TextInput idCode_TextInput() 		{ return new TextInput(driver, By.id("707")); } 
			
			// '�������'
			private TextInput surname_TextInput() 		{ return new TextInput(driver, By.id("700")); } 
			
			// '��'�'
			private TextInput name_TextInput() 			{ return new TextInput(driver,By.id("701")); } 
			
			// '��-�������'
			private TextInput patronymic_TextInput() 	{ return new TextInput(driver,By.id("702")); } 
			
			// '�����'
			private Select gender_Select() 				{ return new Select(driver.findElement(By.id("704"))); } 
			
			// '������������'
			private TextInput citizenship_TextInput() 	{ return new TextInput(driver,By.id("705")); } 
			
			// '���� ����������'
			private TextInput bornDate_TextInput() 		{ return new TextInput(driver,By.id("706")); } 
			
			// '�������'
			private TextInput phone_TextInput() 		{ return new TextInput(driver,By.id("709")); } 
			
			// '�������� �������'
			private TextInput cellPhone_TextInput() 	{ return new TextInput(driver,By.id("708")); } 
			
			// 'Email'
			private TextInput email_TextInput() 		{ return new TextInput(driver,By.id("710")); } 
			
			// ������������ ��������
			private class Values 
			{
				private String idCode = "0007771110";			// '���'
				private String surname = "�������������";		// '�������'				
				private String name = "�����";					// '��'�'				
				private String patronymic = "����������";		// '��-�������'				
				private String gender = "�������";				// '�����' 				
				private String citizenship = "�������������";	// '������������'				
				private String bornDate = "01.01.1975";			// '���� ����������'				
				private String phone = "(000)1112233";			// '�������'				
				private String �ellPhone = "(099)3332211";		// '�������� �������'				
				private String email = "autmtn@email.com";		// 'Email'
			}
		}
		
		// ���� '��������'
		private class DocInfo
		{
			
		}
		
		// ���� '̳��� ����������'
		private class BornPlaceInfo
		{
			
		}
		
		// ���� '̳��� ���������'
		private class ResidenceInfo
		{
			private class Values
			{
			}
		}
	}
}
