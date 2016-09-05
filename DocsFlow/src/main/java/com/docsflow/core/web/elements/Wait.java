package com.docsflow.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.docsflow.core.web.WebComponent;

public class Wait extends WebComponent<Wait>
{
	public Wait(WebDriver driver, By findByMethod) 
	{
		super(driver, findByMethod);
	}
}
