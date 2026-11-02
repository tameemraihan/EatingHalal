@uc04
Feature: Manage stored halal assessment results
  As an authorised admin
  I want to find and review stored halal assessments
  So that I can inspect their product details, status, and justification

  Background:
    Given the assessment-results database is available

  Scenario: View a matching stored assessment
    Given an authorised admin opens the stored assessments
    And stored halal assessments are available
    When the admin searches for assessments matching "Haribo"
    And the admin selects a matching stored assessment
    Then the system displays the selected product details
    And the system displays the selected halal status
    And the system displays the selected assessment justification

  Scenario: Show no results for unmatched search criteria
    Given an authorised admin opens the stored assessments
    And no stored assessment matches "Unknown Product"
    When the admin searches for assessments matching "Unknown Product"
    Then the system displays a no-results message

  Scenario: Refine a stored-assessment search
    Given an authorised admin has received a no-results message for "Goldbears"
    And a stored assessment matches "Haribo Goldbears"
    When the admin refines the search to "Haribo Goldbears"
    Then the system displays the matching assessments

  Scenario: Prevent unauthorised access to stored assessments
    Given an unauthorised user attempts to open the stored assessments
    When the user requests the stored assessments
    Then the system denies access to the stored assessments
