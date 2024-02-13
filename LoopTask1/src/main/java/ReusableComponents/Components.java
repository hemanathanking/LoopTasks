package ReusableComponents;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Components
{
	WebDriver driver;

	public Components(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Properties properties()
	{
		Properties pro = new Properties();
		return pro;
	}

	public FileInputStream File() throws FileNotFoundException 
	{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/data.properties");
		return fis;
	}
	
	public void ImplicitWait()
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	public void waitForElementsToAppear(By findBy) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy));
	}

	public void WaitUntilElementClickable(WebElement FindBy) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(FindBy));
	}

	public void ScrollAndClick(WebElement ele) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click()", ele);
	}

	public void SwitchToWindow(WebDriver driver)
	{
	String ID=driver.getWindowHandle();
		driver.switchTo().window(ID);
	}
	
	public void Type(WebElement ele,String value)
	{
		ele.sendKeys(value);
	}
	
	public void ClickOn(WebElement ele)
	{
		ele.click();
	}
	
	public void SelectByVisibleText(WebElement ele, String value)
	{
		Select s=new Select(ele);
		s.selectByVisibleText(value);
	}
	
	public void SelectByIndex(WebElement ele, int value)
	{
		Select s=new Select(ele);
		s.selectByIndex(value);
	}
	
	public void SwitchToFrameByValue(WebDriver driver, WebElement iframe)
	{
		driver.switchTo().frame(iframe);
	}
	
	public void SwitchToDefaultContent(WebDriver driver)
	{
		driver.switchTo().defaultContent();
	}
}
