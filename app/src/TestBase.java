import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class TestBase extends WaitForCondition {
	public static Properties OR = null;
	public static Properties DR = null;
	public static EventFiringWebDriver d = null;
	public static WebDriver wd = null;
	public static String path, output;
	public static WebElement div;
	public static long wait_Time;
	public static Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
	public static final String DOWNLOAD_PATH = "C:\\Users\\"
			+ System.getProperty("user.name") + "\\Downloads";
	public static final String SRC_FOLDER = "C:\\Users\\"
			+ System.getProperty("user.name") + "\\Downloads";
	public static int sleep = 1000;
	public Select sel;
	public static ValueDTO Dto = new ValueDTO();
	public static Date now = new Date();
	public static Random random = new Random();
	public static String currentDate = new SimpleDateFormat("ddMMyyyyhhmmss")
			.format(now);
	String str = Dto.getURl();
	String str1[] = str.split("com");
	String navurl = str1[0];
	String Homepage = navurl + "com/index.aspx";
	// below variables are to get tool tip in the chart
	public static List<String> tips;
	public static List<WebElement> bars;
	double  start, finish, totalTime,LoadTime_Seconds;

	@BeforeSuite
	public void initialize() {
		try {
			APPLICATION_LOGS.debug("Starting the Resource Advisor suite");
			APPLICATION_LOGS.debug("Loading Object XPATHS");
			OR = new Properties();
			DR = new Properties();
			//FileInputStream fp = new FileInputStream(
			//System.getProperty("user.dir")+ "\\src\\config\\OR.properties");
		//	FileInputStream fpdata = new FileInputStream(System.getProperty("user.dir")	+ "\\src\\config\\DR.properties");
			//OR.load(fp);
			//DR.load(fpdata);
			PropertyConfigurator.configure(System.getProperty("user.dir")
					+ "\\log4j.properties");
			Logger.getRootLogger().setLevel(Level.OFF);
		} catch (Throwable t) {
			t.printStackTrace();
			APPLICATION_LOGS.debug("Error in initialize() of TestBase");
		}
	}

	@AfterSuite
	public void closeAll() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Runtime.getRuntime().exec("taskkill /F /IM ie.exe");
			Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		} catch (IOException e) {
			APPLICATION_LOGS.debug("Error in closeAll() of TestBase");
			e.printStackTrace();
		}
	}
	
	// screenshots
		public static void takeScreenShot(String fileName) {
			try {
				File scrFile = ((TakesScreenshot) d)
						.getScreenshotAs(OutputType.FILE);
				/*FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
						+ "\\Screenshots" + "\\" + fileName + ".jpg"));*/
				FileUtils.copyFile(scrFile, new File("..\\screenshots" + "\\" + fileName + ".jpg"));
			} catch (Exception t) {
				//ErrorUtil.addVerificationFailure(t);
				APPLICATION_LOGS.error("Error in taking screenshot"
						+ t.getMessage());
			}
		}
