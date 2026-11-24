package com.eatinghalal.steps;

import static org.junit.jupiter.api.Assertions.*;

import com.eatinghalal.TestEnvironment;
import com.eatinghalal.model.*;
import com.eatinghalal.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

/** Acceptance steps call the running HTTP API and verify records in real PostgreSQL. */
public class UseCaseSteps {
  @LocalServerPort int port;
  @Autowired ProductRepository products;
  @Autowired AssessmentRepository assessments;
  @Autowired ShoppingListEntryRepository entries;
  @Autowired UserAccountRepository users;
  @Autowired PasswordEncoder passwords;
  @Autowired ObjectMapper json;
  @Autowired com.eatinghalal.integration.OpenFoodFactsClient externalClient;
  private SessionFilter session;
  private String csrfToken, csrfHeader;
  private Response response;
  private Product selected;
  private UUID selectedEntry;
  private String assessmentQuery = "";

  @Before
  public void reset() {
    entries.deleteAll();
    assessments.deleteAll();
    products.deleteAll();
    users.deleteAll();
    for (String name : new String[] {"demo", "other", "admin"})
      users.save(
          new UserAccount(
              name, passwords.encode("test-password"), name.equals("admin") ? "ADMIN" : "USER"));
    TestEnvironment.PROVIDER_RESPONSE.set("{\"products\":[]}");
    org.springframework.test.util.ReflectionTestUtils.setField(
        externalClient, "lastSearch", java.time.Instant.EPOCH);
    ((Map<?, ?>)
            org.springframework.test.util.ReflectionTestUtils.getField(externalClient, "cache"))
        .clear();
    session = new SessionFilter();
    token();
  }

  private RequestSpecification request() {
    return RestAssured.given().baseUri("http://127.0.0.1").port(port).filter(session);
  }

  private RequestSpecification mutation() {
    return request().header(csrfHeader, csrfToken);
  }

  private void token() {
    Response sessionResponse = request().get("/api/session");
    assertEquals(200, sessionResponse.statusCode());
    csrfHeader = sessionResponse.path("csrfHeader");
    csrfToken = sessionResponse.path("csrfToken");
  }

  private void login(String name) {
    token();
    assertEquals(
        204,
        mutation()
            .formParam("username", name)
            .formParam("password", "test-password")
            .post("/api/login")
            .statusCode());
    token();
  }

  private Product product(String name) {
    return products.findTop30ByNameContainingIgnoreCaseOrderByNameAsc(name).stream()
        .filter(product -> product.name.equals(name))
        .findFirst()
        .orElseGet(
            () ->
                products.save(
                    new Product(
                        name,
                        "Synthetic test fixture",
                        "100 g",
                        "Synthetic ingredients",
                        "Demo only",
                        "Synthetic test",
                        "",
                        null)));
  }

  private void assessed(Product product, String verdict) {
    if (assessments.findFirstByProductIdOrderByVersionDesc(product.id).isEmpty())
      assessments.save(
          new HalalAssessment(
              product,
              1,
              verdict,
              "Synthetic test justification",
              "Synthetic evidence",
              "synthetic-test"));
  }

  private void seedEntry(String name) {
    selected = product(name);
    assessed(selected, "Halal");
    UserAccount owner = users.findByUsername("demo").orElseThrow();
    selectedEntry = entries.save(new ShoppingListEntry(owner, selected, 1)).id;
  }

  private Response list() {
    return request().get("/api/me/shopping-list/entries");
  }

  @Given("the EatingHalal application is available")
  public void available() {
    assertEquals(200, request().get("/api/session").statusCode());
  }

  @Given("the hosted database contains a product named {string}")
  public void hosted(String name) {
    product(name);
  }

  @Given("an available external product source contains a product named {string}")
  public void external(String name) throws Exception {
    TestEnvironment.PROVIDER_RESPONSE.set(
        json.writeValueAsString(
            Map.of(
                "products",
                List.of(
                    Map.of(
                        "code",
                        "999000111",
                        "product_name",
                        name,
                        "brands",
                        "Synthetic provider",
                        "quantity",
                        "250 g",
                        "ingredients_text",
                        "Fixture ingredients",
                        "labels",
                        "")))));
  }

