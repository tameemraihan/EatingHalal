package com.eatinghalal.repository;

import com.eatinghalal.model.Product;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
  List<Product> findTop30ByNameContainingIgnoreCaseOrderByNameAsc(String name);

  Optional<Product> findByBarcode(String barcode);
}
