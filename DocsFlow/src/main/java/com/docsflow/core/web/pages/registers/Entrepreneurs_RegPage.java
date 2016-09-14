package com.docsflow.core.web.pages.registers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;

import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Custom;
import com.docsflow.core.web.elements.TextInput;

public class Entrepreneurs_RegPage extends WebPage<Entrepreneurs_RegPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/Add/63";
	
	public Entrepreneurs_RegPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Entrepreneurs_RegPage load()
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

	// �������� ������������� ���������� ������� � ������� ��� ������� ����� ������ � �������
	public void dictValue_SetInability_Check()
	{
		//region Variables
		TextInput citizenship_TextInput = new Elements().new PersonInfo().citizenship_TextInput();
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		Button add_Button = new Elements().dictAdd_Button(driver, "756");
		Custom info_PopUp = new Elements().new ResidenceInfo().info_PopUp(driver);
		String askMessage = new Elements().new PersonInfo().dictValueAdd_AskMessage(citizenship);
		String errorMessage = "�������� ��� � � �������� '������������'";
		//endregion
		
		// ������ ��������
		citizenship_TextInput.inputText(citizenship);
		
		// ������ ������ ����������
		add_Button.click();
		new CustomMethods().simpleWait(1);
		info_PopUp.waitUntilAvailable();

		// �������� ���������
		assertThat(info_PopUp.getText(), is(equalTo(askMessage)));
		
		// ������ '���'
		new Elements().new ResidenceInfo().yes_Button(driver).click();
		new CustomMethods().simpleWait(1);
		info_PopUp.waitUntilAvailable();
		
		// �������� ��������� �� �������� ����������
		assertThat(info_PopUp.getText(), is(equalTo(errorMessage)));
		
		// �������
		new Elements().new ResidenceInfo().close_Button(driver).click();
		add_Button.waitUntilAvailable();
		
		// �������� ��������
		citizenship_TextInput.clear();
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
		new Elements().new PersonInfo().bornDate_TextInput().click();
		new Elements().new PersonInfo().bornDate_TextInput().inputText(bornDate);
		new Elements().new PersonInfo().phone_TextInput().inputText(phone);
		new Elements().new PersonInfo().cellPhone_TextInput().inputText(�ellPhone);
		new Elements().new PersonInfo().email_TextInput().inputText(email);
	}
	
	// ���������� ����� � ����� '��������'
	public void docInfo_Fill()
	{
		//region Variables	
		String docType = new Elements().new DocInfo().new Values().docType;
		String serial = new Elements().new DocInfo().new Values().serial;
		String number = new Elements().new DocInfo().new Values().number;
		String giveDate = new Elements().new DocInfo().new Values().giveDate;
		String organization = new Elements().new DocInfo().new Values().organization;
		//endregion
		
		// ���������� �����
		new Elements().new DocInfo().docKind_Select().selectByVisibleText(docType);
		new Elements().new DocInfo().serial_TextInput().inputText(serial);
		new Elements().new DocInfo().number_TextInput().inputText(number);
		new Elements().new DocInfo().giveDate_TextInput().click();
		new Elements().new DocInfo().giveDate_TextInput().inputText(giveDate);
		new Elements().new DocInfo().organization_TextInput().inputText(organization);
	}
	
	// ���������� ����� � ����� '̳��� ����������'
	public void bornPlaceInfo_Fill()
	{
		//region Variables	
		String country = new Elements().new BornPlaceInfo().new Values().country;
		String place = new Elements().new BornPlaceInfo().new Values().place;
		//endregion
		
		// ���������� �����
		new Elements().new BornPlaceInfo().country_Select().selectByVisibleText(country);
		new Elements().new BornPlaceInfo().place_TextInput().inputText(place);
	}
	
	// ���������� ����� � ����� '̳��� ���������'
	public void residenceInfo_Fill()
	{
		//region Variables	
		String street = new Elements().new ResidenceInfo().new Values().street;
		TextInput street_Input =  new Elements().new ResidenceInfo().street_TextInput();
		String house = new Elements().new ResidenceInfo().new Values().house;
		TextInput house_Input =  new Elements().new ResidenceInfo().house_TextInput();	
		//endregion
		
		// ����� �������� ��� '������'
		street_Input.inputText(street);
		new CommonActions().autoCompleteValue_Set(driver, street_Input, 1);
		new CustomMethods().simpleWait(3);
		
		// ���������� �������� ��� '����������'
		house_Input.inputText(house);
	}
	
	public Entrepreneurs_RegPage card_Save()
	{
		// ��������� ��������� ����
		new Elements().save_Button(driver).click();
		waitUntilClickable(new Elements().new PersonInfo().idCode_TextInput());
		
		return new Entrepreneurs_RegPage(driver).waitUntilAvailable();
	}
	
	public void card_Check()
	{
		//region Variables
		/*____________________________ '�����' _________________________________*/
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
		/*_______________________________________________________________________*/
		
		/*____________________________ '��������' _________________________________*/
		String docType = new Elements().new DocInfo().new Values().docType;
		String serial = new Elements().new DocInfo().new Values().serial;
		String number = new Elements().new DocInfo().new Values().number;
		String giveDate = new Elements().new DocInfo().new Values().giveDate;
		String organization = new Elements().new DocInfo().new Values().organization;
		/*_______________________________________________________________________*/
		
		/*_________________________ '̳��� ����������' __________________________*/
		String country = new Elements().new BornPlaceInfo().new Values().country;
		String place = new Elements().new BornPlaceInfo().new Values().place;
		/*_______________________________________________________________________*/
		
		/*_________________________ '̳��� ���������' __________________________*/
		String region = new Elements().new ResidenceInfo().new Values().region;
		String district = new Elements().new ResidenceInfo().new Values().district;
		String village = new Elements().new ResidenceInfo().new Values().village;
		String street = new Elements().new ResidenceInfo().new Values().street;
		String house = new Elements().new ResidenceInfo().new Values().house;
		/*_______________________________________________________________________*/
		//endregion
		
		// �������� ����� '�����'
		assertThat(new Elements().new PersonInfo().idCode_TextInput().getAttribute("value"), is(equalTo(idCode)));
		assertThat(new Elements().new PersonInfo().surname_TextInput().getAttribute("value"), is(equalTo(surname)));
		assertThat(new Elements().new PersonInfo().name_TextInput().getAttribute("value"), is(equalTo(name)));
		assertThat(new Elements().new PersonInfo().patronymic_TextInput().getAttribute("value"), is(equalTo(patronymic)));
		assertThat(new Elements().new PersonInfo().gender_Select().getFirstSelectedOption().getText(), is(equalTo(gender)));
		assertThat(new Elements().new PersonInfo().citizenship_TextInput().getAttribute("value"), is(equalTo(citizenship)));
		assertThat(new Elements().new PersonInfo().bornDate_TextInput().getAttribute("value"), is(equalTo(bornDate)));
		assertThat(new Elements().new PersonInfo().phone_TextInput().getAttribute("value"), is(equalTo(phone)));
		assertThat(new Elements().new PersonInfo().cellPhone_TextInput().getAttribute("value"), is(equalTo(�ellPhone)));
		assertThat(new Elements().new PersonInfo().email_TextInput().getAttribute("value"), is(equalTo(email)));
		
		// �������� ����� '��������'
		assertThat(new Elements().new DocInfo().docKind_Select().getFirstSelectedOption().getText(), is(equalTo(docType)));
		assertThat(new Elements().new DocInfo().serial_TextInput().getAttribute("value"), is(equalTo(serial)));
		assertThat(new Elements().new DocInfo().number_TextInput().getAttribute("value"), is(equalTo(number)));
		assertThat(new Elements().new DocInfo().giveDate_TextInput().getAttribute("value"), is(equalTo(giveDate)));
		assertThat(new Elements().new DocInfo().organization_TextInput().getAttribute("value"), is(equalTo(organization)));
		
		// �������� ����� '̳��� ����������'
		assertThat(new Elements().new BornPlaceInfo().country_Select().getFirstSelectedOption().getText(), is(equalTo(country)));
		assertThat(new Elements().new BornPlaceInfo().place_TextInput().getAttribute("value"), is(equalTo(place)));
		
		// �������� ����� '̳��� ���������'
		assertThat(new Elements().new ResidenceInfo().country_TextInput().getAttribute("value"), is(equalTo(country)));
		assertThat(new Elements().new ResidenceInfo().region_TextInput().getAttribute("value"), is(equalTo(region)));
		assertThat(new Elements().new ResidenceInfo().district_TextInput().getAttribute("value"), is(equalTo(district)));
		assertThat(new Elements().new ResidenceInfo().village_TextInput().getAttribute("value"), is(equalTo(village)));
		assertThat(new Elements().new ResidenceInfo().street_TextInput().getAttribute("value"), is(equalTo(street)));
		assertThat(new Elements().new ResidenceInfo().house_TextInput().getAttribute("value"), is(equalTo(house)));
	}
	
	public Entrepreneurs_Page card_Close()
	{
		// �������� �������������� ��������
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
		return new Entrepreneurs_Page(driver).waitUntilAvailable();
	}
	
	public void secondCard_Fill()
	{
		//region Variables	
		/*____________________________ '�����' _________________________________*/
		String idCode = new Elements().new PersonInfo().new Values().idCode;
		String surname = new Elements().new PersonInfo().new Values().surname + "2";
		String name = new Elements().new PersonInfo().new Values().name + "2";
		String gender = new Elements().new PersonInfo().new Values().gender;
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		String bornDate = new Elements().new PersonInfo().new Values().second_bornDate;
		/*_______________________________________________________________________*/
		
		/*____________________________ '��������' _________________________________*/
		String docType = new Elements().new DocInfo().new Values().docType;
		/*_______________________________________________________________________*/
		
		/*_________________________ '̳��� ����������' __________________________*/
		String country = new Elements().new BornPlaceInfo().new Values().country;
		String place = new Elements().new BornPlaceInfo().new Values().place;
		/*_______________________________________________________________________*/
		
		/*_________________________ '̳��� ���������' __________________________*/
		String street = new Elements().new ResidenceInfo().new Values().street;
		String house = new Elements().new ResidenceInfo().new Values().house;
		/*_______________________________________________________________________*/
		//endregion
		
		// ���������� ����� ����� '�����'
		new Elements().new PersonInfo().idCode_TextInput().inputText(idCode);
		new Elements().new PersonInfo().surname_TextInput().inputText(surname);
		new Elements().new PersonInfo().name_TextInput().inputText(name);
		new Elements().new PersonInfo().gender_Select().selectByVisibleText(gender);
		new Elements().new PersonInfo().citizenship_TextInput().inputText(citizenship);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new PersonInfo().citizenship_TextInput(), 1);
		new Elements().new PersonInfo().bornDate_TextInput().click();
		new Elements().new PersonInfo().bornDate_TextInput().inputText(bornDate);
		
		// ���������� ����� ����� '��������'
		new Elements().new DocInfo().docKind_Select().selectByVisibleText(docType);
		
		// ���������� ����� ����� '̳��� ����������'
		new Elements().new BornPlaceInfo().country_Select().selectByVisibleText(country);
		new Elements().new BornPlaceInfo().place_TextInput().inputText(place);
		
		// ���������� ����� ����� '̳��� ���������'
		new Elements().new ResidenceInfo().street_TextInput().inputText(street);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new ResidenceInfo().street_TextInput(), 1);
		new Elements().new ResidenceInfo().house_TextInput().inputText(house);	
	}
	
	public void copyCardAdd_Inability_Check()
	{
		//region Variables			
		Custom info_PopUp = new Elements().new ResidenceInfo().info_PopUp(driver);
		String errorMessage = "���������� ���������, ������� ��� ���� ������� - ������� �����-��������� � ����� ���";
		String second_idCode = new Elements().new PersonInfo().new Values().second_idCode;
		//endregion
		
		// ������ �� ������ '���������'
		new Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		info_PopUp.waitUntilAvailable();

		// �������� ���������
		assertThat(info_PopUp.getText(), is(equalTo(errorMessage)));
		
		// �������
		new Elements().new ResidenceInfo().close_Button(driver).click();
		new Elements().close_Button(driver).waitUntilAvailable();
		
		// ������� ���
		new Elements().new PersonInfo().idCode_TextInput().clear();
		new Elements().new PersonInfo().idCode_TextInput().inputText(second_idCode);
	}
	
	public void placeValue_Change()
	{	
		new Elements().new BornPlaceInfo().place_TextInput().click();
		sendKeys("2");
	}
	
	public Entrepreneurs_FilesPage goTo_Files_Page()
	{
		//region Variables	
		WebElement insetLink = new Elements().inset_Link(driver, "2");
		//endregion
				
	 	// ���� �� �������
		insetLink.click();
		
		return new Entrepreneurs_FilesPage(driver).waitUntilAvailable();
	}
	
	// �������� ������ �������� + �������� ���
	public void cardHeader_Check()
	{
		//region Variables	
		String expected_Header = "������� ���";
		String expected_idCode = new Elements().new PersonInfo().new Values().idCode;
		//endregion
		
		// �������� ������
		new CommonActions().cardHeader_Check(driver, expected_Header);
		
		// �������� ���
		assertThat(new Elements().new PersonInfo().idCode_Text().getText(), is(equalTo(expected_idCode)));
	}
	
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	
	protected class Elements extends CommonElements.Card_Elements.General_Elements
	{
		// ���� '�����'
		protected class PersonInfo extends CommonElements.Card_Elements.Pop_Ups
		{
	
			// '���'
			private TextInput idCode_TextInput() 		{ return new TextInput(driver, By.id("758")); } 
			
			// '���' �� ������������ ���������
			private TextInput idCode_Text() 			{ return new TextInput(driver, By.xpath("//div[@class='break-word']/div[2]")); } 
			
			// '�������'
			private TextInput surname_TextInput() 		{ return new TextInput(driver, By.id("751")); } 
			
			// '��'�'
			private TextInput name_TextInput() 			{ return new TextInput(driver,By.id("752")); } 
			
			// '��-�������'
			private TextInput patronymic_TextInput() 	{ return new TextInput(driver,By.id("753")); } 
			
			// '�����'
			private Select gender_Select() 				{ return new Select(driver.findElement(By.id("755"))); } 
			
			// '������������'
			private TextInput citizenship_TextInput() 	{ return new TextInput(driver,By.id("756_auto")); } 
			
			// '���� ����������'
			private TextInput bornDate_TextInput() 		{ return new TextInput(driver,By.id("757")); } 
			
			// '�������'
			private TextInput phone_TextInput() 		{ return new TextInput(driver,By.id("760")); } 
			
			// '�������� �������'
			private TextInput cellPhone_TextInput() 	{ return new TextInput(driver,By.id("759")); } 
			
			// 'Email'
			private TextInput email_TextInput() 		{ return new TextInput(driver,By.id("761")); } 
			
			// ������������ ��������
			protected class Values 
			{
				protected String idCode = "0008881110";			// '���'
				protected String second_idCode = "8880001110";	// '���'
				protected String surname = "ϳ��������";		// '�������'				
				protected String name = "������";				// '��'�'				
				protected String patronymic = "��������";		// '��-�������'				
				private String gender = "�������";				// '�����' 				
				protected String citizenship = new ResidenceInfo().new Values().country;	// '������������'				
				protected String bornDate = "01.01.1985";		// '���� ����������'	
				protected String second_bornDate = "02.01.1985";	// '���� ����������'	
				private String phone = "(000)1112233";			// '�������'				
				private String �ellPhone = "(099)3332211";		// '�������� �������'				
				private String email = "autmtn@email.com";		// 'Email'
			}
		}
		
		// ���� '��������'
		private class DocInfo
		{
			// '��� ���������'
			private Select docKind_Select() 				{ return new Select(driver.findElement(By.id("762"))); } 
			
			// '����'
			private TextInput serial_TextInput() 			{ return new TextInput(driver, By.id("763")); } 
			
			// '�����'
			private TextInput number_TextInput() 			{ return new TextInput(driver, By.id("764")); } 
			
			// '���� ������'
			private TextInput giveDate_TextInput() 			{ return new TextInput(driver, By.id("765")); } 
			
			// '���'
			private TextInput organization_TextInput() 		{ return new TextInput(driver, By.id("766")); } 
			
			// ������������ ��������
			private class Values 
			{
				private String docType = "������� ����������� ������";		// '��� ���������'
				private String serial = "��";								// '����'
				private String number = "555222";							// '�����'
				private String giveDate = "01.01.1988";						// '���� ������'
				private String organization = "�������� ���";				// '���'
			}
		}
		
		// ���� '̳��� ����������'
		protected class BornPlaceInfo
		{
			// '����� ����������'
			private Select country_Select() 				{ return new Select(driver.findElement(By.id("767"))); } 
			
			// '̳��� ����������'
			private TextInput place_TextInput() 			{ return new TextInput(driver, By.id("768")); } 
			
			// ������������ ��������
			protected class Values 
			{
				private String country = new ResidenceInfo().new Values().country;	// '����� ����������'
				protected String place = "���. �������, �. 11, ��. 1";				// '̳��� ����������'
			}
		}
		
		// ���� '̳��� ���������'
		private class ResidenceInfo extends CommonElements.Card_Elements.Pop_Ups
		{
			// '�����'
			private TextInput country_TextInput() 			{ return new TextInput(driver, By.id("769_auto")); } 
			
			// '�������'
			private TextInput region_TextInput() 			{ return new TextInput(driver, By.id("770_auto")); } 
			
			// '�����(����)'
			private TextInput district_TextInput() 			{ return new TextInput(driver, By.id("771_auto")); } 
			
			// '������'
			private TextInput village_TextInput() 			{ return new TextInput(driver, By.id("772_auto")); } 
			
			// '������'
			private TextInput street_TextInput() 			{ return new TextInput(driver, By.id("773_auto")); } 
			
			// '����������'
			private TextInput house_TextInput() 			{ return new TextInput(driver, By.id("774")); } 
			
			private class Values
			{
				String country = "�������������";			// �����
				String region = "Selenium";					// '�������'
				String district = "Java";					// '�����(����)'
				String village = "Maven";					// '������'
				String street = "TestNG";					// '������'
				String house = "1";							// '����������'
			}
		}
	}
}
