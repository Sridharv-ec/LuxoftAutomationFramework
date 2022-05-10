package com.luxoft.models;


import com.jayway.jsonpath.DocumentContext;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class CommonVariables {

    DocumentContext documentContext;
    HashMap<String, Object> headersMap = new HashMap<>();
    public Response response;
    protected RequestSpecification requestSpecification;
    protected Map<String, RequestSpecification> requestSpecificationMap = new HashMap<>();
    protected HashMap<String, Object> parametersMap = new HashMap<>();
    protected String requestName = "";


}
