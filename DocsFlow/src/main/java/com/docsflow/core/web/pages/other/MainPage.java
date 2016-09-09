package com.docsflow.core.web.pages.other;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.docsflow.core.web.CommonActions;
import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.Custom;
import com.docsflow.core.web.elements.Link;
import com.docsflow.core.web.pages.administration.AuditPage;
import com.docsflow.core.web.pages.administration.UsersPage;
import com.docsflow.core.web.pages.docs.IncomingDocs_Page;
import com.docsflow.core.web.pages.docs.IncomingDocs_FilesPage;
import com.docsflow.core.web.pages.docs.IncomingDocs_PerformControlPage;
import com.docsflow.core.web.pages.docs.IncomingDocs_RegistrationPage;
import com.docsflow.core.web.pages.registers.Individuals_Page;
import com.docsflow.core.web.elements.Text;

public class MainPage extends WebPage<MainPage> 
{
	private static final String PAGE_URL = BASE_URL;
	
	public MainPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public MainPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getExit_Link().isAvailable()
			&& getInfoDiv().isAvailable();
	}
	
	public LogInPage redirectToLogInPage()
	{
		load();
		return new LogInPage(driver).waitUntilAvailable();
	}
	
/*	public void waitFor_PageReady()
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("overflow-auto")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.id<locator>));
	}*/
	
	// Проверка имени пользователя
	public void userNameCheck(String expectedUserName)
	{
		// Найти текущее имя пользователя
		String actualUserName = getUserName().getText();
		
		// Проверка равенства ожидаемого значения и реального значения
		assertThat(actualUserName, is(equalTo(expectedUserName)));
	}
	
	// Навигация по меню
	public class goTo
	{
		// Блок 'Документи'
		public class DocsBlock
		{						
			// Пункт меню 'Вхідні документи'
			public IncomingDocs_Page IncomingDocs_Page()
			{    
				// Использовать менюшку
				new CommonActions().menu_Handler(driver, 1, 1);
				
			    return new IncomingDocs_Page(driver).waitUntilAvailable();			   
			}
			
			// Пункт меню 'Вихідні документи'
			public IncomingDocs_Page OutgoingDocs_Page()
			{    
				// Использовать менюшку
				new CommonActions().menu_Handler(driver, 1, 2);
		        
			    return new IncomingDocs_Page(driver).waitUntilAvailable();			   
			}
		}
		
		public class CnapBlock
		{
			// Пункт меню 'Реестр ФО'
			public Individuals_Page individuals_Page()
			{    
				// Использовать менюшку
				new CommonActions().menu_Handler(driver, 2, 2);
				
			    return new Individuals_Page(driver).waitUntilAvailable();			   
			}
		}
		
		// Страничка 'НДІ'
		public Handbooks_Page handbooksPage()
		{
			// Использовать менюшку
			new CommonActions().menu_Handler(driver, 3, 0);
			
			return new Handbooks_Page(driver).waitUntilAvailable();
		}
		
		// Администрирования
		public class AdministrationBlock
		{
			// Аудит
			public AuditPage Audit_Page()
			{    
				// Использовать менюшку
				new CommonActions().menu_Handler(driver, 4, 1);
		    
			    return new AuditPage(driver).waitUntilAvailable();
			}
			
			// Список пользователей
			public UsersPage Users_Page()
			{    
				// Использовать менюшку
				new CommonActions().menu_Handler(driver, 4, 2);
		    
			    return new UsersPage(driver).waitUntilAvailable();
			}
			
			// Роли
			public Roles_Page Roles_Page()
			{    
				// Использовать менюшку
				new CommonActions().menu_Handler(driver, 4, 3);
		    
			    return new Roles_Page(driver).waitUntilAvailable();
			}
		}
		
		// ОШС
		public Stuff_Page stuffPage()
		{
			// Использовать менюшку
			new CommonActions().menu_Handler(driver, 5, 0);
			
			return new Stuff_Page(driver).waitUntilAvailable();
		}
		
		// Страничка 'Звітність'
		public Reporting_Page reportingPage()
		{
			// Использовать менюшку
			new CommonActions().menu_Handler(driver, 6, 0);
			
			return new Reporting_Page(driver).waitUntilAvailable();
		}
		

		
		
		
		
		
		// Для тестирования/дебагинга
		public IncomingDocs_RegistrationPage direct_Redirect()
		{
			driver.get(BASE_URL + "/CommonDocs/Docs/View/55/7086");
			new CommonActions().simpleWait(3);
			
			return new IncomingDocs_RegistrationPage(driver).waitUntilAvailable();
		}
	}
	
	
	/*__________________________________________________ Элементы _______________________________________________________*/
	
	private Custom getInfoDiv()
	{
		return new Custom(driver, By.className("content"));
	}

	private Link getExit_Link()
	{
		return new Link(driver, By.xpath("//a[@href='/Account/LogOff']"));
	}
	
	private Text getUserName()
	{
		return new Text(driver, By.xpath("//*[@class='log']/span"));
	}	
}