/*
	// method to check and accept alert
	public void checkAlert() {
		try {
			d.switchTo().alert().accept();
			APPLICATION_LOGS.debug("Alert accepted");
		} catch (Exception e) {
			APPLICATION_LOGS.debug("Error in checkAlert() of TestBase");
			e.printStackTrace();
		}
	}

	// Method to find element using Xpath using property file
	public static WebElement getObject_Xpath(String xpath) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.xpath(OR.getProperty(xpath)));
		} catch (Throwable e) {
			x = null;
			ErrorUtil.addVerificationFailure(e);
			APPLICATION_LOGS.debug("Error in getObject_Xpath() of TestBase");
			// e.printStackTrace();
		}
		return x;
	}

	// Method to find element using Xpath using Hard Coding
	public static WebElement getObject_Xpath_D(String xpath) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.xpath(xpath));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find object with xpath key -- "
					+ xpath);
		}
		return x;
	}

	// for explicit wait for xpath
	public WebElement explicitWait_Xpath(String name) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, 30))
					.until(ExpectedConditions.presenceOfElementLocated(By
							.xpath(name)));
			Thread.sleep(1500);
		} catch (Exception t) {
			// ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.debug("Cannot find element " + name);
		}
		return dynamicElement;
	}

	// for explicit wait for id
	public WebElement explicitWait_Id(String id) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, 30))
					.until(ExpectedConditions.presenceOfElementLocated(By
							.id(id)));
			Thread.sleep(1500);
		} catch (Exception t) {
			// ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.debug("Cannot find element " + id);
		}
		return dynamicElement;
	}

	// Method to find element using css using Property File
	public WebElement getObject_CSS(String css) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.cssSelector(OR.getProperty(css)));
		} catch (Throwable t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find object with id key -- " + css);
			Reporter.log("Cannot find object with id key -- " + css);
		}
		return x;
	}

	// Method to find element using ID using Hard Coding
	public static WebElement getObject_Css_D(String css) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.cssSelector(css));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS
					.error("Cannot find object with css selector key -- " + css);
		}
		return x;
	}

	// Noramal dropdown
	public void selectNoramalDropDown(String selectdropdown,
			String select1stoption) {
		try {
			Thread.sleep(sleep);
			getObject_Xpath(selectdropdown).click();
			APPLICATION_LOGS.debug("Clicked on " + selectdropdown
					+ " Input feild");
			getObject_Xpath(select1stoption).click();
			APPLICATION_LOGS.debug("Selected 1st option from "
					+ select1stoption + " drop down");
		} catch (Exception t) {
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.warn("The data in " + select1stoption
					+ " dropdown is not present" + t.toString());
		}
	}

	// Ajax dropdown
	public void selectAjaxDropDown(String selectdropdown, String select1stoption) {
		try {
			getObject_Xpath(selectdropdown).click();
			Thread.sleep(6000);
			APPLICATION_LOGS.debug("Clicked on " + selectdropdown
					+ " Input feild");
			getObject_Xpath(select1stoption).click();
			aJaxWait();
			APPLICATION_LOGS.debug("Selected 1st option from "
					+ select1stoption + " drop down");
		} catch (Exception t) {
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.warn("The data in" + select1stoption
					+ "dropdown is not present" + t.toString());
		}
	}

	// Method to find element using Name using Property File
	public static WebElement getObject_Name(String name) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.name(OR.getProperty(name)));
		} catch (Exception t) {
			x = null;
			APPLICATION_LOGS.error("Cannot find object with name key -- "
					+ name);
			ErrorUtil.addVerificationFailure(t);
		}
		return x;
	}

	// Method to find element using Name using Hard Coding
	public static WebElement getObject_Name_D(String name) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.name(name));
		} catch (Exception t) {
			x = null;
			APPLICATION_LOGS.error("Cannot find object with name key -- "
					+ name);
			ErrorUtil.addVerificationFailure(t);
		}
		return x;
	}

	// for explicit wait for name
	public WebElement explicitWait_Name(String name) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, 30))
					.until(ExpectedConditions.presenceOfElementLocated(By
							.name(OR.getProperty(name))));
		} catch (Exception t) {
			APPLICATION_LOGS.debug("Cannot find element "
					+ OR.getProperty(name));
		}
		return dynamicElement;
	}

	// Method to find element using LinkText using Hard Coding
	public static WebElement getObject_LinkText_D(String linkText) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.linkText(linkText));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find object with LinkText key -- "
					+ linkText);
		}
		return x;
	}

	// Method to find element using LinkText using Property File
	public static WebElement getObject_LinkText(String linkText) {
		WebElement x;
		try {
			x = d.findElement(By.linkText(OR.getProperty(linkText)));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find object with LinkText key -- "
					+ linkText);
		}
		return x;
	}

	// Method to find element using ID using Property File
	public static WebElement getObject_Id(String id) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.id(OR.getProperty(id)));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find object with id key -- " + id);
		}
		return x;
	}

	// Method to find element using ID using Property File
	public static WebElement getObject_Id_D(String id) {
		WebElement x;
		try {
			Thread.sleep(sleep);
			x = d.findElement(By.id(id));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find object with id key -- " + id);
		}
		return x;
	}

	*//**
	 * This verifyText() is used to check the availability of the values. It
	 * will accept one String parameter which will fetch the actual value from
	 * property file. If the parameter is not present in property file then it
	 * will consider it as the direct values.
	 * *//*
	public static String verifyText(String toBeVerifyValue) {
		String pattern = "";
		String rtnVal = "Pass";
		// APPLICATION_LOGS.debug("PAGE SOURCE: " + d.getPageSource());
		try {
			pattern = DR.getProperty(toBeVerifyValue);
			if (pattern == null)
				pattern = OR.getProperty(toBeVerifyValue);
			if (pattern == null)
				pattern = toBeVerifyValue;
			APPLICATION_LOGS.debug("Checking " + pattern);
			Assert.assertTrue(d.getPageSource().contains(pattern.trim()));
		} catch (Exception t) {
			APPLICATION_LOGS.error("Text - " + pattern + " not present");
			rtnVal = "Fail - ";
			t.printStackTrace();
			throw t;
		}
		return rtnVal;
	}

	// method to compare two words
	public static String verifyText(String expected, String actual) {
		String rtnVal = "Pass";
		try {
			APPLICATION_LOGS.debug("Executing verifyText");
			APPLICATION_LOGS.debug("Expected " + expected);
			APPLICATION_LOGS.debug("Actual " + actual);
			Assert.assertEquals(expected, actual);
		} catch (Exception t) {
			APPLICATION_LOGS.debug("Error in text - actual:" + actual
					+ "   expected:" + expected);
			rtnVal = "Fail -";
			t.printStackTrace();
			throw t;
		}
		return rtnVal;
	}

	public static String verifyText_Direct(String text) {
		String rtnVal = "Pass";
		try {
			APPLICATION_LOGS.debug("Executing verifyText");
			APPLICATION_LOGS.debug("Chekcing " + text);
			Assert.assertTrue(d.getPageSource().contains(text));
		} catch (Exception t) {
			APPLICATION_LOGS.error("Text - " + text + " not present");
			rtnVal = "Fail -";
			t.printStackTrace();
			throw t;
		}
		return rtnVal;
	}

	// for explicit wait for css
	public WebElement explicitWait_CSS(String name) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, 30))
					.until(ExpectedConditions.presenceOfElementLocated(By
							.cssSelector(name)));
		} catch (Throwable t) {
			APPLICATION_LOGS.debug("Cannot find element " + name);
		}
		return dynamicElement;
	}

	// screenshots
	public static void takeScreenShot(String fileName) {
		try {
			File scrFile = ((TakesScreenshot) d)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
					+ "\\Screenshots" + "\\" + fileName + ".jpg"));
		} catch (Exception t) {
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Error in taking screenshot"
					+ t.getMessage());
		}
	}

	public void PDFValidation(String path) {
		try {
			d.get(path);
			URL url = new URL(d.getCurrentUrl());
			BufferedInputStream fileToParse = new BufferedInputStream(
					url.openStream());
			PDFParser parser = new PDFParser(fileToParse);
			parser.parse();
			output = new PDFTextStripper().getText(parser.getPDDocument());
			parser.getPDDocument().close();
			Thread.sleep(2000);
			d.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reNameFile(String file1, String file2) {
		try {
			File oldfile = new File(file1);
			File newfile = new File(file2);
			if (oldfile.exists()) {
				oldfile.renameTo(newfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// true- present
	// false - not present
	public static boolean isElementPresent(String element_xpath) {
		int count = d.findElements(By.xpath(OR.getProperty(element_xpath)))
				.size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// true- present
	// false - not present
	public static boolean isElementPresent_D(String element_xpath) {
		int count = d.findElements(By.xpath((element_xpath))).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// true- present
	// false - not present
	public static boolean isElementPresentCSS(String element_CSS) {
		int count = d.findElements(By.cssSelector(OR.getProperty(element_CSS)))
				.size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// true- present
	// false - not present
	public static boolean isElementPresentCSS_D(String element_CSS) {
		int count = d.findElements(By.cssSelector(element_CSS)).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// Ajax Wait
	public void aJaxWait() {
		try {
			WebDriverWait wait = new WebDriverWait(d, 100);
		
			// for (int t = 0; t <2; t++) {
			Thread.sleep(1000);
			if (d.findElements(By.xpath("//*[@id='ctl00_Img13']")).size() != 0
					&& d.findElement(By.xpath("//*[@id='ctl00_Img13']"))
							.isDisplayed() == true) 

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.xpath("//*[@id='ctl00_Img13']")));
			
			
				
			

			else if (d.findElements(By.cssSelector("div.loading-widget"))
					.size() != 0
					&& d.findElement(By.cssSelector("div.loading-widget"))
							.isDisplayed() == true) 

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.cssSelector("div.loading-widget")));
				
			

			else if (d.findElements(By.cssSelector("div.loading-element"))
					.size() != 0
					&& d.findElement(By.cssSelector("div.loading-element"))
							.isDisplayed() == true) 
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.cssSelector("div.loading-element")));
				
			

			else if (d.findElements(By.cssSelector("div.loading-element>img"))
					.size() != 0
					&& d.findElement(By.cssSelector("div.loading-element>img"))
							.isDisplayed() == true) 

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.cssSelector("div.loading-element>img")));
				
			

			else if (d
					.findElements(
							By.xpath("//div[contains(@id,'centerPanel')]/div/div/div[3]/div/img"))
					.size() != 0
					&& d.findElement(
							By.xpath("//div[contains(@id,'centerPanel')]/div/div/div[3]/div/img"))
							.isDisplayed() == true) 
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.xpath("//div[contains(@id,'centerPanel')]/div/div/div[3]/div/img")));
			else if(d.findElements(By.cssSelector("div.loading-widget.loading-element>img"))
					.size() != 0
					&& d.findElement(By.cssSelector("div.loading-widget.loading-element>img"))
							.isDisplayed() == true)
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.cssSelector("div.loading-widget.loading-element>img")));
			
			else if (d.findElements(
					By.xpath("//*[contains(@src,'img/animated-waiting-circle.gif')]")).size() != 0
					&& d.findElement(
							By.xpath("//*[contains(@src,'img/animated-waiting-circle.gif')]"))
							.isDisplayed() == true) 
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.xpath("//*[contains(@src,'img/animated-waiting-circle.gif')]")));
						

			else if (d.findElements(
					By.xpath("//*[@id='gemini']/div[2]/div/img")).size() != 0
					&& d.findElement(
							By.xpath("//*[@id='gemini']/div[2]/div/img"))
							.isDisplayed() == true) 
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By
						.xpath("//*[@id='gemini']/div[1]/div/img")));
				
			

			else

				Thread.sleep(1000);

			// }
		} catch (Exception t) {
			System.out.println("ajaxwait is still available");
			// System.out.println("Actual Image not Found");
			// t.printStackTrace();
		}
	}
	
	
	
	
	*//*************** Application Specific Functions ****************//*

	// Edit Widget
	public void editWidget() {
		// Edit Widget
		getObject_CSS("WidgetMenu_Icon").click();
		getObject_Xpath("EditWidget").click();
		APPLICATION_LOGS.debug("Clicked on the widget Edit option");
	}

	// Close Widget
	public void closeWidget() {
		// Close Widget
		getObject_CSS("WidgetMenu_Icon").click();
		getObject_Xpath("CloseWidget").click();
		APPLICATION_LOGS
				.debug("Clicked on close link from the widget menu icon");
	}

	// Close Widget Library
	public void widgetLibrary() {
		getObject_Xpath("WidgetLibrary").click();
		APPLICATION_LOGS
				.debug("Clicked on the widget library tab which is visible on the left side");
	}

	// Save Edited Widget
	public void savewidget() {
		// Save Edited
		getObject_Xpath("SaveWidget").click();
		aJaxWait();
		APPLICATION_LOGS.debug("Clicked on save button");
		Reporter.log("Clicked on save button");
	}

	public void dragAndDrop(String src, String tar) {
		WebElement s = getObject_Xpath(src);
		WebElement t = getObject_Xpath(tar);
		Actions act = new Actions(d);
		if ("ie".equalsIgnoreCase(Dto.getWebDriverObj())) {
			act.clickAndHold(s).perform();
			act.moveToElement(t).perform();
			act.release().perform();
			aJaxWait();
		} else {
			act.dragAndDrop(s, t).build().perform();
			aJaxWait();
		}
		APPLICATION_LOGS.debug("Selected " + src
				+ " widget and Drag it to the dashboard page");
	}

	public void Startdatecalendar() {
		path = "//*[@id='ctl00_ContentPlaceHolder1_rdpStaticStartdate_calendar_Title']";
		div = explicitWait_Xpath(path);
		div.click();
		path = "//*[@id='rcMView_Jan']/a";
		div = explicitWait_Xpath(path);
		div.click();
		path = "//*[@id='rcMView_2013']/a";
		div = explicitWait_Xpath(path);
		path = "//*[@id='rcMView_OK']";
		div = explicitWait_Xpath(path);
		div.click();
		path = "//*[@id='ctl00_ContentPlaceHolder1_rdpStaticStartdate_calendar_Top']/tbody/tr[1]/td[3]/a";
		div = explicitWait_Xpath(path);
		div.click();

	}

	public void StartendDate() {
		path = "//*[@id='ctl00_ContentPlaceHolder1_rdpStaticEnddate_calendar_Title']";
		div = explicitWait_Xpath(path);
		div.click();
		path = "//*[@id='rcMView_Feb']/a";
		div = explicitWait_Xpath(path);
		div.click();
		path = "//*[@id='rcMView_2014']/a";
		div = explicitWait_Xpath(path);
		path = "//*[@id='rcMView_OK']";
		div = explicitWait_Xpath(path);
		div.click();
		path = "//*[@id='ctl00_ContentPlaceHolder1_rdpStaticEnddate_calendar_Top']/tbody/tr[1]/td[3]/a";
		div = explicitWait_Xpath(path);
		div.click();

	}

	public static void multiselect(String xpath, String value) throws Exception {
		try {
			System.out.println(xpath);
			// String x="/div/button";
			// WebElement mul=d.findElement(By.xpath(xpath));
			// System.out.println(mul);
			// mul.click();
			xpath = xpath + "/div/ul";
			WebElement parent1 = d.findElement(By.xpath(xpath));
			List<WebElement> children1 = parent1.findElements(By.tagName("li"));
			int k = children1.size();
			String options[] = value.split(",");

			for (int i = 0; i < options.length; i++) {
				options[i].trim();
				String str1 = options[i].trim();
				String xpath_st = xpath + "/li[";
				String xpath_mid = "]/label";
				String xpath_end = "/input";
				for (int j = 1; j <= k; j++) {
					WebElement multiselect = d.findElement(By.xpath(xpath_st
							+ j + xpath_mid));
					String str = multiselect.getText();
					WebElement select = d.findElement(By.xpath(xpath_st + j
							+ xpath_mid + xpath_end));
					if (str1.trim().equals(str.trim())) {
						select.click();
						Thread.sleep(1000);
					}
				}
			}
			Thread.sleep(1500);
		} catch (Throwable t) {
			t.printStackTrace();
			APPLICATION_LOGS.warn("Cannot find the dropdown id: " + xpath);
		}
	}

	// PAM functions
	public void goToAnalysisPage() {
		getObject_Xpath("AnalyzeMenu").click();
		getObject_Xpath("PerformanceAnalyticsModule").click();
		aJaxWait();
		APPLICATION_LOGS.debug("On Analysis home page");
	}

	public void goToPAMCard(String card) {
		switch (card) {
		case "ComparisonAnalysisCard":
			getObject_Id("ComparisonAnalysisCard").click();
			break;
		case "TrendAnalysisCard":
			getObject_Id("TrendAnalysisCard").click();
			break;
		case "LoadProfileAnalysisCard":
			getObject_Id("LoadProfileAnalysisCard").click();
			break;
		case "CalendarAnalysisCard":
			getObject_Id("CalendarAnalysisCard").click();
			break;
		default:
			System.out.println("Card not found");
		}
	}

	public String getBrowserName() {
		return Dto.getWebDriverObj().toLowerCase();
	}

	public void enterFixedDateRange(String startDate, String endDate) {
		getObject_Xpath("FixedDateMenu").click();
		getObject_Id("TimeLineFixedStartDate").clear();
		getObject_Id("TimeLineFixedStartDate").sendKeys(startDate);
		getObject_Id("TimeLineFixedEndDate").clear();
		getObject_Id("TimeLineFixedEndDate").sendKeys(endDate);
		//getObject_Xpath("ApplyFixedDate").click();
		getObject_Css_D("button.btn.btn-xs.btn-success.pull-right").click();
	}

	public boolean waitForPageLoad() {
		Wait<WebDriver> wait = new WebDriverWait(d, 60);
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				try {
					return ((Long) ((JavascriptExecutor) d)
							.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) d)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	public void dashboard(String dashboardname) throws InterruptedException {
		try {
		
			explicitWait_Xpath("//input[contains(@id,'ddlDashboards_Input')]");
			d.findElement(
					By.xpath("//input[contains(@id,'ddlDashboards_Input')]"))
					.click();
			path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul";
			WebElement board = d.findElement(By.xpath(path));
			List<WebElement> options = board.findElements(By.tagName("li"));
			int xpathCount = options.size();
			List<String> a = new ArrayList<String>();
			for (int i = 1; i <= xpathCount; i++) {
				// to get the list of dashbords to an array list
				Thread.sleep(2000);
				path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["
						+ i + "]/div/span[2]";
				div = this.findElement(d, By.xpath(path));
				a.add(div.getText());
			}
			if (a.contains(dashboardname)) {
				int index = a.indexOf(dashboardname) + 1;
				path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["
						+ index + "]/div/span[2]";
				div = this.findElement(d, By.xpath(path));
				div.click();
			
				aJaxWait();
				
			} else {
				getObject_Id("ManageDashboard").click();
				Thread.sleep(2000);
				WebElement iframe = d
						.findElement(By
								.xpath("/html/body/form/div[1]/table/tbody/tr[2]/td[2]/iframe"));
				d.switchTo().frame(iframe);
				WebElement form = d.findElement(By.id("form1"));
				WebElement input = form.findElement(By.id("rtbDashboardTitle"));
				input.sendKeys(dashboardname);
				WebElement save = form.findElement(By.id("lblsave"));
				save.click();
				aJaxWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
			APPLICATION_LOGS.debug("Error in dashboard() of TestBase");
		}
	}

	public void verifydashboard(String dashboardname)
			throws InterruptedException {
		try {
			waitForPageLoad();
			aJaxWait();
			d.findElement(
					By.xpath("//input[contains(@id,'ddlDashboards_Input')]"))
					.click();
			path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul";
			WebElement board = d.findElement(By.xpath(path));
			List<WebElement> options = board.findElements(By.tagName("li"));
			int xpathCount = options.size();
			List<String> a = new ArrayList<String>();
			for (int i = 1; i <= xpathCount; i++) {
				path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["
						+ i + "]/div/span[2]";
				div = this.findElement(d, By.xpath(path));
				a.add(div.getText());
			}
			for (String ls : a) {
				if (ls.equalsIgnoreCase(dashboardname)) {
					int index = a.indexOf(dashboardname) + 1;
					// to click on the given dashboard
					path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["
							+ index + "]/div/span[2]";
					div = this.findElement(d, By.xpath(path));
					div.click();
					Thread.sleep(2000);
					aJaxWait();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletedashboard(String dashboardname) {
		try {
			waitForPageLoad();
			aJaxWait();
			d.findElement(
					By.xpath("//input[contains(@id,'ddlDashboards_Input')]"))
					.click();
			path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul";
			WebElement board = d.findElement(By.xpath(path));
			List<WebElement> options11 = board.findElements(By.tagName("li"));
			int xpathCount11 = options11.size();
			if (xpathCount11 > 1) {
				List<String> a = new ArrayList<String>();
				for (int i = 1; i <= xpathCount11; i++) {
					path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["
							+ i + "]/div/span[2]";
					div = this.findElement(d, By.xpath(path));
					a.add(div.getText());
				}
				br1: for (String ls : a) {
					if (ls.equalsIgnoreCase(dashboardname)) {
						int index = a.indexOf(dashboardname) + 1;
						path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["
								+ index + "]/div/span[2]";
						div = this.findElement(d, By.xpath(path));
						div.click();
						Thread.sleep(2000);
						aJaxWait();
						// path="//div[contains(@id,'UpdatePanel1')]/div/div[3]/a[1]";
						path = "//img[contains(@src,'App_Themes/Summit/images/dashboard-edit.png')]";
						div = this.findElement(d, By.xpath(path));
						div.click();
						Thread.sleep(2000);
						aJaxWait();
						path = "//div[1]/ul/li[10]/a/span";
						div = d.findElement(By.xpath(path));
						// String s=div.getText();
						// System.out.println(s);
						div.click();
						aJaxWait();
						d.findElement(
								By.xpath("//div[contains(@id,'confirm')]/div/div[2]/a[1]/span/span"))
								.click();
						aJaxWait();
						break br1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * This method is used to select the measurements based on 2nd parameter. It
	 * accepting 2 parameter . 1:Search values with separated by | 2:
	 * measurements type with separated by | Make sure the order of search value
	 * and order of measurement value must be same like
	 * selectmesurement("aa|bb|cc|dd","aa kwh,kvh|bb kwh,kvh|cc kvh|dd kwh")
	 * 
	 * @throws Throwable
	 * 
	 *//*
	public void selectmesurement(String searchstrings) throws Throwable {
		int outerBreak = 0, innerBreak = 0;
		try {
			// List<String> measurementList =
			// Arrays.asList(measurements.split("\\|"));
			List<String> searchstringList = Arrays.asList(searchstrings
					.split("\\|"));
			explicitWait_Xpath(OR.getProperty("Electricytab"));
			// if the sort is available if ascending we need to click two times.
			// if sort is in desecnding
			// we need to click one time to make no sort

			if (isElementPresent("Ascendingicon") == true) {

				getObject_Xpath("Ascenddesendiconxpath").click();
				Thread.sleep(600);
				getObject_Xpath("Ascenddesendiconxpath").click();
			} else if (isElementPresent("Descendingicon") == true) {
				getObject_Xpath("Ascenddesendiconxpath").click();
			}

			// to scroll down
			// JavascriptExecutor jse = (JavascriptExecutor) d;
			for (String search : searchstringList) {
				getObject_Xpath("searchmesurement").clear();
				Thread.sleep(1000);
				getObject_Xpath("searchmesurement").sendKeys(search);
				Thread.sleep(1000);

				List<WebElement> measure = d
						.findElements(By
								.xpath("//div[contains(@class,'ag-body-container')]/div"));

				if (measure.size() == 0) {
					getObject_Xpath("Closemeasurementdialog").click();
					Assert.assertFalse(false);
					Reporter.log("No Measurement available with the search string  :: "
							+ search);
				} else {
					for (int i = 1; i <= measure.size(); i++) {

						WebElement e = getObject_Xpath_D("//div[contains(@class,'ag-body-container')]/div["
								+ i
								+ "]/div[2]/span[1]");
						// The below step cheks the string from the application
						// and the string passed to this method
						String str = e.getText().toLowerCase().contains(search.toLowerCase()) ? search
								: null;

						innerBreak = 0;
						if (str != null && searchstringList.contains(str)) {
							List<WebElement> element =d.findElements(By.xpath("//div[contains(@class,'ag-body-container')]/div["+i+"]/div[1]/i[contains(@class,'fa fa-lg clickable fa-plus-circle')]"));
							if (element.size() > 0)
							{
								getObject_Xpath_D("//div[contains(@class,'ag-body-container')]/div[" +i+ "]/div[1]/i[contains(@class,'fa fa-lg clickable fa-plus-circle')]" ) .click();
							}

							//WebElement commodity = getObject_Xpath_D("//div[contains(@class,'ag-body-container')]/div[" +i+ "]/div[1]/i[contains(@class,'fa fa-lg clickable fa-plus-circle')]");
							//commodity.click();

							// List<WebElement> element =
							// d.findElements(By.xpath("//div[contains(@class,'ag-body-container')]/div["+i+"]/div[1]/i[contains(@class,'fa fa-lg clickable fa-plus-circle')]"));
							// div[contains(@class,'ag-body-container')]/div[1]/div[2]/span[contains(@class,'clickable')]
							
							 * if (element.size() > 0) { getObject_Xpath_D(
							 * "//div[contains(@class,'ag-body-container')]/div["
							 * +i+
							 * "]/div[1]/i[contains(@class,'fa fa-lg clickable fa-plus-circle')]"
							 * ) .click(); Thread.sleep(1000);
							 * //jse.executeScript("scroll(0, 250);");
							 * 
							 * }
							 
								 * else { //
								 * System.out.println("Already selected");
								 * jse.executeScript("scroll(0, 250);"); }
								 

							// new path for clicking on commodity
							
							 * List<WebElement> element =
							 * d.findElements(By.xpath
							 * ("//div[contains(@class,'ag-body-container')]/div["
							 * +
							 * i+"]/div[2]/span[contains(@class,'clickable')]"))
							 * ; if (element.size() > 0) { WebElement
							 * commodity=getObject_Xpath_D
							 * ("//div[contains(@class,'ag-body-container')]/div["
							 * +
							 * i+"]/div[2]/span[contains(@class,'clickable')]");
							 * if(commodity.getText().contains(search))
							 * Thread.sleep(1000);
							 * //jse.executeScript("scroll(0, 250);");
							 * 
							 * }
							 
							++outerBreak;
							++innerBreak;
						}
						if (innerBreak == 1)
							break;
					}
					if (outerBreak == searchstringList.size())
						break;

				}

			}
			getObject_Xpath("SaveandClose").click();

			// jse.executeScript("scroll(0, -250);");
			explicitWait_Xpath(OR.getProperty("LindyStateTest"));
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}

	*//**
	 * This method is used to select the measurements based the searchstrings
	 * 
	 * @throws Throwable
	 * 
	 *//*
	public void SelectCalculatedMesurement(String searchstrings)
			throws Throwable {
		// int i;
		// List<WebElement> measurements1;
		WebElement dialog;
		try {

			// commented for reference
			
			 * WebElement container =
			 * d.findElement(By.className(OR.getProperty("CSTemplateContainer"
			 * ))); //examine each measurements title measurements1 =
			 * container.findElements(By.tagName("commodity-def-template"));
			 * 
			 * br1: for(i=1;i<=measurements1.size();i++){
			 * 
			 * dialog=getObject_Xpath_D(
			 * "//div[contains(@class,'collapse in')]/commodity-def-template["
			 * +i+"]/div/span[1]/h4");
			 * 
			 * if(dialog.getText().contains(searchstrings)) {
			 * dialog=getObject_Xpath_D
			 * ("//div[contains(@class,'collapse in')]/commodity-def-template["
			 * +i+"]/div/i"); dialog.click(); break br1; } }
			 
			explicitWait_Xpath(OR.getProperty("FilterCalcMesurement"));
			getObject_Xpath("FilterCalcMesurement").clear();
			getObject_Xpath("FilterCalcMesurement").sendKeys(searchstrings);
			explicitWait_Xpath(OR.getProperty("SelectMesurement"));
			dialog = getObject_Xpath("SelectMesurement");
			dialog.click();

		} catch (Throwable t) {
			t.printStackTrace();
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find  -- " + searchstrings);
			throw t;
		}
	}

	*//**
	 * This method is used to select the measurements based the searchstrings in
	 * configure tab
	 * 
	 * @throws Throwable
	 * 
	 *//*
	public void SelectCalcMesurementconfigurationtab(String searchstrings)
			throws Throwable {
		// int i;
		// List<WebElement> measurements1;
		try {
			// commented for reference

			// explicitWait_Xpath("//commodity-tab/ul/li[2]/span");
			// commented this code for reference
			
			 * WebElement container =
			 * d.findElement(By.className(OR.getProperty("CSTemplateContainer"
			 * ))); //examine each measurements title measurements1 =
			 * container.findElements(By.tagName("commodity-def-location"));
			 * 
			 * br1: for(i=1;i<=measurements1.size();i++){
			 * 
			 * dialog=getObject_Xpath_D(
			 * "//div[contains(@class,'collapse in')]/commodity-def-location["
			 * +i+"]/div/div/h4");
			 * 
			 * if(dialog.getText().contains(searchstrings)) {
			 * dialog=getObject_Xpath_D
			 * ("//div[contains(@class,'collapse in')]/commodity-def-location["
			 * +i+"]/div/div/span/i"); dialog.click(); break br1; } }
			 
			explicitWait_Xpath(
					OR.getProperty("FilterCalcMesurementconfigurationtab"))
					.clear();
			getObject_Xpath("FilterCalcMesurementconfigurationtab").sendKeys(
					searchstrings);
			explicitWait_Xpath(
					OR.getProperty("SelectMesurementconfigurationtab")).click();

		} catch (Throwable t) {
			t.printStackTrace();
			ErrorUtil.addVerificationFailure(t);
			APPLICATION_LOGS.error("Cannot find  -- " + searchstrings);
			throw t;
		}
	}

	public void CheckAndExitOneClickMode(EventFiringWebDriver d)
			throws Throwable {
		try {
			explicitWait_Xpath("//*[@id='locationOptionButton2']/i[1]");
			System.out.println(getObject_Css_D("span#locationOptionButton2")
					.getCssValue("background-color"));
			if (!getObject_Css_D("span#locationOptionButton2").getCssValue(
					"background-color").equals("rgba(169, 169, 169, 1)")) {
				explicitWait_Xpath(OR.getProperty("clickonpointer"));
				getObject_Xpath("clickonpointer").click();
				Thread.sleep(200);
				getObject_CSS("oneclickmode").click();

			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}

	public void searchnames(String searchname) throws Throwable {

		try {
			List<WebElement> element = d.findElements(By
					.xpath("//*[@id='treeFilter']"));

			if (!(element.size() == 0)) {
				getObject_Xpath_D("//*[@id='treeFilter']").clear();
				getObject_Xpath_D("//*[@id='treeFilter']").sendKeys(searchname);
				Thread.sleep(2000);
			} else {
				Assert.assertFalse(false);

			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}

	public static List<String> TrendGetChartToolTip() throws Throwable {

		try {// Tool tip verification
			getObject_Xpath("TableDataTab").click();
			tips = new ArrayList<String>();

			bars = d.findElements(By.xpath(OR.getProperty("tooltipbar")));
			for (WebElement bar : bars) {
				Actions a = new Actions(d);
				a.moveToElement(bar).build().perform();
				WebElement tip = d.findElement(By.xpath(OR
						.getProperty("tooltiptext")));
				tips.add(tip.getText());

			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return tips;
	}

	public static List<String> LPGetChartToolTip() throws Throwable {

		try {// Tool tip verification
			getObject_Xpath("TableDataTab").click();
			tips = new ArrayList<String>();
			// WebElement s=
			// d.findElement(By.xpath("//*[contains(@class,'highcharts-grid highcharts-xaxis-grid')]/*[contains(@class,'highcharts-grid-line')]"));
			bars = d.findElements(By
					.xpath("//*[contains(@class,'highcharts-grid highcharts-xaxis-grid')]/*[contains(@class,'highcharts-grid-line')]"));
			System.out.println(bars.size());
			int count = 0;
			for (WebElement bar : bars) {

				if (bar.getAttribute("stroke") != null
						&& bar.getAttribute("stroke").equals("#D3D3D3")) {
					Actions a = new Actions(d);
					System.out.println(bar.getAttribute("stroke"));

					a.moveToElement(bar);
					if (d.findElement(By.xpath(OR
							.getProperty("loadtooltiptext"))) != null) {
						WebElement tip = d.findElement(By.xpath(OR
								.getProperty("tooltiptext")));
						tips.add(tip.getText());
						System.out.println(tip.getText());
						count = count + 1;
						System.out.println(count);
					}
				} else {

				}

			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return tips;
	}

	public static List<String> CLPGetChartToolTip() throws Throwable {

		try {// Tool tip verification
				// getObject_Xpath("TableDataTab").click();
			tips = new ArrayList<String>();
			// WebElement s=
			// d.findElement(By.xpath("//*[contains(@class,'highcharts-grid highcharts-xaxis-grid')]/*[contains(@class,'highcharts-grid-line')]"));
			bars = d.findElements(By
					.xpath("//*[@id='highcharts-120']/*[contains(@class,'highcharts-root')]/*[contains(@class,'highcharts-series-group')]/*[contains(@class,'highcharts-markers highcharts-series-0 highcharts-line-series highcharts-color-undefined highcharts-tracker')]/*[contains(@fill,'#2fb5ea')]"));
			System.out.println(bars.size());
			int count = 0;
			for (WebElement bar : bars) {

				Actions a = new Actions(d);

				// if(bar.getAttribute("fill")!=null &&
				// bar.getAttribute("fill").equals("#2fb5ea") )
				// {
				// System.out.println(bar.getAttribute("stroke"));

				a.moveToElement(bar).click(bar).build().perform();
				if (d.findElement(By.xpath(OR.getProperty("tooltiptext"))) != null) {
					WebElement tip = d.findElement(By.xpath(OR
							.getProperty("tooltiptext")));
					tips.add(tip.getText());
					System.out.println(tip.getText());
					count = count + 1;
					System.out.println(count);
				}
				// }
				// else
				// {

				// }

			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return tips;
	}
	
	
	//get time after loading page
	
	public void pageloadstart()
	{
		start = System.currentTimeMillis();
		
      	}
	

		//get time after loading page
		
			public void pageloadstop(String name,double time)
			{
							
				finish = System.currentTimeMillis();
				totalTime = finish - start;
				LoadTime_Seconds = totalTime / 1000;
				System.out.println("Time to  load - "  +name+"::::"
						+ LoadTime_Seconds + " " +" seconds");
				Reporter.log("Time taken to load  : :  " +name+" ::::" + LoadTime_Seconds+ " " +" seconds");
			if((LoadTime_Seconds)>(time))
			{
				System.out.println("***   Note :: Loaded time :: "+" of " +name + " is  "+LoadTime_Seconds   +   ",but should load below  " +  time +"seconds  ***");
				Reporter.log("*** Note :: Loaded time :: "+" of "+name +" is "+LoadTime_Seconds   +   ",but should load below  " +  time +"seconds  ***");
				
			}
				
		    
			}
*/
}
