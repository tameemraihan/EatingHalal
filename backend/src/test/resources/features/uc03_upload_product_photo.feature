@uc03
Feature: Upload a product packaging photo
  As a user
  I want to upload a photo of packaged food
  So that the product information can be prepared for a halal assessment

  Background:
    Given the EatingHalal application is available
    And the OCR and OCR/LLM services are available

  Scenario: Extract canonical product data from a valid photo
    Given the user has a valid product packaging image with readable information
    When the user uploads the product packaging image
    Then the system validates the image file
    And the system extracts text and confidence scores from the image
    And the system structures the available product information
    And the system prepares canonical product data for the halal assessment pipeline

  Scenario: Reject an invalid image and accept a replacement
    Given the user has selected an unsupported or corrupted image file
    When the user uploads the product packaging image
    Then the system returns an invalid-image error
    When the user uploads a valid replacement product packaging image
    Then the system validates the replacement image file

  Scenario: Request a clearer photo when text cannot be read reliably
    Given the user has selected a valid but unreadable product packaging image
    When the user uploads the product packaging image
    Then the system informs the user that the photo cannot be read reliably
    And the system asks the user to upload another product photo

  Scenario: Identify missing essential product information
    Given the user has uploaded a readable product packaging image with incomplete information
    When the system structures the extracted product information
    Then the system informs the user which essential product information could not be identified
    And the system asks the user to upload a clearer or more complete photo
