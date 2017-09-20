import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;

public class BrowserCallingTC extends TestBase {

	protected void browserSelection() {

		if ("firefox".equalsIgnoreCase(Dto.getWebDriverObj())) {
			
			DesiredCapabilities dc=DesiredCapabilities.firefox();
			File profileDirectory = new File(Dto.getFirefoxProfilePath());
			
			//FirefoxProfile profile = new FirefoxProfile(profileDirectory);
			FirefoxProfile fxProfile = new FirefoxProfile(profileDirectory);
			fxProfile.setAcceptUntrustedCertificates(false);
			fxProfile.setAssumeUntrustedCertificateIssuer(true);
			fxProfile.setPreference("browser.download.folderList", 2);
			fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
			fxProfile.setPreference("browser.download.dir", DOWNLOAD_PATH+ "\\");
			fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
			fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf,application/ms-excel,text/csv,image/jpeg,image/svg+xml,image/png,application/csv,application/vnd.ms-excel,application/octet-stream");
		//	System.setProperty("webdriver.gecko.driver","./geckodriver/geckodriver.exe");
			dc = DesiredCapabilities.firefox();
			dc.setCapability(FirefoxDriver.PROFILE, fxProfile);
			dc.setCapability("marionette", true);
			wd = new FirefoxDriver(dc);
			d = new EventFiringWebDriver(wd);
			maximiseWindow();
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if ("ie".equalsIgnoreCase(Dto.getWebDriverObj())) {
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			  caps.setCapability("ignoreZoomSetting", true);
			  caps.setCapability("nativeEvents", true);    
				caps.setCapability("unexpectedAlertBehaviour", "accept");
				caps.setCapability("ignoreProtectedModeSettings", true);
				caps.setCapability("disable-popup-blocking", true);
				caps.setCapability("enablePersistentHover", true);
				caps.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings",true);
				
			//caps.setCapability("requireWindowFocus", false);
			File file = new File(Dto.Iepath);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			//caps.setCapability("requireWindowFocus", false);
			wd = new InternetExplorerDriver(caps);
			d = new EventFiringWebDriver(wd);
			System.out.println("IE Browser Launched");
			maximiseWindow();
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			

			
		} else if ("chrome".equalsIgnoreCase(Dto.getWebDriverObj())) {
			String chromeProfile = Dto.getChromeProfilePath();
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
			String[] switches = { "user-data-dir=" + chromeProfile};
			capabilities.setCapability("chrome.switches", "--disable-extensions");
			capabilities.setCapability("chrome.switches", switches);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability("cssSelectorsEnabled", true);
			System.setProperty("webdriver.chrome.driver",Dto.getChromedriverpath());
			wd = new ChromeDriver(capabilities);
			d = new EventFiringWebDriver(wd);
		//	d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						
		} else if ("Invisible Mode".equalsIgnoreCase(Dto.getWebDriverObj())) {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,Dto.getPhantomJsPath());
			dc.setCapability("takeScreenshot", true);
			wd = new PhantomJSDriver(dc);
			d = new EventFiringWebDriver(wd);
			//d.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			d.manage().window().maximize();
			
		} else {
			Reporter.log("Enter Valid browser name");
		}
	
	}

	public EventFiringWebDriver browserSelection(WebDriver wd) {

		if ("firefox".equalsIgnoreCase(Dto.getWebDriverObj())) {
			File profileDirectory = new File(Dto.getFirefoxProfilePath());
			FirefoxProfile profile = new FirefoxProfile(profileDirectory);
			System.setProperty("webdriver.gecko.driver","D:\\Jars\\New 3.3.1 selenium jars\\geckodriver.exe");
			wd = new FirefoxDriver(profile);
			System.out.println("Firefox Browser Launched");
			d = new EventFiringWebDriver(wd);
			maximiseWindow();
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if ("ie".equalsIgnoreCase(Dto.getWebDriverObj())) {
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			System.setProperty("webdriver.ie.driver", Dto.getIepath());
			wd = new InternetExplorerDriver(caps);
			d = new EventFiringWebDriver(wd);
			System.out.println("IE Browser Launched");
			maximiseWindow();
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
		} else if ("chrome".equalsIgnoreCase(Dto.getWebDriverObj())) {
			String chromeProfile = Dto.getChromeProfilePath();
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("start-maximized");
			String[] switches = { "user-data-dir=" + chromeProfile};
			//capabilities.setCapability("chrome.switches", "--disable-extensions");
			capabilities.setCapability("chrome.switches", switches);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty("webdriver.chrome.driver",Dto.getChromedriverpath());
			wd = new ChromeDriver(capabilities);
			d = new EventFiringWebDriver(wd);
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
		} else if ("Invisible Mode".equalsIgnoreCase(Dto.getWebDriverObj())) {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, Dto.getPhantomJsPath());
			dc.setCapability("takeScreenshot", true);
			wd = new PhantomJSDriver(dc);
			d = new EventFiringWebDriver(wd);
		//	d.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			d.manage().window().maximize();
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else {
			Reporter.log("Enter Valid browser name");
		}
		
		return d;
	}

	public void browserSelectionFF() {

		if ("firefox".equalsIgnoreCase(Dto.getWebDriverObj())) {
			//FirefoxProfile fxProfile = new FirefoxProfile();
			/*DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);*/
			File profileDirectory = new File(Dto.getFirefoxProfilePath());
			FirefoxProfile fxProfile = new FirefoxProfile(profileDirectory);
			fxProfile.setPreference("browser.download.folderList", 2);
			fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
			fxProfile.setPreference("browser.download.dir", DOWNLOAD_PATH+ "\\");
			fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
			fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf,application/ms-excel,text/csv,image/jpeg,image/svg+xml,image/png,application/csv,application/vnd.ms-excel,application/octet-stream");
			System.setProperty("webdriver.gecko.driver","D:\\Jars\\New 3.3.1 selenium jars\\geckodriver.exe");
			
			wd = new FirefoxDriver(fxProfile);
			d = new EventFiringWebDriver(wd);
			maximiseWindow();
		//	d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if ("ie".equalsIgnoreCase(Dto.getWebDriverObj())) {
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			System.setProperty("webdriver.ie.driver", Dto.getIepath());
			wd = new InternetExplorerDriver(caps);
			d = new EventFiringWebDriver(wd);
			maximiseWindow();
			//d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if ("chrome".equalsIgnoreCase(Dto.getWebDriverObj())) {
			String chromeProfile = Dto.getChromeProfilePath();
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			String[] switches = { "user-data-dir=" + chromeProfile };
			capabilities.setCapability("chrome.switches", switches);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			 options.addArguments("start-maximized");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver",Dto.getChromedriverpath());
			wd = new ChromeDriver(capabilities);
			d = new EventFiringWebDriver(wd);
		//	d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		
		} else if ("Invisible Mode".equalsIgnoreCase(Dto.getWebDriverObj())) {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,Dto.getPhantomJsPath());
			dc.setCapability("takeScreenshot", true);
			wd = new PhantomJSDriver(dc);
			d = new EventFiringWebDriver(wd);
			d.manage().window().maximize();
		//	d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Reporter.log("Enter Valid browser name");
		}
	
	}

	protected void maximiseWindow() {
		Point targetPosition = new Point(0, 0);
		d.manage().window().setPosition(targetPosition);
		String w = "return screen.availWidth";
		String h = "return screen.availHeight";
		int width = ((Long) d.executeScript(w)).intValue();
		int height = ((Long) d.executeScript(h)).intValue();
		Dimension targetSize = new Dimension(width, height);
		d.manage().window().setSize(targetSize);
	}
	
	
	
}