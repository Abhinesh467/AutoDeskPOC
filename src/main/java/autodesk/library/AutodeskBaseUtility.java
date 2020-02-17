package autodesk.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AutodeskBaseUtility{

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String parentWinHandle;
	public static ExtentTest test;
	public static ExtentReports report;
	public static Properties prop;
	public static DesktopOptions options;
	public static String ABAPQAPassPercentage;
	public static String LocalPassPercentage;
	public static String DEVPassPercentage;
	public static String QAPassPercentage;

	public static void highLighterMethod(WebDriver driver, WebElement element) throws Exception{
		Thread.sleep(500);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
	}

	public static void startTest(String description){
		test = report.startTest(description);
	}

	public static void initialiseConfig(){
		File file = new File(System.getProperty("user.dir")+"\\Autodesk.properties");
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		prop = new Properties();
		prop.getProperty(",","");
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loginFioriProject(String URL, String Username, String Password) {
		startTest("Invoke Local FIORI Project URL and Login");	
		wait = new WebDriverWait(driver, 500);
		try {		
			driver.get(URL);
			test.log(LogStatus.INFO, "Should Open FIORI PROJECT", "Successfully Opened FIORI PRoject");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='j_username']")));
			WebElement username = driver.findElement(By.xpath("//*[@id='j_username']"));
			WebElement password = driver.findElement(By.xpath("//*[@id='j_password']"));
			WebElement loginButton = driver.findElement(By.xpath("//*[@id='logOnFormSubmit']"));
			try {
				if(username.isDisplayed()&&password.isDisplayed()&&loginButton.isDisplayed()){
					highLighterMethod(driver, username);
					username.sendKeys(Username);
					test.log(LogStatus.PASS, "Should Enter UserName", "Entered Username successfully");
					highLighterMethod(driver, password);					
					password.sendKeys(Password);
					test.log(LogStatus.PASS, "Should Enter Password", "Entered Password successfully");
					highLighterMethod(driver, loginButton);
					loginButton.click();
					test.log(LogStatus.PASS, "Should click on Login button", "Clicked on Login Button successfully");
				}else{
					test.log(LogStatus.FAIL, "Should display Username, Password and Login Button", "Failed to display Username, Password and Login Button");
				}
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "Should display Username, Password and Login Button", "Failed to display Username, Password and Login Button due to:: "+e);
			}		

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Should Open FIORI PROJECT", "Failed to Opened FIORI PRoject due to:: "+e);
		}
		report.endTest(test);
	}

	public static void commonLogin(String URL, String Username, String Password) {
		startTest("Invoke  FIORI Project URL and Login");	
		wait = new WebDriverWait(driver, 500);
		try {		
			driver.get(URL);
			test.log(LogStatus.INFO, "Should Open FIORI PROJECT", "Successfully Opened FIORI PRoject");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='USERNAME_FIELD-inner']")));
			WebElement username = driver.findElement(By.xpath("//*[@id='USERNAME_FIELD-inner']"));
			WebElement password = driver.findElement(By.xpath("//*[@id='PASSWORD_FIELD-inner']"));
			WebElement loginButton = driver.findElement(By.xpath("//*[@id='LOGIN_LINK']"));
			try {
				if(username.isDisplayed()&&password.isDisplayed()&&loginButton.isDisplayed()){
					highLighterMethod(driver, username);
					username.sendKeys(Username);
					test.log(LogStatus.PASS, "Should Enter UserName", "Entered Username successfully");
					highLighterMethod(driver, password);					
					password.sendKeys(Password);
					test.log(LogStatus.PASS, "Should Enter Password", "Entered Password successfully");
					highLighterMethod(driver, loginButton);
					loginButton.click();
					test.log(LogStatus.PASS, "Should click on Login button", "Clicked on Login Button successfully");
				}else{
					test.log(LogStatus.FAIL, "Should display Username, Password and Login Button", "Failed to display Username, Password and Login Button");
				}
			} catch (Exception e) {
				test.log(LogStatus.FAIL, "Should display Username, Password and Login Button", "Failed to display Username, Password and Login Button due to:: "+e);
			}		

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Should Open FIORI PROJECT", "Failed to Opened FIORI PRoject due to:: "+e);
		}	
		report.endTest(test);
	}


	public static void validateGraphData() throws Exception{		
		wait = new WebDriverWait(driver, 800);	
		startTest("Validate Graph Data");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='__content0']")));
		driver.findElement(By.xpath("//*[@id='__content0']")).click();
		test.log(LogStatus.PASS, "Should click on Autodesk Tile", "Clicked on Autodesk Tile Successfully");

		Thread.sleep(9000);	
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
		report.endTest(test);
	}

}
