package com.eatinghalal.web;

import com.eatinghalal.model.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;

public final class ApiModels {
  private ApiModels() {}

  public record ProductView(
      UUID id,
      String name,
      String brand,
      String packageDescription,
      String ingredients,
      String labels,
      String source,
      String sourceUrl,
      Instant retrievedAt,
      String barcode) {
    public static ProductView from(Product product) {
      return new ProductView(
          product.id,
          product.name,
          product.brand,
          product.packageDescription,
          product.ingredients,
          product.labels,
          product.source,
          product.sourceUrl,
          product.retrievedAt,
          product.barcode);
    }
  }

  public record SearchResult(
      List<ProductView> products, boolean photoUploadOffered, String sourceMessage) {}

  public record AssessmentView(
      UUID id,
      ProductView product,
      String verdict,
      String justification,
      int version,
      String evaluatorVersion,
      Instant assessedAt) {
    public static AssessmentView from(HalalAssessment assessment) {
      return new AssessmentView(
          assessment.id,
          ProductView.from(assessment.product),
          assessment.verdict,
          assessment.justification,
          assessment.version,
          assessment.evaluatorVersion,
          assessment.assessedAt);
    }
  }

  public record EntryView(
      UUID id,
      ProductView product,
      int quantity,
      String verdict,
      boolean needsReview,
      Instant addedAt) {}

  public record AddEntry(@NotNull UUID productId, @Min(1) @Max(999) int quantity) {}

  public record ChangeQuantity(@Min(1) @Max(999) int quantity) {}
}
