package JUnitAssignment.ChristianDanielE_Assignment_TestSuite;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Christian Ström
 *
 */
public class TestCase1 {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static String baseURL = "https://www.br.se/";
	private static String expected = "";
	private static String actual = "";
	public static Logger LOG;
	static FileHandler fh;

	@BeforeClass
	public static void SetupOnce() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 3);
		LOG = Logger.getLogger(TestCase1.class.getName());
		try {
			fh = new FileHandler("log/log1.log");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		System.out.println("@BeforeClass setupOnce()");
	}

	@Before
	public void resetData() {
		driver.navigate().to(baseURL);
		// driver.manage().window().maximize();
		expected = "";
		actual = "";
		System.out.println("@Before resetData()");
	}

	@Test
	public void testLogin() {
		expected = "Profile";
		LOG.info("Inputting correct login info, expecting to reach profile page\nExpected: " + expected);

		String correctUsername = "p1092391@mvrht.com";
		String correctPassword = "test123";
		String emailXpath = ".//*[@id='j_username']";
		String passwordXpath = ".//*[@id='j_password']";
		String loginButtonHeaderXpath = ".//*[@id='iconbar']/div[1]/a[1]/div";
		String loginButtonXpath = "//*[@id='loginForm']/div[3]/button";
		String minProfilChangePassXpath = "//*[@id='content']/div/a[1]";

		driver.findElement(By.xpath(loginButtonHeaderXpath)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loginButtonXpath)));
		driver.findElement(By.xpath(emailXpath)).sendKeys(correctUsername);
		driver.findElement(By.xpath(passwordXpath)).sendKeys(correctPassword);
		driver.findElement(By.xpath(loginButtonXpath)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(minProfilChangePassXpath)));

		actual = driver.getTitle();
		LOG.info("Actual: " + actual);
		Assert.assertTrue("Login Fail", driver.getTitle().contains(expected));
		System.out.println("@Test testLogin()");
	}

	@After
	public void tearDown() {
		String logOutButtonXpath = ".//*[@id='clubbr-nav-menu-container']/div/ul/li[5]/a";
		driver.findElement(By.xpath(logOutButtonXpath)).click();
		System.out.println("@After tearDown()");
	}

	@AfterClass
	public static void tearDownOnce() {
		driver.close();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
		}
		driver.quit();
		System.out.println("@AfterClass tearDownOnce()");
	}
}