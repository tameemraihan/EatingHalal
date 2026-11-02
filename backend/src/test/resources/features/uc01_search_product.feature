@uc01
Feature: Search for a product
  As a user
  I want to search for a packaged food product by name
  So that I can select the product whose halal status I want to view

  Background:
    Given the EatingHalal application is available

  Scenario: Display matching products from available product sources
    Given the hosted database contains a product named "Haribo Goldbears"
    And an available external product source contains a product named "Haribo Goldbears"
    When the user searches for "Haribo Goldbears"
    Then the system displays the matching products
    And each result includes its product name
    And each result includes available manufacturer or package information

  Scenario: Offer photo upload when no product matches
    Given no available product source contains a product matching "Unknown Snack"
    When the user searches for "Unknown Snack"
    Then the system informs the user that no matching product was found
    And the system offers the user the option to upload a product packaging photo

  Scenario: Start product photo upload after an unsuccessful search
    Given no available product source contains a product matching "Unknown Snack"
    When the user searches for "Unknown Snack"
    And the user chooses to upload a product packaging photo
    Then the product packaging photo upload flow is started
