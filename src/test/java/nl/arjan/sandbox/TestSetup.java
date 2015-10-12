package nl.arjan.sandbox;

import cucumber.api.java.Before;

public class TestSetup {
    @Before
    public void startBrowserDriver() {
        BrowserDriver.startBrowserDriver(BrowserDriver.REUSE);
    }

}
