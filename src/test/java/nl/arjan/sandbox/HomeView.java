package nl.arjan.sandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomeView {
    public boolean isDisplayed() {
        WebElement textHeader = BrowserDriver.waitForElement(By.id("textHeader"));
        return textHeader.isDisplayed();
    }

    public boolean isHeaderVisible() {
        WebElement textHeader = BrowserDriver.waitForElement(By.id("textHeader"));
        return textHeader.isDisplayed();
    }
}
