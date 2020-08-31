package com.MRA.GolfStore;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonParserType;
import io.restassured.response.Response;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.page.CRESPage;
import com.ca.golfstore.page.OrderConfirmPage;
import com.ca.golfstore.page.ProductCheckoutPage;
import com.ca.golfstore.page.ProductPurchasePage;
import com.ca.golfstore.page.ProductSelectionPage;
import com.ca.golfstore.util.CustomListener;

@SuppressWarnings("unused")


@Listeners(com.ca.golfstore.util.CustomListener.class)

public class InvalidCardNumTest extends BasePage {
	
	@Test
	public void invalidCardNumTest() throws InterruptedException{
		
		BasePage bs = new BasePage();

		bs.launchGolfStoreWebApp();
		
		ProductSelectionPage ps = new ProductSelectionPage();
		ps.clickOnSelectProduct();
		
		ProductCheckoutPage pc = new ProductCheckoutPage();
		
		pc.clickCheckOutButton();
		
		ProductPurchasePage pp = new ProductPurchasePage();
		
		pp.enterCardNumber(BasePage.cardNum);
		
		pp.clickPurchaseButton();
		
		OrderConfirmPage oc = new OrderConfirmPage();
		
		oc.clickConfirmButton();
}
	
}

