# Use-Case Documentation

This directory contains the use-case specifications for EatingHalal. Each file covers one use case: its description, flow of events, special requirements, preconditions, postconditions, and extension points. Activity diagrams and PlantUML sources are in [`../assets/`](../assets/).

## Use Cases

| ID | Use Case | Status |
|---|---|---|
| [UC01](UC01-search-product.md) | Search for a Product | This semester |
| [UC02](UC02-view-halal-status.md) | View Halal Status of a Product | This semester |
| [UC03](UC03-upload-product-photo.md) | Upload Product Packaging Photo | This semester |
| [UC04](UC04-manage-stored-halal-assessment-results.md) | Manage Stored Halal Assessment Results (Admin) | This semester |
| [UC05](UC05-manage-shopping-list.md) | Manage Shopping List | This semester |
| UC06 | Manage Uploaded Product Photos (Admin) | Later phase |
| UC07 | Manage Community Features | Later phase |
| UC08 | Manage User Profile | Later phase |

## Naming Convention

Files follow the pattern:

```
UC<NN>-<kebab-case-use-case-name>.md
```

Assets (PlantUML source and rendered PNG) follow the same base name and live in:

```
docs/assets/plantuml-code/UC<NN>-<kebab-case-use-case-name>.puml
docs/assets/images/UC<NN>-<kebab-case-use-case-name>.png
```
