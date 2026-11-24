package com.eatinghalal.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
  @Id public UUID id;

  @Column(unique = true)
  public String barcode;

  @Column(nullable = false)
  public String name;

  @Column(nullable = false)
  public String brand;

  @Column(name = "package_description", nullable = false)
  public String packageDescription;

  @Column(nullable = false, columnDefinition = "text")
  public String ingredients;

  @Column(nullable = false, columnDefinition = "text")
  public String labels;

  @Column(nullable = false)
  public String source;

  @Column(name = "source_url", nullable = false, columnDefinition = "text")
  public String sourceUrl;

  @Column(name = "retrieved_at", nullable = false)
  public Instant retrievedAt;

  protected Product() {}

  public Product(
      String name,
      String brand,
      String packageDescription,
      String ingredients,
      String labels,
      String source,
      String sourceUrl,
      String barcode) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.brand = brand;
    this.packageDescription = packageDescription;
    this.ingredients = ingredients;
    this.labels = labels;
    this.source = source;
    this.sourceUrl = sourceUrl;
    this.barcode = barcode;
    this.retrievedAt = Instant.now();
  }
}
