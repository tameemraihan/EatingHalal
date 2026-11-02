@uc02
Feature: View halal status of a product
  As a user
  I want to view a selected product's halal status and justification
  So that I can make an informed purchasing decision

  Background:
    Given the EatingHalal application is available

  Scenario: Display a stored halal assessment
    Given the user has selected "Haribo Goldbears"
    And a stored halal assessment exists for "Haribo Goldbears"
    When the user requests the halal status
    Then the system displays the product details
    And the system displays the halal status
    And the system displays a plain-language justification for the status

  Scenario: Create and store an assessment when none exists
    Given the user has selected "Example Product"
    And no stored halal assessment exists for "Example Product"
    And reliable product information and the halal assessment pipeline are available
    When the user requests the halal status
    Then the system normalises the product data into the canonical format
    And the system creates and stores a halal assessment for "Example Product"
    And the system displays the product details, halal status, and justification

  Scenario: Do not present an unsupported assessment as confirmed
    Given the user has selected "Incomplete Product"
    And no stored halal assessment exists for "Incomplete Product"
    And sufficient reliable product information cannot be retrieved
    When the user requests the halal status
    Then the system informs the user that a confirmed halal assessment cannot currently be produced
    And the system does not display an unsupported assessment as a confirmed result
