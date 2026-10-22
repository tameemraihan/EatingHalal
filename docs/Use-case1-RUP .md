# EatingHalal

# Use-Case Specification: Request Halal Assessment for a Product

**Document identifier:** Use Case 02<br>
**Version:** 1.0<br>
**Date:** 22 October 2026

## Revision History

| Date | Version | Description | Author |
|---|---:|---|---|
| 22 October 2026 | 1.0 | Initial use-case specification | EatingHalal Team |

## Table of Contents

1. [Use-Case Name](#1-use-case-name)
   - [Brief Description](#11-brief-description)
2. [Flow of Events](#2-flow-of-events)
   - [Basic Flow](#21-basic-flow)
   - [Alternative Flows](#22-alternative-flows)
     - [No Matching Product Found](#221-no-matching-product-found)
     - [Invalid Image File](#2211-invalid-image-file)
     - [Unreadable Photo](#2212-unreadable-photo)
     - [No Stored Assessment Exists](#222-no-stored-assessment-exists)
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

**Request Halal Assessment for a Product**

### 1.1 Brief Description

A **User** requests a halal assessment for a packaged food product. The user searches by product name and, when the product is not available, can provide a photo of its packaging. The system obtains and structures product information, including certifications, ingredients, manufacturer, and country of origin; then it returns the product details, halal status, and a justification. A previously stored assessment is reused when available; otherwise, the system retrieves the required data, performs the assessment, and stores the result.

Halal means permissible, lawful, and clean under Islamic dietary guidelines. The assessment is based on product information such as ingredients and certification information. This use case is limited to packaged food products; restaurants and freshly prepared food are outside its scope.

**Primary actor:** User<br>
**Supporting actors:** External product data sources; Optical Character Recognition (OCR) Service; OCR/Large Language Model (LLM) Service<br>
**Trigger:** The user wants to know the halal status of a packaged product.

## 2. Flow of Events

### 2.1 Basic Flow

1. The user enters a product name.
<a id="basic-flow-step-2"></a>
2. The system searches its hosted product data and external data sources for products matching the entered name.
3. The system retrieves the matching product and presents it to the user.
4. The user selects the product.
<a id="basic-flow-step-5"></a>
5. The system checks the assessment-results database for a stored halal assessment for the selected product.
6. The system retrieves the stored assessment.
<a id="basic-flow-step-7"></a>
7. The system prepares the product details, halal status, and the justification for that status.
8. The system displays the product details and halal assessment to the user.
9. The use case ends.

### 2.2 Alternative Flows

#### 2.2.1 No Matching Product Found

At [step 2 of the basic flow](#basic-flow-step-2), no matching product is found.

1. The system asks the user to upload a photo of the product packaging.
2. The user uploads a product photo.
<a id="photo-flow-step-3"></a>
3. The system validates the image file.
4. The system sends the valid image to the OCR Service.
5. The OCR Service extracts recognised text and confidence scores from the image.
<a id="photo-flow-step-6"></a>
6. The system evaluates the extracted text and its confidence.
7. The OCR/LLM Service structures the product data, including certifications, ingredients, manufacturer, and country of origin.
8. The system creates a product record in the hosted database.
9. The system presents the created product to the user.
10. The user selects the product.
11. The flow resumes at [step 5 of the basic flow](#basic-flow-step-5).

#### 2.2.1.1 Invalid Image File

At [step 3 of alternative flow 2.2.1](#photo-flow-step-3), the image file is invalid.

1. The system returns an invalid-image error to the user.
2. The user uploads another product photo.
3. The flow resumes at [step 3 of alternative flow 2.2.1](#photo-flow-step-3).

#### 2.2.1.2 Unreadable Photo

At [step 6 of alternative flow 2.2.1](#photo-flow-step-6), the extracted text has insufficient confidence and the photo is unreadable.

1. The system asks the user to upload another product photo.
2. The user uploads a replacement photo.
3. The flow resumes at [step 3 of alternative flow 2.2.1](#photo-flow-step-3).

#### 2.2.2 No Stored Assessment Exists

At [step 5 of the basic flow](#basic-flow-step-5), no stored assessment exists for the selected product.

1. The system fetches the required product data from the available data sources.
2. The system runs the halal assessment pipeline using the product information.
3. The system stores the resulting assessment in the assessment-results database.
4. The flow resumes at [step 7 of the basic flow](#basic-flow-step-7).

## 3. Special Requirements

### 3.1 Assessment Explanation

The system shall display a halal status with a plain-language justification. The status shall be **Halal**, **Haram**, or **Mashbooh** where the available information supports that classification.

### 3.2 Product Information

When information is available, the product record used for the assessment shall include certifications, ingredients, manufacturer, and country of origin.

### 3.3 Response Time

Under normal load, a product lookup result should be returned within 3 seconds. Photo upload and OCR analysis may take longer, but should complete within 10 seconds.

### 3.4 Usability

The search, product selection, photo-upload, error, and result screens shall be understandable without prior training.

### 3.5 Interfaces and Availability

The web application communicates with its backend and external data sources using the Hypertext Transfer Protocol (HTTP) and Hypertext Transfer Protocol Secure (HTTPS). If an external data source is unavailable, the system shall not present an unsupported assessment as a confirmed result.

## 4. Preconditions

1. The EatingHalal web application is available to the user.
2. The user has a product name to search for, or can provide a photo of a packaged product when no matching product is found.
3. To create or refresh an assessment, the required product data sources and assessment pipeline are available.

## 5. Postconditions

1. The user has been shown the selected product’s details, halal status, and supporting justification.
2. If no stored assessment was available, the newly generated assessment is stored in the assessment-results database for later retrieval.
3. If a product was created from a packaging photo, its structured product data is stored in the hosted product database.

## 6. Extension Points

### 6.1 Assessment Result Displayed

After the system displays the halal assessment, the user may continue to related use cases:

- View ingredient analysis.
- View halal certification details.
- View alternative halal products, particularly when the status is Haram or Mashbooh.

