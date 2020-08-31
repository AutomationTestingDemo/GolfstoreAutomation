package com.ca.golfstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.ca.golfstore.base.BaseSuite;


/**
 * @author munka03
 *
 */
@SuppressWarnings("unused")
public class DBConnection {
	private static String dbHost = BaseSuite.dbHost;
	private static String dbPort = BaseSuite.dbPort;
	private static String dbSid = BaseSuite.dbSid;
	private static String dbPwd = BaseSuite.dbPwd;
	private static String db3DSHost = BaseSuite.db3DSHost;
	private static String db3DSSid = BaseSuite.db3DSSid;
	private static String db3DSPort = BaseSuite.db3DSPort;

	
	public Connection getConnACS(String dbUser) throws SQLException {
		Connection dbConn = DriverManager.getConnection("jdbc:oracle:thin:@"
				+ dbHost + ":" + dbPort + ":" + dbSid, dbUser, dbPwd);
		return dbConn;
	}
	
	public Connection getConn3DS(String dbUser) throws SQLException {
		try {
			Class.forName("com.edb.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection dbConn = DriverManager.getConnection("jdbc:edb://"+db3DSHost+":"+db3DSPort+"/edb?user=ms_user",""+dbUser+"",""+dbPwd+"");
		return dbConn;
	}
	
	public void closeConn(Connection conn) throws SQLException {
		DbUtils.close(conn);
	}

	public QueryRunner runQuery() {
		QueryRunner run = new QueryRunner();
		return run;
	}
	
	public static String convertValueToString(Object data) {
		String convertedValue = null;

		try{
		if (data != null) {
			
			convertedValue = (String) data;
		}else{
			
			System.out.println("Data is Null");
		}}
		catch(ClassCastException e){
			convertedValue= data.toString();
		}
		return convertedValue;
	}
}