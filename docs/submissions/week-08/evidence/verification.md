# Week 08 Verification

This records the implementation checks accompanying the [Week 08 package](../README.md). It is a snapshot of the recorded run, not a claim that all planned functionality is complete.

| Check | Result |
| --- | --- |
| Cucumber / HTTP API / real PostgreSQL | 26 scenarios discovered: 20 passed, zero failures/errors, six explicitly skipped as pending. |
| Browser checks | Two passed: desktop search/assessment/CRUD/user separation/admin review, and mobile layout. |
| Full application/database restart | The same shopping-list entry and quantity 7 survived a restart using the same PostgreSQL data directory. |
| Live Open Food Facts adapter | Ten products returned and persisted with provenance. No halal verdict was generated. |

The database is real PostgreSQL, not a mock. Provider adapter tests use a controlled HTTP fixture, with the live-source check reported separately. Native verification used PostgreSQL 14.15; the PostgreSQL 16 Docker Compose path was not exercised.

## Recordings

- [Cucumber running](cucumber-running.webm)
- [Local demo walkthrough](local-demo.webm)

## Reproduce the checks

Follow the [local setup guide](../../../setup/local-demo.md). Run the Maven suite from backend, and the browser suite from frontend with the local app running. Maven generates its full HTML/JSON/Surefire reports under backend/target; Playwright generates reports under frontend/playwright-report and frontend/test-results.

The restart procedure is implemented by [check-restart-persistence.mjs](../../../../scripts/check-restart-persistence.mjs): run create, restart the app/database with the same data directory, then run check.

## Explicit exclusions

The six pending scenarios cover four OCR flows, new verdict generation and one photo-navigation Cucumber scenario. The frontend navigation is separately browser-tested. Skipped scenarios are not counted as implemented functionality.
