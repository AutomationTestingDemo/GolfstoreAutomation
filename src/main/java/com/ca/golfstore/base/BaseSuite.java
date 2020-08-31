package com.ca.golfstore.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;

import com.ca.golfstore.api.ReportsBackup;
import com.ca.golfstore.util.CommonUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.DisplayOrder;

/**
 * @author munka03
 *
 */
@SuppressWarnings("unused")
public class BaseSuite {
	public static Map<Long, String> threadToTesNametMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	public static Map<Long, WebDriver> threadDriverMap=new HashMap<Long, WebDriver>();
	static XmlSuite testNgSuite = null;
		public static String golfstoreUrl=null;
		public static String dbHost=null;
		public static String dbPort = null;
		public static String dbSid = null;
		public static String dbAcsUsr = null;
		public static String dbPwd = null;
		public static String cardNum = null;
		public static String db3DSHost=null;
		public static String db3DSSid = null;
		public static String db3DSUsr = null;
		public static String baseURI = null;
		public WebDriver driver = null;
		public static String strReportfile=null;
		public static String strReportTitle=null;
		public static String strTestCaseName = null;
		public static String parallelType ="none";

		public static Map<String,String> risProperties=null;
		
		public static boolean isRISPropInitialized;
		
		public static Map<Long, String> threadToUrlLoadFlag = new HashMap<Long, String>();
		public static String serverPort = null;
		public static DesiredCapabilities capabilities =null;
		public static String db3DSPort=null;
		public static ExtentReports extentreporter;
		
		public static ExtentTest extenttest;

		public BaseSuite() {
			// TODO Auto-generated constructor stub
		}
		
		
		@BeforeSuite
		public void beforeSuite(ITestContext testContext) {
			
			XmlSuite suite = testContext.getSuite().getXmlSuite();
			testNgSuite = suite;
			String suiteName = suite.getName();
			
			initialiseRISProperties("Config/GS.properties");
			golfstoreUrl=getRISPropertyValue("golfStoreUrl");
			baseURI=getRISPropertyValue("BaseURI");

			dbHost=getRISPropertyValue("dbHost");
			dbPort=getRISPropertyValue("dbPort");
			dbSid=getRISPropertyValue("dbSid");
			dbAcsUsr=getRISPropertyValue("dbAcsDBusr");
			dbPwd=getRISPropertyValue("dbPwd");
			cardNum=getRISPropertyValue("cardNum");
			db3DSHost=getRISPropertyValue("db3DSHost");
			db3DSPort=getRISPropertyValue("db3DSPort");
			db3DSUsr=getRISPropertyValue("db3DSDBusr");
			strReportfile=getRISPropertyValue("reportFile");
			strReportTitle=getRISPropertyValue("reportTitle");
			
			
			//extentreporter = new ExtentReports(System.getProperty("user.dir")+"\\TestResult\\Reports\\GolfStoreTestResults.html");
			
			File TakeShotDir = new File(System.getProperty("user.dir")+"/TestResult/TestStepScreenShots");
			File htmlDir = new File(System.getProperty("user.dir")+"/TestResult/HTML");
			
			if (TakeShotDir.exists()) {
			    for (File file : TakeShotDir.listFiles()) {
			        file.delete();
			    }
			    if(TakeShotDir.delete()){
			    	
			    	//System.out.println("TestStepScreenShots Folder Delete Done");
			    }
			   
			}
			
			if (htmlDir.exists()) {
			    for (File file : htmlDir.listFiles()) {
			        file.delete();
			    }
			    if(htmlDir.delete()){
			    	
			    	//System.out.println("htmlDir Folder Delete Done");
			    }
			   
			}
			
			
			initialiseReport();
		}
		
		public synchronized void changeExtentTitle(Map<String, String> testCaseData){
			
			ExtentTest extentTest = BaseSuite.getTest();
			String strTestName = extentTest.getTest().getName();
			String strTestCaseId = testCaseData.get("TestCaseID") == null ? " "
					: testCaseData.get("TestCaseID");
			String strTestCaseTitle = testCaseData.get("TestCaseTitle") == null ? " "
					: testCaseData.get("TestCaseTitle");	
			
			String testDesc = "";

			System.out.println(" TestCaseName "+strTestName);
			
			strTestName = strTestCaseId+"_"+strTestName;
			
			strTestCaseName=strTestName;
			
			extentTest.getTest().setName(strTestName);
			
			//Code to Show the Title and Input XL sheet Related COntents in the Extent Report
			CommonUtils cu = new CommonUtils();
			// Test Data from Excel.
			String htmlfile = cu.generateHTMLReportForInputTestData(testCaseData,
					strTestCaseName, "Input XL Sheet Data for " + strTestCaseId,
					"Input XL Sheet Data");
			extentInfoLog(LogStatus.INFO,strTestCaseId+"_"+strTestCaseTitle, htmlfile);
		}
		
