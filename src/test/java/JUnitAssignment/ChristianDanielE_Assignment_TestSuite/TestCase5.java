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
public class TestCase5 {
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static String baseURL = "https://www.br.se/";
	private static Boolean expected;
	private static Boolean actual;
	public static Logger LOG;
	static FileHandler fh;

	@BeforeClass
	public static void SetupOnce() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 3);
		LOG = Logger.getLogger(TestCase5.class.getName());
		try {
			fh = new FileHandler("log/log5.log");
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
		expected = null;
		actual = null;
		System.out.println("@Before resetData()");
	}
	
	@Test
	public void testPriser() {
		expected = true;
		LOG.info("Comparing prices at productpage with checkout, expected = true if they are equal");

		String productURL = "https://www.br.se/vaara-kategorier/kreativitetsleksaker-och-pyssel-foer-barn/glitter/color-kids-glitterstift?id=000000000113247001";
		String laggIKorgenButtonXpath = ".//*[@id='content_0_productwrapper_0_basketandwishlist_0_basketIcon']";
		String productPagePriceXpath = ".//*[@id='product-details-wrapper']/div[1]/div/div[1]/div/div/span/span";
		String checkoutXpath = ".//*[@id='basket']/footer/div/div[1]/a";
		String checkoutPriceXpath = ".//*[@id='basket']/div[2]/div/table/tbody/tr/td[4]/div[1]/b";
		String checkoutIconXpath = ".//*[@id='iconbar']/div[4]/a/span/span";

		driver.navigate().to(productURL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(laggIKorgenButtonXpath)));
		double productPagePrice = Double
				.valueOf(driver.findElement(By.xpath(productPagePriceXpath)).getText().replaceAll(",", "."));
		driver.findElement(By.xpath(laggIKorgenButtonXpath)).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(checkoutIconXpath)));
		driver.navigate().to("https://www.br.se/cart");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(checkoutXpath)));
		double checkoutPagePrice = Double
				.valueOf(driver.findElement(By.xpath(checkoutPriceXpath)).getText().replaceAll(",", "."));

		actual = productPagePrice == checkoutPagePrice;
		LOG.info("Product page price: " + productPagePrice + "\nCheckout page price: " + checkoutPagePrice);
		Assert.assertEquals(productPagePrice, checkoutPagePrice, 0);
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