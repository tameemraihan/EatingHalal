# Use-Case Documentation

This directory groups the source documentation for each use case.

## Naming Convention

Each file begins with the zero-padded use-case identifier from the Software Requirements Specification (SRS):

```text
UC<NN>-<kebab-case-use-case-name>.<artifact>.<extension>
```

Examples:

```text
UC01-search-product.rup.md
UC01-search-product.activity.puml
```

| Directory | Contents |
|---|---|
| `rup/` | RUP use-case specifications in Markdown. |
| `activity-diagrams/PlantUML/` | PlantUML source files for activity diagrams. |
| `activity-diagrams/images/` | Rendered activity-diagram images. |

## Current Use Cases

| ID | Use Case |
|---|---|
| UC01 | Search for a Product |
| UC02 | View Halal Status of a Product |
| UC03 | Upload Product Packaging Photo |
| UC04 | Manage Stored Halal Assessment Results (Admin) |
| UC05 | Manage Shopping List |
| UC06 | Manage Uploaded Product Photos (Admin, later phase) |
| UC07 | Manage Community Features (later phase) |
| UC08 | Manage User Profile (later phase) |
