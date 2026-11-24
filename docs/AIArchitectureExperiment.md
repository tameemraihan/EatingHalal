# Architecture-Aware AI Experiment — Prepared, Not Executed

The AI mini-experiment on the supplied architecture slides compares three contexts. The materials do not clearly establish whether the full experiment belongs to this week's hand-in. A human-contribution record is supplied separately.

## Common task

Implement a shopping-list quantity update for an authenticated user. The acceptance criteria are independent: quantities 1–999 persist; invalid quantities leave the original value; another user cannot modify the entry; CSRF is required; shared products/assessments remain unchanged.

## Three input conditions

- **A — requirements only:** supply the common task and acceptance criteria.
- **B — requirements plus framework:** add Java 17, Spring Boot, Spring MVC, React, PostgreSQL and Spring Data JPA.
- **C — architectural contract:** add the [SAD](SoftwareArchitectureDocument.md), actual directory responsibilities, controller/service/repository boundaries, authenticated owner scope, migration constraints and existing API response shapes.

## Evaluation

Use isolated copies of the same starting code, the same model/settings, equivalent time budgets and independent acceptance checks. Record passing tests, dependency violations, unauthorized access, future-change effort and review time. Keep prompts, generated patches, test output, corrections and unresolved risks. Do not give the hidden evaluation answers to the model.

## Status

No three independent model runs were performed. No productivity/security comparison or winning condition is claimed. This file is a reproducible experiment brief, not completed empirical evidence. Team/instructor confirmation and actual runs remain outstanding.
