package com.docsflow.core.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.docsflow.core.web.elements.Button;
import com.docsflow.core.web.elements.Link;
import com.docsflow.core.web.elements.TextInput;
import com.docsflow.core.web.pages.other.LogInPage;
import com.docsflow.core.web.pages.other.MainPage;

public class CommonActions 
{
	public void simpleWait(int Seconds)
	{
		try
		{
			Thread.sleep(Seconds * 1000);
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
	
	public LogInPage userOut(WebDriver driver)
	{
		new Elements().getUserOutButton(driver).click();
		return new LogInPage(driver).waitUntilAvailable();
	}
	
	public MainPage backToMainPage(WebDriver driver)
	{
		new Elements().getBackToMainLink(driver).click();
		return new MainPage(driver).waitUntilAvailable();
	}
	
	public void autoCompleteValue_Set(WebDriver driver, TextInput element)
	{
		try
		{
			element.click();
			Thread.sleep(1000);
			Actions action = new Actions(driver);
			action.sendKeys(Keys.DOWN).build().perform();
			Thread.sleep(1000);
			action.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Навигация по менюшке
	public void menu_Handler(WebDriver driver, int mainMenu_Num,  int subMenu_Num)
	{
		// Определение всех пунктов главного меню
		List<WebElement> main_Items = new Elements().getMainMenu_Div(driver).findElements(By.xpath("//div[contains(@class, 'menu-item left')]"));
		
		// Определение нужного элемента главного меня
		WebElement main_Item = main_Items.get(mainMenu_Num - 1);
	
		// Клик по главному пункту меню
		main_Item.click();
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// Продолжить только если есть субменюшки
		if (subMenu_Num != 0)
		{
			// Выбрать <li> субменю
			List<WebElement> sub_Items = main_Item.findElement(By.className("sub-menu-item")).findElements(By.tagName("li"));
			
			//// Клик по суб меню
			////sub_Items.get(subMenu_Num - 1).click(); // Не работает, хз
			
			// Переход по URL, который лежит в пункте меню
			String page_Url = sub_Items.get(subMenu_Num - 1).findElement(By.tagName("a")).getAttribute("href");
			driver.get(page_Url);
		}
	}
	
	// Навигация по дереву подразделений
		public void tree_Handler(WebDriver driver, int[] tree_Path)  // где tree_Path - массив номеров элементов в дереве по которым нужно пройти.
		{														     // соответственно количество элементов в массиве = глубина иерархии
			// Билдер
			Actions builder = new Actions(driver);
			
			// Опускаемся по иерархии tree_Path, кликая по элементам дерева в соответствии с tree_Path
			for (int i = 0; i < tree_Path.length; i++)
			{				
				// Клик ARROW_DOWN
				for(int j = 0; j < tree_Path[i]; j++)
				{
					builder.sendKeys(Keys.ARROW_DOWN).build().perform();
					simpleWait(1);
				}
				
				// Как только уровень иерархии предположительно закончен - ARROW_RIGHT
				builder.sendKeys(Keys.ARROW_RIGHT).build().perform();
				simpleWait(1);
			}
			
			// Выбрать подразделение
			builder.sendKeys(Keys.ENTER).build().perform();
			simpleWait(1);
		}
	
	/*______________________________ Элементы ________________________________*/
	
	private class Elements 
	{
		// Ссылка выхода из системы
		private Button getUserOutButton(WebDriver driver)
		{
			return new Button(driver, By.xpath("//a[@href='/Account/LogOff']"));
		}
		
		// Ссылка возврата на главную страничку(В header(е))
		private Link getBackToMainLink(WebDriver driver)
		{
			return new Link(driver, By.className("header_link"));
		}
		
		// <div> в котором лежит главная меню
		private WebElement getMainMenu_Div(WebDriver driver)
		{
			return driver.findElement(By.className("main-menu"));
		}
	}
}
