@uc05
Feature: Manage shopping list
  As a user
  I want to manage a personal shopping list of halal-approved products
  So that I can retain products I intend to purchase or revisit during shopping

  Background:
    Given the EatingHalal application is available
    And the shopping list database is available

  Scenario: View a populated shopping list
    Given the user's shopping list contains "Haribo Goldbears" with status "Halal"
    When the user opens the shopping list
    Then the system displays the shopping list
    And the shopping list includes "Haribo Goldbears"
    And each entry includes the product name and halal status

  Scenario: View an empty shopping list
    Given the user's shopping list is empty
    When the user opens the shopping list
    Then the system informs the user that the shopping list is empty

  Scenario: Add a product to the shopping list
    Given the halal assessment result for "Haribo Goldbears" is displayed
    And "Haribo Goldbears" is not on the user's shopping list
    When the user adds "Haribo Goldbears" to the shopping list
    Then the system saves "Haribo Goldbears" to the user's shopping list
    And the system confirms that "Haribo Goldbears" has been added

  Scenario: Prevent duplicate entries on the shopping list
    Given the halal assessment result for "Haribo Goldbears" is displayed
    And "Haribo Goldbears" is already on the user's shopping list
    When the user adds "Haribo Goldbears" to the shopping list
    Then the system informs the user that "Haribo Goldbears" is already on the shopping list
    And the system does not add a duplicate entry

  Scenario: Remove a product from the shopping list
    Given the user's shopping list contains "Haribo Goldbears" with status "Halal"
    When the user opens the shopping list
    And the user removes "Haribo Goldbears" from the shopping list
    Then the system removes "Haribo Goldbears" from the user's shopping list
    And the system confirms that "Haribo Goldbears" has been removed
    And the system displays the updated shopping list

  Scenario: Clear the entire shopping list
    Given the user's shopping list contains "Haribo Goldbears" with status "Halal"
    When the user opens the shopping list
    And the user clears the shopping list
    Then the system removes all products from the user's shopping list
    And the system confirms that the shopping list has been cleared
    And the system informs the user that the shopping list is empty
