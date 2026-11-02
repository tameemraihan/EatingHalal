Feature: Manage Halal Assessment

  Scenario: Request assessment for a known product
    Given the product "Haribo Goldbears" is in the database with status "Haram"
    When the user searches for the product "Haribo Goldbears"
    Then the assessment pipeline should return "Haram"
