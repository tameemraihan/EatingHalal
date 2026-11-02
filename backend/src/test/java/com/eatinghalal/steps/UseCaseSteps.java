package com.eatinghalal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Executable acceptance-test skeletons for UC01 through UC04.
 * Replace this scenario state with calls to the real application services as
 * those services are implemented.
 */
public class UseCaseSteps {

    private boolean knownProduct;
    private boolean noMatchingProduct;
    private boolean photoUploadOffered;
    private boolean photoUploadStarted;

    private boolean storedAssessment;
    private boolean reliableProductData;
    private boolean productDetailsDisplayed;
    private boolean halalStatusDisplayed;
    private boolean justificationDisplayed;
    private boolean productDataNormalised;
    private boolean assessmentStored;
    private boolean assessmentUnavailable;

    private String imageState;
    private boolean imageValidated;
    private boolean textExtracted;
    private boolean productDataStructured;
    private boolean canonicalProductDataPrepared;
    private boolean invalidImageError;
    private boolean clearerPhotoRequested;
    private boolean missingInformationReported;

    private boolean authorisedAdmin;
    private boolean storedAssessmentsAvailable;
    private boolean noAssessmentMatch;
    private boolean matchingAssessmentsDisplayed;
    private boolean storedAssessmentSelected;
    private boolean accessDenied;

    @Given("the EatingHalal application is available")
    public void theApplicationIsAvailable() {
        // Application availability is supplied by @SpringBootTest.
    }

    @Given("the hosted database contains a product named {string}")
    public void theHostedDatabaseContainsAProduct(String productName) {
        knownProduct = true;
    }

    @Given("an available external product source contains a product named {string}")
    public void anExternalSourceContainsAProduct(String productName) {
        knownProduct = true;
    }

    @Given("no available product source contains a product matching {string}")
    public void noProductSourceContainsAMatch(String productName) {
        noMatchingProduct = true;
    }

    @When("the user searches for {string}")
    public void theUserSearchesFor(String productName) {
        photoUploadOffered = noMatchingProduct;
    }

    @Then("the system displays the matching products")
    public void theSystemDisplaysMatchingProducts() {
        assertTrue(knownProduct);
    }

    @Then("each result includes its product name")
    public void eachResultIncludesItsProductName() {
        assertTrue(knownProduct);
    }

    @Then("each result includes available manufacturer or package information")
    public void eachResultIncludesAvailableProductInformation() {
        assertTrue(knownProduct);
    }

    @Then("the system informs the user that no matching product was found")
    public void theSystemInformsTheUserNoProductWasFound() {
        assertTrue(noMatchingProduct);
    }

    @Then("the system offers the user the option to upload a product packaging photo")
    public void theSystemOffersPhotoUpload() {
        assertTrue(photoUploadOffered);
    }

    @When("the user chooses to upload a product packaging photo")
    public void theUserChoosesToUploadAPhoto() {
        photoUploadStarted = photoUploadOffered;
    }

    @Then("the product packaging photo upload flow is started")
    public void thePhotoUploadFlowIsStarted() {
        assertTrue(photoUploadStarted);
    }

    @Given("the user has selected {string}")
    public void theUserHasSelectedAProduct(String productName) {
        // The selected product will be supplied by the UI/API in a later iteration.
    }

    @Given("a stored halal assessment exists for {string}")
    public void aStoredAssessmentExists(String productName) {
        storedAssessment = true;
    }

    @Given("no stored halal assessment exists for {string}")
    public void noStoredAssessmentExists(String productName) {
        storedAssessment = false;
    }

    @Given("reliable product information and the halal assessment pipeline are available")
    public void reliableProductInformationAndPipelineAreAvailable() {
        reliableProductData = true;
    }

    @Given("sufficient reliable product information cannot be retrieved")
    public void sufficientReliableProductInformationCannotBeRetrieved() {
        reliableProductData = false;
    }

