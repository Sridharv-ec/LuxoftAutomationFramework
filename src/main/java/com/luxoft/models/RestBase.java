package com.luxoft.models;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.luxoft.utils.ExcelUtils;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Component
@Scope("cucumber-glue")
public class RestBase {

    private String orderId;

    @Autowired
    public CommonVariables variables;

    @Autowired
    public ExcelUtils excelUtils;

    public DocumentContext getDocumentContext() {
        return variables.documentContext;
    }

    protected void setDocumentContext(DocumentContext context) {
        variables.documentContext = context;
    }

    protected void setTemplate(String templatePath) {
        try {
            setDocumentContext(JsonPath.parse(getClass().getResource(templatePath)));
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    protected RequestSpecification getRequestSpecification() {
        return variables.requestSpecification;
    }

    protected RequestSpecification getRequestSpecification(String nameOfRequest) {
        return variables.requestSpecificationMap.get(nameOfRequest);
    }

    protected void setRequestSpecification(RequestSpecification spec) {
        variables.requestSpecification = spec;
    }

    protected void setHeaders(HashMap<String, Object> headers) {
        variables.headersMap.clear();
        variables.headersMap = headers;
    }

    protected HashMap<String, Object> getParameters() {
        return variables.parametersMap;
    }

    protected void setParameters(HashMap<String, Object> parameters) {
        variables.parametersMap = parameters;
    }

    protected HashMap<String, Object> getHeaders() {
        return variables.headersMap;
    }

    protected void setJsonPathValue(String path, int value) {
        getDocumentContext().set(path, value);
    }

    protected void setJsonPathValue(String path, String value) {
        getDocumentContext().set(path, value);
    }

    public String getJsonPathValue(String key) {
        return getResponse().then().extract().jsonPath().getString(key);
    }


    protected void setResponse(Response res) {
        variables.response = res;
    }

    public Response getResponse() {
        return variables.response;
    }


    protected void post(String url, boolean body, boolean headers, boolean parameters) {
        setRequestSpecification(given());
        if (body) {
            setRequestSpecification(getRequestSpecification().body(getDocumentContext().jsonString()));
        }
        if (headers) {
            setRequestSpecification(getRequestSpecification().headers(getHeadersFromMap()));
        }
        if (parameters) {
            setRequestSpecification(getRequestSpecification().queryParams(getParameters()));
        }
        setResponse(getRequestSpecification().when().post(url));

    }

    protected void put(String url, boolean body, boolean headers, boolean parameters) {
        setRequestSpecification(given());
        if (body) {
            setRequestSpecification(getRequestSpecification().body(getDocumentContext().jsonString()));
        }
        if (headers) {
            setRequestSpecification(getRequestSpecification().headers(getHeadersFromMap()));
        }
        if (parameters) {
            setRequestSpecification(getRequestSpecification().queryParams(getParameters()));
        }
        setResponse(getRequestSpecification().when().put(url));

    }

    protected void get(String url, boolean body, boolean headers, boolean parameters) {
        setRequestSpecification(given());

        if (headers) {
            setRequestSpecification(getRequestSpecification().headers(getHeadersFromMap()));
        }
        if (parameters) {
            setRequestSpecification(getRequestSpecification().queryParams(getParameters()));
        }
        setResponse(getRequestSpecification().when().get(url));
    }

    protected void delete(String url, boolean body, boolean headers, boolean parameters) {
        setRequestSpecification(given());

        if (headers) {
            setRequestSpecification(getRequestSpecification().headers(getHeadersFromMap()));
        }
        if (parameters) {
            setRequestSpecification(getRequestSpecification().queryParams(getParameters()));
        }
        setResponse(getRequestSpecification().when().delete(url));
    }

    protected Headers getHeadersFromMap() {
        List<Header> list = new ArrayList<Header>();
        for (String key : getHeaders().keySet()) {
            String value;
            try {
                value = getHeaders().get(key).toString();
                if (value == null || value.equalsIgnoreCase("null")) {
                    list.add(new Header(key, null));
                } else {
                    list.add(new Header(key, getHeaders().get(key).toString()));
                }
            } catch (Exception e) {
                list.add(new Header(key, null));
            }
        }
        return new Headers(list);
    }


    public ValidatableResponse getValidatableResponse() {
        return (ValidatableResponse) getResponse().then();
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public Map getExpectedOrderDetails() throws Exception {
        Map dataMap = new HashMap<String, String>();
        dataMap = excelUtils.getData(getOrderId());
        return dataMap;
    }


}
