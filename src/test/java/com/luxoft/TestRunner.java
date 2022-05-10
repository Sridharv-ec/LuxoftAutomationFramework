package com.luxoft;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        plugin = {"pretty", "html:target/cucumber.html",
                "json:target/report/cucumber-report.json",
                "junit:target/cucumber.xml"},
        glue = {"com/luxoft/stepdefs"},
        features = "src/test/resources/features/",
        tags = "@test",
        monochrome = true
)

public class TestRunner {
}
