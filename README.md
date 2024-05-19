# Virtual Open Space

## Project Overview

**Virtual Open Space** is a web application designed to visualize collaborations and virtual meetings within an organization using Microsoft Teams. The goal of the application is to streamline the viewing and management of virtual interactions to enhance organizational efficiency and connectivity.

---
# `Create Collaboration Service`

This section details the progress and components of the Create Collaboration Service.

## Current Progress

- [x] **Database Connection**
  - Connect to database with Spring Data JPA.
  - Database: MySQL Workbench 8.0.34
  - Status: **Completed**

- [x] **Endpoints for Collaboration**
  - Create and manually test endpoints for collaboration management.
  - External communication (WebClient v3)
  - Status: **Completed**

- [x] **Endpoints for Invitation**
  - Create and manually test endpoints for invitation management.
  - External communication (WebClient v3)
  - HTTP Methods implemented with all validations in create an invitation.
  - Create Unit and Integration test for Collaboration API
  - Create Unit and Integration test for Invitation API
  - Status: **Completed**

## Next Progress

Separate these two APIs into two microservices:

- Collaboration-Service should contain classes for Collaboration and Participation.
- Invitation-Service should contain the Invitation class.

---

# `Create Membre Service`

This section details the progress and components of the Create Membre Service.

## Current Progress

- [x] **Database Connection**
  - Connect to database with Spring Data JPA.
  - Database: MongoDB
  - Status: **Completed**

- [x] **Endpoints for Membre (Post, GetOne, and GetAll)**
  - Create endpoints for membre management.
  - Unit test endpoints for membre management.
  - Integrate test endpoints for membre management.
  - Status: **Completed**

## Next Progress

Change MongoDB database to Firebase RealTime database

---

# `Create Team Service`

This section details the progress and components of the Create Team Service.

## Current Progress

- [X] **Database Connection**
  - Connect to database with Spring Data MongoDB.
  - Database: MongoDB
  - Status: **Completed**

- [ ] **Endpoints for Membre **
  - Create endpoints for team management.
  - Unit test endpoints for team management.
  - Status: **Progress**
