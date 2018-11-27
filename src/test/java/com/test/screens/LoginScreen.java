package com.test.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.framework.core.DriverManager;
import com.framework.core.Page;

public class LoginScreen extends Page {

	private Elements elements;

	private static class Elements {
		@FindBy(id = "com.Slack:id/sign_in_button")
		WebElement button_SignIn;

		@FindBy(id = "com.Slack:id/sign_in_manually_button")
		WebElement button_SignIn_Manually;

		@FindBy(id = "com.Slack:id/team_url_edit_text")
		WebElement input_TeamUrl;

		@FindBy(id = "com.Slack:id/next_button_url_entry")
		WebElement button_NextTeamUrl;
		
		@FindBy(id = "com.Slack:id/email_edit_text")
		WebElement input_Email;

		@FindBy(id = "com.Slack:id/next_button")
		WebElement button_NextEmail;

		@FindBy(id = "com.Slack:id/password_edit_text")
		WebElement input_Password;

		@FindBy(id = "com.Slack:id/next_button_password")
		WebElement button_NextPassword;
		
		@FindBy(id = "com.Slack:id/team_name")
		WebElement text_TeamName;

		@FindBy(id = "com.Slack:id/description")
		WebElement desc_WelcomeText;
	}

	@Override
	protected void initElements() {
		this.elements = new Elements();
		PageFactory.initElements(DriverManager.getInstance(), this.elements);		
	}
	
	public LoginScreen() {
		this.initElements();
	}

	public LoginScreen clickSignInButtion() {
		this.waitForElement(this.elements.button_SignIn);
		this.click(this.elements.button_SignIn);
		return new LoginScreen();
	}
	
	public LoginScreen clickSignInManuallyButton() {
		this.waitForElement(this.elements.button_SignIn_Manually);
		this.click(this.elements.button_SignIn_Manually);
		return new LoginScreen();
	}
	
	public LoginScreen enterTeamUrl(String teamUrl) {
		this.waitForElement(this.elements.input_TeamUrl);
		this.type(this.elements.input_TeamUrl, teamUrl);
		return new LoginScreen();
	}
	
	public LoginScreen tapNextButtonFromEnterTeamUrlScreen() {
		try {
			this.click(this.elements.button_NextTeamUrl);
		} catch(Exception e) {}
		this.waitForElement(this.elements.button_NextTeamUrl);
		this.click(this.elements.button_NextTeamUrl);
		return new LoginScreen();
	}
	
	public LoginScreen enterEmail(String email) {
		this.waitForElement(this.elements.input_Email);
		this.type(this.elements.input_Email, email);
		return new LoginScreen();
	}
	
	public LoginScreen tapNextButtonFromEnterEmailScreen() {
		this.waitForElement(this.elements.button_NextEmail);
		this.click(this.elements.button_NextEmail);
		return new LoginScreen();
	}
	public LoginScreen enterPassword(String password) {
		this.waitForElement(this.elements.input_Password);
		this.type(this.elements.input_Password, password);
		return new LoginScreen();
	}
	
	public LoginScreen tapNextButtonFromEnterPasswordScreen() {
		this.waitForElement(this.elements.button_NextPassword);
		this.click(this.elements.button_NextPassword);
		return new LoginScreen();
	}
	
	public String getTeamName() {
		this.waitForElement(this.elements.text_TeamName);
		return this.getText(this.elements.text_TeamName);
	}
	
	public String getWelcomeText() {
		this.waitForElement(this.elements.desc_WelcomeText);
		return this.getText(this.elements.desc_WelcomeText);
	}
}
