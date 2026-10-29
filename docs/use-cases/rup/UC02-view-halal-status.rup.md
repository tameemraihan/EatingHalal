# EatingHalal

# Use-Case Specification: View Halal Status of a Product

**Document identifier:** Use Case 02<br>
**Version:** 1.0<br>
**Date:** 29 October 2026

## Revision History

| Date | Version | Description | Author |
|---|---:|---|---|
| 29 October 2026 | 1.0 | Initial use-case specification | EatingHalal Team |

## Table of Contents

1. [Use-Case Name](#1-use-case-name)
   - [Brief Description](#11-brief-description)
2. [Flow of Events](#2-flow-of-events)
   - [Basic Flow](#21-basic-flow)
   - [Alternative Flows](#22-alternative-flows)
     - [No Stored Assessment Exists](#221-no-stored-assessment-exists)
     - [Assessment Cannot Be Completed](#222-assessment-cannot-be-completed)
3. [Special Requirements](#3-special-requirements)
   - [Assessment Explanation](#31-assessment-explanation)
   - [Product Information](#32-product-information)
   - [Response Time](#33-response-time)
   - [Usability](#34-usability)
   - [Interfaces and Availability](#35-interfaces-and-availability)
4. [Preconditions](#4-preconditions)
5. [Postconditions](#5-postconditions)
6. [Extension Points](#6-extension-points)
   - [Assessment Result Displayed](#61-assessment-result-displayed)

## 1. Use-Case Name

**View Halal Status of a Product**

### 1.1 Brief Description

A **User** views the halal status of a selected packaged food product. The system first checks whether a stored assessment is available. When no stored assessment exists, the system obtains the required product data, normalises it into the canonical format required by the halal assessment pipeline, runs the assessment, and stores the result. The system then displays the product details, halal status, and a justification.

Halal means permissible, lawful, and clean under Islamic dietary guidelines. The assessment is based on available product information such as ingredients, certifications, manufacturer, and country of origin. This use case is limited to packaged food products; restaurants and freshly prepared food are outside its scope.

**Primary actor:** User<br>
**Supporting actors:** External product data sources; Halal assessment pipeline<br>
**Trigger:** The user selects a product and wants to know its halal status.

## 2. Flow of Events

### 2.1 Basic Flow

1. The user selects a product.
2. The system checks the assessment-results database for a stored halal assessment for the selected product.
3. The system retrieves the stored assessment.
4. The system prepares the product details, halal status, and the justification for that status.
5. The system displays the product details and halal assessment to the user.
6. The use case ends.

### 2.2 Alternative Flows

#### 2.2.1 No Stored Assessment Exists

At step 2 of the basic flow, no stored assessment exists for the selected product.

1. The system fetches the required product data from the available data sources when needed.
2. The system normalises the selected product data into the canonical format used by the halal assessment pipeline. This format is also used for data extracted from an uploaded product photo.
3. The system runs the halal assessment pipeline using the normalised product information.
4. The system stores the resulting assessment in the assessment-results database.
5. The flow resumes at step 4 of the basic flow.

#### 2.2.2 Assessment Cannot Be Completed

At step 1 of alternative flow 2.2.1, the system cannot retrieve enough reliable product information, or a required assessment service is unavailable.

1. The system informs the user that a confirmed halal assessment cannot currently be produced.
2. The system does not display an unsupported assessment as a confirmed result.
3. The use case ends.

## 3. Special Requirements

### 3.1 Assessment Explanation

The system shall display a halal status with a plain-language justification. The status shall be **Halal**, **Haram**, or **Mashbooh** where the available information supports that classification.

### 3.2 Product Information

The assessment pipeline shall receive the same canonical product-data structure regardless of whether the selected product originated from an external product API, the hosted database, or uploaded packaging-photo processing. When information is available, this structure shall include certifications, ingredients, manufacturer, and country of origin.

### 3.3 Response Time

When a stored assessment exists, the system should display it within 3 seconds under normal load. Creating a new assessment may take longer.

### 3.4 Usability

The assessment-result screen shall be understandable without prior training and shall clearly distinguish the halal status from its supporting justification.

### 3.5 Interfaces and Availability

The web application communicates with its backend and external data sources using HTTP and HTTPS. If an external data source or the assessment pipeline is unavailable, the system shall not present an unsupported assessment as a confirmed result.

## 4. Preconditions

1. The EatingHalal web application is available to the user.
2. The user has selected a product from a product search or product-photo upload.
3. To create a new assessment, the required product data sources and assessment pipeline are available.

## 5. Postconditions

1. The user has been shown the selected product’s details, halal status, and supporting justification, or has been informed that a confirmed assessment cannot be produced.
2. If no stored assessment was available and a new assessment was completed, the resulting assessment is stored in the assessment-results database for later retrieval.

## 6. Extension Points

### 6.1 Assessment Result Displayed

After the system displays the halal assessment, the user may continue to related use cases:

- View ingredient analysis.
- View halal certification details.
- View alternative halal products, particularly when the status is Haram or Mashbooh.
