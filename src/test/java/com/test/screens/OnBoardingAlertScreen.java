package com.test.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.framework.core.Page;

public class OnBoardingAlertScreen extends Page{
	
	public OnBoardingAlertScreen() {
		this.elements = new Elements();
		PageFactory.initElements(com.framework.core.DriverManager.getInstance(), this.elements);
	}

	private Elements elements;

	private static class Elements {
		@FindBy(id = "com.weather.Weather.qa:id/ok_button")
		private WebElement gotIt;
	}

	public void clickGotIt() {
		this.waitForElementPresent(this.elements.gotIt).click();
	}

	@Override
	protected void initElements() {
		// TODO Auto-generated method stub
		
	}
}
