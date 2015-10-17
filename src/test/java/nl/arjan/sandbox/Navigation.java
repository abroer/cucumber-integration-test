package nl.arjan.sandbox;

public class Navigation {
    public static HomeView openHomepage() {
        String simpleWebUrl = System.getProperty("simpleWebUrl");
        BrowserDriver.loadPage(simpleWebUrl);

        return new HomeView();
    }
}
