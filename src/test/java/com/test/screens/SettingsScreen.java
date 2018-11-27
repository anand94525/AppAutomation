package com.test.screens;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.framework.core.DriverManager;
import com.framework.core.Page;

public class SettingsScreen extends Page {

	private Elements elements;

	private static class Elements {
		@FindBy(id = "com.Slack:id/overflow_button")
		WebElement button_Option;

		@FindBy(id = "com.Slack:id/label")
		List<WebElement> listSettingOptions;

		@FindBy(id = "com.Slack:id/profile_edit")
		WebElement button_EditProfile;

		@FindBy(xpath = "(//*[@resource-id='com.Slack:id/editprofile_floatlabellayout'])[2]/android.widget.EditText")
		WebElement input_DisplayName;
		
		@FindBy(xpath = "(//*[@resource-id='com.Slack:id/editprofile_floatlabellayout'])[1]/android.widget.EditText")
		WebElement input_FullName;
		
		@FindBy(id = "com.Slack:id/menu_item")
		WebElement buttonSave;
		
		//TODO : Need to move this to home screen.
		@FindBy(id = "com.Slack:id/user_name")
		WebElement text_UserName;
	}

	@Override
	protected void initElements() {
		this.elements = new Elements();
		PageFactory.initElements(DriverManager.getInstance(), this.elements);		
	}
	
	public SettingsScreen() {
		this.initElements();
	}

	public SettingsScreen openSettingsOptions() {
		this.waitForElement(this.elements.button_Option);
		this.click(this.elements.button_Option);
		return new SettingsScreen();
	}
	
	public SettingsScreen openSetting() {
		this.wait(2);
		this.click(this.elements.listSettingOptions.get(5));
		return new SettingsScreen();
	}
	
	public SettingsScreen tapEditProfile() {
		this.waitForElement(this.elements.button_EditProfile);
		this.click(this.elements.button_EditProfile);
		return new SettingsScreen();
	}
	
	public SettingsScreen tapDisplayName() {
		this.waitForElement(this.elements.input_DisplayName);
		this.click(this.elements.input_DisplayName);
		return new SettingsScreen();
	}
	
	public SettingsScreen enterNewName(String name) {
		this.waitForElement(this.elements.input_DisplayName);
		this.type(this.elements.input_DisplayName, name);
		wait(1);
		this.click(this.elements.input_FullName);
		return new SettingsScreen();
	}
	
	public SettingsScreen saveEditProfile() {
		this.waitForElement(this.elements.buttonSave);
		this.click(this.elements.buttonSave);
		wait(1);
		return new SettingsScreen();
	}
	
	public String getUpdatedName() {
		this.waitForElement(this.elements.text_UserName);
		return this.getText(this.elements.text_UserName);
	}
}
