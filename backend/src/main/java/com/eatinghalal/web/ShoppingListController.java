package com.eatinghalal.web;

import com.eatinghalal.service.ShoppingListService;
import com.eatinghalal.web.ApiModels.*;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me/shopping-list/entries")
public class ShoppingListController {
  private final ShoppingListService shoppingList;

  public ShoppingListController(ShoppingListService shoppingList) {
    this.shoppingList = shoppingList;
  }

  @GetMapping
  public List<EntryView> list(Principal principal) {
    return shoppingList.list(principal.getName());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EntryView add(Principal principal, @Valid @RequestBody AddEntry request) {
    return shoppingList.add(principal.getName(), request);
  }

  @PatchMapping("/{id}")
  public EntryView update(
      Principal principal, @PathVariable UUID id, @Valid @RequestBody ChangeQuantity request) {
    return shoppingList.update(principal.getName(), id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remove(Principal principal, @PathVariable UUID id) {
    shoppingList.remove(principal.getName(), id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void clear(Principal principal) {
    shoppingList.clear(principal.getName());
  }
}
