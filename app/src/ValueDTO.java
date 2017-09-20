import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ValueDTO {

	String URl;
	String search;
	String password;
	String client;
	String chromedriverpath;
	String phantomJsPath;
	String Iepath;
	String email;
	String firefoxprofilepath;
	String chromeprofilepath;
	String compareurl1;
	String webDriverObj;
	String webDriverObj1;
	String languageSelection;
	String pageName;

	public String getLanguageSelection() {
		return languageSelection;
	}

	public void setLanguageSelection(String languageSelection) {
		this.languageSelection = languageSelection;
	}

	public String getWebDriverObj() {
		return webDriverObj;
	}

	public void setWebDriverObj(String webDriverObj) {
		this.webDriverObj = webDriverObj;
	}

	public String getWebDriverObj1() {
		return webDriverObj1;
	}

	public void setWebDriverObj1(String webDriverObj1) {
		this.webDriverObj1 = webDriverObj1;
	}

	public String getsearch() {
		return search;
	}

	public void setsearch(String search) {
		this.search = search;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChromedriverpath() {
		return chromedriverpath;
	}

	public void setChromedriverpath(String chromedriverpath) {
		this.chromedriverpath = chromedriverpath;
	}

	public String getIepath() {
		return Iepath;
	}

	public void setIepath(String Iepath) {
		this.Iepath = Iepath;
	}

	public String getFirefoxProfilePath() {
		return firefoxprofilepath;
	}

	public void setFirefoxProfilePath(String firefoxprofilepath) {
		this.firefoxprofilepath = firefoxprofilepath;
	}

	public String getChromeProfilePath() {
		return chromeprofilepath;
	}

	public void setChromeProfilePath(String chromeprofilepath) {
		this.chromeprofilepath = chromeprofilepath;
	}

	public String getURl() {
		return URl;
	}

	public void setURl(String uRl) {
		URl = uRl;
	}

	public String getcompareurl1() {
		return compareurl1;
	}

	public void setcompareurl1(String compareurl1) {
		this.compareurl1 = compareurl1;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	// Move widgets to Other Pages
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	//Phantom JS
	public String getPhantomJsPath() {
		return phantomJsPath;
	}

	public void setPhantomJsPath(String phantomJsPath) {
		this.phantomJsPath = phantomJsPath;
	}

	public ValueDTO() {
		try {
			Workbook wb;
			File file1 = new File("./TestData/InputData.xls");
			Sheet sh, sh1;
			Cell c;
			wb = Workbook.getWorkbook(file1);
			sh = wb.getSheet("Input Sheet");
			sh1 = wb.getSheet("Tech Config");


			// Url
			c = sh.getCell(0, 2);
			this.URl = c.getContents();
			// compare url to know type of user(external or internal)

			// username
			c = sh.getCell(1, 2);
			this.search = c.getContents();
			/*// Password
			c = sh.getCell(2, 2);
			this.password = c.getContents();
			// Browser
			c = sh.getCell(3, 2);
			this.webDriverObj = c.getContents();

			c = sh.getCell(4, 2);
			this.client = c.getContents();

			c = sh.getCell(5, 2);
			this.email = c.getContents();

			// chrome Driver path
			c = sh1.getCell(0, 1);
			this.chromedriverpath = c.getContents();

			// firefox profile path
			c = sh1.getCell(1, 1);
			this.firefoxprofilepath = c.getContents();

			// chrome profile path
			c = sh1.getCell(2, 1);
			this.chromeprofilepath = c.getContents();

			c = sh1.getCell(3, 1);
			this.Iepath = c.getContents();
			
			c = sh1.getCell(4, 1);
			this.phantomJsPath = c.getContents();

			// Page Name
			c = sh.getCell(0, 6);
			this.pageName = c.getContents();

			// Language Selection
			c = sh.getCell(6, 2);
			this.languageSelection = c.getContents();*/

		} catch (Exception e) {
			e.printStackTrace();
			// setting default values if excel not available
			this.URl = "http://track5-dv-dev1/";

		}

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
