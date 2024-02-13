package TestPackage;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

import Pages.Login;
import Pages.TransactionsPage;
import ReusableComponents.Basics;


public class Scenario2Validation extends Basics
{
	ExtentReports extent;
	@Test(dataProvider="data")
	public void ExtractingDatatoCSV(HashMap<String, String> input) throws IOException, InterruptedException
	{
		Login loginmodule=LaunchBrowser();
		loginmodule.LaunchApp(input.get("URL"));
		TransactionsPage Transaction=loginmodule.LoginTest(input.get("username"), input.get("password"));
		Transaction.TranactionsDetailsPage(input.get("TransactionPageURL"));
		Transaction.Filters();
		Transaction.DataExtraction();
	}
	
}
