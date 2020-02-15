package autodesk.tests;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import autodesk.library.AutodeskBaseUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalFIORI extends AutodeskBaseUtility {

	@BeforeClass
	public static void localFIORIInititation() {		
		initialiseConfig();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		report = new ExtentReports(System.getProperty("user.dir")+"\\POCReports\\LocalFIORIProjectResults.html");			
		loginFioriProject(prop.getProperty("LocalURL"),prop.getProperty("Username"),prop.getProperty("Password"));		
	}

	@Test(priority=1)
	public static void validateGraphData() throws Exception{
		parentWinHandle = driver.getWindowHandle();
		wait = new WebDriverWait(driver, 800);	
		startTest("Validate Graph Data");
		JavascriptExecutor js =((JavascriptExecutor)driver);
		js.executeScript("window.scrollBy(0, 200)");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li//span[@class='sapUiTreeNodeContent' and text()='StackedBar']")));
		Thread.sleep(7000);
		WebElement stackedBar = driver.findElement(By.xpath("//li//span[@class='sapUiTreeNodeContent' and text()='StackedBar']"));
		js.executeScript("arguments[0].scrollIntoView(true)", stackedBar);
		highLighterMethod(driver, stackedBar);
		stackedBar.click();		
		test.log(LogStatus.PASS, "Should click on Stacked Bar", "Successfully Clicked on Stacked Bar");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__toolbar1-applicationToolbar-preview.run']")));		
		WebElement run = driver.findElement(By.xpath("//*[@id='__toolbar1-applicationToolbar-preview.run']"));
		highLighterMethod(driver, run);
		run.click();
		test.log(LogStatus.PASS, "Should click on Execute Graph button", "Successfully Clicked on Graph button");
		Thread.sleep(7000);
		Set<String> winHandles = driver.getWindowHandles();
		for(String handle: winHandles){
			if(!handle.equals(parentWinHandle)){
				driver.switchTo().window(handle);
				Thread.sleep(3000);
				System.out.println(driver.getTitle());
				test.log(LogStatus.PASS, "Should Switch to Graph Window", "Successfully Switched to graph window "+driver.getTitle());
			}
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='g' and @class='v-datapoint v-morphable-datapoint v-datapoint-default']")));	
		int graphCount = driver.findElements(By.xpath("//*[name()='g' and @class='v-datapoint v-morphable-datapoint v-datapoint-default']")).size();
		for (int i = 1; i<=graphCount; i++) {
			WebElement graph1 = driver.findElement(By.xpath("(//*[name()='g' and @class='v-datapoint v-morphable-datapoint v-datapoint-default'])["+i+"]"));		
			Actions builder = new Actions(driver);
			builder.click(graph1).build().perform();
			Thread.sleep(1000);
			WebElement Year = driver.findElement(By.xpath("//*[@class='v-tooltip-dimension-measure']/tr[1]/td[2]"));			
			highLighterMethod(driver, Year);
			String year = Year.getText();
			WebElement Item = driver.findElement(By.xpath("//*[@class='v-tooltip-dimension-measure']/tr[2]/td[1]"));
			WebElement ItemData = driver.findElement(By.xpath("//*[@class='v-tooltip-dimension-measure']/tr[2]/td[2]"));
			highLighterMethod(driver, Item);
			highLighterMethod(driver, ItemData);
			String item = Item.getText();
			String itemData = ItemData.getText();
			if(prop.getProperty("Year").contains(year)&&prop.getProperty("Itemvalues").contains(itemData)){
				test.log(LogStatus.PASS, "DEV Year =" +year, "Captured Year = "+year);
				test.log(LogStatus.PASS, "Dev data = "+itemData, "Expected "+item+" Value = "+itemData);
			}else{
				test.log(LogStatus.FAIL, "DEV Year =" +year, "Captured Year = "+year);
				test.log(LogStatus.FAIL, "Dev data = "+itemData, "Expected "+item+" Value = "+itemData);
			}
			System.out.println(year);
			System.out.println(itemData);
			WebElement graphTitle = driver.findElement(By.xpath("//*[name()='g']/following::*[text()='Resource Analysis']"));
			builder.click(graphTitle).build().perform();
			Thread.sleep(1000);
		}
		
		driver.close();
		test.log(LogStatus.INFO, "Should close Graph window", "Closed Graph window successfuly");
		Thread.sleep(3000);
		driver.switchTo().window(parentWinHandle);		
		Thread.sleep(3000);
		test.log(LogStatus.PASS, "Should Switch to Parent Window", "Successfully Switched to Parent window "+driver.getTitle());
		report.endTest(test);
		report.flush();
		driver.get(System.getProperty("user.dir")+"\\POCReports\\LocalFIORIProjectResults.html");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"slide-out\"]/li[2]/a[text()='Analysis']")).click();
		LocalPassPercentage = driver.findElement(By.xpath("//span[@class=\"pass-percentage panel-lead\"]")).getText();
		System.out.println(LocalPassPercentage);	
		driver.close();
	}	
	
}
