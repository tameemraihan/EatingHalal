# Product examples and verdicts below are synthetic test fixtures, not real certifications.
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


  Scenario: Update a saved quantity
    Given the user's shopping list contains "Synthetic Oat Drink" with status "Halal"
    When the user changes the saved quantity to 3
    Then the persisted quantity is 3

  Scenario: Reject an invalid quantity
    Given the user's shopping list contains "Synthetic Oat Drink" with status "Halal"
    When the user changes the saved quantity to 0
    Then the quantity change is rejected

  Scenario: A second user cannot update another user's list
    Given the user's shopping list contains "Synthetic Oat Drink" with status "Halal"
    When another signed-in user requests the first user's shopping-list entry
    Then the entry is inaccessible and remains unchanged

  Scenario: Do not save a product with a Haram assessment
    Given the selected product has only a Haram assessment
    When the user tries to save the selected product
    Then the ineligible product is not saved

  Scenario: Reject a mutation without a CSRF token
    Given the user's shopping list contains "Synthetic Oat Drink" with status "Halal"
    When the user submits a quantity change without a CSRF token
    Then the CSRF check rejects the change
