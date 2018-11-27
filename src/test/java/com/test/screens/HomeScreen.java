package com.test.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.framework.core.DriverManager;
import com.framework.core.Page;

public class HomeScreen extends Page{

	public HomeScreen() {
		this.elements = new Elements();
		PageFactory.initElements(DriverManager.getInstance(), this.elements);
	}

	private Elements elements;

	private static class Elements {
		@FindBy(id = "com.weather.Weather.qa:id/temperature")
		private WebElement txtTemperature;

		@FindBy(id = "com.weather.Weather.qa:id/temperature_icon")
		private WebElement iconWeatherCondition;

		@FindBy(id = "com.weather.Weather.qa:id/txt_location_name")
		private WebElement txtLocationTitle;

		@FindBy(id = "com.weather.Weather.qa:id/narrative")
		private WebElement txtWeatherPhrase;

		@FindBy(id = "com.weather.Weather.qa:id/hilo")
		private WebElement txtTemperatureHiLo;

		@FindBy(id = "com.weather.Weather.qa:id/message")
		private WebElement searchToAddLocTextLabel;

		@FindBy(id = "com.weather.Weather.qa:id/search_icon")
		private WebElement btnSearch;

		@FindBy(id = "com.weather.Weather.qa:id/search_text")
		WebElement inputSearchCity;
	}
	
	public boolean isDisplayedTemp() {
		return this.elements.txtTemperature.isDisplayed();
	}
	
	public boolean isDisplayedSearchCity() {
		return this.elements.inputSearchCity.isDisplayed();
	}
	
	
	/**
	 * Waits for weather data to load. Used when slow connection is simulated.
	 */
	public boolean waitForWeatherData(int timeoutInSeconds) {
		this.waitForElementOptional(this.elements.txtTemperature, 120000);
		boolean tempFound = false;
		final long timeout = System.nanoTime() + TimeUnit.SECONDS.toNanos(timeoutInSeconds);
		String currentTemp = this.getText(this.elements.txtTemperature);
		while (!tempFound && (timeout > System.nanoTime())) {
			if (!currentTemp.isEmpty()) {
				tempFound = true;
			} else {
				wait(5);
				currentTemp = this.elements.txtTemperature.getText();
			}
		}
		return tempFound;
	}

	@Override
	protected void initElements() {
		// TODO Auto-generated method stub
		
	}
}
