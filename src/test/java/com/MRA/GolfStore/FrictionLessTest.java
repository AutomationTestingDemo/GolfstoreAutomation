package com.MRA.GolfStore;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonParserType;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.ca.golfstore.api.GolfStoreDbAPI;
import com.ca.golfstore.api.ValidationAPI;
import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.ca.golfstore.base.GSResult;
import com.ca.golfstore.page.CRESPage;
import com.ca.golfstore.page.OrderConfirmPage;
import com.ca.golfstore.page.ProductCheckoutPage;
import com.ca.golfstore.page.ProductPurchasePage;
import com.ca.golfstore.page.ProductSelectionPage;
import com.ca.golfstore.util.CommonUtils;
import com.ca.golfstore.util.CustomListener;
import com.ca.golfstore.util.Dataprovider;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings("unused")


@Listeners(com.ca.golfstore.util.CustomListener.class)

public class FrictionLessTest extends BasePage {
	
	  @Test (dataProvider="cardNumProvider")
	  public void frictionLessTest(Map<String, String> testCaseData) throws Exception{
		  
		  
		
		LinkedHashMap<String, String> expectedMap = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> actualMap = new LinkedHashMap<String, String>();
		changeExtentTitle(testCaseData);
		BasePage bs = new BasePage();

		bs.launchGolfStoreWebApp();
		
		ProductSelectionPage ps = new ProductSelectionPage();
		ps.clickOnSelectProduct();
		
		ProductCheckoutPage pc = new ProductCheckoutPage();
		
		pc.clickCheckOutButton();
		
		ProductPurchasePage pp = new ProductPurchasePage();
		pp.enterCardNumber(testCaseData.get("DScardNum"));
		
		pp.clickPurchaseButton();
		
		OrderConfirmPage oc = new OrderConfirmPage();
		
		oc.clickConfirmButton();
		
		CRESPage crp = new CRESPage();
		
		actualMap = crp.getAres();
		
		String threeDStransactionID = crp.get3DSTransactionID();
		
		GolfStoreDbAPI db = new GolfStoreDbAPI();
		
		expectedMap = db.getMTAuthLogDataFromDB(threeDStransactionID);
		
		GSResult result = new GSResult();
		result.setActualMap(actualMap);
        result.setExpectedMap(expectedMap);
        CommonUtils cu = new CommonUtils();
        String detailReport = cu.generateDetailReport(result,
				BaseSuite.strTestCaseName);
        
        ValidationAPI vAPI = new ValidationAPI();
        
        if (vAPI.mapsAreEqual(result.getExpectedMap(),result.getActualMap())){
    	    
    	      Reporter.log("TestCase Pass");
            
            extenttest.log(LogStatus.PASS, "EmailOTPChallengeTest Pass",detailReport);}
    	      
    		else{
    			
    			Reporter.log("TestCase Fail");
            
            extenttest.log(LogStatus.FAIL, "EmailOTPChallengeTest Fail",detailReport);
            
            Assert.fail();}
        
		}
	
	@DataProvider
    public Object[][] cardNumProvider(ITestContext testContext) throws IOException {
		
		  return new CommonUtils().getInputData(testContext, "cardNumProvider", "Sheet");
    }

}
	


