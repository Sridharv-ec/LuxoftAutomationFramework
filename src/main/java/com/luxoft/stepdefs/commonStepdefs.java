package com.luxoft.stepdefs;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.luxoft.models.RestBase;
import io.cucumber.java.en.Then;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

import static junit.framework.TestCase.assertTrue;

public class commonStepdefs {


    @Autowired
    private RestBase restBase;

    @Then("^a (.*) status code is returned$")
    public void verifyResponseReturnCode(String returnCode) {
        System.out.println("Response body \n"+restBase.getResponse().body().asPrettyString() );
        Assert.assertEquals("Expected status code: " + Integer.parseInt(returnCode) + " is not equal Actual Status code: " + restBase.getResponse().getStatusCode(), Integer.parseInt(returnCode), restBase.getResponse().getStatusCode());
    }

    @Then("^response contains \"([^\"]*)\" as string$")
    public void responseContainsKeyValueString(String key) {
        try {
            assertTrue(!restBase.getJsonPathValue(key).isEmpty());
            assertTrue(restBase.getJsonPathValue(key).length() > 1 && restBase.getJsonPathValue(key).length() < 33);
        } catch (IllegalStateException e) {
            DocumentContext test = JsonPath.parse(restBase.getResponse().body().print());
            LinkedHashMap map = test.read("$");
            assert(map.containsKey(key)) : "Key not present in " + map;
        }
    }

    @Then("^response contains \"([^\"]*)\" \"([^\"]*)\"$")
    public void responseContains(String key, String message) {
        try {
            ((ValidatableResponse)this.restBase.getValidatableResponse().assertThat()).body(key, CoreMatchers.containsString(message), new Object[0]);
        } catch (IllegalStateException var6) {
            DocumentContext test = JsonPath.parse(this.restBase.getResponse().body().print());
            LinkedHashMap map = (LinkedHashMap)test.read("$", new Predicate[0]);
            assert map.containsKey(key) : "Key not present in " + map;
            assert map.get(key).equals(message) : "Value not correct in " + map;
        }

    }
}
