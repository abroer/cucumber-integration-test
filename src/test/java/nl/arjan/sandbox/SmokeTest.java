package nl.arjan.sandbox;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class SmokeTest {

    private HomeView homeView;

    @Given("^the user is at the homepage$")
    public void the_user_is_at_the_homepage() throws Throwable {
        homeView = Navigation.openHomepage();
    }

    @Then("^the header should be visible$")
    public void the_header_should_be_visible() throws Throwable {
        homeView.isHeaderVisible();
    }
}
