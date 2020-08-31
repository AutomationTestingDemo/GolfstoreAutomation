package com.ca.golfstore.util;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings("unused")
public class TakeScreenShotClass extends BaseSuite {
	
	public static void takeScreenShot(String method, WebDriver driver){
		
		String filePath = System.getProperty("user.dir")+"\\"+"TestResult"+"\\"+"ScreenShots"+"\\";
		
		String destPath =System.getProperty("user.dir")+"\\"+"TestResult"+"\\"+"ScreenShots"+"\\"+method+".jpg";
		
		 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try {
				FileHandler.copy(scrFile, new File(filePath+method+".jpg"));
				
				System.out.println("***Placed screen shot in "+filePath+" ***");
				
				extenttest.log(LogStatus.FAIL,"Test Failed image attached below");
				
				extenttest.log(LogStatus.FAIL,extenttest.addScreenCapture(destPath));
				
				
			} catch (IOException e) {
				
				System.out.println("ERROR while taking screen shot");
				
				extenttest.log(LogStatus.WARNING,
						"Exception while capturing image " + e.getMessage()
								+ "\nstackTrace:" + e.getStackTrace().toString());
			}
		
		
		
	}

}
