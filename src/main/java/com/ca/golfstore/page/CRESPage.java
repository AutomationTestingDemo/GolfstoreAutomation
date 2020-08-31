package com.ca.golfstore.page;

import java.util.LinkedHashMap;

import io.restassured.path.json.JsonPath;

import org.apache.commons.codec.DecoderException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.ca.golfstore.api.HexaDecoder;
import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.LogStatus;

public class CRESPage extends BasePage{
	
	@FindBy(name="authframe")
	
	public WebElement cresFrame;
	
	@FindBy (id="crestal")
	
	public WebElement cresText;
	
	@FindBy (id="arestal")
	
	public WebElement arestalText;
	
    @FindBy (xpath="//div[@id ='aresta']")
	public static WebElement aresText;
    
    @FindBy (xpath="//p[text()=' Transferring to your bank for credit card authentication.']")
   	public static WebElement spinner;
    
    @FindBy (xpath="//td[contains(text(),'Result(Transaction Status)')]/..//td[3]")
   	public static WebElement transstatus;
    
    @FindBy (xpath="//td[contains(text(),'ECI')]/..//td[3]")
   	public static WebElement Eci;
    
    @FindBy (xpath="//td[contains(text(),'Auth Value')]/..//td[3]")
   	public static WebElement Authvalue;
    
    @FindBy (xpath="//td[contains(text(),'3DS Server')]/..//td[3]")
   	public static WebElement threeDSServerID;
    BasePage bp = new BasePage();
	public CRESPage(){
		
		PageFactory.initElements(BasePage.driver, this);
	}
	
	public void swithToFrame(){
			
		bp.waitForFrameAvailability(cresFrame);
		
		try{
			String ares =arestalText.getText();
		if(!ares.isEmpty()){
			System.out.println("Ares_Response :"+ares);
		    Reporter.log("Ares_Response :"+ares); 
		    extenttest.log(LogStatus.INFO,"Ares_Response :"+ares); }}
		catch(WebDriverException e){
			
			}
		}
	
	public String getCresText(){
		
		String cres = cresText.getText();
		
		extenttest.log(LogStatus.INFO,"cres: "+cres);
		
		JsonPath js = new JsonPath(cres);
		
		String transactionID = js.get("threeDSServerTransID");
		
		System.out.println("3DSServerTransactionID: "+transactionID);
		
		Reporter.log("3DSServerTransactionID :"+transactionID);
		
		extenttest.log(LogStatus.INFO,"3DSServerTransactionID :"+transactionID);
		
		Assert.assertNotNull(transactionID);
		
		return transactionID;
	}
	
	   public String encodedCres(){
		
      String source = driver.getPageSource();
        
        String[] cresdata = source.split("var cresdata = '");
       
        cresdata =cresdata[1].split("';");
                
         String encodedCres =cresdata[0];
         
         Reporter.log("EncodedCRES :"+encodedCres);
         
         driver.switchTo().parentFrame();
         
         driver.switchTo().defaultContent();
           
          return  encodedCres;
	}

	   public LinkedHashMap<String, String> getAres() throws InterruptedException, DecoderException{
		   
		   LinkedHashMap<String, String> actualMap = new LinkedHashMap<String, String>();

		   bp.waitForInvisibilityofElement(spinner);
		     bp.waitForElementPresence("aresta");
	    String ares =aresText.getText();
	    String eci = null;
	    try{
	    	
	     if(!ares.isEmpty()){
	    	 
	    Reporter.log("Ares_Response :"+ares);
	    
	    extenttest.log(LogStatus.INFO,"Ares_Response :"+ares);
	    
	    BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Ares_Response Attached Below");
	    
	          }
	   
	  	       swithToFrame();
	  	       
	    	    String transStatus = transstatus.getText();

	    	   if(!transStatus.isEmpty()){
	    		   
			   extenttest.log(LogStatus.INFO,"TransStatus: "+transStatus);
			   
			   
			   eci = Eci.getText();
			   if(!eci.isEmpty()){
				   
				   actualMap.put("ECI",eci.trim());
			   extenttest.log(LogStatus.INFO,"ECI_Value: "+eci);}
			   else{
				   actualMap.put("ECI","Eci not generated"); 
				   
			   }
			   
	    	   }
			   
			 
			   String AuthValue = Authvalue.getText();
			   if(!AuthValue.isEmpty()){
			extenttest.log(LogStatus.INFO,"Auth_Value: "+AuthValue);
			
			if(AuthValue.length()>=30){
				
				AuthValue = HexaDecoder.hexaDecoder(AuthValue);
				
				actualMap.put("authenticationValue",AuthValue.trim());
			}
			else{
			   actualMap.put("authenticationValue",AuthValue.trim());}}
			   
			   else{
				   
				   actualMap.put("authenticationValue","Transaction Rejected or Challenge Cancelled, no Authentication Value Generated");  
			   }
			   
			   driver.switchTo().defaultContent();
			   
               if(!transStatus.isEmpty()&& transStatus.length()==1){
            	   
            	   if(eci.equals("05")){
            	   
            	   extenttest.log(LogStatus.PASS, "FrictionLess Transaction Successfull");}
            	   
            	   else if(eci.equals("07")){
                	   
                	   extenttest.log(LogStatus.PASS, "Reject Transaction Successfull");
            		   
            	   }
            	   
               }
               else{
            	   
            	   extenttest.log(LogStatus.FAIL,"Transaction Fail");
            	   
            	   Assert.fail();
            	   
            	 }
               
                  }
			   catch(WebDriverException e){
				   
				   Reporter.log("TestCase Fail");
				   extenttest.log(LogStatus.FAIL,"ARES Fail");
				   Assert.fail(e.getMessage());
			   }
	    
	    return actualMap;
	     }
	   
	   public String get3DSTransactionID(){
		   
		   swithToFrame();
		   
		   String transactionID = threeDSServerID.getText();
		   
		   extenttest.log(LogStatus.INFO,"threeDSServerID :"+transactionID);
		   
		   driver.switchTo().defaultContent();
		   
		return transactionID;
		   
	   }
}
