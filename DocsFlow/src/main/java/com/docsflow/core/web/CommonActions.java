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
	
	// ��������� �� �������
	public void menu_Handler(WebDriver driver, int mainMenu_Num,  int subMenu_Num)
	{
		// ����������� ���� ������� �������� ����
		List<WebElement> main_Items = new Elements().getMainMenu_Div(driver).findElements(By.xpath("//div[contains(@class, 'menu-item left')]"));
		
		// ����������� ������� �������� �������� ����
		WebElement main_Item = main_Items.get(mainMenu_Num - 1);
	
		// ���� �� �������� ������ ����
		main_Item.click();
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		// ���������� ������ ���� ���� ����������
		if (subMenu_Num != 0)
		{
			// ������� <li> �������
			List<WebElement> sub_Items = main_Item.findElement(By.className("sub-menu-item")).findElements(By.tagName("li"));
			
			//// ���� �� ��� ����
			////sub_Items.get(subMenu_Num - 1).click(); // �� ��������, ��
			
			// ������� �� URL, ������� ����� � ������ ����
			String page_Url = sub_Items.get(subMenu_Num - 1).findElement(By.tagName("a")).getAttribute("href");
			driver.get(page_Url);
		}
	}
	
	// ��������� �� ������ �������������
		public void tree_Handler(WebDriver driver, int[] tree_Path)  // ��� tree_Path - ������ ������� ��������� � ������ �� ������� ����� ������.
		{														     // �������������� ���������� ��������� � ������� = ������� ��������
			// ������
			Actions builder = new Actions(driver);
			
			// ���������� �� �������� tree_Path, ������ �� ��������� ������ � ������������ � tree_Path
			for (int i = 0; i < tree_Path.length; i++)
			{				
				// ���� ARROW_DOWN
				for(int j = 0; j < tree_Path[i]; j++)
				{
					builder.sendKeys(Keys.ARROW_DOWN).build().perform();
					simpleWait(1);
				}
				
				// ��� ������ ������� �������� ���������������� �������� - ARROW_RIGHT
				builder.sendKeys(Keys.ARROW_RIGHT).build().perform();
				simpleWait(1);
			}
			
			// ������� �������������
			builder.sendKeys(Keys.ENTER).build().perform();
			simpleWait(1);
		}
	
	/*______________________________ �������� ________________________________*/
	
	private class Elements 
	{
		// ������ ������ �� �������
		private Button getUserOutButton(WebDriver driver)
		{
			return new Button(driver, By.xpath("//a[@href='/Account/LogOff']"));
		}
		
		// ������ �������� �� ������� ���������(� header(�))
		private Link getBackToMainLink(WebDriver driver)
		{
			return new Link(driver, By.className("header_link"));
		}
		
		// <div> � ������� ����� ������� ����
		private WebElement getMainMenu_Div(WebDriver driver)
		{
			return driver.findElement(By.className("main-menu"));
		}
	}
}
