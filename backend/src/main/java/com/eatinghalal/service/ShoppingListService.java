package com.eatinghalal.service;

import com.eatinghalal.model.*;
import com.eatinghalal.repository.*;
import com.eatinghalal.web.ApiModels.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ShoppingListService {
  private final ShoppingListEntryRepository entries;
  private final UserAccountRepository users;
  private final ProductRepository products;
  private final AssessmentRepository assessments;

  public ShoppingListService(
      ShoppingListEntryRepository entries,
      UserAccountRepository users,
      ProductRepository products,
      AssessmentRepository assessments) {
    this.entries = entries;
    this.users = users;
    this.products = products;
    this.assessments = assessments;
  }

  @Transactional(readOnly = true)
  public List<EntryView> list(String username) {
    return entries.findByOwnerUsernameOrderByAddedAtAsc(username).stream().map(this::view).toList();
  }

  public EntryView add(String username, AddEntry request) {
    Product product =
        products
            .findById(request.productId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found."));
    if (entries.existsByOwnerUsernameAndProductId(username, product.id))
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "Product is already on your shopping list.");
    String verdict =
        assessments
            .findFirstByProductIdOrderByVersionDesc(product.id)
            .map(assessment -> assessment.verdict)
            .orElse("Unavailable");
    if (!verdict.equals("Halal"))
      throw new ResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          "Only products with assessment stored Halal assessment can be added.");
    UserAccount owner =
        users
            .findByUsername(username)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sign in again."));
    return view(entries.save(new ShoppingListEntry(owner, product, request.quantity())));
  }

  public EntryView update(String username, UUID id, ChangeQuantity request) {
    ShoppingListEntry entry = ownEntry(username, id);
    entry.quantity = request.quantity();
    return view(entries.save(entry));
  }

  public void remove(String username, UUID id) {
    entries.delete(ownEntry(username, id));
  }

  public void clear(String username) {
    entries.deleteByOwnerUsername(username);
  }

  private ShoppingListEntry ownEntry(String username, UUID id) {
    return entries
        .findByIdAndOwnerUsername(id, username)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Shopping-list entry not found."));
  }

  private EntryView view(ShoppingListEntry entry) {
    String verdict =
        assessments
            .findFirstByProductIdOrderByVersionDesc(entry.product.id)
            .map(assessment -> assessment.verdict)
            .orElse("Unavailable");
    return new EntryView(
        entry.id,
        ProductView.from(entry.product),
        entry.quantity,
        verdict,
        !verdict.equals("Halal"),
        entry.addedAt);
  }
}
