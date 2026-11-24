package com.eatinghalal.web;

import com.eatinghalal.service.AssessmentService;
import com.eatinghalal.web.ApiModels.AssessmentView;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/assessments")
public class AdminAssessmentController {
  private final AssessmentService assessments;

  public AdminAssessmentController(AssessmentService assessments) {
    this.assessments = assessments;
  }

  @GetMapping
  public List<AssessmentView> search(@RequestParam(defaultValue = "") String query) {
    return assessments.search(query);
  }

  @GetMapping("/{id}")
  public AssessmentView detail(@PathVariable UUID id) {
    return assessments.detail(id);
  }
}
