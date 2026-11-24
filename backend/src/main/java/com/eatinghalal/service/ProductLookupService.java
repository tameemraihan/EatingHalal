package com.eatinghalal.service;

import com.eatinghalal.integration.ProductDataClient;
import com.eatinghalal.model.Product;
import com.eatinghalal.repository.ProductRepository;
import com.eatinghalal.web.ApiModels.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductLookupService {
  private final ProductRepository products;
  private final ProductDataClient external;

  public ProductLookupService(ProductRepository products, ProductDataClient external) {
    this.products = products;
    this.external = external;
  }

  public SearchResult search(String query) {
    if (query == null || query.isBlank())
      return new SearchResult(List.of(), false, "Enter a product name to search.");
    String normalized = query.trim();
    if (normalized.length() > 100)
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Search must be at most 100 characters.");
    List<Product> local = products.findTop30ByNameContainingIgnoreCaseOrderByNameAsc(normalized);
    if (!local.isEmpty())
      return new SearchResult(
          local.stream().map(ProductView::from).toList(), false, "Showing stored products.");
    ProductDataClient.LookupResult lookup = external.search(normalized);
    List<ProductView> imported = new ArrayList<>();
    for (ProductDataClient.SourceProduct source : lookup.products()) {
      Product product =
          products
              .findByBarcode(source.barcode())
              .orElseGet(
                  () ->
                      products.save(
                          new Product(
                              source.name(),
                              source.brand(),
                              source.packageDescription(),
                              source.ingredients(),
                              source.labels(),
                              "Open Food Facts",
                              source.sourceUrl(),
                              source.barcode())));
      imported.add(ProductView.from(product));
    }
    return new SearchResult(imported, imported.isEmpty(), lookup.message());
  }
}
