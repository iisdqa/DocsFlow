package com.docsflow.core.web.pages.registers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.CommonElements;
import com.docsflow.core.web.CustomMethods;
import com.docsflow.core.web.WebPage;

public class Individuals_Page extends WebPage<Individuals_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Dictionaries/DictionariesUDB/List";
	
	public Individuals_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Individuals_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Grid().add_Button(driver).isAvailable();
	}

	/*__________________________________________________ Actions ________________________________________________________*/
	
	// Ожидание прогрузки грида
	public void gridDownload_Wait()
	{
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
		new CommonActions().simpleWait(1);
	}
	
	// Перейти к добавлению карточки документа
	public Individuals_RegPage cardAdd_Call()
	{
		// Нажать на кнопку добавления
		new Elements().new Grid().add_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Individuals_RegPage(driver).waitUntilAvailable();	
	}
	
	// Проверка подсвечивания записи в гриде
	public void rowHighlighted_Check()
	{
		//region Variables	
		String inn = new Individuals_RegPage(driver).new Elements().new PersonInfo().new Values().idCode;
		WebElement inn_Cell = new Elements().new Grid().inn_Cell(inn);
		WebElement row = inn_Cell.findElement(By.xpath(".."));
		//endregion
		
		// Проверка, что запись подсвечена
		assertThat(row.getAttribute("aria-selected"), is(equalTo("true")));
		assertThat(row.getAttribute("class"), is(containsString("ui-state-highlight")));
	}
	
	// Поиск карточки
		public void card_Search()
		{
			//region Variables	
			String fieldName = new Elements().new Filtration_Accordion().new Values().fieldName;
			String matchType = new Elements().new Filtration_Accordion().new Values().matchType;
			String value = new Elements().new Filtration_Accordion().new Values().value;
			//endregion
			
			// Фильтрация
			new CommonActions().grid_Filtration(driver, fieldName, matchType, value);
			
			// Ожидание прогрузки грида
			waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
		}
		
		// Проверка найденной карточки
		public void card_Check(String checkType)
		{
			//region Variables
			WebElement grid = new Elements().new Grid().grid_Body(driver);
			String surname = new Individuals_RegPage(driver).new Elements().new PersonInfo().new Values().surname;
			String name = new Individuals_RegPage(driver).new Elements().new PersonInfo().new Values().name;
			String patronymic = new Individuals_RegPage(driver).new Elements().new PersonInfo().new Values().patronymic;
			String bornDate = new Individuals_RegPage(driver).new Elements().new PersonInfo().new Values().bornDate;
			String inn = new Elements().new Filtration_Accordion().new Values().value;
			String bornPlace = new Individuals_RegPage(driver).new Elements().new BornPlaceInfo().new Values().place;
			if(checkType == "edit") bornPlace = new Individuals_RegPage(driver).new Elements().new BornPlaceInfo().new Values().place + "2";
			//endregion
			
			// Определение ожидаемых значений
			String[][] ExpectedValues = new String [1][];
			ExpectedValues[0] = new String[] {"",
											  "",
											  "",
											  surname + " " + name + " " + patronymic, 
											  bornDate, 
											  inn, 
											  bornPlace};
			
			// Вытянуть последнее значения из грида
			String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
			
			// Проверка значений грида
			new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		}
	
		
		// Переход к редактированию карточки
		public Individuals_RegPage card_Edit()
		{
			// Нажать на кнопку редактирования
			new Elements().new Grid().edit_Button(driver).click();
			new CommonActions().simpleWait(2);
			
			return new Individuals_RegPage(driver).waitUntilAvailable();	
		}
		
		// Переход к просмотру карточки
		public Individuals_RegPage card_View()
		{
			// Нажать на кнопку просмотра
			new Elements().new Grid().view_Button(driver).click();
			new CommonActions().simpleWait(2);
			
			return new Individuals_RegPage(driver).waitUntilAvailable();	
		}
		
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	
	private class Elements
	{		
		// Грид
		private class Grid extends CommonElements.BaseGrid_Elements
		{
			// 'Дата регистрации'
			private WebElement inn_Cell(String inn)   			{ return driver.findElement(By.xpath("//td[@aria-describedby='grid_DVL_TEXT1' and @title='" + inn + "']")); }	
		}
		
		// Унаследовать элементы аккордеона фильтрации
		private class Filtration_Accordion extends CommonElements.FiltrationControl_Elements 
		{
			// Используемые значения
			private class Values
			{
				private String fieldName = "ІДН";
				private String matchType = "Дорівнює";
				private String value = new Individuals_RegPage(driver).new Elements().new PersonInfo().new Values().idCode;
			}
		}	
	}
	
	/*___________________________________________________________________________________________________________________*/
}
