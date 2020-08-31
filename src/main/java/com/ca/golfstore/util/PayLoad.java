package com.ca.golfstore.util;

public class PayLoad {
	
public static String createBody(String otp){
		
		String s ="{"+
				"\"storage\": \"software\","+
				"\"algorithm\": \"3DES\","+
				"\"key\": \"3DES-KEY-1001\","+
				"\"cipher\": \""+otp+"\""+"}";
		
	//	System.out.println(s);
		return s;
	}

public static String verifyAPIBody(String transactionId, String cresEncodedValue,String callerTxnRefID,String messageType,String caMerchantID){
	
	String s ="{\r\n  \"threeDSServerTransID\" : \""+transactionId+"\",\r\n  \"callerTxnRefID\" : \""+callerTxnRefID+"\",\r\n  \"messageType\" : \""+messageType+"\",\r\n  \"caMerchantID\" : \""+caMerchantID+"\"\r\n}";
	
	System.out.println(s);
	return s;
}

}
