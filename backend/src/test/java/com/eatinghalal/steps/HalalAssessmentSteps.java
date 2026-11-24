package com.eatinghalal.steps;

import com.eatinghalal.TestEnvironment;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HalalAssessmentSteps {
  @DynamicPropertySource
  static void database(DynamicPropertyRegistry registry) {
    registry.add(
        "spring.datasource.url", () -> TestEnvironment.DATABASE.getJdbcUrl("postgres", "postgres"));
    registry.add("spring.datasource.username", () -> "postgres");
    registry.add("spring.datasource.password", () -> "");
    registry.add("eatinghalal.demo", () -> false);
    registry.add("eatinghalal.open-food-facts.enabled", () -> true);
    registry.add(
        "eatinghalal.open-food-facts.base-url",
        () -> "http://127.0.0.1:" + TestEnvironment.PROVIDER.getAddress().getPort());
    registry.add("eatinghalal.open-food-facts.user-agent", () -> "EatingHalalIntegrationTests/1.0");
  }
}
