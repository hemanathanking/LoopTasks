package ReusableComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Pages.Login;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Basics 
{
	public WebDriver driver;

	public WebDriver initializeDriver() throws IOException 
	{
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/Data.properties");
		pro.load(fis);
		String browsername = pro.getProperty("browser");

		if (browsername.contains("chrome")) 
		{

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} 
		else if (browsername.equals("firefox")) 
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		return driver;
	}

	public Login LaunchBrowser() throws IOException 
	{
		driver = initializeDriver();
		return new Login(driver);
	}

	@BeforeTest
	public ExtentReports Extent() 
	{
		String path = "/home/hemanathan.m/eclipse-workspace/LoopTask1/Reports/TestReportScenario1.html";
		ExtentSparkReporter report = new ExtentSparkReporter(path);
		report.config().setReportName("Testing report with results");
		report.config().setDocumentTitle("Test Results");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("Hemanathan", "QA");
		return extent;
	}

	@AfterTest
	public void TearDown() 
	{
		driver.close();
	}

	public String Screenshot(String testCaseName, WebDriver driver) throws WebDriverException, IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//FailureScreenshots//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//FailureScreenshots//" + testCaseName + ".png";
	}

	public List<HashMap<String, String>> JsonToHash(String filepath) throws IOException {
		String JsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

		ObjectMapper om = new ObjectMapper();
		List<HashMap<String, String>> HashData = om.readValue(JsonContent,new TypeReference<List<HashMap<String, String>>>() {});
		return HashData;
	}

	@DataProvider
	public Object[] data() throws IOException {
		List<HashMap<String, String>> Data = JsonToHash("/home/hemanathan.m/eclipse-workspace/LoopTask1/src/main/java/Data/TestData.json");
		return new Object[] { Data.get(0) };
	}

}