    @When("the user requests the halal status")
    public void theUserRequestsTheHalalStatus() {
        if (storedAssessment) {
            productDetailsDisplayed = true;
            halalStatusDisplayed = true;
            justificationDisplayed = true;
        } else if (reliableProductData) {
            productDataNormalised = true;
            assessmentStored = true;
            productDetailsDisplayed = true;
            halalStatusDisplayed = true;
            justificationDisplayed = true;
        } else {
            assessmentUnavailable = true;
        }
    }

    @Then("the system displays the product details")
    public void theSystemDisplaysProductDetails() {
        assertTrue(productDetailsDisplayed);
    }

    @Then("the system displays the halal status")
    public void theSystemDisplaysHalalStatus() {
        assertTrue(halalStatusDisplayed);
    }

    @Then("the system displays a plain-language justification for the status")
    public void theSystemDisplaysAPlainLanguageJustification() {
        assertTrue(justificationDisplayed);
    }

    @Then("the system normalises the product data into the canonical format")
    public void theSystemNormalisesProductData() {
        assertTrue(productDataNormalised);
    }

    @Then("the system creates and stores a halal assessment for {string}")
    public void theSystemCreatesAndStoresAnAssessment(String productName) {
        assertTrue(assessmentStored);
    }

    @Then("the system displays the product details, halal status, and justification")
    public void theSystemDisplaysTheAssessment() {
        assertTrue(productDetailsDisplayed && halalStatusDisplayed && justificationDisplayed);
    }

    @Then("the system informs the user that a confirmed halal assessment cannot currently be produced")
    public void theSystemInformsTheUserAnAssessmentCannotBeProduced() {
        assertTrue(assessmentUnavailable);
    }

    @Then("the system does not display an unsupported assessment as a confirmed result")
    public void theSystemDoesNotDisplayAnUnsupportedAssessment() {
        assertFalse(halalStatusDisplayed);
    }

    @Given("the OCR and OCR\\/LLM services are available")
    public void theOcrServicesAreAvailable() {
        // Service doubles are represented by the scenario state below.
    }

    @Given("the user has a valid product packaging image with readable information")
    public void theUserHasAValidReadableImage() {
        imageState = "valid";
    }

    @Given("the user has selected an unsupported or corrupted image file")
    public void theUserHasSelectedAnInvalidImage() {
        imageState = "invalid";
    }

    @Given("the user has selected a valid but unreadable product packaging image")
    public void theUserHasSelectedAnUnreadableImage() {
        imageState = "unreadable";
    }

    @Given("the user has uploaded a readable product packaging image with incomplete information")
    public void theUserHasUploadedAnIncompleteImage() {
        imageState = "incomplete";
    }

    @When("the user uploads the product packaging image")
    public void theUserUploadsTheProductPackagingImage() {
        imageValidated = !"invalid".equals(imageState);
        invalidImageError = "invalid".equals(imageState);
        textExtracted = "valid".equals(imageState);
        productDataStructured = "valid".equals(imageState);
        canonicalProductDataPrepared = "valid".equals(imageState);
        clearerPhotoRequested = "unreadable".equals(imageState);
    }

    @Then("the system validates the image file")
    public void theSystemValidatesTheImageFile() {
        assertTrue(imageValidated);
    }

    @Then("the system extracts text and confidence scores from the image")
    public void theSystemExtractsTextAndConfidenceScores() {
        assertTrue(textExtracted);
    }

    @Then("the system structures the available product information")
    public void theSystemStructuresAvailableProductInformation() {
        assertTrue(productDataStructured);
    }

    @Then("the system prepares canonical product data for the halal assessment pipeline")
    public void theSystemPreparesCanonicalProductData() {
        assertTrue(canonicalProductDataPrepared);
    }

    @Then("the system returns an invalid-image error")
    public void theSystemReturnsAnInvalidImageError() {
        assertTrue(invalidImageError);
    }

    @When("the user uploads a valid replacement product packaging image")
    public void theUserUploadsAValidReplacementImage() {
        imageState = "valid";
        imageValidated = true;
    }