  @Given("no available product source contains a product matching {string}")
  public void missing(String name) {
    assertTrue(products.findTop30ByNameContainingIgnoreCaseOrderByNameAsc(name).isEmpty());
    TestEnvironment.PROVIDER_RESPONSE.set("{\"products\":[]}");
  }

  @When("the user searches for {string}")
  public void search(String name) {
    response = request().queryParam("query", name).get("/api/products");
    assertEquals(200, response.statusCode());
  }

  @Then("the system displays the matching products")
  public void matchingProducts() {
    assertFalse(response.jsonPath().getList("products").isEmpty());
  }

  @Then("each result includes its product name")
  public void names() {
    List<String> names = response.jsonPath().getList("products.name");
    assertFalse(names.isEmpty());
    assertTrue(names.stream().allMatch(n -> n != null && !n.isBlank()));
  }

  @Then("each result includes available manufacturer or package information")
  public void details() {
    assertFalse(response.jsonPath().getList("products.brand").isEmpty());
    assertFalse(response.jsonPath().getList("products.packageDescription").isEmpty());
  }

  @Then("the system informs the user that no matching product was found")
  public void noProducts() {
    assertTrue(response.jsonPath().getList("products").isEmpty());
  }

  @Then("the system offers the user the option to upload a product packaging photo")
  public void offersPhoto() {
    assertEquals(true, response.path("photoUploadOffered"));
  }

  @Then("the imported product is persisted with its source")
  public void imported() {
    Product product = products.findByBarcode("999000111").orElseThrow();
    assertEquals("Open Food Facts", product.source);
    assertFalse(product.sourceUrl.isBlank());
    assertEquals("EatingHalalIntegrationTests/1.0", TestEnvironment.LAST_USER_AGENT.get());
  }

  @Given("the user has selected {string}")
  public void selected(String name) {
    selected = product(name);
  }

  @Given("a stored halal assessment exists for {string}")
  public void stored(String name) {
    assessed(product(name), "Halal");
  }

  @Given("no stored halal assessment exists for {string}")
  public void noStored(String name) {
    assertTrue(assessments.findFirstByProductIdOrderByVersionDesc(product(name).id).isEmpty());
  }

  @Given("sufficient reliable product information cannot be retrieved")
  public void insufficient() {
    assertTrue(selected.ingredients.startsWith("Synthetic"));
  }

  @When("the user requests the halal status")
  public void status() {
    response = request().get("/api/products/" + selected.id + "/assessment");
  }

  @Then("the system displays the product details")
  public void productDetails() {
    assertEquals(200, response.statusCode());
    assertEquals(selected.name, response.path("product.name"));
  }

  @Then("the system displays the halal status")
  public void verdict() {
    assertEquals(200, response.statusCode());
    assertNotNull(response.path("verdict"));
  }

  @Then("the system displays a plain-language justification for the status")
  public void justification() {
    assertFalse(response.<String>path("justification").isBlank());
  }

  @Then(
      "the system informs the user that a confirmed halal assessment cannot currently be"
          + " produced")
  public void unavailable() {
    assertEquals(422, response.statusCode());
    assertTrue(response.<String>path("message").contains("cannot currently be produced"));
  }

  @Then("the system does not display an unsupported assessment as a confirmed result")
  public void notConfirmed() {
    assertNull(response.path("verdict"));
    assertTrue(assessments.findFirstByProductIdOrderByVersionDesc(selected.id).isEmpty());
  }

  @Given("the assessment-results database is available")
  public void assessmentDatabase() {
    assertTrue(assessments.count() >= 0);
  }

  @Given("an authorised admin opens the stored assessments")
  public void admin() {
    login("admin");
  }

  @Given("stored halal assessments are available")
  public void storedAvailable() {
    assessed(product("Haribo Goldbears"), "Halal");
  }

