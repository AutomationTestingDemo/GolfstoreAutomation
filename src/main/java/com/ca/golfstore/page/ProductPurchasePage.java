package com.ca.golfstore.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.LogStatus;

  public class ProductPurchasePage extends BasePage{
	
	@FindBy(xpath="//input[@name='_cc_number']")
	
	public WebElement cardNumTextBox;
	
	@FindBy (id="button-purchase")
	
	public WebElement purchaseButton;
	BasePage bp  = new BasePage();
  public ProductPurchasePage(){
		
		PageFactory.initElements(BasePage.driver, this);

		}
  public void enterCardNumber(String cardNum){
	  
	  bp.waitForElementPresence("cardNum");
	
	  cardNumTextBox.sendKeys(cardNum);
	  
	  BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"cardNum: "+cardNum);
	  
	  }
  
   public void clickPurchaseButton(){
	  
	purchaseButton.click();
	
	extenttest.log(LogStatus.INFO,"Product Purchase Button click done");
	  
	  }
  
 }