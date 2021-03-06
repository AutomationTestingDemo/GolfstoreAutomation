package com.MRA.GolfStore;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ca.golfstore.api.GolfStoreDbAPI;
import com.ca.golfstore.api.ValidationAPI;
import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.ca.golfstore.base.GSResult;
import com.ca.golfstore.page.CRESPage;
import com.ca.golfstore.page.ChallengePage;
import com.ca.golfstore.page.OrderConfirmPage;
import com.ca.golfstore.page.ProductCheckoutPage;
import com.ca.golfstore.page.ProductPurchasePage;
import com.ca.golfstore.page.ProductSelectionPage;
import com.ca.golfstore.util.CRESVerifyAPI;
import com.ca.golfstore.util.CommonUtils;
import com.ca.golfstore.util.OTPDecryption;
import com.relevantcodes.extentreports.LogStatus;
@Listeners(com.ca.golfstore.util.CustomListener.class)
public class MobileOTPChallengeTest extends BasePage{
	
	
	@Test (dataProvider="cardNumProvider")
	public void mobileotpChallengeTest(Map<String, String> testCaseData) throws Exception{
		
		LinkedHashMap<String, String> expectedMap = new LinkedHashMap<String, String>();
		LinkedHashMap<String, String> actualMap = new LinkedHashMap<String, String>();
		changeExtentTitle(testCaseData);
		BasePage bs = new BasePage();
	
		bs.launchGolfStoreWebApp();
		
		ProductSelectionPage ps = new ProductSelectionPage();
		ps.clickOnSelectProduct();
		
		ProductCheckoutPage pc = new ProductCheckoutPage();
		
		pc.selectQuantity();
		
		pc.clickCheckOutButton();
		
		ProductPurchasePage pp = new ProductPurchasePage();
		
		pp.enterCardNumber(testCaseData.get("DScardNum"));
		
		pp.clickPurchaseButton();
		
		OrderConfirmPage oc = new OrderConfirmPage();
		oc.clickConfirmButton();
		
		ChallengePage cp = new ChallengePage();
		
		cp.swithToframe();
		
		cp.selectOTPAuth();
		
		cp.selectMobileAuth();
		cp.clickNextButton();
		
		GolfStoreDbAPI db = new GolfStoreDbAPI();
		
		HashMap<String, String> map = db.getDataAsStringFromDB();
		
		String otpEncrypted = map.get("VALUE").trim();
		
		String otp = OTPDecryption.otpDecryption(otpEncrypted);
		
		cp.enterOTP(otp);
		
		cp.clickVerifyButton();
		
		CRESPage cresp = new CRESPage();
		
		cresp.swithToFrame();
		
		String transactionId = cresp.getCresText();
		
		String cresEncodedValue = cresp.encodedCres();
		
		CRESVerifyAPI cresVerify = new CRESVerifyAPI();
		actualMap = cresVerify.cresVerify(transactionId, cresEncodedValue,testCaseData.get("callerTxnRefID"),testCaseData.get("messageType"),testCaseData.get("caMerchantID"));
		
		expectedMap = db.getMTAuthLogDataFromDB(transactionId);
		GSResult result = new GSResult();
		result.setActualMap(actualMap);
        result.setExpectedMap(expectedMap);
        
        CommonUtils cu = new CommonUtils();
        
        String detailReport = cu.generateDetailReport(result,
				BaseSuite.strTestCaseName);
        
        ValidationAPI vAPI = new ValidationAPI();
          
        if (vAPI.mapsAreEqual(result.getExpectedMap(),result.getActualMap())){
	    
	      Reporter.log("TestCase Pass");
        
        extenttest.log(LogStatus.PASS, "MobileOTPChallengeTest Pass",detailReport);}
	      
		else{
			
			Reporter.log("TestCase Fail");
        
        extenttest.log(LogStatus.FAIL, "MobileOTPChallengeTest Fail",detailReport);
        
        Assert.fail();}
        
		}
	
	
	@DataProvider
    public Object[][] cardNumProvider(ITestContext testContext) throws IOException {
		
		  return new CommonUtils().getInputData(testContext, "cardNumProvider", "Sheet");
    }

     }
	
