package com.bHaNuChand.autoMation.BaseEngine;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.bHaNuChand.autoMation.utility.DriverPath;
import com.bHaNuChand.autoMation.utility.ScreenShotUtility;


public class BaseEngine {
	private static RemoteWebDriver driver;
	private static String CurDir;
	private static String tcName;


	@Parameters("browser")
	@BeforeSuite
	public void openBrwser(@org.testng.annotations.Optional("chrome")String browser) {
		CurDir=System.getProperty("user.dir");

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty(DriverPath.ChromeKey,DriverPath.ChromeValue);
			driver= new ChromeDriver();
			init();
		}
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty(DriverPath.FirefoxKey,DriverPath.FirefoxValue);
			driver= new FirefoxDriver();
			init();
		}
		if (browser.equalsIgnoreCase("ie")) {
			System.setProperty(DriverPath.ieKey,DriverPath.ieValue);
			driver= new InternetExplorerDriver();
			init();

		} 
	}
	@AfterSuite
	public void browserClose() throws InterruptedException {

		Optional<RemoteWebDriver> optional =Optional.ofNullable(driver);
		if (optional.isPresent()) {
			Thread.sleep(5000);
			driver.close();
		}


		/*if (driver!=null) {

			driver.close();
		}
		else
		System.out.println("driver pointing null pointer excep");	
		 */}


	@BeforeMethod
	public void beforeTCExecution(Method method) {
		tcName = method.getName();
		System.out.println("CURRENTLY EXECUTING TC IS: " +tcName);

	}

	@AfterMethod
	public void afterTCExecution(ITestResult result) throws IOException {
		tcName =result.getName();
		if (result.getStatus()==ITestResult.SUCCESS) {
			System.out.println("afterTCExecution IS SUCCESS"+tcName);
		}
		else if (result.getStatus()==ITestResult.SKIP) {
			System.out.println("afterTCExecution IS SKIP"+tcName);
			ScreenShotUtility.screenshotMethod();
		}
		else if (result.getStatus()==ITestResult.FAILURE) {
			System.out.println("afterTCExecution IS FAILURE"+tcName);
			ScreenShotUtility.screenshotMethod();

		}}



	public static RemoteWebDriver getDriver() {
		return driver;
	}
	private void init(){
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	public static String getCurDir() {
		return CurDir;
	}
	public static String getTcName() {
		return tcName;
	} 

}



















