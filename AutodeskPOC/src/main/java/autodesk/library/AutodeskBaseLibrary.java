package autodesk.library;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutodeskBaseLibrary extends AutodeskBaseUtility{
	
	public static String LocalPassPercentage;
	public static String DEVPassPercentage;
	public static String QAPassPercentage;
	
	@BeforeSuite
	public static void invokeBrowser() {		
		initialiseConfig();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();		
	}
	
	
	@AfterClass
	public static void closeInstance(){
		startTest("Close the Instance");
		if(driver!=null){
			driver.quit();
			test.log(LogStatus.PASS, "Should close browser Instance", "Closed browser instance successfuly");
		}else{
			test.log(LogStatus.FAIL, "Should close browser Instance", "Failed to close browser instance");
		}
		report.endTest(test);
		report.flush();
	}
		
}
