package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ReusableComponents.Components;


public class Login extends Components
{
	WebDriver driver;
	public Login(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	By UN=By.xpath("(//input[contains(@class,'MuiInputBase')])[1]");
	@FindBy(xpath="(//input[contains(@class,'MuiInputBase')])[1]")
	WebElement UserName;
	@FindBy(xpath="(//input[contains(@class,'MuiInputBase')])[2]")
	WebElement Password;
	@FindBy(xpath = "//button[text()='Login']")
	WebElement Login;
	By SkipButton=By.xpath("//button[.='Skip for now']");
	@FindBy(xpath = "//button[.='Skip for now']")
	WebElement Skip;
	
	public void LaunchApp(String URL) 
	{
		driver.get(URL);
	}
	
	public HistoryPage LoginTest(String name,String Pass) throws InterruptedException
	{
		waitForElementsToAppear(UN);
		Type(UserName, name);
		Type(Password,Pass);
		Login.click();
		waitForElementsToAppear(SkipButton);
		Skip.click();
		return new HistoryPage(driver);
		 
	}

}
