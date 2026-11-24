# Week 8 Submission Status

This is the submission entry point. It maps the supplied board/architecture TASK to concrete files and distinguishes completed local artifacts from unfinished functionality and publication. Source pages are recorded in [Assignment Sources](AssignmentSources.md).

## Core package

| Deliverable | Status | Artifact / remaining action |
| --- | --- | --- |
| Framework explanation | Draft prepared; not published | [Blog draft](blog/08-software-architecture.md), [SAD](SoftwareArchitectureDocument.md). Publish after team review. |
| Framework-based CRUD | Implemented locally | [Shopping-list controller](../backend/src/main/java/com/eatinghalal/web/ShoppingListController.java), [service](../backend/src/main/java/com/eatinghalal/service/ShoppingListService.java), Spring Data repository and React UI. Quantity editing supplies Update. |
| Completed RUP SAD | Prepared locally | [Software Architecture Document](SoftwareArchitectureDocument.md), sections 1–11 with prescribed n/a sections and explicit limits. |
| Framework diagram | Included | Official Spring MVC diagram and original responsibility view in SAD section 5.1. |
| Code class diagram with architectural regions | Generated and rendered | [Class diagram](assets/images/SAD-ClassDiagram.png), [source](assets/plantuml-code/SAD-ClassDiagram.puml), [generator](../scripts/generate-diagrams.py). |
| Overall use-case diagram and SRS link | Included | SAD section 4, existing overall diagram and [SRS](SoftwareRequirementsSpecification.md). |
| Correct UML deployment diagram | Prepared/rendered | [Deployment diagram](assets/images/SAD-Deployment.png) showing actual local placement. |
| Database/EER | Generated/rendered | [EER](assets/images/SAD-DatabaseModel.png) from the applied [migration](../backend/src/main/resources/db/migration/V1__initial_schema.sql). |
| Local first-use-case demo | Implemented and verified | [Local Demo Guide](LocalDemoGuide.md), [recording](evidence/local-demo.webm), [screenshot](assets/images/local-crud-demo.png). |
| Cucumber recording | Captured from a real passing run | [Video](evidence/cucumber-running.webm), [HTML report](evidence/cucumber-report.html), [raw output](evidence/cucumber-output.txt). |
| First-demo recording | Captured from passing browser walkthrough | [Video](evidence/local-demo.webm), [browser summary](evidence/browser-test-summary.txt), [mobile screenshot](assets/images/local-mobile-demo.png). |
| Code/SAD/diagrams | Submission package on EH-60 | [Branch](https://github.com/tameemraihan/EatingHalal/tree/EH-60) contains the code, architecture document, diagrams and recordings once pushed. |
| WordPress framework post | Publication outstanding | [Draft](blog/08-software-architecture.md) is prepared; WordPress has not been updated. |
| Publicly reachable demo | NOT delivered | Owner chose local setup. This does not satisfy the TASK slide's public-server wording; confirm local acceptability with the instructor. |

## Verification

The final recorded API run discovered 26 scenarios: **20 executed successfully, zero failures/errors, six explicitly skipped as pending**. See the [Surefire summary](evidence/api-test-summary.txt) and [Cucumber JSON](evidence/cucumber-report.json). No simulated-success steps are used for excluded flows. The browser suite passed **two tests**, including desktop CRUD/user separation/admin review and mobile layout. A [restart check](evidence/restart-persistence.txt) verified that the same entry and quantity 7 survived a full application/PostgreSQL restart. The optional [live Open Food Facts probe](evidence/live-product-source.txt) returned and persisted ten products with provenance; no new verdict was generated.

Tests exercise HTTP endpoints and real database records. Open Food Facts adapter tests use a controlled external HTTP fixture; the database is not mocked. Docker Compose/PostgreSQL 16 is supplied but not exercised here; native local verification uses PostgreSQL 14.15. The recordings and text summaries document the actual checks; they are not inferred from implementation alone.

## Unimplemented or unverified expected functionality

- **Automatic halal assessment generation:** no approved rules/evaluator; stored synthetic results and unavailable responses only. One acceptance scenario pending.
- **OCR and canonical product extraction:** no OCR/LLM provider configured. Four acceptance scenarios pending. File selection and an unavailable endpoint do not constitute the upload use case's successful flow.
- **Photo-navigation Cucumber scenario:** tagged pending; React navigation is implemented and separately checked by browser automation.
- **Public hosting:** not implemented, by the owner's local-only decision.
- **WordPress blog publication:** not completed. The architecture package is versioned on EH-60; ensure the instructor can access the branch. The reviewed blog still needs publication.
- **CI/CD:** not implemented; SAD section 6 n/a follows the board/semester-2 wording.
- **Full AI A/B/C comparison:** [brief prepared](AIArchitectureExperiment.md), no independent trials or measured comparison.
- **Production account lifecycle, provider/evidence freshness, retention/deletion policies and load targets:** not implemented/verified; documented as limitations, not completed requirements.

## Supplemental chapter evidence

[CRC/data inventory/derived product and policy tests](DataStewardship.md) and [Human Contribution](HumanContribution.md) are prepared. The chapter itself says to check official assessment instructions before treating these as mandatory. No unrelated student-records application was introduced.

## Final hand-in checklist

- [ ] Review the generated code, diagram semantics and blog claims as a team.
- [x] Capture passing API/browser results, screenshots, demo and Cucumber recordings, and verify restart persistence.
- [ ] Review the recordings before submitting them.
- [ ] Confirm the instructor can access EH-60 and its linked artifacts.
- [ ] Publish the reviewed framework blog post to WordPress.
- [ ] Put demo and Cucumber recordings on the blog or link an accessible video host.
- [ ] Confirm the deadline/submission destination and whether local deployment is accepted.
- [ ] Confirm whether the full AI comparison is assessed this week; run it if required.
- [ ] Make the OCR/new-assessment limitations explicit in the hand-in; do not count them as implemented use cases.
- [x] Include this submission page linking the artifacts.
