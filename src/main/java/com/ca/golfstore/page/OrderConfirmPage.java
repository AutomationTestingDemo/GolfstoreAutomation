package com.ca.golfstore.page;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.LogStatus;



public class OrderConfirmPage extends BasePage {
	
	@FindBy(id="button-confirmorder")
	
	public WebElement confirmButton;
	
    @FindBy (xpath="//p[text()=' Transferring to your bank for credit card authentication.']")
   	public static WebElement spinner;
    
    @FindBy(xpath="//div[contains(text(),'Error Connecting ACS/3DS MethodURL')]")
    
    public WebElement ThreeDSFail;
    BasePage bp = new BasePage();
	 public OrderConfirmPage(){
			
		PageFactory.initElements(BasePage.driver, this);

	}
	 
	 public void clickConfirmButton(){
		 
		 BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Order Confirm Page");
		 
		 confirmButton.click();
		 
		 extenttest.log(LogStatus.INFO,"Order confirm button click done");
		 
		 bp.waitForInvisibilityofElement(spinner);
		  
		 try{
			 
		if(ThreeDSFail.isDisplayed()){
			 
			 extenttest.log(LogStatus.FAIL,"Error Connecting ACS/3DS MethodURL");
		      
		      BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Error Connecting ACS/3DS MethodURL");
		      
		      Assert.fail();
		 }
			 
		 }
		 catch(WebDriverException e){
			 
 
		 }
	 }

}
