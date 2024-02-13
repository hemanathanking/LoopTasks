package ReusableComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ListenersClass extends Basics implements ITestListener 
{
	
	ExtentTest testname;
	ExtentReports extent = Extent();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); 

	public void onTestStart(ITestResult result)
	{
		testname = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(testname);
	}

	public void onTestSuccess(ITestResult result) 
	{
		testname.log(Status.PASS, "Test is passed");
	}

	public void onTestFailure(ITestResult result) 
	{
		extentTest.get().fail(result.getThrowable());
		try 
		{
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		} 
		
		String filepath = null;
		
			try 
			{
		 filepath=Screenshot(result.getMethod().getMethodName(),driver);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			extentTest.get().addScreenCaptureFromPath(filepath, result.getMethod().getMethodName());
		
	}

	public void onFinish(ITestContext context) 
	{
        extent.flush();
	}


}
