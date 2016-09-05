package com.docsflow.core.web;

import org.openqa.selenium.WebDriver;

import com.docsflow.core.EnvironmentConfiguration;
import com.docsflow.core.YamlEnvironment;
import com.docsflow.core.web.pages.other.MainPage;

public abstract class WebPage<T extends WebPage<T>> extends Component<T>
{
	private static final EnvironmentConfiguration CONFIG = EnvironmentConfiguration.getConfig();
	private static final YamlEnvironment ENVIRONMENT = CONFIG.getEnvironmentSettings();
	protected static final String BASE_URL = ENVIRONMENT.scheme + "://" + 
			       						     ENVIRONMENT.host + ":" + 
			       						     ENVIRONMENT.port;
	protected static final String BASE_DIR = System.getProperties().get("basedir").toString();
	
	public WebPage(WebDriver driver)
	{
		super(driver);
	}
	
	public abstract T load();
	
	public T loadAndWaitUntilAvailable()
	{
		load();
		return waitUntilAvailable();
	}
	
	// ������� �� ������� ���������
	public MainPage backToMainPage()
	{
		return new CommonActions().backToMainPage(driver);
	}
}
