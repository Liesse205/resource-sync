# Resource-Sync: Knowledge Structuring Engine

Resource Sync is a backend knowledge structuring system that organizes scattered learning materials (notes, links, PDFs, videos, and code) into connected, concept-based clusters.

## Tech Stack
Java 17 · Spring Boot · Spring Web (REST API) · PostgreSQL · Maven 

## Setup
- DB: `resource_sync`
- User: `postgres` / `postgres`

## Running
```bash
mvn clean install
mvn spring-boot:run
```
## Key Features
-Store and manage learning resources (notes, links, files, code)
-Concept-based grouping of resources
-Intelligent duplicate detection and merging
-Relationship mapping between concepts
-RESTful backend with clean layered architecture (DTOs, services, repositories)
