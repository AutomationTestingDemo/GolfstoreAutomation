package com.ca.golfstore.util;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

@SuppressWarnings("unused")
public class OTPDecryption {
	
	public static String otpDecryption(String encryptedOTP){
		
		RestAssured.baseURI ="http://10.131.94.242:2080";
     	Response resp = given().header("Content-Type","application/json").
		body(PayLoad.createBody(encryptedOTP)).when().
		post("/crypto-service/api/v1/decrypt").
        then().statusCode(200).and().extract().response();
     	
         String s = resp.asString();
		
		// System.out.println(s);
		
		JsonPath js = new JsonPath(s);
		
		String otp = js.get("data");
		
		System.out.println("OTP :"+otp);
		
		return otp;
}

}
