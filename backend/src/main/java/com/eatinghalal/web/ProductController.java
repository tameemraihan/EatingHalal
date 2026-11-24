package com.eatinghalal.web;

import com.eatinghalal.service.*;
import com.eatinghalal.web.ApiModels.*;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductLookupService lookup;
  private final AssessmentService assessments;

  public ProductController(ProductLookupService lookup, AssessmentService assessments) {
    this.lookup = lookup;
    this.assessments = assessments;
  }

  @GetMapping
  public SearchResult search(@RequestParam(defaultValue = "") String query) {
    return lookup.search(query);
  }

  @GetMapping("/{id}/assessment")
  public AssessmentView assessment(@PathVariable UUID id) {
    return assessments.current(id);
  }
}
