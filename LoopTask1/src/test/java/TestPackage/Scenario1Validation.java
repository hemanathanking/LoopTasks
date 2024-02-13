package TestPackage;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

import Pages.Login;
import Pages.HistoryPage;
import ReusableComponents.Basics;


public class Scenario1Validation extends Basics
{
	ExtentReports extent;
	@Test(dataProvider="data")
	public void TableDataValidation(HashMap<String, String> input) throws IOException, InterruptedException
	{
		Login loginmodule=LaunchBrowser();
		loginmodule.LaunchApp(input.get("URL"));
		HistoryPage Transaction=loginmodule.LoginTest(input.get("username"), input.get("password"));
		Transaction.StoresViewPage(input.get("ChargebacksPageURL"));
		Transaction.ColumnAndGrandTotalValidation();

	}
	
}
