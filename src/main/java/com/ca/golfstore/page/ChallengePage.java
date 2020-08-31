package com.ca.golfstore.page;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings("unused")
public class ChallengePage extends BasePage {

	@FindBy (id="iframe-foreground")
	
	public WebElement frame;
	
    @FindBy (xpath="//input[@value='mobilenumber1']")
	
	public WebElement mobileAuthentication;

    @FindBy (xpath="//input[@value='email2']")

    public WebElement emailAuthentication;
    
    @FindBy (xpath="//input[@value='SMS_EMAIL_OTP']")

    public WebElement oneTimePwd;
    
    @FindBy(xpath="//input[@id='next-button']")
    
    public WebElement nextButton;
    
    @FindBy (xpath="//input[@type='text']")
    
    public WebElement otpBox;
    
    @FindBy (xpath="//input[@id='verify-button']")
    
    public WebElement verifyButton;
    
    @FindBy (xpath="//span[text()='Cancel and change payment method']")
    
    public WebElement cancelButton;
	
    BasePage bp = new BasePage();
    
	public ChallengePage() {
		PageFactory.initElements(BasePage.driver, this);
	}
	
	public void swithToframe(){
		
		bp.waitForFrameAvailability(frame);
		
		//driver.switchTo().frame(frame);
	}
	
	public void selectMobileAuth(){
		
		mobileAuthentication.click();
		
		 BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Mobile Auth Selected");
	}
	
       public void selectEmailAuth(){
		
    	   emailAuthentication.click();
    	   
    	   BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Email Auth Selected");
	}
       
       public void selectOTPAuth(){
    	   
    	   try{
    	   
    	   if(oneTimePwd.isDisplayed()){

    	   BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"OTP Auth Selected");
    	   
    	   nextButton.click();
    	   
    	   } }
    	   
    	   catch(WebDriverException e){
    		   
    		 }
	}
       
       
       public void clickNextButton(){
      		
    	   nextButton.click();
    	   
    	   extenttest.log(LogStatus.INFO,"Next button Click Done");
	}
         
       public void enterOTP(String otp){
   		
    	   otpBox.sendKeys(otp);
    	   
    	   BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"OTP Submitted: "+otp);
	}
       
       public void clickVerifyButton(){
     		   
    	   verifyButton.click();
    	   
    	   extenttest.log(LogStatus.INFO,"OTP Verify button Click Done");
    	   
	}
       
       public void clickCancelButton(){
    	   
    	   cancelButton.click();
    	   
    	 Alert alt=  driver.switchTo().alert(); 
    	 
    	 alt.accept();
    	 
    	 extenttest.log(LogStatus.INFO,"Transaction Cancelled");
	}
       
       public void multipleOTPInteraction(String otp){
      		
    	   if(otp!=null){
    	   otpBox.sendKeys("123456");
    	   extenttest.log(LogStatus.INFO,"Invalid OTP Entered : 123456");
    	   clickVerifyButton();
    	   otpBox.sendKeys("45mk89");
    	   extenttest.log(LogStatus.INFO,"Invalid OTP Entered : 45mk89");
    	   clickVerifyButton();
    	   otpBox.sendKeys(otp);
    	   extenttest.log(LogStatus.INFO,"Invalid OTP Entered :"+otp);
    	   clickVerifyButton();}
    	   
    	   else{
    		   otpBox.sendKeys("123456");
        	   extenttest.log(LogStatus.INFO,"Invalid OTP Entered : 123456");
        	   clickVerifyButton();
        	   otpBox.sendKeys("45mk8A");
        	   extenttest.log(LogStatus.INFO,"Invalid OTP Entered : 45mk8A");
        	   clickVerifyButton();
        	   otpBox.sendKeys("!@#123");
        	   extenttest.log(LogStatus.INFO,"Invalid OTP Entered : !@#123");
        	   clickVerifyButton();
    		   
    	   }
	}
       
       
}
