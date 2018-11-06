package com.framework.core;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Page {
	private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);
	private final WebDriver driver;

	protected Page() {
		this.driver = DriverManager.getInstance();
	}

	protected WebDriver driver() {
		return this.driver;
	}

	abstract protected void initElements();


	protected boolean waitForElement(WebElement element) {
		LOGGER.info("Waiting for element (optional)", true);
		try {
			new WebDriverWait(DriverManager.getInstance(), 30, 1)
					.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (final Exception e) {
			LOGGER.info("Element not found, Skipping element check as it was optional", true);
			return false;
		}
	}

	protected void click(final WebElement element) {
		LOGGER.info("Clicking on element", true);
		element.click();
		LOGGER.info("Click done", true);
	}

	protected void type(WebElement element, final String textToType) {
		LOGGER.info("Typing [" + textToType + "]", true);
		// Clear out and type
		element.clear();
		this.wait(2); // delay so first letter isn't gobbled up
		element.sendKeys(textToType);
		LOGGER.info("Typing Done", true);
	}

	protected String getText(WebElement element) {
		LOGGER.info("Reading text of element", true);
		try {
			String textFound = element.getText();
			LOGGER.info("Text found: [" + textFound + "]");
			return textFound;
		} catch (final TimeoutException e) {
			LOGGER.info("Element not found: " + element, true);
			throw new NoSuchElementException(e.getMessage());
		}
	}

	protected boolean isDisplayed(final WebElement element) {
		boolean isVisible;
		try {
			isVisible = element.isDisplayed();
		} catch (final WebDriverException e) {
			isVisible = false;
		}
		LOGGER.info("Element visible: " + isVisible);
		return isVisible;
	}

	protected void wait(final int seconds) {
		BaseTest.wait(seconds);
	}
}
