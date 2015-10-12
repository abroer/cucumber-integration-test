package nl.arjan.sandbox;

public class Navigation {
    public static HomeView openHomepage() {
        BrowserDriver.loadPage("http://127.0.0.1:8080/cucumber-integration-test");

        return new HomeView();
    }
}
