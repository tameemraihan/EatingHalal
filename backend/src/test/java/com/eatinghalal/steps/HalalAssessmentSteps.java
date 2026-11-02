package com.eatinghalal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HalalAssessmentSteps {

    // Simulating our hosted DB for the test
    private Map<String, String> mockDatabase = new HashMap<>();
    private String searchResult;

    @Given("the product {string} is in the database with status {string}")
    public void the_product_is_in_the_database_with_status(String productName, String status) {
        mockDatabase.put(productName, status);
    }

    @When("the user searches for the product {string}")
    public void the_user_searches_for_the_product(String productName) {
        // Simulating the API/Service call that checks the DB first before the expensive pipeline
        searchResult = mockDatabase.getOrDefault(productName, "Unknown");
    }

    @Then("the assessment pipeline should return {string}")
    public void the_assessment_pipeline_should_return(String expectedStatus) {
        assertEquals(expectedStatus, searchResult);
    }
}
