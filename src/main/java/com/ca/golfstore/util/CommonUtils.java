package com.ca.golfstore.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.ITestContext;

import com.ca.golfstore.base.GSResult;

public class CommonUtils {
	

	private static Boolean isAllTrue = null;
	
	public String generateDetailReport(GSResult result, String testCaseName,
			String... expectedUIMessage) {
		String outputFile = null;
		RandomNumberAndString ranNum = new RandomNumberAndString();
		Long ranInt = null;
		try {
			ranInt = ranNum.generateRandInt(1, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> expectedReslMap = result.getExpectedMap();
		Map<String, String> actualReslMap = result.getActualMap();
		if (expectedReslMap == null) {
			expectedReslMap = new HashMap<String, String>();
			expectedReslMap.put(IRISConstants.NA, IRISConstants.NA);
			actualReslMap = new HashMap<String, String>();
			actualReslMap.put(IRISConstants.NA, IRISConstants.NA);
		}
		if (expectedReslMap != null && actualReslMap != null) {
			String timeStamp = getCurrentTimeStamp();
			if (testCaseName == null)
				testCaseName = "Html";
			String TestCaseName = testCaseName;
			testCaseName = testCaseName + "_" + ranInt + "_" + timeStamp
					+ ".html";
			String destDirFile = createDestDirFile(testCaseName);
			String query = IRISConstants.NA;
		boolean isSuccess = false;
			if (actualReslMap.containsKey(IRISConstants.query)) {
				query = actualReslMap.get(IRISConstants.query);
				actualReslMap.remove(IRISConstants.query);
			}
			StringBuilder htmlQuery = new StringBuilder(
					"<font face=\"verdana\" color=\"blue\">" + query
							+ "</font>");

			String queryHtmlData = "NA";
			if (query.length() > 1000)
				queryHtmlData = generateQueryReport(testCaseName, query);
			else
				queryHtmlData = createHTMLFileData(htmlQuery, "DB Query");
			queryHtmlData="";  // This should be enabled later - Debi

			StringBuilder tableData = createTableDataForExpAulResult(
					expectedReslMap, actualReslMap);
			if (isAllTrue)
				isSuccess = true;
			
			String htmlData = "NA";
		htmlData=createHTMLFileData(tableData, "DB Results");
		htmlData=""; // This should be enabled later - Debi
			
			StringBuilder uitableData = createTableDataForExpAulResult(
					expectedReslMap, actualReslMap);
			if (!isAllTrue || !isSuccess)
				isSuccess = false;
			StringBuilder uiReport = null;
			StringBuilder csvReport = null;
			String uihtmlData = createHTMLFileData(uitableData, "Test Result :"+TestCaseName);
			// String UIReportData = createHTMLFileData(tableData,
			// "DB Results");
			if (result.getActualUIMapList() != null
					|| result.getExpectedUIMapList() != null) {
				uiReport = createTableDataForReportResult(result
						.getExpectedUIMapList());
				csvReport = createTableDataForReportResult(result
						.getActualUIMapList());
				String uiReportData = createHTMLFileData(uiReport,
						"Before Updation");
				String csvReportData = createHTMLFileData(csvReport,
						"After Updation");
				uihtmlData = uihtmlData + uiReportData + csvReportData
						+ htmlData + queryHtmlData;
			} else {
				
				uihtmlData = uihtmlData + htmlData + queryHtmlData;
			}
			outputFile = createHTMLFile(destDirFile, uihtmlData, testCaseName,
					isSuccess);
		}
		System.out.println("out file for compare name:" + outputFile);
		return outputFile;
	}
	
	
	@SuppressWarnings("unused")
	public StringBuilder createTableDataForReportResult(
            List<LinkedHashMap<String, String>> expReslMap) {
		StringBuilder tableData = new StringBuilder();
		String tableStartTag = "<table>";
		String tableEndTag = "</table>";
		String thStartTag = "<th>";
		String thEndTag = "</th>";
		String trStartTag = "<tr>";
		String tdStartTag = "<td>";
		String tdEndTag = "</td>";
		String trEndTag = "</tr>";
		String tdStartRedTag = "<td style=\"background-color:red\"";
		String tdStartGreenTag = "<td style=\"background-color:green\"";
		
		tableData.append(tableStartTag);
		tableData.append(trStartTag);
		for (String key : expReslMap.get(0).keySet())
		{
		            StringBuilder headerCellData = createSingleDivisionData(
		                                            thStartTag, key, thEndTag);
		            tableData.append(headerCellData);
		}
		tableData.append(trEndTag);
		for (int i = 0; i < expReslMap.size(); i++) {
		            tableData.append(trStartTag);
		            for (String key : expReslMap.get(i).keySet()) {
		
		            StringBuilder thirdCellData = createSingleDivisionData(tdStartTag, expReslMap
		                                                            .get(i).get(key), tdEndTag);
		            tableData.append(thirdCellData);
		
		            }
		            tableData.append(trEndTag);
		}
		
		tableData.append(tableEndTag);
		return tableData;
		}
	
	
	
	private StringBuilder createSingleDivisionData(String thStartTag,
			String actualValue, String thEndTag) {
		StringBuilder cellData = new StringBuilder();
		cellData.append(thStartTag);
		cellData.append(actualValue);
		cellData.append(thEndTag);
		return cellData;
	}
	
	
	private String createHTMLFile(String destDirFile, String htmlData,
			String testCaseName, boolean isSuccess) {
		String relativedir = ".." + File.separator + "HTML" + File.separator;
		File fss = new File(destDirFile);
		fss.getParentFile().mkdirs();
		FileWriter fw = null;
		try {
			fss.createNewFile();
			fw = new FileWriter(fss.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(htmlData);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String reldirFile = relativedir + testCaseName;
		// String windowOpen =
		// "<a href=\"javascript:window.open("+reldirFile+",'Satya','width=600,height=400')\">"+tableDisplayName+"</a>";
		String outputFile = null;
		if (isSuccess == false)
			outputFile = "<b><a href=" + reldirFile
					+ " target=\"_blank\" style=\"color: #FF0000\">"
					+ "Test Results - Failed </a></b>";
		else
			outputFile = "<b><a href=" + reldirFile
					+ " target=\"_blank\" style=\"color: #008000\">"
					+ "Test Results - Passed </a></b>";
		isAllTrue = null;
		return outputFile;
	}
	
	
	private String createDestDirFile(String testCaseName) {
		String destdir1 = System.getProperty("user.dir");
		String destDirFile = destdir1 + File.separator + "TestResult"
				+ File.separator + "HTML" + File.separator + testCaseName;
		System.out.println("dest dir File===========>" + destDirFile);
		return destDirFile;
	}
	
	public String generateQueryReport(String testCaseName, String query) {
		String outputFile = null;
		RandomNumberAndString ranNum = new RandomNumberAndString();
		Long ranInt = null;
		try {
			ranInt = ranNum.generateRandInt(1, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String timeStamp = getCurrentTimeStamp();
		testCaseName = testCaseName + "_" + ranInt + "_" + timeStamp
				+ ".html";
		String destDirFile = createDestDirFile(testCaseName);
		StringBuilder htmlQuery = new StringBuilder(
				"<font face=\"verdana\" color=\"blue\">" + query + "</font>");
		String queryHtmlData = createHTMLFileData(htmlQuery, "DB Query");
		outputFile = createHTMLFile(destDirFile,queryHtmlData,testCaseName,"DB Query");
		createHTMLFile(destDirFile, queryHtmlData, testCaseName,
				true);
		return outputFile;
	} 
	
	
	
	private StringBuilder createTableDataForExpAulResult(
			Map<String, String> expReslMap, Map<String, String> actReslMap) {
		StringBuilder tableData = new StringBuilder();
		String tableStartTag = "<table>";
		String tableEndTag = "</table>";
		String thStartTag = "<th>";
		String thEndTag = "</th>";
		String trStartTag = "<tr>";
		String trEndTag = "</tr>";
		String tdStartRedTag = "<td style=\"background-color:red\"";
		String tdStartGreenTag = "<td style=\"background-color:green\"";
		String tdEndTag = "</td>";

		tableData.append(tableStartTag);
		// StringBuilder firstCellData
		StringBuilder firstCellData = createSingleDivisionData(thStartTag, "",
				thEndTag);
		StringBuilder secCellData = createSingleDivisionData(thStartTag,
				"Expected", thEndTag);
		StringBuilder thrdCellData = createSingleDivisionData(thStartTag,
				"Actual", thEndTag);
		tableData.append(firstCellData);
		tableData.append(secCellData);
		tableData.append(thrdCellData);

		StringBuffer sb = compareExpectedWithActualValue(expReslMap, actReslMap);
		tableData.append(sb);
		tableData.append(tableEndTag);
		return tableData;
	}
	
	
	
	@SuppressWarnings("unused")
	public StringBuffer compareExpectedWithActualValue(
			Map<String, String> expectedMap, Map<String, String> actualMap) {
		StringBuffer tableData = new StringBuffer();
		// String tdStartREDTag = "<td style=\"background-color:red\">";
		String tdStartREDTag = "<td style=\"background-color:#FF7050\">"; // light
																			// red
		String tdStartGREENTag = "<td style=\"background-color:#90EE90\">";// lightgreen
		String tdStartTag = "<td>";
		String tdEndTag = "</td>";
		String trStartTag = "<tr>";
		String trEndTag = "</tr>";
		HashSet<String> allKeys = new HashSet<String>();
		Set<String> expKeys = expectedMap.keySet();
		Set<String> actKeys = actualMap.keySet();
		allKeys.addAll(expKeys);
		allKeys.addAll(actKeys);
		tableData.append(trStartTag);
		if (expectedMap == null) {
			System.out.println("expected map is null");
		}
		if (actualMap == null) {
			System.out.println("actual map is null");
		}
		isAllTrue = true;
		for (String key : allKeys) {
			if (expectedMap.containsKey(key)) {
				if (actualMap != null && actualMap.containsKey(key)) {
					String expMapValue = expectedMap.get(key);
					String actMapValue = actualMap.get(key);
					if ((expMapValue != null && expMapValue
							.equalsIgnoreCase(actMapValue))
							|| (expMapValue == null && actMapValue == null)) { // In
																				// few
																				// cases
																				// both
																				// the
																				// values
																				// would
																				// expect
																				// null
						StringBuilder firstCellData = createSingleDivisionData(
								tdStartTag, key, tdEndTag);
						// Expected value
						StringBuilder secCellData = createSingleDivisionData(
								tdStartTag,
								"<font face=\"verdana\" color=\"green\">"
										+ expMapValue + "</font>", tdEndTag);
						// Actual value
						StringBuilder thrdCellData = createSingleDivisionData(
								tdStartTag,
								"<font face=\"verdana\" color=\"green\">"
										+ actMapValue + "</font>", tdEndTag);
						tableData.append(firstCellData);
						tableData.append(secCellData);
						tableData.append(thrdCellData);

						tableData.append(trEndTag);
						/*
						 * test.log(LogStatus.PASS, "expected=" +
						 * expectedMap.get(key) + "|actual=" +
						 * actualMap.get(key));
						 */
					} else {
						StringBuilder firstCellData = createSingleDivisionData(
								tdStartTag, key, tdEndTag);
						// Expected value
						StringBuilder secCellData = createSingleDivisionData(
								tdStartTag,
								"<font face=\"verdana\" color=\"red\">"
										+ expectedMap.get(key) + "</font>",
								tdEndTag);
						// Actual value
						StringBuilder thrdCellData = createSingleDivisionData(
								tdStartTag,
								"<font face=\"verdana\" color=\"red\">"
										+ actualMap.get(key) + "</font>",
								tdEndTag);
						tableData.append(firstCellData);
						tableData.append(secCellData);
						tableData.append(thrdCellData);

						tableData.append(trEndTag);
						isAllTrue = false;
						/*
						 * test.log(LogStatus.FAIL, "expected=" +
						 * expectedMap.get(key) + "|actual=" +
						 * actualMap.get(key));
						 */

					}
				} else {
					tableData.append(tdStartTag);
					tableData.append(key);
					tableData.append(tdEndTag);
					// Expected value
					tableData.append(tdStartTag);
					tableData.append(expectedMap.get(key));
					tableData.append(tdEndTag);

					tableData.append(trEndTag);
					/*
					 * test.log(LogStatus.FAIL, "expected=" +
					 * expectedMap.get(key) + "|actual=KEYNOTPRESENT");
					 */
				}
			} else {
				tableData.append(tdStartTag);
				tableData.append(key);
				tableData.append(tdEndTag);
				// Actual value
				tableData.append(tdStartTag);
				tableData.append(actualMap.get(key));
				tableData.append(tdEndTag);

				tableData.append(trEndTag);
				/*
				 * test.log(LogStatus.FAIL, "expected=KEYNOTPRESENT" +
				 * "|actual=" + actualMap.get(key));
				 */
			}
		}
		return tableData;

	}
	
	
	
	public String generateHTMLReportForInputTestData(
			Map<String, String> inputTestDataMap, String testCaseName,
			String tableDisplayName, String detailDesc) {
		String outputFile = null;
		RandomNumberAndString ranNum = new RandomNumberAndString();
		Long ranInt = null;
		try {
			ranInt = ranNum.generateRandInt(1, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (inputTestDataMap != null) {
			String timeStamp = getCurrentTimeStamp();
			if (testCaseName == null)
				testCaseName = "Html";
			if (tableDisplayName == null)
				tableDisplayName = "Input XL Sheet Data";
			testCaseName = testCaseName.replace(' ', '_');
			testCaseName = testCaseName + "_" + ranInt + "_" + timeStamp
					+ ".html";
			String destDirFile = createDestDirFileName(testCaseName);
			StringBuilder tableData = createTableData(inputTestDataMap);
			String htmlData = createHTMLFileData(tableData, tableDisplayName);
			outputFile = createHTMLFile(destDirFile, htmlData, testCaseName,
					detailDesc);
			// childtest.log(LogStatus.INFO, "Test_Data", outputFile);
		}
		System.out.println(" out file name:" + outputFile);
		return outputFile;
	}
	
	
	private String getCurrentTimeStamp() {
		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		System.out.println(t);
		String timeStamp = t.toString();
		timeStamp = timeStamp.replace(' ', '_');
		timeStamp = timeStamp.replace(':', '_');
		return timeStamp;
	}
	
	
	
	private String createDestDirFileName(String strHtmlFileName) {
		String destdir1 = System.getProperty("user.dir");
		String destDirFile = destdir1 + File.separator + "TestResult"
				+ File.separator + "HTML" + File.separator + strHtmlFileName;
		System.out.println("dest dir File===========>" + destDirFile);
		return destDirFile;
	}
	

	
	private StringBuilder createTableData(Map<String, String> inputMap) {
		StringBuilder tableData = new StringBuilder();
		String tableStartTag = "<table>";
		String tableEndTag = "</table>";
		String thStartTag = "<th>";
		String thEndTag = "</th>";
		String trStartTag = "<tr>";
		String trEndTag = "</tr>";
		String tdStartTag = "<td>";
		String tdEndTag = "</td>";

		tableData.append(tableStartTag);

		tableData.append(thStartTag);
		tableData.append("Key Name");
		tableData.append(thEndTag);

		tableData.append(thStartTag);
		tableData.append("Value Details");
		tableData.append(thEndTag);

		tableData.append(trStartTag);
		for (String key : inputMap.keySet()) {
			// <td>key</td>
			tableData.append(tdStartTag);
			tableData.append(key);
			tableData.append(tdEndTag);

			// <td>value</td>
			tableData.append(tdStartTag);
			tableData.append(inputMap.get(key));
			tableData.append(tdEndTag);

			tableData.append(trEndTag);
		}
		tableData.append(tableEndTag);
		return tableData;
	}

	
	
	
	private String createHTMLFileData(StringBuilder tableData,
			String htmlDisplayName) {
		StringBuilder sb = new StringBuilder();
		String header = "<!DOCTYPE html><html><head><style>table, th, td {    border: 1px solid black;    border-collapse: collapse;}th, td {    padding: 5px;}</style></head><body> ";
		String title = "<h2><left>" + htmlDisplayName + "</left></h2>";
		String closingTags = "</body></html>";
		sb.append(header);
		sb.append(title);
		sb.append(tableData);
		sb.append("<br/>");
		sb.append("<br/>");
		sb.append(closingTags);
		return sb.toString();
	}
	
	
	
	private String createHTMLFile(String destDirFile, String htmlData,
			String testCaseName, String hyperLinkName) {
		String relativedir = ".." + File.separator + "HTML" + File.separator;
		File fss = new File(destDirFile);
		fss.getParentFile().mkdirs();
		FileWriter fw = null;
		try {
			fss.createNewFile();
			fw = new FileWriter(fss.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(htmlData);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String reldirFile = relativedir + testCaseName;
		String outputFile = null;
		/*
		 * if (isAllTrue != null && isAllTrue == false) outputFile =
		 * "<b><a href=" + reldirFile +
		 * " target=\"_blank\" style=\"color: #FF0000\">" + tableDisplayName +
		 * "" + "</a></b>"; else if (isAllTrue != null && isAllTrue == true)
		 * outputFile = "<b><a href=" + reldirFile +
		 * " target=\"_blank\" style=\"color: #008000\">" + tableDisplayName +
		 * "" + "</a></b>"; else
		 */
		if (htmlData.toString().contains("red"))
			outputFile = "<b><a  href=" + reldirFile
					+ " target=\"_blank\" style=\"color: #CC0000\">"
					+ hyperLinkName + "</a></b>";
		else if (htmlData.toString().contains("green"))
			outputFile = "<b><a  href=" + reldirFile
					+ " target=\"_blank\" style=\"color: #00cc00\">"
					+ hyperLinkName + "</a></b>";
		else
			outputFile = "<b><a href=" + reldirFile + " target=\"_blank\" >"
					+ hyperLinkName + "</a></b>";
		return outputFile;
	}
	
	public Object[][] getInputData(ITestContext testContext,
			String strRingBufferFile, String strRingBufferSheet) {
		String fileName = null, sheetName = null, testCaseIdKey = null;
		ReadExcel re = new ReadExcel();
		if (testContext == null)
			System.out.println("test context is null");
		else {
			
			String strfileName = testContext.getCurrentXmlTest().getParameter(
					strRingBufferFile);
			sheetName = testContext.getCurrentXmlTest().getParameter(
					strRingBufferSheet);
			testCaseIdKey = testContext.getCurrentXmlTest().getParameter(
					"TestCaseID");
			String dataProviderPath = testContext.getCurrentXmlTest().getParameter(
					"DataProviderPath");
			if(dataProviderPath!=null){
				fileName=dataProviderPath+strfileName;
			}
			else{
				fileName=strfileName;
			}
			System.out.println("Excel Path ="+dataProviderPath);
			System.out.println("File Name ="+fileName);
			
		}
		if (testCaseIdKey == null)
			testCaseIdKey = "TestCaseID";

		Map<String, Map<String, String>> mom=null;
		try {
			System.out.println("Current File Name in Common Util->"+fileName);
			System.out.println("Current Sheet Name in Common Util->"+sheetName);
			mom = re.getTestAllData(fileName,
					sheetName, testCaseIdKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int sizeOfMap = mom.size();
		Iterator<String> keyIterator = mom.keySet().iterator();

		Object m[][] = new Object[sizeOfMap][];
		for (int i = 0; i < sizeOfMap; i++) {
			m[i] = new Object[1];
			m[i][0] = mom.get(keyIterator.next());
		}
		return m;
	}
	
	public String generateHTMLReportForInputTestData(
			Map<String, String> inputTestDataMap, String testCaseName,
			String tableDisplayName) {
		return generateHTMLReportForInputTestData(inputTestDataMap,
				testCaseName, tableDisplayName, tableDisplayName);
	}

}
