package com.framework.core;

import java.lang.reflect.Proxy;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.apps_auto.mobile.framework.config.Constants;

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
	
	/**
	 * Wait for element to be present. Present is defined as on the current
	 * page, but may or may not be visible.
	 *
	 * @param locator
	 * @return
	 */
	protected WebElement waitForElementPresent(final By locator) {
		LOGGER.info("Waiting for presence of element: " + locator, true);
		final WebElement ele = new WebDriverWait(DriverManager.getInstance(), Constants.ELEMENT_WAIT, Constants.POLLING_RATE)
				.until(ExpectedConditions.presenceOfElementLocated(locator));
		LOGGER.info("Element found");
		return ele;
	}
	
	/**
	 * Waits for the presence of the given Proxy element on the page
	 */
	protected WebElement waitForElementPresent(final WebElement element) {
		WebElement convertedElement = null;
		if (element instanceof Proxy) {
			LOGGER.info("Waiting for presence of proxy element", true);
			try {
				final WebDriverWait wait = new WebDriverWait(DriverManager.getInstance(), Constants.ELEMENT_WAIT, Constants.POLLING_RATE);
				convertedElement = wait.until(new ExpectedCondition<WebElement>() {
					@Override
					public WebElement apply(WebDriver driver) {
						try {
							// If we can pull any info out the findElement() worked for the proxy element
							return ((WrapsElement)element).getWrappedElement();
						} catch (final WebDriverException e) {
							// do nothing
						}
						return null;
					}
				});
			} catch (final TimeoutException e) {
				throw new NoSuchElementException("Proxy element: " + element + " not found within time limit");
			}
			return convertedElement;
		} else {
			return element;
		}
	}
	
	/**
	 * Wait for an element to be displayed, but do not fail test if it is not
	 * found. Often used in tandem with <code>waitForElementNotVisible</code> to
	 * get around an element that is shown for a small period of time, like the
	 * weather glance notification
	 *
	 * @param element
	 *            Element to wait for, but not fail test if it isn't found
	 *            within time limit
	 * @param timeoutOverride
	 *            Override default timeout
	 */
	protected boolean waitForElementOptional(final WebElement element, final long timeoutOverride) {
		LOGGER.info("Waiting for element(optional)", true);
		try {
			new WebDriverWait(DriverManager.getInstance(), timeoutOverride, Constants.POLLING_RATE)
					.until(ExpectedConditions.visibilityOf(element));
			LOGGER.info("Element found: " + this.getLocatorFromElement(element), true);
			return true;
		} catch (final Exception e) {
			// Do nothing if we dont find element
			LOGGER.info("Element not found, Skipping element check as it was optional", true);
			return false;
		}
	}
	
	/**
	 * Take an element and get a readable form for logging purposes
	 */
	private String getLocatorFromElement(WebElement element) {
		String elementString = element.toString();
		// Extract out the readable locator from already found element, or just return the proxy locator string if not
		if (!elementString.contains("Proxy element for") && elementString.contains("->")) {
			String readableLocator = element.toString().split("->")[1];
			readableLocator = readableLocator.substring(1, readableLocator.indexOf(']'));
			return readableLocator;
		} else {
			return elementString;
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