  @Given("no stored assessment matches {string}")
  public void noAssessment(String query) {
    assertTrue(
        assessments
            .findTop30ByProductNameContainingIgnoreCaseOrderByAssessedAtDesc(query)
            .isEmpty());
  }

  @When("the admin searches for assessments matching {string}")
  public void adminSearch(String query) {
    assessmentQuery = query;
    response = request().queryParam("query", query).get("/api/admin/assessments");
  }

  @When("the admin selects a matching stored assessment")
  public void adminSelect() {
    assertEquals(200, response.statusCode());
    String id = response.jsonPath().getString("[0].id");
    response = request().get("/api/admin/assessments/" + id);
  }

  @Then("the system displays the selected product details")
  public void selectedDetails() {
    assertEquals(200, response.statusCode());
    assertNotNull(response.path("product.name"));
  }

  @Then("the system displays the selected halal status")
  public void selectedStatus() {
    verdict();
  }

  @Then("the system displays the selected assessment justification")
  public void selectedJustification() {
    justification();
  }

  @Then("the system displays a no-results message")
  public void noResults() {
    assertEquals(200, response.statusCode());
    assertTrue(response.jsonPath().getList("$").isEmpty());
  }

  @Given("an authorised admin has received a no-results message for {string}")
  public void previousNoResults(String query) {
    login("admin");
    adminSearch(query);
    noResults();
  }

  @Given("a stored assessment matches {string}")
  public void matchingAssessment(String name) {
    assessed(product(name), "Halal");
  }

  @When("the admin refines the search to {string}")
  public void refine(String query) {
    adminSearch(query);
  }

  @Then("the system displays the matching assessments")
  public void matchingAssessments() {
    assertEquals(200, response.statusCode());
    assertFalse(response.jsonPath().getList("$").isEmpty());
  }

  @Given("an unauthorised user attempts to open the stored assessments")
  public void ordinaryUser() {
    login("demo");
  }

  @When("the user requests the stored assessments")
  public void adminRequest() {
    response = request().get("/api/admin/assessments");
  }

  @Then("the system denies access to the stored assessments")
  public void denies() {
    assertEquals(403, response.statusCode());
    assertFalse(response.asString().contains("justification"));
  }

  @Given("the shopping list database is available")
  public void listDatabase() {
    login("demo");
    assertEquals(200, list().statusCode());
  }

  @Given("the user's shopping list contains {string} with status {string}")
  public void contains(String name, String verdict) {
    selected = product(name);
    assessed(selected, verdict);
    seedEntry(name);
  }

  @Given("the user's shopping list is empty")
  public void empty() {
    assertTrue(entries.findByOwnerUsernameOrderByAddedAtAsc("demo").isEmpty());
  }

  @Given("the halal assessment result for {string} is displayed")
  public void displayed(String name) {
    selected = product(name);
    assessed(selected, "Halal");
    response = request().get("/api/products/" + selected.id + "/assessment");
    assertEquals(200, response.statusCode());
  }

  @Given("{string} is not on the user's shopping list")
  public void notListed(String name) {
    assertFalse(entries.existsByOwnerUsernameAndProductId("demo", product(name).id));
  }

  @Given("{string} is already on the user's shopping list")
  public void alreadyListed(String name) {
    seedEntry(name);
  }

  @When("the user opens the shopping list")
  public void opensList() {
    response = list();
  }

  @When("the user adds {string} to the shopping list")
  public void adds(String name) {
    response =
        mutation()
            .contentType("application/json")
            .body(Map.of("productId", product(name).id, "quantity", 1))
            .post("/api/me/shopping-list/entries");
  }

  @Then("the system displays the shopping list")
  public void displaysList() {
    assertEquals(200, response.statusCode());
  }

  @Then("the shopping list includes {string}")
  public void includes(String name) {
    assertTrue(response.jsonPath().getList("product.name").contains(name));
  }

  @Then("each entry includes the product name and halal status")
  public void entryDetails() {
    assertNotNull(response.jsonPath().getString("[0].product.name"));
    assertNotNull(response.jsonPath().getString("[0].verdict"));
  }

