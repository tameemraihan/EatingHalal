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
This Software Requirements Specification (SRS) describes all specifications for the application "Common Playground". It includes an overview about this project and its vision, detailed information about the planned features and boundary conditions of the development process.


### 1.2 Scope
The project is going to be realized as an Android App.  
  
Actors of this App can be users or moderators.  
  
Planned Subsystems are: 
* Notice Board:  
The notice board is the essential part of the user interface. Game sessions should be visualized as postings including relevant information about the session. Those should be partly standardized by a form with a free text option for specifics. The data must be stored accordingly.
* Account System:  
Users can create accounts so sessions can be connected to a person as well as to join requests. User data must be stored alongside the posting data.
* Friend List and User Rating:  
Once the account system is created there will be the option to mark users as favorites. Also users or game sessions should get a rating to counter abuse.
* Connecting People:  
The host of a game session has to be notified when someone wants to join their game. Both must then be able to get in touch to organize the details, so messages between the host and the guest have to be enabled. This could be done via automated emails or a custom in-app system. For this an account system is needed.
* Storing Data:  
User data for accounts and possibly profiles has to be stored. Also the game sessions have to be stored as datasets containing the form contents and possibly contact data. The data storage will form the foundation for the visualization, account system and the search feature.
* Finding your Game:  
We need a tag system so everyone looking to join a game can search for the kind of games they are interested in. Possibly other aspects can be searchable, such as place or date. Tags must be stored and a search function developed. 

### 1.3 Definitions, Acronyms and Abbreviations
| Abbrevation | Explanation                            |
| ----------- | -------------------------------------- |
| SRS         | Software Requirements Specification    |
| UC          | Use Case                               |
| n/a         | not applicable                         |
| tbd         | to be determined                       |
| UCD         | overall Use Case Diagram               |
| FAQ         | Frequently asked Questions             |

### 1.4 References

