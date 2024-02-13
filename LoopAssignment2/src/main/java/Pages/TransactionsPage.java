package Pages;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ReusableComponents.Components;

public class TransactionsPage extends Components {

	WebDriver driver;

	public TransactionsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	By Location=By.xpath("//span[text()='Locations']");

	@FindBy(xpath = "//span[text()='Locations']")
	WebElement Locations;
	@FindBy(xpath = "//button[text()='Clea']")
	WebElement DeselectAll;
	@FindBy(xpath = "//p[.='Artisan Alchemy']")
	WebElement Location1;
	@FindBy(xpath = "//p[.='Blissful Buffet']")
	WebElement Location2;
	@FindBy(xpath = "//button[@data-testid='applyBtn']")
	WebElement ApplyFliters;
	@FindBy(xpath = "//span[text()='Marketplaces']")
	WebElement MarketPlace;
	@FindBy(xpath = "//p[text()='Grubhub']")
	WebElement Grubhub;

	@FindBy(xpath = "//table//th")
	List<WebElement> HeaderCells;
	@FindBy(xpath = "//table//tbody//tr[position() > 0]")
	List<WebElement> Rows;

	Pattern orderIdPattern = Pattern.compile("#O-\\d+");
	Pattern typePattern = Pattern.compile("Delivery|Error Charges / Inaccurate|Adjustment");

	private String orderId = "";
	private String location = "";
	private String orderState = "";
	private String type = "";
	private String lostSale = "";
	private String netPayout = "";
	private String payoutId = "";
	private String payoutDate = "";
	
	private String firstCellText;

	public void TranactionsDetailsPage(String TransactonPage) throws InterruptedException {
		driver.get(TransactonPage);
	}

	public void Filters() 
	{
		WaitUntilElementClickable(Locations);
		ClickOn(Locations);
		ClickOn(DeselectAll);
		ClickOn(Location1);
		ClickOn(Location2);
		ClickOn(ApplyFliters);
		ClickOn(MarketPlace);
		ClickOn(DeselectAll);
		ClickOn(Grubhub);
		ClickOn(ApplyFliters);
	}

	public void DataExtraction() throws InterruptedException 
	{
		try 
		{
			Thread.sleep(5000);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		StringBuilder headers = new StringBuilder();
		for (WebElement headerCell : HeaderCells) 
		{
			headers.append(headerCell.getText()).append(",");
		}
		headers.deleteCharAt(headers.length() - 1);
		
		 try (BufferedWriter writer = new BufferedWriter(new FileWriter("chargeback_data.csv"))) {
	        	writer.write(headers.toString() + "\n");
	            // Iterate through each row
	            for (WebElement row : Rows) {
	                // Get the first cell's text
	                firstCellText = row.findElement(By.xpath(".//td[1]")).getText().trim();


	                // Check if the first cell text matches the order ID pattern
	                Matcher orderIdMatcher = orderIdPattern.matcher(firstCellText);
	                Matcher typeMatcher = typePattern.matcher(firstCellText);

	                // If it matches the order ID pattern
	                if (orderIdMatcher.matches()) 
	                {
	                    // Store values for all 8 columns
	                	orderId = firstCellText;
	                    location = row.findElement(By.xpath(".//td[2]")).getText().trim();
	                    orderState = row.findElement(By.xpath(".//td[3]")).getText().trim();
	                    type = row.findElement(By.xpath(".//td[4]")).getText().trim();
	                    lostSale = row.findElement(By.xpath(".//td[5]")).getText().trim();
	                    netPayout = row.findElement(By.xpath(".//td[6]")).getText().trim();
	                    payoutId = row.findElement(By.xpath(".//td[7]")).getText().trim();
	                    payoutDate = row.findElement(By.xpath(".//td[8]")).getText().trim();

	                 // Write the extracted data to the CSV file
	                    writer.write(orderId + "," + location + "," + orderState + "," +
	                            type + "," + lostSale + "," + netPayout + "," +
	                            payoutId + "," + "\"" + payoutDate + "\"" + "\n");


	                } else if (typeMatcher.find()) 
	                {
	                    // Store values with type and continue iterating for the next 5 columns
	                    type = firstCellText;
	                    lostSale = row.findElement(By.xpath(".//td[2]")).getText().trim();
	                    netPayout = row.findElement(By.xpath(".//td[3]")).getText().trim();
	                    payoutId = row.findElement(By.xpath(".//td[4]")).getText().trim();
	                    payoutDate = row.findElement(By.xpath(".//td[5]")).getText().trim();

	                 // Write the extracted data to the CSV file
	                    writer.write(orderId + "," + location + "," + orderState + "," +
	                            type + "," + lostSale + "," + netPayout + "," +
	                            payoutId + "," + "\"" + payoutDate + "\"" + "\n");

	                }
	            }
	        } catch (IOException e) 
		 {
	            e.printStackTrace();
	        }
	}
}
