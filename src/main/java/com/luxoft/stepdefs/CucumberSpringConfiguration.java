package com.luxoft.stepdefs;

import com.luxoft.LuxoftAutomationFramework;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = LuxoftAutomationFramework.class)
public class CucumberSpringConfiguration {

}