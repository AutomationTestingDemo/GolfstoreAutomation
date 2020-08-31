package com.ca.golfstore.api;

import static org.testng.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.Assert;

import com.ca.golfstore.base.BasePage;
import com.ca.golfstore.base.BaseSuite;
import com.ca.golfstore.util.DBConnection;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings("unused")
public class GolfStoreDbAPI extends BasePage {

	DBConnection dbConn = null;
	public ResultSet rs = null;
	Connection con = null;
	Statement stmt = null;

	public GolfStoreDbAPI() {
		
		dbConn = new DBConnection();
	}
	
	
	/**
	 * This API executes the given DB query
	 * @param  query - DB query to be executed  
	 *         dbUser - Db schema
	 * @return DB query result in form of HasMap where KEY is the db table column Name and VALUE is the value of the key   
	 */	
	public synchronized HashMap<String, String> getDataAsStringFromDB() throws Exception {
		
		HashMap<String, String> dataMap = new HashMap<String, String>();
		String dbUser = BaseSuite.dbAcsUsr;
		String query= "select value from (select * from TD_OTP_LOG order by 13 desc)where ROWNUM=1";
		try {
			
			stmt = dbConn.getConnACS(dbUser).createStatement();
			rs = stmt.executeQuery(query);
			ResultSetMetaData rowData = rs.getMetaData();
			int columns = rowData.getColumnCount();
			dataMap = new HashMap<String, String>(columns);
			while (rs.next()) {
				
				for (int i = 1; i <= columns; ++i) {
					
					dataMap.put(rowData.getColumnName(i), DBConnection.convertValueToString(rs.getObject(i)));
				}
			}
			
		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
			
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		return dataMap;
	}
	
	public synchronized LinkedHashMap<String, String> getMTAuthLogDataFromDB(String threeDStransactionId) throws Exception {
		
		HashMap<String, String> dataMap = new HashMap<String, String>();
		
		LinkedHashMap<String, String> expectedMap = new LinkedHashMap<String, String>();
		String dbUser = BaseSuite.db3DSUsr;
		String query= "select eci,authenticationvalue,interactioncounter from ms_user.mtdauthlog where threedsservertransid='"+threeDStransactionId+"'";
		try {
			
			stmt = dbConn.getConn3DS(dbUser).createStatement();
			rs = stmt.executeQuery(query);
			ResultSetMetaData rowData = rs.getMetaData();
			int columns = rowData.getColumnCount();
			dataMap = new HashMap<String, String>(columns);
			while (rs.next()) {
				
				for (int i = 1; i <= columns; ++i) {
					
					dataMap.put(rowData.getColumnName(i), DBConnection.convertValueToString(rs.getObject(i)));
				}
			}
			
		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
			
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		

		if(dataMap.get("eci")!=null){
		expectedMap.put("ECI",dataMap.get("eci").trim());}
		else{
			expectedMap.put("ECI",null);	
		}
		
		if(dataMap.get("authenticationvalue")!=null){
			expectedMap.put("authenticationValue",dataMap.get("authenticationvalue").trim());
		}
		else{
			
			expectedMap.put("authenticationValue","Transaction Rejected or Challenge Cancelled, no Authentication Value Generated");
		}
		
		
		if(dataMap.get("interactioncounter")!=null){
		
		expectedMap.put("InteractionCounter",dataMap.get("interactioncounter").trim());}
		
		return expectedMap;
	}
public synchronized LinkedHashMap<String, String> getMTDErrorLogDataFromDB(String threeDStransactionId) throws Exception {
		
		HashMap<String, String> dataMap = new HashMap<String, String>();
		
		LinkedHashMap<String, String> expectedMap = new LinkedHashMap<String, String>();
		String dbUser = BaseSuite.db3DSUsr;
		String query= "select errorcode,errordetail,threedsservertransid from ms_user.mtderrormsg where threedsservertransid='"+threeDStransactionId+"'";
		try {
			
			stmt = dbConn.getConn3DS(dbUser).createStatement();
			rs = stmt.executeQuery(query);
			ResultSetMetaData rowData = rs.getMetaData();
			int columns = rowData.getColumnCount();
			dataMap = new HashMap<String, String>(columns);
			while (rs.next()) {
				
				for (int i = 1; i <= columns; ++i) {
					
					dataMap.put(rowData.getColumnName(i), DBConnection.convertValueToString(rs.getObject(i)));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			throw new Exception(e.getMessage());
			
					
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		
		if(dataMap.get("threedsservertransid")!=null){

		if(dataMap.get("errorcode")!=null){
		expectedMap.put("errorCode",dataMap.get("errorcode").trim());}
			
		if(dataMap.get("errordetail")!=null){
		
		expectedMap.put("errorDetail",dataMap.get("errordetail").trim());}
	    }
		else{
			
			extenttest.log(LogStatus.FAIL,"3DSServerTransactionID Not Logged in ms_user.mtderrormsg Table");
			expectedMap.put("errorCode",null);
			expectedMap.put("errorDetail",null);
		}
		
		return expectedMap;
	}
} 