  @Then("the system informs the user that the shopping list is empty")
  public void listEmpty() {
    Response current = list();
    assertEquals(200, current.statusCode());
    assertTrue(current.jsonPath().getList("$").isEmpty());
  }

  @Then("the system saves {string} to the user's shopping list")
  public void saved(String name) {
    assertTrue(entries.existsByOwnerUsernameAndProductId("demo", product(name).id));
  }

  @Then("the system confirms that {string} has been added")
  public void added(String name) {
    assertEquals(201, response.statusCode());
    assertEquals(name, response.path("product.name"));
  }

  @Then("the system informs the user that {string} is already on the shopping list")
  public void duplicate(String name) {
    assertEquals(409, response.statusCode());
  }

  @Then("the system does not add a duplicate entry")
  public void noDuplicate() {
    assertEquals(1, entries.findByOwnerUsernameOrderByAddedAtAsc("demo").size());
  }

  @When("the user removes {string} from the shopping list")
  public void removes(String name) {
    response = mutation().delete("/api/me/shopping-list/entries/" + selectedEntry);
  }

  @Then("the system removes {string} from the user's shopping list")
  public void removed(String name) {
    assertFalse(entries.existsByOwnerUsernameAndProductId("demo", product(name).id));
    assertTrue(products.existsById(product(name).id));
  }

  @Then("the system confirms that {string} has been removed")
  public void removedConfirmation(String name) {
    assertEquals(204, response.statusCode());
  }

  @Then("the system displays the updated shopping list")
  public void updatedList() {
    assertEquals(200, list().statusCode());
  }

  @When("the user clears the shopping list")
  public void clears() {
    response = mutation().delete("/api/me/shopping-list/entries");
  }

  @Then("the system removes all products from the user's shopping list")
  public void cleared() {
    assertTrue(entries.findByOwnerUsernameOrderByAddedAtAsc("demo").isEmpty());
  }

  @Then("the system confirms that the shopping list has been cleared")
  public void clearedConfirmation() {
    assertEquals(204, response.statusCode());
  }

  @When("the user changes the saved quantity to {int}")
  public void quantity(int quantity) {
    response =
        mutation()
            .contentType("application/json")
            .body(Map.of("quantity", quantity))
            .patch("/api/me/shopping-list/entries/" + selectedEntry);
  }

  @Then("the persisted quantity is {int}")
  public void persistedQuantity(int quantity) {
    assertEquals(200, response.statusCode());
    assertEquals(quantity, entries.findById(selectedEntry).orElseThrow().quantity);
  }

  @Then("the quantity change is rejected")
  public void rejectedQuantity() {
    assertEquals(400, response.statusCode());
    assertEquals(1, entries.findById(selectedEntry).orElseThrow().quantity);
  }

  @When("another signed-in user requests the first user's shopping-list entry")
  public void crossUser() {
    login("other");
    quantity(5);
  }

  @Then("the entry is inaccessible and remains unchanged")
  public void ownership() {
    assertEquals(404, response.statusCode());
    assertEquals(1, entries.findById(selectedEntry).orElseThrow().quantity);
    assertTrue(list().jsonPath().getList("$").isEmpty());
  }

  @Given("the selected product has only a Haram assessment")
  public void haram() {
    selected = product("Synthetic Haram Fixture");
    assessed(selected, "Haram");
  }

  @When("the user tries to save the selected product")
  public void saveSelected() {
    adds(selected.name);
  }

  @Then("the ineligible product is not saved")
  public void ineligible() {
    assertEquals(422, response.statusCode());
    assertEquals(0, entries.count());
  }

  @When("the user submits a quantity change without a CSRF token")
  public void withoutCsrf() {
    response =
        request()
            .contentType("application/json")
            .body(Map.of("quantity", 4))
            .patch("/api/me/shopping-list/entries/" + selectedEntry);
  }

  @Then("the CSRF check rejects the change")
  public void csrfRejected() {
    assertEquals(403, response.statusCode());
    assertEquals(1, entries.findById(selectedEntry).orElseThrow().quantity);
  }
}