| Title                                                              | Date       | Publishing organization   |
| -------------------------------------------------------------------|:----------:| ------------------------- |
| [Common Playground Blog](http://commonplayground.wordpress.com)    | 18.10.2018 | Common Playground Team    |
| [GitHub](https://github.com/nilskre/CommonPlayground)              | 18.10.2018 | Common Playground Team    |


### 1.5 Overview
The following chapter provides an overview of this project with vision and Overall Use Case Diagram. The third chapter (Requirements Specification) delivers more details about the specific requirements in terms of functionality, usability and design parameters. Finally there is a chapter with supporting information. 
    
## 2. Overall Description

### 2.1 Vision
Inspired by carpool coordination services like ‘BlaBlaCar’ or ‘Mitfahrzentrale’ we want to build an application to coordinate game sessions. We plan to create a platform for people who are looking for other people to play games with. Covering online multiplayer games, tabletop, pen and paper or regular board games we want to provide a kind of bulletin board where people can state what they want to play, when and where they want to do it and how many people they are looking for. Others can then react to the postings and virtually join the play session to be connected by us so everyone can coordinate the actual play session together on a Common Playground.

### 2.2 Use Case Diagram

![OUCD](./UseCaseDiagramCP.png)

- Green: Planned till end of december
- Yellow: Planned till end of june

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

 3.1 Functionality                                                                                                                                       
                                                                                                                                                          
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
                                                                                                                                                          
  3.1.1 Searching for a product                                                                                                                           
                                                                                                                                                          
  This feature is the essential one of our project. The user gets the possibility to search for a packaged food product by entering its name or scanning  
  its barcode. The system then queries multiple data sources and returns a list of matching products for the user to select from.                         
                                                                                                                                                          
  ./use_cases/UC01_Search_Product.md                                                                                                                      
  
  3.1.2 Viewing halal status of a product                                                                                                                 
                                                            
  This feature provides the core value of the application. Once a product is selected, the system evaluates its ingredients, certifications, manufacturing
   details, and country of origin to deliver a halal verdict — Halal, Haram, or Mashbooh — along with a plain-language explanation.
                                                                                                                                                          
  ./use_cases/UC02_View_Halal_Status.md                     

  3.1.3 Uploading a product packaging photo                                                                                                               
  
  When a product cannot be found in the database, the user gets the possibility to upload a photo of the product packaging. The system then performs OCR  
  and image analysis to extract the product name, barcode, ingredients, and certification marks, and proceeds with the halal assessment.
                                                                                                                                                          
  ./use_cases/UC03_Upload_Photo.md                          

  3.1.4 Viewing ingredient analysis

  This feature gives the user a detailed, ingredient-by-ingredient breakdown of a product. Each ingredient is individually classified as Halal, Haram, or 
  Mashbooh with colour-coded indicators, allowing the user to understand exactly which components influence the overall verdict.
                                                                                                                                                          
  ./use_cases/UC04_Ingredient_Analysis.md                   

  3.1.5 Viewing alternative halal products                                                                                                                
   
  When a product is found to be Haram or Mashbooh, the app provides the user with a list of certified halal alternatives in the same product category.    
  This helps users quickly find a suitable replacement without having to start a new search.
                                                                                                                                                          
  ./use_cases/UC05_View_Alternatives.md                     

  3.1.6 Filtering search results by dietary preference                                                                                                    
   
  Based on the product search results, this feature enables the user to narrow down results by additional dietary requirements such as vegan, gluten-free,
   or nut-free, alongside halal compliance. This way, users with multiple dietary needs can find suitable products more efficiently.
                                                                                                                                                          
  ./use_cases/UC09_Filter_Results.md                        

  3.1.7 Adding a product to the shopping list                                                                                                             
   
  The user gets the possibility to save any product to a personal shopping list for future reference. This is useful when planning purchases in advance or
   when the user wants to keep a record of approved halal products they intend to buy regularly.
                                                                                                                                                          
  ./use_cases/UC06_Add_To_List.md                                                                                                                         
   
  3.1.8 Viewing the shopping list                                                                                                                         
                                                            
  The app provides the user with a separate page view where they get an overview of all products they have saved to their shopping list, including the    
  name, brand, and halal verdict of each item.
                                                                                                                                                          
  ./use_cases/UC07_View_List.md                             

  3.1.9 Removing a product from the shopping list

  The user also gets the possibility to remove a product they no longer need from their shopping list, keeping it relevant and up to date.                
   
  ./use_cases/UC08_Remove_From_List.md                                                                                                                    
                                                            
  3.1.10 Sharing a product halal status

  There must be the possibility for users to share their findings with family or friends. The user who checks a product gets the possibility to share its 
  halal status via a link through any messaging app, email, or clipboard. This is particularly useful in group shopping scenarios.
                                                                                                                                                          
  ./use_cases/UC10_Share_Status.md                          

  3.1.11 Viewing recently searched products

  The app provides the user with quick access to products they have previously looked up, without having to re-enter a search query. Recent searches are  
  shown automatically when the user opens the search field or navigates to the history section.
                                                                                                                                                          
  ./use_cases/UC11_Recent_Searches.md                       

  3.1.12 Reporting incorrect product information

  After viewing a product, the app provides the user with the possibility to flag inaccurate verdict or ingredient data by submitting a correction report.
   This is helpful because we want a platform with reliable and community-validated information.
                                                                                                                                                          
  ./use_cases/UC12_Report_Information.md                    

  3.1.13 Viewing halal certification details                                                                                                              
   
  When a product carries a halal certification, the user can access the full details of the certifying organisation, including its name, country,         
  accreditation status, certificate number, and validity dates. This allows users to independently verify the legitimacy of a certification before
  trusting a verdict.   

### 3.2 Usability
We plan on designing the user interface as intuitive and self-explanatory as possible to make the user feel as comfortable as possible using the app. Though an FAQ document will be available, it should not be necessary to use it.

#### 3.2.1 No training time needed
Our goal is that a user installs the android application, opens it and is able to use all features without any explanation or help.

#### 3.2.2 Familiar Feeling
We want to implement an app with familiar designs and functions. This way the user is able to interact in familiar ways with the app without having to get to know new interfaces.

### 3.3 Reliability

#### 3.3.1 Availability
The server shall be available 95% of the time. This also means we have to figure out the "rush hours" of our app because the downtime of the server is only tolerable when as few as possible players want to use the app.

#### 3.3.2 Defect Rate
Our goal is that we have no loss of any data. This is important so that the game sessions can carry on, even after a downtime of the server.

### 3.4 Perfomance

#### 3.4.1 Capacity
The system should be able to manage thousands of requests. Also it should be possible to register as many users as necessary.

#### 3.4.2 Storage 
Smartphones don't provide much storage. Therefore we are aiming to keep the needed storage as small as possible.

#### 3.4.3 App perfomance / Response time
To provide the best App perfomance we aim to keep the response time as low as possible. This will make the user experience much better.

### 3.5 Supportability

#### 3.5.1 Coding Standards
We are going to write the code by using all of the most common clean code standards. For example we will name our variables and methods by their functionalities. This will keep the code easy to read by everyone and make further developement much easier.

#### 3.5.2 Testing Strategy
The application will have a high test coverage and all important functionalities and edge cases should be tested. Further mistakes in the implementation will be discovered instantly and it will be easy to locate the error. 

### 3.6 Design Constraints
We are trying to provide a modern and easy to handle design for the UI aswell as for the architecture of our application. To achieve that the functionalities will be kept as modular as possible.

Because we are progamming an Android App we chose Java as our programming language. Also we are using the common MVC-architecture to keep the front end and back end seperated. For a clean front end structure we use MVVM.
To make the communication between the two parts easy, we will implement a RESTful-API between them which will provide the data in JSON-Format. 
The supported Platforms will be:
- Android 4.4 and higher
- Java 8 and higher

### 3.7 On-line User Documentation and Help System Requirements
The usage of the app should be as intuitive as possible so it won't need any further documentation. If the user needs some help we will implement a "Help"-Button in the App which includes a FAQ and a formular to contact the developement team.

### 3.8 Purchased Components
We don't have any purchased components yet. If there will be purchased components in the future we will list them here.

### 3.9 Interfaces

#### 3.9.1 User Interfaces
The User interfaces that will be implented are:
- Dashboard - lists all session and makes it possible to filter sessions
- Session Page - shows detailed information about the session and makes it possible to connect session attendants for example via messaging system
- Login - this page is used to log in 
- Register - provides a registration form
- Overwiew of personal sessions - shows all the sessions a user participates in
- Friend List - friends can be added
- Profile - makes it possible to post information about yourself, might provide messaging feature, also shows additional information about users (for example: Language, country, favorite games, etc.)
- Settings - shows the settings

#### 3.9.2 Hardware Interfaces
(n/a)

#### 3.9.3 Software Interfaces
The app will be runnable on Android 4.4 and higher. iOS won't be featured at the moment.

#### 3.9.4 Communication Interfaces
The server and hardware will communicate using the http protocol. 

### 3.10 Licensing Requirements

### 3.11 Legal, Copyright, and Other Notices
The logo is licensed to the Common Playground Team and is only allowed to use for the application. We do not take responsibilty for any incorrect data or errors in the application.

### 3.12 Applicable Standards
The development will follow the common clean code standards and naming conventions. Also we will create a definition of d which will be added here as soon as its complete.

## 4. Supporting Information
For any further information you can contact the Common Playground Team or check our [Common Playground Blog](http://commonplayground.wordpress.com). 
The Team Members are:
- Celina Adam
- Inga Batton
- Nils Krehl 
- Denis Reibel

<!-- Picture-Link definitions: -->
[OUCD]: https://github.com/IB-KA/CommonPlayground/blob/master/UseCaseDiagramCP.png "Overall Use Case Diagram"
