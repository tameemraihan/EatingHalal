package com.eatinghalal.repository;

import com.eatinghalal.model.HalalAssessment;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<HalalAssessment, UUID> {
  Optional<HalalAssessment> findFirstByProductIdOrderByVersionDesc(UUID productId);

  List<HalalAssessment> findTop30ByProductNameContainingIgnoreCaseOrderByAssessedAtDesc(
      String query);
}