    @Then("the system validates the replacement image file")
    public void theSystemValidatesTheReplacementImageFile() {
        assertTrue(imageValidated);
    }

    @Then("the system informs the user that the photo cannot be read reliably")
    public void theSystemInformsTheUserThePhotoIsUnreadable() {
        assertTrue(clearerPhotoRequested);
    }

    @Then("the system asks the user to upload another product photo")
    public void theSystemAsksForAnotherProductPhoto() {
        assertTrue(clearerPhotoRequested);
    }

    @When("the system structures the extracted product information")
    public void theSystemStructuresExtractedProductInformation() {
        missingInformationReported = "incomplete".equals(imageState);
    }

    @Then("the system informs the user which essential product information could not be identified")
    public void theSystemInformsTheUserOfMissingInformation() {
        assertTrue(missingInformationReported);
    }

    @Then("the system asks the user to upload a clearer or more complete photo")
    public void theSystemAsksForAClearerPhoto() {
        assertTrue(missingInformationReported);
    }

    @Given("the assessment-results database is available")
    public void theAssessmentResultsDatabaseIsAvailable() {
        // The database will be replaced by a repository integration later.
    }

    @Given("an authorised admin opens the stored assessments")
    public void anAuthorisedAdminOpensTheStoredAssessments() {
        authorisedAdmin = true;
    }

    @Given("stored halal assessments are available")
    public void storedHalalAssessmentsAreAvailable() {
        storedAssessmentsAvailable = true;
    }

    @Given("no stored assessment matches {string}")
    public void noStoredAssessmentMatches(String criteria) {
        noAssessmentMatch = true;
    }

    @When("the admin searches for assessments matching {string}")
    public void theAdminSearchesForAssessments(String criteria) {
        matchingAssessmentsDisplayed = authorisedAdmin && storedAssessmentsAvailable && !noAssessmentMatch;
    }

    @When("the admin selects a matching stored assessment")
    public void theAdminSelectsAMatchingStoredAssessment() {
        storedAssessmentSelected = matchingAssessmentsDisplayed;
    }

    @Then("the system displays the selected product details")
    public void theSystemDisplaysSelectedProductDetails() {
        assertTrue(storedAssessmentSelected);
    }

    @Then("the system displays the selected halal status")
    public void theSystemDisplaysSelectedHalalStatus() {
        assertTrue(storedAssessmentSelected);
    }

    @Then("the system displays the selected assessment justification")
    public void theSystemDisplaysSelectedAssessmentJustification() {
        assertTrue(storedAssessmentSelected);
    }

    @Then("the system displays a no-results message")
    public void theSystemDisplaysANoResultsMessage() {
        assertTrue(noAssessmentMatch);
    }

    @Given("an authorised admin has received a no-results message for {string}")
    public void anAuthorisedAdminHasReceivedANoResultsMessage(String criteria) {
        authorisedAdmin = true;
        noAssessmentMatch = true;
    }

    @Given("a stored assessment matches {string}")
    public void aStoredAssessmentMatches(String criteria) {
        storedAssessmentsAvailable = true;
    }

    @When("the admin refines the search to {string}")
    public void theAdminRefinesTheSearch(String criteria) {
        noAssessmentMatch = false;
        matchingAssessmentsDisplayed = authorisedAdmin && storedAssessmentsAvailable;
    }

    @Then("the system displays the matching assessments")
    public void theSystemDisplaysMatchingAssessments() {
        assertTrue(matchingAssessmentsDisplayed);
    }

    @Given("an unauthorised user attempts to open the stored assessments")
    public void anUnauthorisedUserAttemptsToOpenStoredAssessments() {
        authorisedAdmin = false;
    }

    @When("the user requests the stored assessments")
    public void theUserRequestsTheStoredAssessments() {
        accessDenied = !authorisedAdmin;
    }

    @Then("the system denies access to the stored assessments")
    public void theSystemDeniesAccessToStoredAssessments() {
        assertTrue(accessDenied);
    }
}
