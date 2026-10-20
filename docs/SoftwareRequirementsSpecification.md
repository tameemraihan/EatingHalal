# EatingHalal- Software Requirements Specification 

## Table of contents
- [Table of contents](#table-of-contents)
- [Introduction](#1-introduction)
    - [Purpose](#11-purpose)
    - [Scope](#12-scope)
    - [Definitions, Acronyms and Abbreviations](#13-definitions-acronyms-and-abbreviations)
    - [References](#14-references)
    - [Overview](#15-overview)
- [Overall Description](#2-overall-description)
    - [Vision](#21-vision)
    - [Use Case Diagram](#22-use-case-diagram)
	- [Technology Stack](#23-technology-stack)
- [Specific Requirements](#3-specific-requirements)
    - [Functionality](#31-functionality)
    - [Usability](#32-usability)
    - [Reliability](#33-reliability)
    - [Performance](#34-performance)
    - [Supportability](#35-supportability)
    - [Design Constraints](#36-design-constraints)
    - [Online User Documentation and Help System Requirements](#37-on-line-user-documentation-and-help-system-requirements)
    - [Purchased Components](#purchased-components)
    - [Interfaces](#39-interfaces)
    - [Licensing Requirements](#310-licensing-requirements)
    - [Legal, Copyright And Other Notices](#311-legal-copyright-and-other-notices)
    - [Applicable Standards](#312-applicable-standards)
- [Supporting Information](#4-supporting-information)

## 1. Introduction

### 1.1 Purpose
This Software Requirements Specification (SRS) describes all specifications for the application "EatingHalal". It includes an overview of this project and its vision, detailed information about the planned features, and the boundary conditions of the development process.

### 1.2 Scope
EatingHalal is a web application that helps users determine whether packaged food products comply with Islamic dietary laws. Halal is defined as food that is permissible, lawful, and clean according to Islamic dietary guidelines.

The application is scoped exclusively to **packaged food products** — restaurants and freshly prepared foods are out of scope.

Actors of this application are:
- **Users** — individuals seeking halal product information
- **External APIs** — third-party data sources used for certification, ingredient, and product information

Planned subsystems are:
* **Product Lookup:**
  Users can search for a product or upload a photo of its packaging. The system queries multiple external APIs to analyze certifications, ingredient lists, manufacturer, and country of origin, then returns an instant halal/non-halal verdict with supporting rationale.
* **Ingredient Analysis:**
  For each product, the system breaks down individual ingredients to identify any non-halal components and explains the reasoning behind the determination.
* **Alternative Products:**
  When a product is determined to be non-halal, the system suggests alternative halal-certified products within the same category.
* **Shopping List:**
  Users can maintain a personal saved list of halal-approved products for reference during shopping.
* **Reporting:**
  Users can flag and report products they believe have incorrect halal status information.

### 1.3 Definitions, Acronyms and Abbreviations
| Abbreviation | Explanation                         |
| ------------ | ----------------------------------- |
| SRS          | Software Requirements Specification |
| UC           | Use Case                            |
| UCD          | Overall Use Case Diagram            |
| n/a          | not applicable                      |
| tbd          | to be determined                    |
| API          | Application Programming Interface   |
| FAQ          | Frequently Asked Questions          |

### 1.4 References

| Title                                                                                          | Date       | Publishing organization |
| -----------------------------------------------------------------------------------------------|:----------:| ----------------------- |
| [EatingHalal Blog](https://eatinghalal2.wordpress.com)                                         | 01.10.2026 | EatingHalal Team        |
| [GitHub](https://github.com/tameemraihan/EatingHalal)                                          | 01.10.2026 | EatingHalal Team        |

### 1.5 Overview
The following chapter provides an overall description of this project including its vision and Use Case Diagram. The third chapter delivers specific requirements covering functionality, usability, reliability, performance, and design constraints. The final chapter contains supporting information.
    
## 2. Overall Description

### 2.1 Vision
A world where no one has to wonder if their food is halal.

### 2.2 Use Case Diagram

The use case diagram was created using PlantUML (diagram-as-text) and rendered via Draw.io.

![Use Case Diagram](./UseCaseDiagram.png)

#### Actors
- **User** (Application User)
- **External APIs**

#### Use Cases

| # | Use Case |
|---|----------|
| UC1 | Search for a product |
| UC2 | Upload product packaging photo |
| UC3 | View Halal status of a product |
| UC4 | View Halal certification details |
| UC5 | View ingredient analysis |
| UC6 | View alternative Halal products |
| UC7 | View recently searched products |
| UC8 | Add product to shopping list |
| UC9 | Remove product from shopping list |
| UC10 | View shopping list |
| UC11 | Share a product halal status |
| UC12 | Report incorrect product information |

### 2.3 Technology Stack
The technology we use is:

Backend:
-Gradle and Springboot
-H2 Database

Frontend:
-Android with Java and XML

IDE:
-IntelliJ and Android Studio

Project Management:
-YouTrack
-GitHub
-Microsoft Teams

Deployment:
-Travis CI
-Docker and Heroku

Testing:
-Cucumber
-Espresso
-JUnit
-Codacy
-CodeMR
-RestAssured

## 3. Specific Requirements

### 3.1 Functionality

This section will explain the different use cases, you could see in the Use Case Diagram, and their functionality.

Until December we plan to implement:
- 3.1.1 Searching for a product
- 3.1.2 Viewing halal status of a product
- 3.1.3 Uploading a product packaging photo
- 3.1.4 Viewing ingredient analysis
- 3.1.5 Viewing alternative halal products

Until June, we want to implement:
- 3.1.6 Filtering search results by dietary preference
- 3.1.7 Adding a product to the shopping list
- 3.1.8 Viewing the shopping list
- 3.1.9 Removing a product from the shopping list
- 3.1.10 Sharing a product halal status
- 3.1.11 Viewing recently searched products
- 3.1.12 Reporting incorrect product information
- 3.1.13 Viewing halal certification details

#### 3.1.1 Searching for a product

This feature is the essential one of our project. The user gets the possibility to search for a packaged food product by entering its name or scanning its barcode. The system then queries multiple data sources and returns a list of matching products for the user to select from.

[Search for a product](./use_cases/UC01_Search_Product.md)

#### 3.1.2 Viewing halal status of a product

This feature provides the core value of the application. Once a product is selected, the system evaluates its ingredients, certifications, manufacturing details, and country of origin to deliver a halal verdict — Halal, Haram, or Mashbooh — along with a plain-language explanation.

[View halal status](./use_cases/UC02_View_Halal_Status.md)

#### 3.1.3 Uploading a product packaging photo

When a product cannot be found in the database, the user gets the possibility to upload a photo of the product packaging. The system then performs OCR and image analysis to extract the product name, barcode, ingredients, and certification marks, and proceeds with the halal assessment.

[Upload product packaging photo](./use_cases/UC03_Upload_Photo.md)

#### 3.1.4 Viewing ingredient analysis

This feature gives the user a detailed, ingredient-by-ingredient breakdown of a product. Each ingredient is individually classified as Halal, Haram, or Mashbooh with color-coded indicators, allowing the user to understand exactly which components influence the overall verdict.

[View ingredient analysis](./use_cases/UC04_Ingredient_Analysis.md)

#### 3.1.5 Viewing alternative halal products

When a product is found to be Haram or Mashbooh, the app provides the user with a list of certified halal alternatives in the same product category. This helps users quickly find a suitable replacement without having to start a new search.

[View alternative halal products](./use_cases/UC05_View_Alternatives.md)

#### 3.1.6 Filtering search results by dietary preference

Based on the product search results, this feature enables the user to narrow down results by additional dietary requirements such as vegan, gluten-free, or nut-free, alongside halal compliance. This way, users with multiple dietary needs can find suitable products more efficiently.

[Filter search results](./use_cases/UC09_Filter_Results.md)

#### 3.1.7 Adding a product to the shopping list

The user gets the possibility to save any product to a personal shopping list for future reference. This is useful when planning purchases in advance or when the user wants to keep a record of approved halal products they intend to buy regularly.

[Add product to shopping list](./use_cases/UC06_Add_To_List.md)

#### 3.1.8 Viewing the shopping list

The app provides the user with a separate page view where they get an overview of all products they have saved to their shopping list, including the name, brand, and halal verdict of each item.

[View shopping list](./use_cases/UC07_View_List.md)

#### 3.1.9 Removing a product from the shopping list

The user also gets the possibility to remove a product they no longer need from their shopping list, keeping it relevant and up to date.

[Remove product from shopping list](./use_cases/UC08_Remove_From_List.md)

#### 3.1.10 Sharing a product halal status

There must be the possibility for users to share their findings with family or friends. The user who checks a product gets the possibility to share its halal status via a link through any messaging app, email, or clipboard. This is particularly useful in group shopping scenarios.

[Share a product halal status](./use_cases/UC10_Share_Status.md)

#### 3.1.11 Viewing recently searched products

The app provides the user with quick access to products they have previously looked up, without having to re-enter a search query. Recent searches are shown automatically when the user opens the search field or navigates to the history section.

[View recently searched products](./use_cases/UC11_Recent_Searches.md)

#### 3.1.12 Reporting incorrect product information

After viewing a product, the app provides the user with the possibility to flag inaccurate verdict or ingredient data by submitting a correction report. This is helpful because we want a platform with reliable and community-validated information.

[Report incorrect product information](./use_cases/UC12_Report_Information.md)

#### 3.1.13 Viewing halal certification details

When a product carries a halal certification, the user can access the full details of the certifying organization, including its name, country, accreditation status, certificate number, and validity dates. This allows users to independently verify the legitimacy of a certification before trusting a verdict.

### 3.2 Usability
We plan on designing the user interface as intuitive and self-explanatory as possible so that any user can determine the halal status of a product without prior training or technical knowledge.

#### 3.2.1 No training time needed
Our goal is that a user opens the web application and is able to use all features without any explanation or help. Search, photo upload, and shopping list management should all be immediately understandable.

#### 3.2.2 Familiar Feeling
We want to implement a web app with familiar designs and interactions. Users should be able to navigate the application using patterns they already know from other product lookup or food apps.

### 3.3 Reliability

#### 3.3.1 Availability
The server shall be available 95% of the time. Any planned downtime should be scheduled during off-peak hours to minimize disruption to users.

#### 3.3.2 Defect Rate
Our goal is to have no loss of user data. Shopping lists and search history must be preserved across sessions, even after a server restart or downtime.

### 3.4 Performance

#### 3.4.1 Capacity
The system should be able to handle thousands of concurrent requests. It should also be able to support an unrestricted number of users simultaneously searching for products.

#### 3.4.2 Response Time
Product lookup results should be returned within 3 seconds under normal load. Photo upload and OCR analysis may take longer but should complete within 10 seconds.

### 3.5 Supportability

#### 3.5.1 Coding Standards
We will write code following common clean code standards. Variables, methods, and components will be named clearly by their function. This will keep the codebase readable and make further development easier.

#### 3.5.2 Testing Strategy
The application will have high test coverage. All critical functionality — including halal verdict logic, API integrations, and shopping list operations — must be covered by unit and integration tests. Bugs should be discoverable and locatable quickly through automated testing.

### 3.6 Design Constraints
We are aiming for a modern, clean, and responsive web design. Functionality will be kept modular to allow independent development of frontend and backend.

The technology stack is:
- **Backend:** Java Spring Boot with a RESTful API serving data in JSON format
- **Frontend:** ReactJS
- **Communication:** HTTP/HTTPS protocol between frontend and backend

Supported platforms:
- Any modern web browser (Chrome, Firefox, Safari, Edge)
- Desktop and mobile browsers

### 3.7 Online User Documentation and Help System Requirements
The usage of the application should be as intuitive as possible so it will not need extensive documentation. If the user needs help, a FAQ section will be available within the application covering common questions about halal status verdicts, how to report incorrect information, and how to use the shopping list.

### 3.8 Purchased Components
We don't have any purchased components yet. External APIs used for product and ingredient data may require paid tiers as usage scales; these will be listed here when confirmed.

### 3.9 Interfaces

#### 3.9.1 User Interfaces
The user interfaces to be implemented are:
- **Home / Search Page** — allows users to search for a product by name or barcode
- **Product Result Page** — displays the halal verdict, certification details, and ingredient analysis for a selected product
- **Photo Upload Page** — allows users to upload a product packaging photo for OCR-based analysis
- **Alternatives Page** — shows halal-certified alternative products when a product is non-halal
- **Shopping List Page** — displays all products saved by the user with their halal verdicts
- **Recent Searches Page** — shows the user's previously searched products
- **Report Page** — allows users to submit a correction report for incorrect product information

#### 3.9.2 Hardware Interfaces
(n/a)

#### 3.9.3 Software Interfaces
The application will be accessible via any modern web browser. The backend exposes a RESTful API consumed by the ReactJS frontend. External APIs are integrated server-side for product data, ingredient analysis, and halal certification lookups.

#### 3.9.4 Communication Interfaces
The frontend and backend communicate over HTTPS. External API calls are made server-side over HTTPS.

### 3.10 Licensing Requirements
(to be determined)

### 3.11 Legal, Copyright, and Other Notices
We do not take responsibility for any incorrect halal verdicts resulting from inaccurate or outdated data provided by external APIs. Users are encouraged to report any incorrect information using the in-app reporting feature.

### 3.12 Applicable Standards
The development will follow common clean code standards and naming conventions. A definition of done will be established and added here once finalized.

## 4. Supporting Information
For any further information you can contact the EatingHalal team or check our [EatingHalal Blog](https://eatinghalal2.wordpress.com).

The team members are:
- Tamim
- Taha
- Benjamin