		@SuppressWarnings("deprecation")
		public void initialiseReport() {
			/*
			 * This method reads the report file name and creates it at given path after
			 * initialization the extent reports.
			 */
				if (extentreporter == null) {

					String dest = System.getProperty("user.dir")+strReportfile;
					System.out.println(" Extent Report path is " + dest);
					extentreporter = new ExtentReports(dest, true,
							DisplayOrder.OLDEST_FIRST);
					extentreporter.config().documentTitle(strReportTitle);
				}
			}
		
		
		public static void extentInfoLog(LogStatus testStatus, String stepName,
				String description) {

			if (testStatus.compareTo(LogStatus.WARNING) == 0) {
				testStatus = LogStatus.INFO;
			}

			ExtentTest extenttest = BaseSuite.getTest();
			extenttest.log(testStatus, stepName);
			extenttest.log(testStatus, description);

		}
		
		public synchronized static ExtentTest getTest() {
			Long threadID = Thread.currentThread().getId();

			if (threadToTesNametMap.containsKey(threadID)) {
				String testName = threadToTesNametMap.get(threadID);
				return nameToTestMap.get(testName);
			}
			// In actual scenario null should never happen
			return null;
		}

		private void initialiseRISProperties(String risFile) {
			try {
				Properties properties = new Properties();

				FileInputStream fis = new FileInputStream(risFile);
				properties.load(fis);
				fis.close();
				System.out.println("Initialising the RIS properties from file - "
						+ risFile);
				risProperties = new HashMap<String, String>();
				for (String name : properties.stringPropertyNames())
					risProperties.put(name, properties.getProperty(name));

				isRISPropInitialized = true;

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				isRISPropInitialized = false;
			} catch (IOException e) {
				e.printStackTrace();
				isRISPropInitialized = false;
			}
		}

		
		public static String getRISPropertyValue(String parameterName) {
			if (parameterName != null && risProperties.containsKey(parameterName))
				return risProperties.get(parameterName);
			else
				System.out.println(parameterName + " : no such property exists");
			return null;
		}
		
		@BeforeClass(alwaysRun=true)
		@Parameters("browser")
		public  void launchBrowser(String browser){
			Long threadID = new Long(Thread.currentThread().getId());
			
			if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver","./exeFiles/chromedriver.exe");
			driver =new ChromeDriver();}
			
			if(browser.equals("Firefox")){
				System.setProperty("webdriver.firefox.marionette","./exeFiles/geckodriver.exe");
				driver =new FirefoxDriver();}
			
