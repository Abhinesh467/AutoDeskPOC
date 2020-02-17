package autodesk.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;

import autodesk.library.AutodeskBaseUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class QAFIORI extends AutodeskBaseUtility {	

	@BeforeClass
	public static void qaFIORIInititation() {		
		initialiseConfig();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		report = new ExtentReports(System.getProperty("user.dir")+"\\POCReports\\QA_FIORIProjectResults.html");	
		commonLogin(prop.getProperty("QARUL"),prop.getProperty("FIORIQAUser"),prop.getProperty("FIORIQAPW"));		
	}

	@Test(priority = 1)
	public static void validateQAFioriData() throws Exception {
		validateGraphData();
		report.flush();
		driver.get(System.getProperty("user.dir")+"\\POCReports\\QA_FIORIProjectResults.html");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"slide-out\"]/li[2]/a[text()='Analysis']")).click();
		QAPassPercentage = driver.findElement(By.xpath("//span[@class=\"pass-percentage panel-lead\"]")).getText();
		System.out.println(QAPassPercentage);
		driver.close();
	}

}
