# Virtual Open Space

## Project Overview

**Virtual Open Space** is a web application designed to visualize collaborations and virtual meetings within an organization using Microsoft TeamsPages. The goal of the application is to streamline the viewing and management of virtual interactions to enhance organizational efficiency and connectivity.

---

# Gateway-Service

The Gateway-Service is implemented using Spring Cloud Gateway. It acts as an entry point for all client requests, routing them to the appropriate microservices within the Virtual Open Space architecture. This service handles request routing, filtering, and provides cross-cutting concerns such as security, monitoring, and resiliency.

Status: **Completed**

## Key Features

- **Routing**: Directs client requests to the correct microservice based on the URL path.
- **Filtering**: Applies pre- and post-processing filters to requests and responses, such as logging, authentication, and rate limiting.
- **Load Balancing**: Distributes requests across multiple instances of a service to ensure high availability and reliability.

---

# Discovery-Service

The Discovery-Service is implemented using Spring Cloud Eureka. It functions as a service registry, allowing microservices to register themselves at runtime and discover other registered services. This promotes dynamic scaling and robustness within the microservice architecture.

Status: **Completed**

## Key Features

- **Service Registration**: Each microservice registers itself with the Eureka server, providing its location and status.
- **Service Discovery**: Microservices query the Eureka server to find the location of other services, enabling communication between services without hardcoding endpoints.
- **Health Monitoring**: Regularly checks the health of registered services and updates their status in the registry.

---

# Create CollaborationsPage Service

This section details the progress and components of the Create CollaborationsPage Service.

## Current Progress

- [x] **Database Connection**
  - Connect to database with Spring Data JPA.
  - Database: MySQL Workbench 8.0.34
  - Status: **Completed**

- [x] **Endpoints for CollaborationsPage**
  - Create and manually test endpoints for collaboration management.
  - External communication (WebClient v3)
  - Status: **Completed**

- [x] **Endpoints for Invitation**
  - Create and manually test endpoints for invitation management.
  - External communication (WebClient v3)
  - HTTP Methods implemented with all validations in create an invitation.
  - Create Unit and Integration test for CollaborationsPage API
  - Create Unit and Integration test for Invitation API
  - Status: **Completed**

## Next Progress

Separate these two APIs into two microservices:

- CollaborationsPage-Service should contain classes for CollaborationsPage and Participation.
- Invitation-Service should contain the Invitation class.

---

# Create Membre Service

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

# Create TeamsPages Service

This section details the progress and components of the Create TeamsPages Service.

## Current Progress

- [x] **Database Connection**
  - Connect to database with Spring Data MongoDB.
  - Database: MongoDB
  - Status: **Completed**

- [x] **Endpoints for Membre**
  - Create endpoints for team management.
  - Unit test endpoints for team management.
  - Status: **In Progress**
