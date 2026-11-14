# CLAUDE.md — EatingHalal Project Instructions

This file tells Claude how to work in this repository. When the user names a task, apply only the rules for that task scope in addition to the project-wide context below.

---

## Project Overview

**EatingHalal** is a web application that helps users determine whether packaged food products comply with Islamic dietary laws. It is a university project for Software Engineering at DHBW Mosbach (CS TINFO25).

**Repository structure:**
- `README.md` — front door: project description, repo map, tech stack, links
- `docs/SoftwareRequirementsSpecification.md` — full SRS
- `docs/use-cases/` — use case specifications (UC01–UC04 documented, UC05–UC08 pending)
- `docs/assets/images/` — rendered PNG diagrams
- `docs/assets/plantuml-code/` — PlantUML diagram sources
- `backend/` — Java Spring Boot backend
- `backend/src/test/resources/features/` — Cucumber feature files

**Technology stack:**
- Backend: Java 17, Spring Boot, MariaDB
- Frontend: ReactJS
- Testing: Cucumber, JUnit, RestAssured
- CI/CD: Travis CI, Docker
- Project management: YouTrack

**Version control:** Git is the sole version tracker. No manual version numbers, dates, or revision history tables belong in any document.

---

## Task: Write Use Case Documentation

Triggered by: "execute task: Write Use Case Documentation"

### File location and naming

- Place the file at `docs/use-cases/UC<NN>-<kebab-case-name>.md`
- PlantUML source at `docs/assets/plantuml-code/UC<NN>-<kebab-case-name>.puml`
- Rendered PNG at `docs/assets/images/UC<NN>-<kebab-case-name>.png`
- Cucumber feature file stays at `backend/src/test/resources/features/uc<nn>_<snake_case_name>.feature`

### Document structure

Use exactly this section order:

```
# Use-Case Specification: <Use Case Name>

## Table of Contents
...

## 1. Brief Description
<brief description>

**Primary actor:** ...<br>
**Supporting actors:** ...<br>
**Trigger:** ...

## 2. Flow of Events

### 2.1 Basic Flow
...

### 2.2 Alternative Flows

#### 2.2.1 ...
...

### Activity Diagram

![Activity Diagram](../assets/images/UC<NN>-<name>.png)

[PlantUML source](../assets/plantuml-code/UC<NN>-<name>.puml) | [Cucumber feature](../../backend/src/test/resources/features/uc<nn>_<name>.feature)

## 3. Special Requirements
...

## 4. Preconditions
...

## 5. Postconditions
...

## 6. Extension Points
...
```

### What to never include

- No `# EatingHalal` top heading
- No `**Document identifier:**`, `**Version:**`, `**Date:**` header block
- No `## Revision History` section or table
- No `## 1. Use-Case Name` wrapper section — the name is the `# h1` title

### Hyperlinking rules — no exceptions

Every reference to any document, section, step, flow, or abbreviation that has a target must be a hyperlink. This means:

- Every mention of another UC (e.g. UC03) links to its `.md` file — every single occurrence, not just the first
- Every "step N of the basic flow" links to `#21-basic-flow`
- Every "alternative flow 2.2.X" links to its heading anchor
- Every "step N of alternative flow 2.2.X" links to that flow's heading anchor
- The Cucumber feature file is linked in the Activity Diagram block
- The PlantUML source is linked in the Activity Diagram block
- Extension points that name other UCs link to those UC files

### Activity diagram

- The diagram block sits after section 2.2 (end of all alternative flows), before section 3
- The heading is `### Activity Diagram` (a subsection of section 2)
- It is listed in the Table of Contents under section 2

### Cross-referencing the SRS

- The SRS `docs/SoftwareRequirementsSpecification.md` links to each UC specification from sections 3.1.1–3.1.N
- After creating a new UC doc, update the SRS section for that UC to point to the new file
- Add the UC to the use case table in SRS section 2.2 with a hyperlink

---

## Task: Documentation Quality Check

Triggered by: "execute task: Documentation Quality Check"

Run through every item below. Report each as pass or fail with the exact file and line where a failure occurs.

### Structure checks

- [ ] Every UC file follows the section order: Brief Description → Flow of Events (Basic → Alternative → Activity Diagram) → Special Requirements → Preconditions → Postconditions → Extension Points
- [ ] No UC file contains a `## Revision History` section, version header, or date header
- [ ] No UC file contains a `## 1. Use-Case Name` wrapper heading
- [ ] The `### Activity Diagram` subsection exists in every UC file and is listed in its Table of Contents
- [ ] The UC filename matches the pattern `UC<NN>-<kebab-case-name>.md`

### Asset checks

- [ ] Every UC has a corresponding `.puml` in `docs/assets/plantuml-code/` with the matching base name
- [ ] Every UC has a corresponding `.png` in `docs/assets/images/` with the matching base name
- [ ] The SRS use case diagram assets are named `SoftwareRequirementsSpecification-UseCases.puml` and `SoftwareRequirementsSpecification-UseCases.png`
- [ ] No asset files remain in old locations (`docs/EatingHalal_UseCases.puml`, `docs/use-cases/activity-diagrams/`, `docs/use-cases/rup/`)

### Hyperlink checks — zero tolerance

- [ ] Every mention of a UC identifier (e.g. UC01, UC03) in running text is a hyperlink — every occurrence, not just the first
- [ ] Every "step N of the basic flow" reference is a hyperlink to `#21-basic-flow`
- [ ] Every "alternative flow 2.2.X" reference is a hyperlink to that flow's anchor
- [ ] Every UC file's Activity Diagram block links to both the `.puml` source and the Cucumber feature file
- [ ] The SRS image reference for the use case diagram points to `./assets/images/SoftwareRequirementsSpecification-UseCases.png`
- [ ] The SRS PlantUML reference points to `./assets/plantuml-code/SoftwareRequirementsSpecification-UseCases.puml`
- [ ] Every UC mentioned in the SRS use case table (section 2.2) that has a spec file is hyperlinked
- [ ] Every UC mentioned in the SRS functionality section (3.1.x) links to its spec file
- [ ] The SRS section 1.5 references to "Use Case Diagram", "third chapter", and "final chapter" are hyperlinks
- [ ] The `docs/use-cases/README.md` UC table links to every documented UC spec file

### Consistency checks

- [ ] UC identifiers and names in the SRS table match the actual filenames and `# h1` titles of the UC files
- [ ] The Cucumber feature file path linked in each UC doc actually exists in the repository
- [ ] The PlantUML source path linked in each UC doc actually exists in the repository
- [ ] The PNG path linked in each UC doc actually exists in the repository

---

## Task: Backend Development

Triggered by: "execute task: Backend Development"

### Stack and conventions

- Java 17, Spring Boot 3.x, Maven
- RESTful API, JSON responses
- MariaDB for persistence
- Follow clean code standards: clear naming by function, no abbreviations in identifiers

### Testing conventions

- BDD tests use Cucumber 7.x with Gherkin feature files in `backend/src/test/resources/features/`
- Feature files are named `uc<nn>_<snake_case_name>.feature` and tagged `@uc<nn>`
- Step definitions live in `backend/src/test/java/com/eatinghalal/steps/`
- Unit and integration tests use JUnit 5
- Use RestAssured for API-level integration tests
- Do not mock the database in integration tests

### Feature file naming

Feature files must correspond to a documented use case. The feature file name must match the UC doc base name in snake_case: e.g. `UC01-search-product.md` → `uc01_search_product.feature`.

### Before marking backend work done

- All Cucumber scenarios for the affected UC must pass
- No compilation errors or test failures introduced in unrelated UCs
