package com.xcel.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static WebDriver driver = null;
	public static String browserTitle;
	public static Properties properties;
	public static Wait<WebDriver> wait;

	@BeforeSuite
	public void launchBrowser() throws IOException {

		properties = new Properties();
		FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/properties/testdata.properties");
		properties.load(inputStream);

		String browser = properties.getProperty("browser");
		String testingUrl = properties.getProperty("url");

		System.out.println(browser);
		System.out.println(testingUrl);

		if (browser.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("Mozilla") || browser.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} /*else if (browser.equals("Opera")) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		}*/ else if (browser.equals("Safari")) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		}else if (browser.equals("Remote")) {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName("chrome");
			URL url = new URL("http://localhost:4444");
			driver = new RemoteWebDriver(url,dc);
		} else {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.get(testingUrl);

		wait = new FluentWait<WebDriver>(driver) // Fluent Wait
				.withTimeout(Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(2));
	}

	// @AfterSuite
	// public void closingBrowser() {
	// driver.close();

}



