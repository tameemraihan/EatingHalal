package com.eatinghalal.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shopping_list_entry")
public class ShoppingListEntry {
  @Id public UUID id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "owner_id")
  public UserAccount owner;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "product_id")
  public Product product;

  @Column(nullable = false)
  public int quantity;

  @Column(name = "added_at", nullable = false)
  public Instant addedAt;

  protected ShoppingListEntry() {}

  public ShoppingListEntry(UserAccount owner, Product product, int quantity) {
    this.id = UUID.randomUUID();
    this.owner = owner;
    this.product = product;
    this.quantity = quantity;
    this.addedAt = Instant.now();
  }
}
