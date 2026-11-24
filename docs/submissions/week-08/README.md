# Week 08 — Software Architecture and CRUD

This package groups the Week 08 hand-in, its recorded verification and remaining submission actions. Project documents describe the implementation; this page records the weekly submission snapshot.

## Deliverables

| Deliverable | Artifact | Status |
| --- | --- | --- |
| Framework explanation | [Blog draft](../../blog/08-software-architecture.md) | Prepared for team review; not published to WordPress. |
| RUP architecture document | [SAD](../../architecture/SoftwareArchitectureDocument.md) | Prepared with implementation boundaries and limits. |
| Framework and high-level architecture diagrams | SAD section 5.1 | Included; official Spring source credited. |
| Code-derived class diagram with architectural regions | [Image](../../assets/images/architecture/SAD-ClassDiagram.png), [source](../../assets/plantuml-code/architecture/SAD-ClassDiagram.puml) | Generated from production source and rendered. |
| Overall use-case diagram and SRS | [SRS](../../SoftwareRequirementsSpecification.md), SAD section 4 | Included. |
| UML deployment diagram | [Image](../../assets/images/architecture/SAD-Deployment.png) | Shows the local application and database placement. |
| Database/EER | [Image](../../assets/images/architecture/SAD-DatabaseModel.png), [migration](../../../backend/src/main/resources/db/migration/V1__initial_schema.sql) | Generated from the applied schema migration. |
| CRUD implementation | [Controller](../../../backend/src/main/java/com/eatinghalal/web/ShoppingListController.java), [service](../../../backend/src/main/java/com/eatinghalal/service/ShoppingListService.java), React UI | Create, Read, quantity Update, Delete and Clear, with owner checks. |
| Local demo | [Setup](../../setup/local-demo.md), [recording](evidence/local-demo.webm), [desktop](evidence/images/local-crud-demo.png), [mobile](evidence/images/local-mobile-demo.png) | Implemented and recorded. |
| Cucumber execution | [Recording](evidence/cucumber-running.webm), [verification record](evidence/verification.md) | Twenty implemented scenarios passed; six remain pending. |
| Data responsibilities / CRC | [Data stewardship](../../architecture/DataStewardship.md) | Included as supplemental evidence. |
| Human contribution | [Record](human-contribution.md) | AI assistance disclosed; team review pending. |

## Verification snapshot

[Verification](evidence/verification.md) records the API/browser checks, full restart test, live product-source check and reproduction commands. Generated Maven/Playwright reports remain build outputs, rather than tracked submission files.

## Remaining work

- OCR and canonical product extraction are not implemented; four scenarios remain pending.
- Automatic new halal assessment generation is not implemented; one scenario remains pending. Demo results are explicitly synthetic.
- One photo-navigation Cucumber scenario remains pending; React navigation is covered by the browser test.
- Public hosting is outstanding. Local setup was selected, so the public-server requirement needs instructor clarification.
- The framework blog is a draft. Team review and WordPress publication are outstanding.
- The full AI A/B/C comparison has not been run. Its applicability to this hand-in needs confirmation.
- CI/CD is not implemented. Automated deployment is documented as unavailable at this stage.

## Before hand-in

- [ ] Review code, diagrams, recordings and blog claims as a team.
- [ ] Confirm the deadline/submission destination and acceptance of local deployment.
- [ ] Publish the reviewed blog and link the demo/test recordings.
- [ ] Confirm whether the full AI comparison is required.
- [ ] Verify that all submitted links are accessible to the instructor.
- [ ] State the unimplemented OCR/new-assessment flows explicitly.
