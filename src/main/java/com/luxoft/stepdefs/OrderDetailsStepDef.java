package com.luxoft.stepdefs;

import com.luxoft.LuxoftApiHelper;
import com.luxoft.models.RestBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsStepDef {

    @Autowired
    private LuxoftApiHelper luxoftApiHelper;

    @Autowired
    private RestBase restBase;

    @Given("^order details api is up$")
    public void orderDetailsHeathCheck(){
        luxoftApiHelper.healthCheck();
    }

    @When("^get the order details for orderid \"([^\"]*)\"$")
    public void getOrderDetails(String orderId){
        restBase.setOrderId(orderId);
        luxoftApiHelper.luxoftAP1Request(LuxoftApiHelper.luxoftFunctions.getNaceDetails);
    }

    @Then("^validate the order details in the response$")
    public void validateOrderDetails() throws Exception {
        luxoftApiHelper.validateOrderDetailsResponse();
    }
}
