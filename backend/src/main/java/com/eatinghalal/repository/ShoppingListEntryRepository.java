package com.eatinghalal.repository;

import com.eatinghalal.model.ShoppingListEntry;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListEntryRepository extends JpaRepository<ShoppingListEntry, UUID> {
  List<ShoppingListEntry> findByOwnerUsernameOrderByAddedAtAsc(String username);

  Optional<ShoppingListEntry> findByIdAndOwnerUsername(UUID id, String username);

  boolean existsByOwnerUsernameAndProductId(String username, UUID productId);

  void deleteByOwnerUsername(String username);
}
