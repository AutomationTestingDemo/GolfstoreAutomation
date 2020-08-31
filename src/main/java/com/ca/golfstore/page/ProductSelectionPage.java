package com.ca.golfstore.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.relevantcodes.extentreports.LogStatus;

public class ProductSelectionPage extends BasePage {
	
	@FindBy(id ="button-addtocartsmall")
	public WebElement selectProduct;
	
	@FindBy(xpath="//div[5]//div[1]//a[2]")
	public WebElement overPriced;


public ProductSelectionPage(){
	
PageFactory.initElements(BasePage.driver, this);

}

public void clickOnSelectProduct(){
	
	selectProduct.click();
	
	BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Product Added to cart");
}

public void selectProduct(){
	
	overPriced.click();
	
	BaseSuite.takeScreenShot(BasePage.driver,LogStatus.INFO,"Product Added to cart");
}

}