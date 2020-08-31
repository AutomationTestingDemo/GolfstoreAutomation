package com.ca.golfstore.page;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.LogStatus;

public class ProductCheckoutPage extends BasePage {
	
	@FindBy(name="quantity")
	
	public WebElement quantityDropdown;

      @FindBy(xpath="//a[@id='button-checkout']")
	
	public WebElement checkoutButton;
      BasePage bp = new BasePage();
	public ProductCheckoutPage(){
		
		PageFactory.initElements(BasePage.driver, this);

		}
	
	public void selectQuantity(){
		
		bp.waitForElementPresence("quantity");
		
		Select s = new Select(quantityDropdown);
		   
		   s.selectByValue("8");
		   
		   BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Product Quantity Selection Done");
	}
	
	public void selectquantity(){
		
		bp.waitForElementPresence("quantity");
		
		Select s = new Select(quantityDropdown);
		   
		   s.selectByValue("10");
		   
		   BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Product Quantity Selection Done");
	}
	
	public void clickCheckOutButton(){
		
		try{
		  WebDriverWait wait = new WebDriverWait(BasePage.driver,30);
		  
		  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='button-checkout']")));
		
		  bp.waitForElementClickable(checkoutButton);
		
		checkoutButton.click();
		
		extenttest.log(LogStatus.INFO,"Product Checkout done");
		
		}
		
		catch(StaleElementReferenceException e){
			
			
			checkoutButton.click();
			
			extenttest.log(LogStatus.INFO,"Product Checkout done");
			
		}

	}

}
