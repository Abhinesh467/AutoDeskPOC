package autodesk.tests;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import autodesk.library.AutodeskBaseUtility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ABAPQACodeTest extends AutodeskBaseUtility{
	public static WiniumDriverService service;
	public static String ABAPQAPassPercentage;
	
	public static String DBData  = "C:\\Program Files (x86)\\Jenkins\\workspace\\ABAP-CICD\\DBData";
	public static String APPData = "C:\\Program Files (x86)\\Jenkins\\workspace\\ABAP-CICD\\APPData\\";
	
	@BeforeClass
	public static void driverInvoke() throws Exception{
		initialiseConfig();
		report = new ExtentReports(System.getProperty("user.dir")+"\\POCReports\\"+prop.getProperty("ProgramName")+"ABAPQA_FIORIProjectResults.html");	
		options = new DesktopOptions();
		options.setApplicationPath("C:\\Program Files (x86)\\SAP\\FrontEnd\\SapGui\\saplogon.exe");
		File driverPath = new File(System.getProperty("user.dir")+"//Winium.Desktop.Driver.exe");
		service = new WiniumDriverService.Builder().usingDriverExecutable(driverPath).usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
		try {				
			service.start();
		} catch (IOException e) {
			System.out.println("Exception while starting WINIUM service");
			e.printStackTrace();
		}
		driver = new WiniumDriver(service,options);
		Thread.sleep(5000);
	}
	
	@Test(priority=1)
	public static void ExportProgramData() throws Exception{
		System.out.println("First Test");		
		startTest("Export Program Data");
		Thread.sleep(2000);
		Actions actions = new Actions(driver);	
		actions.doubleClick(driver.findElement(By.name("NQ5"))).build().perform();		
		Thread.sleep(2000);
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("QAUser"));
		Robot r = new Robot();
		/*
		 * r.keyPress(java.awt.event.KeyEvent.VK_TAB);
		 * r.keyRelease(java.awt.event.KeyEvent.VK_TAB);
		 */
		driver.findElement(By.id("100")).sendKeys(prop.getProperty("QAPSW"));
		r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		wait = new WebDriverWait(driver, 600);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("1001")));
		driver.findElement(By.id("1001")).sendKeys(prop.getProperty("ProgramName"));
		r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		driver.findElement(By.id("157")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("154")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("111")).click();
		Thread.sleep(1000);
		driver.findElement(By.name("Desktop")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("1148")).clear();
		driver.findElement(By.id("1148")).sendKeys(APPData+prop.getProperty("ProgramName"));
		Thread.sleep(1000);
		driver.findElement(By.name("Save")).click();
		test.log(LogStatus.PASS, "Should save data", "Data Saved Successfully");
		try {
			Thread.sleep(1000);
			driver.findElement(By.name("Allow")).click();
		} catch (Exception e) {
			System.out.println("No Allow Button");
		}
		
		/*
		 * try { Thread.sleep(1000); driver.findElement(By.name("Allow")).click(); }
		 * catch (Exception e) { System.out.println("No Allow Button"); }
		 */
		
		try {
			Thread.sleep(3000);
			driver.findElement(By.name("Close")).click();
		} catch (Exception e) {
			System.out.println("No Close Button");
		}
		Thread.sleep(5000);
		
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_SHIFT);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_SHIFT);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_F3);
		r.keyRelease(KeyEvent.VK_SHIFT);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_TAB);
		r.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		report.endTest(test);
		service.stop();
	}

	@SuppressWarnings("resource")
	@Test(priority=2)
	public static void testDataComparison() throws Exception{
		startTest("Validate APP data and DB Data");
		File file =    new File(DBData+"\\"+prop.getProperty("ProgramName")+".xlsx");
		File file2 = new File(APPData+prop.getProperty("ProgramName")+".XLSX");
		FileInputStream inputStream = new FileInputStream(file);
		FileInputStream inputStream2 = new FileInputStream(file2);
		Workbook wb = null;		
		Workbook wb2 = null;
		wb = new XSSFWorkbook(inputStream);
		wb2 = new XSSFWorkbook(inputStream2);
		Sheet Sheet1 = wb.getSheetAt(0);
		Sheet Sheet2 = wb2.getSheetAt(0);
		int rowCount_Sheet1 = Sheet1.getLastRowNum()-Sheet1.getFirstRowNum();
		int rowCount_Sheet2 = Sheet2.getLastRowNum()-Sheet2.getFirstRowNum();
		ArrayList<Object> arr = new ArrayList<Object>();
		ArrayList<Object> arr2 = new ArrayList<Object>();
		if(rowCount_Sheet1==rowCount_Sheet2) {
			System.out.println("Rows count matched in both Excel sheets");
			
			for(int i =0; i<rowCount_Sheet1; i++) {
				Row row = Sheet1.getRow(i);
						
				for(int j =0; j<row.getLastCellNum();j++) {
					arr.add(row.getCell(j));
				}
			}
			
			for(int i =0; i<rowCount_Sheet1; i++) {
				Row row2 = Sheet2.getRow(i);				
				for(int j =0; j<row2.getLastCellNum();j++) {
					arr2.add(row2.getCell(j));
				}
			}
			
			System.out.println(arr.size());
			for(int i=0;i<arr.size();i++){
				if(arr.get(i).toString().equals(arr2.get(i).toString())){
					test.log(LogStatus.PASS, "Program Data =" +arr.get(i), "DB Data = "+arr2.get(i).toString());
					System.out.println(arr.get(i)+ " "+arr2.get(i).toString());
				}
				else{
					test.log(LogStatus.FAIL, "Program Data =" +arr.get(i), "DB Data = "+arr2.get(i).toString());
					System.out.println("Data is not matched for "+arr.get(i)+""+arr2.get(i));
				}
			}
		}
		inputStream.close();
		inputStream2.close();
		file2.delete();
		
		report.endTest(test);
		report.flush();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(System.getProperty("user.dir")+"\\POCReports\\"+prop.getProperty("ProgramName")+"ABAPQA_FIORIProjectResults.html");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"slide-out\"]/li[2]/a[text()='Analysis']")).click();		
		ABAPQAPassPercentage = driver.findElement(By.xpath("//span[@class=\"pass-percentage panel-lead\"]")).getText();
		Thread.sleep(5000);
		System.out.println(ABAPQAPassPercentage);
		driver.close();
	}

}
