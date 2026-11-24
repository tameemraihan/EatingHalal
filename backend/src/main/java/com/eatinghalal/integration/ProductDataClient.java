package com.eatinghalal.integration;

import java.util.List;

public interface ProductDataClient {
  record SourceProduct(
      String barcode,
      String name,
      String brand,
      String packageDescription,
      String ingredients,
      String labels,
      String sourceUrl) {}

  record LookupResult(List<SourceProduct> products, String message) {}

  LookupResult search(String query);
}