			if(browser.equals("ChromeHeadLess")){
				
				System.setProperty("webdriver.chrome.driver","./exeFiles/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
		        options.addArguments("--headless");
		         driver = new ChromeDriver(options);
				 
			}
			
			else if(browser.equals("FireFoxHeadLess")){
				
				FirefoxBinary firefoxBinary = new FirefoxBinary();
			    firefoxBinary.addCommandLineOptions("--headless");
				System.setProperty("webdriver.firefox.marionette","./exeFiles/geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();
				options.setBinary(firefoxBinary);
				driver =new FirefoxDriver(options);
				
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
	        BaseSuite.threadDriverMap.put(threadID, driver);
		    System.out.println("Before test-class. Thread id is: " + threadID);
		}
		
		@BeforeMethod
		public void beforeMethod(Method method) {
			
			extenttest = extentreporter.startTest(""+method.getName(),method.getName());
			Long threadID = Thread.currentThread().getId();
			String mthd = method.getName();
			nameToTestMap.put(mthd, extenttest);
			threadToTesNametMap.put(threadID, mthd);
			}
		
		@AfterMethod
		public void afterMethod(Method method){	
			
       // BaseSuite.endTest();
			
			extentreporter.endTest(extenttest);
			
		}
		
		@AfterClass (alwaysRun=true)
		public void closeBrowser(){
	   Long threadID = new Long(Thread.currentThread().getId());
		driver = threadDriverMap.get(new Long(threadID));
			driver.close();
			
			
		}
		
		public synchronized static String getScreenshotFilePath(WebDriver driver) throws IOException,
				WebDriverException, NullPointerException {
			
			if (driver == null) {
				System.out
						.println("ERROR: DRIVER IS NULL PLEASE DEBUG PLEASE CHECK");
				throw new NullPointerException(
						"Driver passed to take screen shot is null");
			}

			Date d = new Date();
			Timestamp t = new Timestamp(d.getTime());
			String timeStamp = t.toString();
			timeStamp = timeStamp.replace(' ', '_');
			timeStamp = timeStamp.replace(':', '_');
			File scrFile = null;
			try {
				scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						
			} catch (WebDriverException ex) {
				System.out
						.println("ERROR while taking screen shot please debug:\nmessage:"
								+ ex.getMessage()
								+ "\nURL"
								+ ex.getSupportUrl()
								+ "\nSystem Info:"
								+ ex.getSystemInformation()
								+ "\n BuildInfo:"
								+ ex.getBuildInformation()
								+ "\n additional Info:"
								+ ex.getAdditionalInformation());
				ex.printStackTrace();
				
				throw ex;
			}

			String destdir = System.getProperty("user.dir") + "\\" + "TestResult\\TestStepScreenShots";
			
			String relativedir = ".." + "\\" + "TestStepScreenShots" + "\\";
			
			if (!new File(destdir).exists())
				new File(destdir).mkdir();
			String destFile = destdir + "\\"+ "_" + timeStamp
					+ ".jpg";
			String reldirFile = relativedir + "\\"+ "_" + timeStamp
					+ ".jpg";
			try {
				FileHandler.copy(scrFile, new File(destFile));
			} catch (IOException e) {
				System.out.println("ERROR: IOException while copying");
				e.printStackTrace();
				throw e;
			}
			return reldirFile;
		}
		
		public static void takeScreenShot(WebDriver GSdriver,LogStatus testStatus, String stepName) {

			Long threadID = new Long(Thread.currentThread().getId());
			GSdriver = threadDriverMap.get(new Long(threadID));
			if (testStatus.compareTo(LogStatus.WARNING) == 0) {
				testStatus = LogStatus.INFO;
			}
			String destFile = null;
			try {
				destFile = BaseSuite.getScreenshotFilePath(GSdriver);
			} catch (NullPointerException e) {
				e.printStackTrace();
				if (extenttest != null)
					extenttest.log(LogStatus.WARNING,
							"Exception while capturing image " + e.getMessage()
									+ "\nstackTrace:"
									+ e.getStackTrace().toString());
				return;
			} catch (IOException e) {
				e.printStackTrace();
				if (extenttest != null)
					extenttest.log(LogStatus.WARNING,
							"Exception while capturing image " + e.getMessage()
									+ "\nstackTrace:"
									+ e.getStackTrace().toString());
				return;
			} catch (WebDriverException e) {
				e.printStackTrace();
				if (extenttest != null)
					extenttest.log(LogStatus.WARNING,
							"Exception while capturing image " + e.getMessage()
									+ "\nstackTrace:"
									+ e.getStackTrace().toString());
				return;
			}

			try {
				if (extenttest == null) {
					System.out
							.println("ERROR: Extent TEST IS NULL. IMAGE IS CAPTURED AT "
									+ destFile
									+ " BUT IMAGE WILL NOT BE ATTACHED TO EXTENT REPORT");
					System.out.println("ERROR: PLEASE DEBUG PLEASE LOOK INTO THIS");
					return;
				}
				extenttest.log(testStatus, stepName + " image attached below");
				String image = extenttest.addScreenCapture(destFile);
				extenttest.log(testStatus, stepName, image);
			} catch (Exception e) {
				e.printStackTrace();
				extenttest.log(LogStatus.WARNING,
						"Exception while capturing image " + e.getMessage()
								+ "\nstackTrace:" + e.getStackTrace().toString());
			}
		}
		
		public synchronized static void endTest(String testName) {

			if (nameToTestMap.containsKey(testName)) {

				extenttest = nameToTestMap.get(testName);
				nameToTestMap.remove(testName);
			}

			if (extenttest != null) {
				extentreporter.endTest(extenttest);
				extentreporter.flush();
			}
		}
		public synchronized static void endTest() {
			
			Long threadID = Thread.currentThread().getId();
			if (threadToTesNametMap.containsKey(threadID)) {
				String testName = threadToTesNametMap.get(threadID);
				extenttest = nameToTestMap.get(testName);
				nameToTestMap.remove(testName);
			}

			if (extenttest != null) {
				extentreporter.endTest(extenttest);
				extentreporter.flush();
			}

		}
		
		@AfterSuite
        public void afterSuite() throws IOException{
			
			extentreporter.flush();	
			extentreporter.close();	
			
		ReportsBackup.zippingReports();
		
        }
     }
