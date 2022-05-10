package com.luxoft;

import com.luxoft.models.CommonVariables;
import com.luxoft.models.RestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class LuxoftApiHelper extends RestBase {

    public static final String TEMPLATE_PATH = "/templates/";
    final String host_url = "https://ec.europa.eu/eurostat/ramon/nomenclatures/index.cfm";
    final String luxoft_url = "https://ec.europa.eu/eurostat/ramon/nomenclatures/";
    Boolean setDefaultLuxoftApiFlag = false;
    private String luxoftApiTemplatePath = TEMPLATE_PATH + "luxoft/";
    private String requestBodyTemplatePath = luxoftApiTemplatePath + "requestbody/";
    private String responseBodyTemplatePath = luxoftApiTemplatePath + "responsebody/";
    private String luxoftRequestBody = requestBodyTemplatePath + "getorderdetails.json";
    private String luxoftEndPoint = "/order";
    private String apiName = "luxoft";

    @Autowired
    private CommonVariables commonVariables;

    private String getEndpoint(luxoftFunctions function) {

        switch (function) {
            case Create:
            case Update:
            case Delete:
                return host_url + luxoftEndPoint;
            case getNaceDetails:
                return host_url + luxoftEndPoint;
            default:
                return "Endpoint name is incorrect";
        }

    }

    private void setDefaultParameters(luxoftFunctions function) {

        HashMap<String, Object> params = new HashMap<>();

        switch (function) {
            case getNaceDetails: {
                params.put("order", getOrderId());
                break;
            }
        }
        setParameters(params);
    }

    public void setDefaultRequestTemplate(luxoftFunctions function) {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        setHeaders(headers);
        setDefaultParameters(function);

        switch (function) {
            case Create:
                break;
            case Update:
                setTemplate(luxoftRequestBody);
                break;
            default:
                System.out.println("");
                break;
        }

        setDefaultLuxoftApiFlag = true;
    }

    public void luxoftAP1Request(luxoftFunctions function) {

        if (!setDefaultLuxoftApiFlag) {
            setDefaultRequestTemplate(function);
        }

        switch (function) {
            case Create:
                post(getEndpoint(function), true, true, false);
                break;
            case Update:
                put(getEndpoint(function), true, true, false);
                break;
            case getNaceDetails:
                get(getEndpoint(function), true, true, true);
                break;
            case Delete:
                break;
            default:
                break;

        }
        setDefaultLuxoftApiFlag = false;
    }

    public void healthCheck() {
        get(luxoft_url, false, false, false);
    }

    public enum luxoftFunctions {
        Create, Update, getNaceDetails, Delete
    }


    public void validateOrderDetailsResponse() throws Exception {
        Map expectedDataMap = new HashMap<String, String>();
        expectedDataMap = getExpectedOrderDetails();

        expectedDataMap.forEach((key, value) -> {
                    System.out.println(key + ":" + value);
                    assert  getJsonPathValue((String) key).equals(value);
        });



    }



}
