package com.ca.golfstore.util;

public interface IRISConstants {

	
	// create transaction related excel sheet names
	public static final String allow = "ALLOW";
	public static final String deny = "DENY";
	public static final String inauth = "INAUTH";
	public static final String alert = "ALERT";
	
	// AMDS Delivery status
	public static final String amdsSmsSent="2";
	public static final String amdsEmailSent="6";
	public static final String amdsPushSent="6";
	public static final String amdsSmsPending="1";
	public static final String amdsEmailPending="";
	public static final String amdsPushPending="";
	
	//AM Opeation ID
	public static final String amOperationId="2100";
	
	
	// Default expected result message
	public static final String NA = "NA";
	public static final String query = "QUERY";
	public enum ElementName{
		ACQ_BIN,
		ACTION ,   
	    AMOUNT ,
	    BROWSER ,
	    CARDTYPE ,
	    CHEXTAUTH ,
	    CITY ,
	    CLIENTIPADDRESS,  
	    CONNECTIONTYPE ,
	    CONTINENT ,
	    COUNTRY ,
	    CURR_CODE, 
	    CURRENTTIME,  
	    DATE ,
	    DAYOFMONTH,  
	    DAYOFWEEK ,
	    DEVICEID ,
	    DEVICETYPE, 
	    ENDRANGE ,
	    EXTENSIONID,  
	    HOUROFDAY ,
	    IP_ROUTINGTYPE,  
	    ISSUERCOUNTRY ,
	    ISSUERNAME ,
	    LINESPEED ,
	    MERCH_CAT,
	    MERCH_COUN,
	    MERCHANT_ID,
	    MERCHANT_NAME,
	    MERCHANT_URL,
	    MFPMATCHPERCENT, 
	    MODEL_ID,
	    MODEL_RTN,
	    MODEL_SCORE,
	    MODEL_VER,
	    MONTH,
	    OS,
	    PERSPECTIVE, 
	    PREDICTIVE_SCORE, 
	    PREVTXNDATA,
	    REGION,
	    STATE,
	    USERNAME, 
	    YEAR,
}

	//General_Purpose =General Purpose
	public enum ListUsage{
		General_Purpose,
		Blacklist,
		Whitelist,
	}
	public static final String IRIS_DATE_FORMAT = "MM/dd/yyyy";
}
