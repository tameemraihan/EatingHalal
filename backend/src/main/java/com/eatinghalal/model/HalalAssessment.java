package com.eatinghalal.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "halal_assessment")
public class HalalAssessment {
  @Id public UUID id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "product_id")
  public Product product;

  @Column(nullable = false)
  public int version;

  @Column(nullable = false)
  public String verdict;

  @Column(nullable = false, columnDefinition = "text")
  public String justification;

  @Column(name = "input_snapshot", nullable = false, columnDefinition = "text")
  public String inputSnapshot;

  @Column(name = "evaluator_version", nullable = false)
  public String evaluatorVersion;

  @Column(name = "assessed_at", nullable = false)
  public Instant assessedAt;

  protected HalalAssessment() {}

  public HalalAssessment(
      Product product,
      int version,
      String verdict,
      String justification,
      String inputSnapshot,
      String evaluatorVersion) {
    this.id = UUID.randomUUID();
    this.product = product;
    this.version = version;
    this.verdict = verdict;
    this.justification = justification;
    this.inputSnapshot = inputSnapshot;
    this.evaluatorVersion = evaluatorVersion;
    this.assessedAt = Instant.now();
  }
}
