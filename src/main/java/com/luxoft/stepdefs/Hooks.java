package com.luxoft.stepdefs;

import com.luxoft.models.RestBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {

//    RestBase restBase = new RestBase();
//    @Autowired
//    private RestBase restBase;

    @Before
    public void setup(Scenario scenarioName) {

    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
//            if (restBase.getResponse() != null) {
//
//            }
        }
    }
}
