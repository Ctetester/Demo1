
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Googlesearch extends TestBase {
	
	
@Test
	public void googledemo() throws Throwable {
	try{
	APPLICATION_LOGS.debug("Execution Started");
	Reporter.log("Execution Started");
	//load browser
	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	 Map<String, Object> prefs = new HashMap<String, Object>();
	ChromeOptions options = new ChromeOptions();
	options.addArguments("--disable-extensions");
	options.addArguments("test-type");
	options.addArguments("start-maximized");
	options.addArguments("--js-flags=--expose-gc");  
	options.addArguments("--enable-precise-memory-info"); 
	options.addArguments("--disable-popup-blocking");
	options.addArguments("--disable-default-apps");
	options.addArguments("disable-infobars");
	prefs.put("credentials_enable_service", false);
   prefs.put("profile.password_manager_enabled", false);
   options.setExperimentalOption("prefs", prefs);
	//String[] switches = { "user-data-dir=" + chromeProfile};
	capabilities.setCapability("chrome.switches", "--disable-extensions");
	//capabilities.setCapability("chrome.switches", switches);
	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	capabilities.setCapability("cssSelectorsEnabled", true);
	
	System.setProperty("webdriver.chrome.driver","./ChromeDriver/chromedriver.exe");
	wd = new ChromeDriver(capabilities);
	d = new EventFiringWebDriver(wd);
	Reporter.log("Opened web Browser");
	APPLICATION_LOGS.debug("Opened web Browser");
		//load url
		d.get(Dto.getURl());
		Reporter.log("Navigated to Google");
		APPLICATION_LOGS.debug("Navigated to Google");
		
		Thread.sleep(3000);
		//enter search string
		
		d.findElement(By.name("q")).sendKeys(Dto.getsearch());
		Thread.sleep(2000);
		Reporter.log("Entered search string");
		APPLICATION_LOGS.debug("Entered search string");
		//click on first list item
		d.findElement(By.xpath("//*[@id='sbse0']/div[2]")).click();
		Reporter.log("Selected gmail from the list");
		APPLICATION_LOGS.debug("Selected gmail from the list");
		//close browser
		d.quit();
		Reporter.log("Execution completed");
		APPLICATION_LOGS.debug("Execution completed");
	}
	catch(Exception e)
	{
		e.printStackTrace();
		takeScreenShot("Googlesearcherror");
		throw e;
	}

}
}
