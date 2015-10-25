package nl.arjan.sandbox;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BrowserDriver {
    private static final Logger LOGGER = Logger.getLogger(BrowserDriver.class.getName());

    private static String PHANTOMJS_BINARY;
    public static final boolean REUSE = true;
    public static final boolean NEWINSTANCE = false;
    private static WebDriver driver;
    private static Properties properties = new Properties();

    @BeforeClass
    public static void beforeTest() {
        PHANTOMJS_BINARY = System.getProperty("phantomjs.binary");

        assertNotNull(PHANTOMJS_BINARY);
        assertTrue(new File(PHANTOMJS_BINARY).exists());
    }

    public synchronized static WebDriver getCurrentDriver() {
        return driver;
    }

    public static void loadPage(String url) {
        LOGGER.info("Directing browser to:" + url);
        getCurrentDriver().get(url);
    }

    public static WebElement waitForElement(By locator) {
        WebDriverWait driverWait = new WebDriverWait(getCurrentDriver(), 25);
        WebElement element = driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void startBrowserDriver() {
        startBrowserDriver(NEWINSTANCE);
    }

    public static void startBrowserDriver(boolean reuse) {
        if (!reuse) {
            close();
        }
        if (driver == null) {
            try {

                String locationPhantomjs = getLocationPhantomJS();
                final DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setJavascriptEnabled(true);
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, locationPhantomjs);

                driver = new PhantomJSDriver(capabilities);
            } finally {
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
    }

    private static String getLocationPhantomJS() {
        String locationPhantomjs = "";

        Collection<File> files = FileUtils.listFiles(new File("target/"), null, true);
        for (File file : files) {
            if (file.getName().startsWith("phantomjs")) {
                LOGGER.info("Found: " + file.getAbsolutePath());
                locationPhantomjs = file.getAbsolutePath();
            }
        }
        LOGGER.info("location phantomjs: " + locationPhantomjs);
        return locationPhantomjs;
    }

    private static class BrowserCleanup implements Runnable {

        public void run() {
            LOGGER.info("Closing the browser");
            close();
        }
    }

    public static void close() {
        try {
            if (driver != null) {
                getCurrentDriver().quit();
                driver = null;
                LOGGER.info("closing the browser");
            }
        } catch (UnreachableBrowserException e) {
            LOGGER.info("cannot close browser: unreachable browser");
        }
    }
}
