package com.ca.golfstore.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.LogStatus;



/**
 * @author munka03
 *
 */

@SuppressWarnings("unused")
public class BasePage extends BaseSuite {

	public static WebDriver driver = null;
	
	private String golfstoreUrl=null;
	
	public static String cardNum=null;
	
	public static final long WAIT_THIRTY =10;

	BaseSuite bs = new BaseSuite();
	
	public BasePage() {
		
		   golfstoreUrl = BaseSuite.golfstoreUrl;
		   cardNum=BaseSuite.cardNum;
		    Long threadId = new Long(Thread.currentThread().getId());
			driver = BaseSuite.threadDriverMap.get(threadId);
	}
	
	
	public  void launchGolfStoreWebApp(){
		Long threadID = new Long(Thread.currentThread().getId());
		driver = threadDriverMap.get(new Long(threadID));
		if(driver!=null){
			driver.get(golfstoreUrl);
			if(driver.getTitle().equals("Golf Store")){
				
				extenttest.log(LogStatus.PASS, "Navigated to Golf Store Application");}
				
				else{
					
					extenttest.log(LogStatus.FAIL, "Test Failed");}
          }
	}

	public void waitForElementClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_THIRTY);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void waitForElementPresence(String id) {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_THIRTY);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));

	}
	
	public void waitForElementVisibility(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_THIRTY);
		wait.until(ExpectedConditions.visibilityOf(element));

	}
	
	public  void waitForFrameAvailability(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, WAIT_THIRTY);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));

	}

	public void waitForElementClickable(WebElement element, long waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}
	public void waitForInvisibilityofElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[text()=' Transferring to your bank for credit card authentication.']")));

	}
	
	public boolean verifyElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	   
}
