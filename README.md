# EatingHalal

A web application that helps users determine whether packaged food products comply with Islamic dietary guidelines.

## Repository Map

| Path | Contents |
|---|---|
| [`docs/SoftwareRequirementsSpecification.md`](docs/SoftwareRequirementsSpecification.md) | Full software requirements specification |
| [`docs/use-cases/`](docs/use-cases/) | Use-case specifications (UC01–UC05 documented) |
| [`docs/assets/images/`](docs/assets/images/) | Rendered diagram images (PNG) |
| [`docs/assets/plantuml-code/`](docs/assets/plantuml-code/) | PlantUML diagram sources |
| [`backend/`](backend/) | Java Spring Boot backend |
| [`backend/src/test/resources/features/`](backend/src/test/resources/features/) | Cucumber feature files |

## This Week's Submission

- [Submission status and remaining gaps](docs/SubmissionStatus.md)
- [Software Architecture Document](docs/SoftwareArchitectureDocument.md)
- [Local setup and demo walkthrough](docs/LocalDemoGuide.md)
- [Framework blog draft](docs/blog/08-software-architecture.md)
- [Data stewardship / CRC evidence](docs/DataStewardship.md)
- [Human contribution](docs/HumanContribution.md)

The local application implements stored product/assessment viewing, personal CRUD and admin review. OCR and new verdict generation remain unimplemented; public hosting and blog publication are outstanding. Synthetic demo verdicts are labelled and are not real dietary assessments.

## Technology Stack

- **Backend:** Java 17, Spring Boot, PostgreSQL
- **Frontend:** ReactJS
- **Testing:** Cucumber, JUnit, RestAssured
- **Local database setup:** Docker Compose or native PostgreSQL helper
- **CI/CD:** Travis CI planned; no pipeline implemented
- **Project management:** YouTrack

## Run Locally

~~~sh
sh scripts/run-local-demo.sh
~~~

Open http://127.0.0.1:8080. See the [guide](docs/LocalDemoGuide.md) for prerequisites, isolated tests, PostgreSQL persistence, local accounts and provider settings.

## Links

- [EatingHalal Blog](https://eatinghalal2.wordpress.com)
- [GitHub Repository](https://github.com/tameemraihan/EatingHalal)
