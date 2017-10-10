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
public class TestCase2 {
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
		LOG = Logger.getLogger(TestCase2.class.getName());
		try {
			fh = new FileHandler("log/log2.log");
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
	public void testHittaButik() {
		expected = "Alingsås";
		LOG.info("Going to the find store page, expecting to find Alingsås at the top left");

		String hittaButikButtonID = "find-store-link-id";
		String allaButikerXpath = ".//*[@id='content']/div/div[2]/a";
		String merInfoXpath = ".//*[@id='content']/div/div/div[2]/ul/li[1]/span[5]/a";
		String topLeftH3Xpath = ".//*[@id='content']/div/div/div[2]/ul/li[1]/h3";

		driver.findElement(By.id(hittaButikButtonID)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(allaButikerXpath)));
		driver.findElement(By.xpath(allaButikerXpath)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(merInfoXpath)));

		actual = driver.findElement(By.xpath(topLeftH3Xpath)).getText();
		LOG.info("Actual: " + actual);
		Assert.assertTrue("StoreLocation Fail", actual.contains(expected));
		System.out.println("@Test testHittaButik()");
	}

	@After
	public void tearDown() {
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