package com.docsflow.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.docsflow.core.web.WebPage;
import com.docsflow.core.web.elements.Button;

public class Handbooks_Page extends WebPage<Handbooks_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Dictionaries/DictionariesUDB/List";
	
	public Handbooks_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Handbooks_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAdd_Button().isAvailable();
	}

	
	
	/*__________________________________________________ Ёлементы _______________________________________________________*/
	
	private Button getAdd_Button()
	{
		return new Button(driver, By.id("add_value_dict"));
	}
}
