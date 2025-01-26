package com.example.ecommerce;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = EcommerceApplication.class)
public class StepDefinitions {

    private boolean isRunning = false;

    @Given("the application is running")
    public void the_application_is_running() {
        isRunning = true;
    }

    @When("I check the status")
    public void i_check_the_status() {
        // No action needed for this example
    }

    @Then("it should be up and running")
    public void it_should_be_up_and_running() {
        assertTrue(isRunning);
    }
}
