package com.eatinghalal.integration;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.time.Instant;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenFoodFactsClient implements ProductDataClient {
  private final RestClient client;
  private final boolean enabled;
  private final String baseUrl;
  private final Map<String, LookupResult> cache = new HashMap<>();
  private Instant lastSearch = Instant.EPOCH;

  public OpenFoodFactsClient(
      @Value("${eatinghalal.open-food-facts.enabled:false}") boolean enabled,
      @Value("${eatinghalal.open-food-facts.base-url}") String baseUrl,
      @Value("${eatinghalal.open-food-facts.user-agent}") String userAgent) {
    this.enabled = enabled;
    this.baseUrl = baseUrl;
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(1500);
    factory.setReadTimeout(2000);
    client =
        RestClient.builder().requestFactory(factory).defaultHeader("User-Agent", userAgent).build();
  }

  @Override
  public synchronized LookupResult search(String query) {
    if (!enabled)
      return new LookupResult(List.of(), "External lookup is disabled; showing stored products.");
    String key = query.toLowerCase(Locale.ROOT);
    if (cache.containsKey(key)) return cache.get(key);
    if (Instant.now().isBefore(lastSearch.plusSeconds(7)))
      return new LookupResult(List.of(), "External search is rate-limited; try again shortly.");
    lastSearch = Instant.now();
    try {
      URI uri =
          UriComponentsBuilder.fromHttpUrl(baseUrl)
              .path("/cgi/search.pl")
              .queryParam("search_terms", query)
              .queryParam("search_simple", 1)
              .queryParam("action", "process")
              .queryParam("json", 1)
              .queryParam("page_size", 10)
              .queryParam("fields", "code,product_name,brands,quantity,ingredients_text,labels")
              .build()
              .encode()
              .toUri();
      JsonNode response = client.get().uri(uri).retrieve().body(JsonNode.class);
      List<SourceProduct> products = new ArrayList<>();
      if (response != null)
        for (JsonNode product : response.path("products")) {
          String code = product.path("code").asText("");
          String name = product.path("product_name").asText("");
          if (!code.isBlank() && !name.isBlank())
            products.add(
                new SourceProduct(
                    code,
                    name,
                    product.path("brands").asText(""),
                    product.path("quantity").asText(""),
                    product.path("ingredients_text").asText(""),
                    product.path("labels").asText(""),
                    "https://world.openfoodfacts.org/product/" + code));
        }
      LookupResult result =
          new LookupResult(
              products,
              "Data from Open Food Facts; imported labels are not verified halal verdicts.");
      if (cache.size() >= 100) cache.clear();
      cache.put(key, result);
      return result;
    } catch (Exception exception) {
      return new LookupResult(
          List.of(), "External source is unavailable; showing stored products.");
    }
  }
}
