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
public class TestCase4 {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static String baseURL = "https://www.br.se/";
	private static Double expected = 0.0;
	private static Double actual = 0.0;
	public static Logger LOG;
	static FileHandler fh;

	@BeforeClass
	public static void SetupOnce() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 3);
		LOG = Logger.getLogger(TestCase4.class.getName());
		try {
			fh = new FileHandler("log/log4.log");
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
		driver.manage().window().maximize();
		expected = 0.0;
		actual = 0.0;
		System.out.println("@Before resetData()");
	}

	@Test
	public void testAndraVarukorg() {
		expected = 179.8;
		LOG.info(
				"Adding a product to the shopping cart, and increasing the quantity by one. Expecting the price to double from 89.90 to 179.80.");

		String productURL = "https://www.br.se/vaara-kategorier/kreativitetsleksaker-och-pyssel-foer-barn/glitter/color-kids-glitterstift?id=000000000113247001";
		String laggIKorgenButtonXpath = ".//*[@id='content_0_productwrapper_0_basketandwishlist_0_basketIcon']";
		String checkoutIconXpath = ".//*[@id='iconbar']/div[4]/a/span/span";
		String checkoutXpath = ".//*[@id='basket']/footer/div/div[1]/a";
		String checkoutPlusButtonXpath = ".//*[@id='basket']/div[2]/div/table/tbody/tr/td[2]/span[2]";
		String totalSumXpath = ".//*[@id='basket']/div[2]/div/table/tbody/tr/td[4]/div[1]/b";

		driver.navigate().to(productURL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(laggIKorgenButtonXpath)));
		driver.findElement(By.xpath(laggIKorgenButtonXpath)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkoutIconXpath)));
		driver.navigate().to("https://www.br.se/cart");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkoutXpath)));
		driver.findElement(By.xpath(checkoutPlusButtonXpath)).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actual = Double.valueOf(driver.findElement(By.xpath(totalSumXpath)).getText().replaceAll(",", "."));
		Assert.assertEquals(expected, actual, 0);

	}

	@After
	public void tearDown() {
		String emptyCartButtonXpath = ".//*[@id='basket']/div[2]/div/table/tbody/tr/td[4]/div[2]/ul/li/a";
		driver.findElement(By.xpath(emptyCartButtonXpath)).click();
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