# Data Stewardship and CRC Evidence

These artifacts supplement the [architecture document](SoftwareArchitectureDocument.md). The lecture chapter proposes them as learning evidence; official assessment scope still needs confirmation.

## CRC card

| Class | Responsibilities | Collaborators |
| --- | --- | --- |
| HalalAssessment | Represent a stored assessment version; retain verdict, rationale, input snapshot, evaluator identifier and creation time. | Product, AssessmentService, AssessmentRepository |

The entity represents the result; services coordinate application work and provider access. It does not make HTTP calls or infer permission from a database relationship.

## Data inventory

| Data | Meaning / purpose | Access / lifecycle |
| --- | --- | --- |
| Product name, brand, packaging, ingredients and labels | Imported or synthetic packaged-product evidence. | Public selected fields. Provider statements remain attributable; no authoritative verdict is inferred merely from a label. |
| Barcode, source URL and retrieval time | Identity, provenance and freshness context. | Stored with imported records. Refresh/freshness policy is still unresolved. |
| Assessment verdict, rationale, snapshot, evaluator and version | Derived interpretation of a particular input. | Public selected stored result; administrative collection restricted to admins. Exact retention/correction policy remains unresolved. |
| Account ID, username, password hash and role | Identity and authorization. | Credentials are not API responses; only seeded local accounts are implemented. Production provisioning/account lifecycle is outstanding. |
| List entry, product, quantity and added time | Personal shopping intentions. | Server-scoped owner access. Deleting an entry does not delete shared evidence. |
| Uploaded photo | Proposed OCR input. | OCR unavailable; uploaded data is not persisted or forwarded. Real OCR retention/provider rules remain unresolved. |

## Governed derived product

HalalAssessment is the derived product. It references one Product, records a version and retains input snapshot, evaluator identifier, verdict, rationale and time. The current demo fixtures use synthetic evidence and are visibly identified. Automatic generation is not implemented: real criteria, trusted evidence, ambiguity handling, certification verification and accountable review are still required.

## Executable rules

The [admin feature](../backend/src/test/resources/features/uc04_manage_stored_halal_assessment_results.feature) checks that a non-admin receives 403 without assessment content. The [shopping-list feature](../backend/src/test/resources/features/uc05_manage_shopping_list.feature) checks owner isolation, invalid quantities, duplicates, eligibility and CSRF denial against actual HTTP endpoints and PostgreSQL records. A quantity update by another user receives 404 and leaves the original entry unchanged.

These are application authorization checks, not a claim of complete legal compliance or institutional policy approval. Deletion propagation, production backups, audit retention and external-provider data handling are not implemented.
