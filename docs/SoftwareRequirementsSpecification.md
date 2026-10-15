# Software Requirements Specification (SRS)
## Project: EatingHalal
**Author:** Mohammad Taha Jahangiri Yaghooti ,James Benjamin Mubiru , Md Tamim Raihan
**Date:** October 15, 2026

### 1. Introduction
#### 1.1 Purpose
The purpose of this document is to outline the software requirements for the EatingHalal platform. This system aims to help users reliably find halal food options, integrating closely with the EatingHalal blog and community. 

#### 1.2 Scope
EatingHalal provides a centralized application where users can discover, verify, and read about halal eateries. It acts as an extension of the existing EatingHalal WordPress blog, offering technical features like search, filtering, and user contributions.

### 2. Overall Description
#### 2.1 User Characteristics
The primary users are individuals strictly adhering to a halal diet, tourists seeking reliable food options, and contributors to the EatingHalal community.

#### 2.2 Operating Environment
The platform will be a web-based application accessible via modern web browsers (Chrome, Firefox, Safari) and mobile-responsive.

### 3. System Features
#### 3.1 Directory Search and Filtering
* **Description:** Users can search for halal restaurants and filter by cuisine, location, and halal certification level.
* **Requirements:** The system must return search results in under 2 seconds and correctly filter data based on the backend database.

#### 3.2 Blog Integration
* **Description:** Seamlessly display recent posts from the EatingHalal WordPress site.
* **Requirements:** The system shall fetch data from the WordPress API and display it on the homepage.