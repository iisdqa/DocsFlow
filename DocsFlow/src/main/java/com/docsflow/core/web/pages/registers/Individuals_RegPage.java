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
import com.docsflow.core.web.CustomMethods.WorkWith_TextFiles;
import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Custom;
import com.docsflow.core.web.elements.TextInput;
import com.docsflow.core.web.pages.docs.IncomingDocs_RegistrationPage.Elements;
import com.docsflow.core.web.pages.docs.IncomingDocs_RegistrationPage.Elements.Values;
import com.docsflow.core.web.pages.registers.Entrepreneurs_RegPage.Elements.PersonInfo;

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

	// Добавить страну в словарь
	public void country_Add()
	{
		//region Variables
		Button countryAdd = new Elements().new PersonInfo().countryAdd_Button();
		TextInput citizenshipInput = new Elements().new PersonInfo().citizenship_TextInput();
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		String askMessage = new Elements().new PersonInfo().dictValueAdd_AskMessage(citizenship);
		String successMessage = new Elements().new PersonInfo().dictValueAdd_SuccessMessage("Громадянство");
		//endregion
		
		// Проверка недоступности кнопки добавления пока не внесено значение
		assertThat(countryAdd.getAttribute("disabled"), is(equalTo("true")));
		
		// Внести значение
		citizenshipInput.inputText(citizenship);
		
		// Доавить в словарь
		new CommonActions().dictValue_Add(driver, countryAdd, askMessage, successMessage);
		
		// Обновить страничку
		driver.navigate().refresh();
		countryAdd.waitUntilAvailable();
	}
	
	// Заполнение полей в блоке 'Особа'
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
		String сellPhone = new Elements().new PersonInfo().new Values().сellPhone;
		String email = new Elements().new PersonInfo().new Values().email;
		//endregion
		
		// Заполнение полей
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
		new Elements().new PersonInfo().cellPhone_TextInput().inputText(сellPhone);
		new Elements().new PersonInfo().email_TextInput().inputText(email);
	}
	
	// Заполнение полей в блоке 'Документ'
	public void docInfo_Fill()
	{
		//region Variables	
		String docType = new Elements().new DocInfo().new Values().docType;
		String serial = new Elements().new DocInfo().new Values().serial;
		String number = new Elements().new DocInfo().new Values().number;
		String giveDate = new Elements().new DocInfo().new Values().giveDate;
		String organization = new Elements().new DocInfo().new Values().organization;
		//endregion
		
		// Заполнение полей
		new Elements().new DocInfo().docKind_Select().selectByVisibleText(docType);
		new Elements().new DocInfo().serial_TextInput().inputText(serial);
		new Elements().new DocInfo().number_TextInput().inputText(number);
		new Elements().new DocInfo().giveDate_TextInput().click();
		new Elements().new DocInfo().giveDate_TextInput().inputText(giveDate);
		new Elements().new DocInfo().organization_TextInput().inputText(organization);
	}
	
	// Заполнение полей в блоке 'Місце народження'
	public void bornPlaceInfo_Fill()
	{
		//region Variables	
		String country = new Elements().new BornPlaceInfo().new Values().country;
		String place = new Elements().new BornPlaceInfo().new Values().place;
		//endregion
		
		// Заполнение полей
		new Elements().new BornPlaceInfo().country_Select().selectByVisibleText(country);
		new Elements().new BornPlaceInfo().place_TextInput().inputText(place);
	}
	
	// Проверка невозможности добавления записей в словарь при отсутствии значения в родительском словаре
	public void dictValue_SetInability_Check()
	{
		//region Variables
		TextInput region_Input = new Elements().new ResidenceInfo().region_TextInput();
		String region = new Elements().new ResidenceInfo().new Values().region;
		Button add_Button = new Elements().dictAdd_Button(driver, "719");
		Custom info_PopUp = new Elements().new ResidenceInfo().info_PopUp(driver);
		String askMessage = new Elements().new PersonInfo().dictValueAdd_AskMessage(region);
		String errorMessage = "Неможливо додати запис довідника 'МР Область', тому що не задане значення батьківського довідника 'МР Країна'.";
		//endregion
		
		// Внести значнеие
		region_Input.inputText(region);
		
		// Нажать кнопку добавления
		add_Button.click();
		new CustomMethods().simpleWait(1);
		info_PopUp.waitUntilAvailable();

		// Проверка сообщения
		assertThat(info_PopUp.getText(), is(equalTo(askMessage)));
		
		// Нажать 'Так'
		new Elements().new ResidenceInfo().yes_Button(driver).click();
		new CustomMethods().simpleWait(1);
		info_PopUp.waitUntilAvailable();
		
		// Проверка сообщения об успешном добавлении
		assertThat(info_PopUp.getText(), is(equalTo(errorMessage)));
		
		// Закрыть
		new Elements().new ResidenceInfo().close_Button(driver).click();
		add_Button.waitUntilAvailable();
	}
	
	// Заполнение полей в блоке 'Місце реєстрації'
	public void residenceInfo_Fill()
	{
		//region Variables
		/*____________________________ 'Країна' _________________________________*/
		String country = new Elements().new ResidenceInfo().new Values().country;
		TextInput country_Input =  new Elements().new ResidenceInfo().country_TextInput();
		/*_______________________________________________________________________*
		
		/*___________________________ 'Область' ________________________________*/
		Button regionAdd_Button = new Elements().dictAdd_Button(driver, "719");
		String region = new Elements().new ResidenceInfo().new Values().region;
		TextInput region_Input =  new Elements().new ResidenceInfo().region_TextInput();
		
		/*_________________________ 'Район(місто)' _____________________________*/
		Button districtAdd_Button = new Elements().dictAdd_Button(driver, "720");
		String district = new Elements().new ResidenceInfo().new Values().district;
		TextInput district_Input =  new Elements().new ResidenceInfo().district_TextInput();
		/*_______________________________________________________________________*/
		
		/*__________________________ 'Селище' ___________________________________*/
		Button villageAdd_Button = new Elements().dictAdd_Button(driver, "721");
		String village = new Elements().new ResidenceInfo().new Values().village;
		TextInput village_Input =  new Elements().new ResidenceInfo().village_TextInput();
		/*_______________________________________________________________________*/
		
		/*___________________________ 'Вулиця' __________________________________*/
		Button streetAdd_Button = new Elements().dictAdd_Button(driver, "722");
		String street = new Elements().new ResidenceInfo().new Values().street;
		TextInput street_Input =  new Elements().new ResidenceInfo().street_TextInput();
		/*_______________________________________________________________________*/
		
		/*_________________________ 'Помешкання' ________________________________*/
		String house = new Elements().new ResidenceInfo().new Values().house;
		TextInput house_Input =  new Elements().new ResidenceInfo().house_TextInput();
		/*_______________________________________________________________________*/
		//endregion
		
		// Выбор значения для 'Країна'
		country_Input.inputText(country);
		new CommonActions().autoCompleteValue_Set(driver, country_Input, 1);
		
		// Выбор значения для 'Область'
		dictValue_Add(regionAdd_Button, region_Input, region, "МР Область");
		
		// Выбор значения для 'Район(місто)'
		dictValue_Add(districtAdd_Button, district_Input, district, "МР Район(місто)");
		
		// Выбор значения для 'Селище'
		dictValue_Add(villageAdd_Button, village_Input, village, "МР Селище");
		
		// Выбор значения для 'Вулиця'
		dictValue_Add(streetAdd_Button, street_Input, street, "МР Вулиця");
		
		// Установить значение для 'Помешкання'
		house_Input.inputText(house);
	}
	
	// Добавить запись в словарь
	private void dictValue_Add(Button add_Button, TextInput valueInput, String Value, String FieldName)
	{
		//region Variables
		String askMessage = new Elements().new PersonInfo().dictValueAdd_AskMessage(Value);
		String successMessage = new Elements().new PersonInfo().dictValueAdd_SuccessMessage(FieldName);
		//endregion
	
		// Внести значение
		valueInput.inputText(Value);
		
		// Доавить в словарь
		new CommonActions().dictValue_Add(driver, add_Button, askMessage, successMessage);
	}
	
	public Individuals_RegPage card_Save()
	{
		// Сохранить введенную инфу
		new Elements().save_Button(driver).click();
		waitUntilClickable(new Elements().new PersonInfo().idCode_TextInput());
		
		return new Individuals_RegPage(driver).waitUntilAvailable();
	}
	
	public void card_Check()
	{
		//region Variables
		/*____________________________ 'Особа' _________________________________*/
		String idCode = new Elements().new PersonInfo().new Values().idCode;
		String surname = new Elements().new PersonInfo().new Values().surname;
		String name = new Elements().new PersonInfo().new Values().name;
		String patronymic = new Elements().new PersonInfo().new Values().patronymic;
		String gender = new Elements().new PersonInfo().new Values().gender;
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		String bornDate = new Elements().new PersonInfo().new Values().bornDate;
		String phone = new Elements().new PersonInfo().new Values().phone;
		String сellPhone = new Elements().new PersonInfo().new Values().сellPhone;
		String email = new Elements().new PersonInfo().new Values().email;
		/*_______________________________________________________________________*/
		
		/*____________________________ 'Документ' _________________________________*/
		String docType = new Elements().new DocInfo().new Values().docType;
		String serial = new Elements().new DocInfo().new Values().serial;
		String number = new Elements().new DocInfo().new Values().number;
		String giveDate = new Elements().new DocInfo().new Values().giveDate;
		String organization = new Elements().new DocInfo().new Values().organization;
		/*_______________________________________________________________________*/
		
		/*_________________________ 'Місце народження' __________________________*/
		String country = new Elements().new BornPlaceInfo().new Values().country;
		String place = new Elements().new BornPlaceInfo().new Values().place;
		/*_______________________________________________________________________*/
		
		/*_________________________ 'Місце реєстрації' __________________________*/
		String region = new Elements().new ResidenceInfo().new Values().region;
		String district = new Elements().new ResidenceInfo().new Values().district;
		String village = new Elements().new ResidenceInfo().new Values().village;
		String street = new Elements().new ResidenceInfo().new Values().street;
		String house = new Elements().new ResidenceInfo().new Values().house;
		/*_______________________________________________________________________*/
		//endregion
		
		// Проверка блока 'Особа'
		assertThat(new Elements().new PersonInfo().idCode_TextInput().getAttribute("value"), is(equalTo(idCode)));
		assertThat(new Elements().new PersonInfo().surname_TextInput().getAttribute("value"), is(equalTo(surname)));
		assertThat(new Elements().new PersonInfo().name_TextInput().getAttribute("value"), is(equalTo(name)));
		assertThat(new Elements().new PersonInfo().patronymic_TextInput().getAttribute("value"), is(equalTo(patronymic)));
		assertThat(new Elements().new PersonInfo().gender_Select().getFirstSelectedOption().getText(), is(equalTo(gender)));
		assertThat(new Elements().new PersonInfo().citizenship_TextInput().getAttribute("value"), is(equalTo(citizenship)));
		assertThat(new Elements().new PersonInfo().bornDate_TextInput().getAttribute("value"), is(equalTo(bornDate)));
		assertThat(new Elements().new PersonInfo().phone_TextInput().getAttribute("value"), is(equalTo(phone)));
		assertThat(new Elements().new PersonInfo().cellPhone_TextInput().getAttribute("value"), is(equalTo(сellPhone)));
		assertThat(new Elements().new PersonInfo().email_TextInput().getAttribute("value"), is(equalTo(email)));
		
		// Проверка блока 'Документ'
		assertThat(new Elements().new DocInfo().docKind_Select().getFirstSelectedOption().getText(), is(equalTo(docType)));
		assertThat(new Elements().new DocInfo().serial_TextInput().getAttribute("value"), is(equalTo(serial)));
		assertThat(new Elements().new DocInfo().number_TextInput().getAttribute("value"), is(equalTo(number)));
		assertThat(new Elements().new DocInfo().giveDate_TextInput().getAttribute("value"), is(equalTo(giveDate)));
		assertThat(new Elements().new DocInfo().organization_TextInput().getAttribute("value"), is(equalTo(organization)));
		
		// Проверка блока 'Місце народження'
		assertThat(new Elements().new BornPlaceInfo().country_Select().getFirstSelectedOption().getText(), is(equalTo(country)));
		assertThat(new Elements().new BornPlaceInfo().place_TextInput().getAttribute("value"), is(equalTo(place)));
		
		// Проверка блока 'Місце реєстрації'
		assertThat(new Elements().new ResidenceInfo().country_TextInput().getAttribute("value"), is(equalTo(country)));
		assertThat(new Elements().new ResidenceInfo().region_TextInput().getAttribute("value"), is(equalTo(region)));
		assertThat(new Elements().new ResidenceInfo().district_TextInput().getAttribute("value"), is(equalTo(district)));
		assertThat(new Elements().new ResidenceInfo().village_TextInput().getAttribute("value"), is(equalTo(village)));
		assertThat(new Elements().new ResidenceInfo().street_TextInput().getAttribute("value"), is(equalTo(street)));
		assertThat(new Elements().new ResidenceInfo().house_TextInput().getAttribute("value"), is(equalTo(house)));
	}
	
	public Individuals_Page card_Close()
	{
		// Закрытие редактирования карточки
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
		return new Individuals_Page(driver).waitUntilAvailable();
	}
	
	public void secondCard_Fill()
	{
		//region Variables	
		/*____________________________ 'Особа' _________________________________*/
		String idCode = new Elements().new PersonInfo().new Values().idCode;
		String surname = new Elements().new PersonInfo().new Values().surname + "2";
		String name = new Elements().new PersonInfo().new Values().name + "2";
		String gender = new Elements().new PersonInfo().new Values().gender;
		String citizenship = new Elements().new PersonInfo().new Values().citizenship;
		String bornDate = new Elements().new PersonInfo().new Values().second_bornDate;
		/*_______________________________________________________________________*/
		
		/*____________________________ 'Документ' _________________________________*/
		String docType = new Elements().new DocInfo().new Values().docType;
		/*_______________________________________________________________________*/
		
		/*_________________________ 'Місце народження' __________________________*/
		String country = new Elements().new BornPlaceInfo().new Values().country;
		String place = new Elements().new BornPlaceInfo().new Values().place;
		/*_______________________________________________________________________*/
		
		/*_________________________ 'Місце реєстрації' __________________________*/
		String street = new Elements().new ResidenceInfo().new Values().street;
		String house = new Elements().new ResidenceInfo().new Values().house;
		/*_______________________________________________________________________*/
		//endregion
		
		// Заполнение полей блока 'Особа'
		new Elements().new PersonInfo().idCode_TextInput().inputText(idCode);
		new Elements().new PersonInfo().surname_TextInput().inputText(surname);
		new Elements().new PersonInfo().name_TextInput().inputText(name);
		new Elements().new PersonInfo().gender_Select().selectByVisibleText(gender);
		new Elements().new PersonInfo().citizenship_TextInput().inputText(citizenship);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new PersonInfo().citizenship_TextInput(), 1);
		new Elements().new PersonInfo().bornDate_TextInput().click();
		new Elements().new PersonInfo().bornDate_TextInput().inputText(bornDate);
		
		// Заполнение полей блока 'Документ'
		new Elements().new DocInfo().docKind_Select().selectByVisibleText(docType);
		
		// Заполнение полей блока 'Місце народження'
		new Elements().new BornPlaceInfo().country_Select().selectByVisibleText(country);
		new Elements().new BornPlaceInfo().place_TextInput().inputText(place);
		
		// Заполнение полей блока 'Місце реєстрації'
		new Elements().new ResidenceInfo().street_TextInput().inputText(street);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new ResidenceInfo().street_TextInput(), 1);
		new Elements().new ResidenceInfo().house_TextInput().inputText(house);	
	}
	
	public void copyCardAdd_Inability_Check()
	{
		//region Variables			
		Custom info_PopUp = new Elements().new ResidenceInfo().info_PopUp(driver);
		String errorMessage = "Збереження неможливе, оскільки вже існує заявник - фізична особа з таким ІДН";
		String second_idCode = new Elements().new PersonInfo().new Values().second_idCode;
		//endregion
		
		// Нажать на кнопку 'Сохранить'
		new Elements().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		info_PopUp.waitUntilAvailable();

		// Проверка сообщения
		assertThat(info_PopUp.getText(), is(equalTo(errorMessage)));
		
		// Закрыть
		new Elements().new ResidenceInfo().close_Button(driver).click();
		new Elements().close_Button(driver).waitUntilAvailable();
		
		// Сменить ИНН
		new Elements().new PersonInfo().idCode_TextInput().clear();
		new Elements().new PersonInfo().idCode_TextInput().inputText(second_idCode);
	}
	
	public void placeValue_Change()
	{	
		new Elements().new BornPlaceInfo().place_TextInput().click();
		sendKeys("2");
	}
	
	public Individuals_FilesPage goTo_Files_Page()
	{
		//region Variables	
		WebElement insetLink = new Elements().inset_Link(driver, "2");
		//endregion
				
	 	// Клик по вкладке
		insetLink.click();
		
		return new Individuals_FilesPage(driver).waitUntilAvailable();
	}
	
	// Проверка хэдера карточки + проверка ИНН
	public void cardHeader_Check()
	{
		//region Variables	
		String expected_Header = "Заявник ФО";
		String expected_idCode = new Elements().new PersonInfo().new Values().idCode;
		//endregion
		
		// Проверка хэдера
		new CommonActions().cardHeader_Check(driver, expected_Header);
		
		// Проверка ИНН
		assertThat(new Elements().new PersonInfo().idCode_Text().getText(), is(equalTo(expected_idCode)));
	}
	
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	
	protected class Elements extends CommonElements.Card_Elements.General_Elements
	{
		// Блок 'Особа'
		protected class PersonInfo extends CommonElements.Card_Elements.Pop_Ups
		{
			// 'Громадянство'
			private Button countryAdd_Button() 			{ return new Button(driver,By.id("705_btn_add")); } 
			
			// 'ІДН'
			private TextInput idCode_TextInput() 		{ return new TextInput(driver, By.id("707")); } 
			
			// 'ІДН' на просмотровой страничке
			private TextInput idCode_Text() 			{ return new TextInput(driver, By.xpath("//div[@class='break-word']/div[2]")); } 
			
			// 'Прізвище'
			private TextInput surname_TextInput() 		{ return new TextInput(driver, By.id("700")); } 
			
			// 'Ім'я'
			private TextInput name_TextInput() 			{ return new TextInput(driver,By.id("701")); } 
			
			// 'По-батькові'
			private TextInput patronymic_TextInput() 	{ return new TextInput(driver,By.id("702")); } 
			
			// 'Стать'
			private Select gender_Select() 				{ return new Select(driver.findElement(By.id("704"))); } 
			
			// 'Громадянство'
			private TextInput citizenship_TextInput() 	{ return new TextInput(driver,By.id("705_auto")); } 
			
			// 'Дата народження'
			private TextInput bornDate_TextInput() 		{ return new TextInput(driver,By.id("706")); } 
			
			// 'Телефон'
			private TextInput phone_TextInput() 		{ return new TextInput(driver,By.id("709")); } 
			
			// 'Мобільний телефон'
			private TextInput cellPhone_TextInput() 	{ return new TextInput(driver,By.id("708")); } 
			
			// 'Email'
			private TextInput email_TextInput() 		{ return new TextInput(driver,By.id("710")); } 
			
			// Используемые значения
			protected class Values 
			{
				protected String idCode = "0007771110";			// 'ІДН'
				protected String second_idCode = "7770001110";	// 'ІДН'
				protected String surname = "Автоматизатор";		// 'Прізвище'				
				protected String name = "Петро";				// 'Ім'я'				
				protected String patronymic = "Васильович";		// 'По-батькові'				
				private String gender = "Чоловіча";				// 'Стать' 				
				protected String citizenship = new ResidenceInfo().new Values().country;	// 'Громадянство'				
				protected String bornDate = "01.01.1975";		// 'Дата народження'	
				protected String second_bornDate = "02.01.1975";	// 'Дата народження'	
				private String phone = "(000)1112233";			// 'Телефон'				
				private String сellPhone = "(099)3332211";		// 'Мобільний телефон'				
				private String email = "autmtn@email.com";		// 'Email'
			}
		}
		
		// Блок 'Документ'
		private class DocInfo
		{
			// 'Вид документа'
			private Select docKind_Select() 				{ return new Select(driver.findElement(By.id("711"))); } 
			
			// 'Серія'
			private TextInput serial_TextInput() 			{ return new TextInput(driver, By.id("712")); } 
			
			// 'Номер'
			private TextInput number_TextInput() 			{ return new TextInput(driver, By.id("713")); } 
			
			// 'Дата видачі'
			private TextInput giveDate_TextInput() 			{ return new TextInput(driver, By.id("714")); } 
			
			// 'ІДН'
			private TextInput organization_TextInput() 		{ return new TextInput(driver, By.id("715")); } 
			
			// Используемые значения
			private class Values 
			{
				private String docType = "Паспорт громадянина України";		// 'Вид документа'
				private String serial = "СС";								// 'Серія'
				private String number = "222555";							// 'Номер'
				private String giveDate = "01.01.1980";						// 'Дата видачі'
				private String organization = "Київським ДМС";				// 'ІДН'
			}
		}
		
		// Блок 'Місце народження'
		protected class BornPlaceInfo
		{
			// 'Країна народження'
			private Select country_Select() 				{ return new Select(driver.findElement(By.id("716"))); } 
			
			// 'Місце народження'
			private TextInput place_TextInput() 			{ return new TextInput(driver, By.id("717")); } 
			
			// Используемые значения
			protected class Values 
			{
				private String country = new ResidenceInfo().new Values().country;	// 'Країна народження'
				protected String place = "вул. Тестова, д. 11, кв. 1";				// 'Місце народження'
			}
		}
		
		// Блок 'Місце реєстрації'
		private class ResidenceInfo extends CommonElements.Card_Elements.Pop_Ups
		{
			// 'Країна'
			private TextInput country_TextInput() 			{ return new TextInput(driver, By.id("718_auto")); } 
			
			// 'Область'
			private TextInput region_TextInput() 			{ return new TextInput(driver, By.id("719_auto")); } 
			
			// 'Район(місто)'
			private TextInput district_TextInput() 			{ return new TextInput(driver, By.id("720_auto")); } 
			
			// 'Селище'
			private TextInput village_TextInput() 			{ return new TextInput(driver, By.id("721_auto")); } 
			
			// 'Вулиця'
			private TextInput street_TextInput() 			{ return new TextInput(driver, By.id("722_auto")); } 
			
			// 'Помешкання'
			private TextInput house_TextInput() 			{ return new TextInput(driver, By.id("723")); } 
			
			private class Values
			{
				String country = "Автоматизація";			// Країна
				String region = "Selenium";					// 'Область'
				String district = "Java";					// 'Район(місто)'
				String village = "Maven";					// 'Селище'
				String street = "TestNG";					// 'Вулиця'
				String house = "1";							// 'Помешкання'
			}
		}
	}
}
