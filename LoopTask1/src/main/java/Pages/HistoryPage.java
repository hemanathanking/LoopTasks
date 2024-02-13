package Pages;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ReusableComponents.Components;

public class HistoryPage extends Components {

	WebDriver driver;

	public HistoryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	By Table=By.tagName("table");

	@FindBy(tagName ="table")
	WebElement TableData;
	List<WebElement> rows;
	WebElement row;
	WebElement headerRow;
	List<WebElement> cells;
	WebElement cell;
	List<WebElement> headerCells;
	WebElement grandTotalRow;
	
	private double columnTotal;
	private double cellValue;
	private double grandTotalValue;
	


	public void StoresViewPage(String StoresViewPageURL) throws InterruptedException 
	{
		driver.get(StoresViewPageURL);
	}
	
	public void ColumnAndGrandTotalValidation() throws InterruptedException
	{
		Thread.sleep(15000);
        rows = TableData.findElements(By.tagName("tr"));
        
         headerRow = rows.get(0);
         headerCells = headerRow.findElements(By.tagName("th"));
         
         List<String> monthNames = new ArrayList<>();
         for (int i = 1; i < headerCells.size() - 1; i++) 
         { // Exclude only the first header
             monthNames.add(headerCells.get(i).getText());
         }
         
         grandTotalRow = rows.get(rows.size() - 1);
         
         for (int i = 1; i < headerCells.size() - 2; i++) 
         {
             

             // Iterate over each row (excluding the header and Grand Total and dummy rows)
             for (int j = 1; j < rows.size() - 2; j++) 
             {
            	 columnTotal = 0.0;
                 row = rows.get(j);
                 cells = row.findElements(By.tagName("td"));
                 
                 // Get the cell corresponding to the current column
                 cell = cells.get(i);

                 // Parse the cell text to a double and add it to the column total
                  cellValue = parseCellValue(cell.getText());
                 columnTotal += cellValue;
                 
             }
                 // Get the Grand Total value for the current column
                 grandTotalValue = parseCellValue(grandTotalRow.findElements(By.tagName("td")).get(i).getText());
                 System.out.println("Grand total for " + monthNames.get(i - 1) + ": " + grandTotalValue);
              // Calculate the column total
                 System.out.println("Column total for " + monthNames.get(i - 1) + ": " + columnTotal);

                 // Compare the column total with the Grand Total value
                 if (Double.compare(columnTotal, grandTotalValue) == 0) 
                 {
                     System.out.println("For column " + monthNames.get(i - 1) + ", the summed value matches Grand Total.");
                 } else 
                 {
                     System.out.println("For column " + monthNames.get(i - 1) + ", the summed value does not match Grand Total.");
                 }
             }
             
         
	}
	
	private static double parseCellValue(String cellValue) 
    {
        // Check if the cell value is empty
        if (cellValue.isEmpty()) 
        {
            // Return 0.0 for empty cell value
            return 0.0;
        } 
        else 
        {
            // Remove any non-numeric characters and convert to double
            return Double.parseDouble(cellValue.replaceAll("[^\\d.]", ""));
        }
    }
         


}
