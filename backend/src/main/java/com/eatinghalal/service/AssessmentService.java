package com.eatinghalal.service;

import com.eatinghalal.repository.*;
import com.eatinghalal.web.ApiModels.AssessmentView;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class AssessmentService {
  private final AssessmentRepository assessments;
  private final ProductRepository products;

  public AssessmentService(AssessmentRepository assessments, ProductRepository products) {
    this.assessments = assessments;
    this.products = products;
  }

  public AssessmentView current(UUID productId) {
    if (!products.existsById(productId))
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.");
    return assessments
        .findFirstByProductIdOrderByVersionDesc(productId)
        .map(AssessmentView::from)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A confirmed assessment cannot currently be produced. No reviewed assessment is"
                        + " stored."));
  }

  public List<AssessmentView> search(String query) {
    return assessments
        .findTop30ByProductNameContainingIgnoreCaseOrderByAssessedAtDesc(query.trim())
        .stream()
        .map(AssessmentView::from)
        .toList();
  }

  public AssessmentView detail(UUID id) {
    return assessments
        .findById(id)
        .map(AssessmentView::from)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found."));
  }
}
