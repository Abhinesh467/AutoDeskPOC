package autodesk.tests;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;

import autodesk.library.AutodeskBaseUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReleaseTaskAndTransport extends AutodeskBaseUtility {
	
	
	@BeforeClass	
	public static void releaseTRTask() throws Exception {
		initialiseConfig();		
		report = new ExtentReports(System.getProperty("user.dir")+"\\POCReports\\QA_FIORIProjectResults.html");	
		options = new DesktopOptions();
		options.setApplicationPath(prop.getProperty("SAPLogin"));
		File driverPath = new File(System.getProperty("user.dir")+"//Winium.Desktop.Driver.exe");		
		WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
		try {				
			service.start();
		} catch (IOException e) {
			System.out.println("Exception while starting WINIUM service");
			e.printStackTrace();
		}
		driver = new WiniumDriver(service,options);
		Thread.sleep(5000);		
		System.out.println("First Test");		
		Thread.sleep(5000);
		Actions actions = new Actions(driver);	
		actions.doubleClick(driver.findElement(By.name("ND5"))).build().perform();		
		Thread.sleep(2000);
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("DevUser"));
		Robot r = new Robot();
		r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
		r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
		Thread.sleep(2000);
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("DevPSW"));
		r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		Thread.sleep(7000);
		driver.findElement(By.id("1001")).sendKeys(prop.getProperty("ReleaseNo"));
		r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("FunctionalModule"));		

		r.keyPress(KeyEvent.VK_F8);
		r.keyRelease(KeyEvent.VK_F8);
		Thread.sleep(5000);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("TRR"));	
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("TRT"));	
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		driver.findElement(By.id("100")).sendKeys("X");	
		
		r.keyPress(KeyEvent.VK_F8);
		r.keyRelease(KeyEvent.VK_F8);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_SHIFT);		
		r.keyRelease(KeyEvent.VK_F3);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_SHIFT);		
		r.keyRelease(KeyEvent.VK_F3);

		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_SHIFT);		
		r.keyRelease(KeyEvent.VK_F3);
		 
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);

		r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		
	}
	
	@Test(priority = 1)
	public static void releaseTransport() throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get(prop.getProperty("CharmURL"));	
		Actions actions = new Actions(driver);	
		wait = new WebDriverWait(driver, 600);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='sap-user']")));
		driver.findElement(By.xpath("//*[@id='sap-user']")).sendKeys(prop.getProperty("ReleaseUser"));		
		driver.findElement(By.xpath("//*[@id='sap-password']")).sendKeys(prop.getProperty("ReleasePSW"));		
		driver.findElement(By.xpath("//*[@id='LOGON_BUTTON']")).click();			
		Thread.sleep(1000);
		try {
			driver.findElement(By.xpath("//*[@id='SESSION_QUERY_CONTINUE_BUTTON']")).click();
		} catch (Exception e) {
			System.out.println("NO Continue button");
		}
		
		Thread.sleep(5000);
		String MainWindow=driver.getWindowHandle();
		driver.switchTo().frame("CRMApplicationFrame").switchTo().frame("WorkAreaFrame1");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@id,'_SM-CHANGE')]")));
		driver.findElement(By.xpath("//a[contains(@id,'_SM-CHANGE')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'_SM-UC-SR') and text()=' Urgent Changes']")));
		driver.findElement(By.xpath("//*[contains(@id,'_SM-UC-SR') and text()=' Urgent Changes']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@title='Choose the field of criterion Document ID']")).click();
		Thread.sleep(2000);
		actions.moveToElement(driver.findElement(By.xpath("//ul[@class='th-hb-ul']/li/a[@key='START_DATE_RANGE']"))).build().perform();
		driver.findElement(By.xpath("//ul[@class='th-hb-ul']/li/a[@key='START_DATE_RANGE']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//input[@title='Choose the value of criterion Requested Start'])[1]")).sendKeys(prop.getProperty("Date"));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//b[text()='Search']")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+prop.getProperty("CharmTID")+"']")));
		driver.findElement(By.xpath("//a[text()='"+prop.getProperty("CharmTID")+"']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//b[text()='Edit'])[1]")));
		driver.findElement(By.xpath("(//b[text()='Edit'])[1]")).click();
		Thread.sleep(4000);		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)");
		WebElement element = driver.findElement(By.xpath("(//*[@title='Select table row'])[1]"));
		WebElement element1 = driver.findElement(By.xpath("(//*[contains(@id,'_Release') and @title='Release Transport Request'])[1]"));		
		Thread.sleep(500); 		
		element.click();
		Thread.sleep(7000);
		actions.moveToElement(element1).build().perform();
		driver.findElement(By.xpath("(//*[contains(@id,'_Release') and @title='Release Transport Request'])[1]")).click();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		Set<String> s1=driver.getWindowHandles();		
		Iterator<String> i1=s1.iterator();
		while(i1.hasNext()){		
			String ChildWindow=i1.next();            		
			if(!MainWindow.equalsIgnoreCase(ChildWindow)){
				Thread.sleep(2000);
				driver.switchTo().window(ChildWindow);	
				Thread.sleep(2000);
				driver.switchTo().frame(0);
				Thread.sleep(6000);
				System.out.println(driver.getTitle());
				driver.findElement(By.xpath("//b[text()='Release']")).click();
				Thread.sleep(7000);				
			}		
		}		
		driver.switchTo().window(MainWindow);		
		Thread.sleep(10000);
		driver.switchTo().frame("CRMApplicationFrame").switchTo().frame("WorkAreaFrame1");
		Thread.sleep(2000);		
		driver.findElement(By.xpath("//span/b[text()='Save']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[contains(@id,'_ACTIONS')]/img")).click();
		actions.moveToElement(driver.findElement(By.xpath("//span[text()='Pass Urgent Change to Test']"))).build().perform();
		driver.findElement(By.xpath("//span[text()='Pass Urgent Change to Test']")).click();
		Thread.sleep(2000);		
		driver.findElement(By.xpath("//span/b[text()='Save']")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='To Be Tested Selected']")));
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		Thread.sleep(2000);	
		driver.switchTo().frame("CRMApplicationFrame").switchTo().frame("HeaderFrame");
		Thread.sleep(2000);	
		driver.findElement(By.xpath("//*[@id='LOGOFF']")).click();		
		driver.switchTo().alert().accept();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		Thread.sleep(5000);
		driver.close();
	}

}
