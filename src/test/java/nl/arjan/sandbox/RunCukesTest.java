package nl.arjan.sandbox;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCukesTest {

    @Test
    public void mustfail() {
        boolean expected = true;

        boolean actual = false;
        Assert.assertEquals(expected, actual);
    }
}
