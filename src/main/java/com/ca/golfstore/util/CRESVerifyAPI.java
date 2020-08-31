package com.ca.golfstore.util;

import java.util.LinkedHashMap;

import org.apache.commons.codec.DecoderException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ca.golfstore.api.HexaDecoder;
import com.ca.golfstore.base.BaseSuite;
import com.ca.golfstore.base.GSResult;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("unused")
public class CRESVerifyAPI extends BaseSuite {
	
	public LinkedHashMap<String, String> cresVerify(String transactionId, String cresEncodedValue,String callerTxnRefID,String messageType,String caMerchantID) throws DecoderException{
		LinkedHashMap<String, String> actualMap = new LinkedHashMap<String, String>();
		RestAssured.baseURI =BaseSuite.baseURI;
     	Response resp = given().header("Content-Type","application/json;charset=UTF-8").
		body(PayLoad.verifyAPIBody(transactionId,cresEncodedValue,callerTxnRefID,messageType,caMerchantID)).when().
		post("/v1/getResultsStatus").
        then().assertThat().statusCode(200).contentType(ContentType.JSON).and().extract().response();
     	
         String s = resp.asString();
		
		System.out.println("APIResponse :"+s);
		
		//System.out.println("APIResponse :"+s);
		
		extenttest.log(LogStatus.INFO, "VerifyAPIResponse :"+s);
		
		JsonPath js = new JsonPath(s);
		
		
		if(js.get("transStatus")!=null){
		
		
		extenttest.log(LogStatus.INFO, "TransactionStatus: "+js.get("transStatus"));}
		
		
		if(js.get("eci")!=null){
		Reporter.log("ECI_Value: "+js.get("eci"));
		
		actualMap.put("ECI",js.get("eci")+"");
		
		extenttest.log(LogStatus.INFO, "ECI_Value: "+js.get("eci"));}
		
		
		if(js.get("authenticationValue")!=null){
			
			String AuthValue = js.get("authenticationValue");
			
        extenttest.log(LogStatus.INFO, "authenticationValue: "+js.get("authenticationValue"));
        
        if(AuthValue.length()>=30){
			
			AuthValue = HexaDecoder.hexaDecoder(AuthValue);
			
			actualMap.put("authenticationValue",AuthValue.trim());
		}
		else{
		   actualMap.put("authenticationValue",AuthValue.trim());}
        
		}
		else if(js.get("eci")!=null&&js.get("transStatus")!=null){
			
			actualMap.put("authenticationValue","Transaction Rejected or Challenge Cancelled, no Authentication Value Generated");

			extenttest.log(LogStatus.INFO, "Transaction Rejected or Challenge Cancelled, no Authentication Value Generated");
		}
         
		if(js.get("interactionCounter")!=null){
        Reporter.log("InteractionCounter: "+js.get("interactionCounter"));
        
        actualMap.put("InteractionCounter",js.get("interactionCounter")+"");
        
        extenttest.log(LogStatus.INFO, "interactionCounter: "+js.get("interactionCounter"));}
		
          if(js.get("errorCode")!=null){
	        
        	  actualMap.put("errorCode",js.get("errorCode")+"");
        	  
	        extenttest.log(LogStatus.INFO, "errorCode: "+js.get("errorCode"));}
		
		if(js.get("errorDescription")!=null){
	        
			//actualMap.put("errorDescription",js.get("errorDescription"));
	        extenttest.log(LogStatus.INFO, "errorDescription: "+js.get("errorDescription"));}
		
         if(js.get("errorDetail")!=null){
        	 
        	 actualMap.put("errorDetail",js.get("errorDetail")+"");
	        extenttest.log(LogStatus.INFO, "errorDetail: "+js.get("errorDetail"));}
        
		return actualMap;
}

}