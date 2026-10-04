# Memora — GitHub Copilot Instructions

## Project

Memora is a local-first personal memory application.

The core idea is not "AI chat" or "note taking".

Memora captures moments from a person's life and turns them into a searchable, contextual memory system.

The application should help answer:

- "What happened?"
- "When did this happen?"
- "What did I say about this?"
- "What was I doing around that time?"
- "What did this person mean to me?"
- "Remind me what I knew about this."

The user's data stays on their device.

## Architecture

Backend:
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- SQLite
- Flyway
- Bean Validation
- Lombok where useful
- SpringDoc/OpenAPI

Database:
- SQLite
- Local file
- No cloud database
- No Firebase

Authentication:
- None for now
- Single local user/device

AI:
- Must be replaceable
- Prefer open-weight/local models
- Never make the backend depend directly on one AI provider
- AI must operate on Memora's structured memory data

## Core Principle

Memora is memory infrastructure, not a chatbot.

Do not turn the backend into a generic chat application.

A memory should contain meaningful structured information such as:

- content
- source
- timestamp
- people
- places
- topics
- emotions
- events
- relationships
- importance
- embeddings when required
- AI-generated summaries when required

## Backend Architecture

Use:

Controller
↓
Service
↓
Repository
↓
Entity
↓
SQLite

Controllers must not contain business logic.

Services contain business rules.

Repositories only handle persistence.

Entities represent database state.

DTOs represent API contracts.

## Package Structure

Prefer feature-based packages:

com.Memora.backend
├── memory
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
├── search
├── ai
├── timeline
├── common
└── config

Do not create a huge global controller/service/repository hierarchy.

## API Rules

Use REST.

Use nouns in URLs.

Good:
GET    /api/memories
GET    /api/memories/{id}
POST   /api/memories
PUT    /api/memories/{id}
DELETE /api/memories/{id}

Avoid:
POST /api/createMemory
GET  /api/getAllMemories

Use appropriate HTTP status codes.

Validate incoming DTOs.

Never expose JPA entities directly from controllers.

## Database Rules

Flyway owns the schema.

Never use:

spring.jpa.hibernate.ddl-auto=create
spring.jpa.hibernate.ddl-auto=update

Use migrations instead.

Migration format:

V1__create_memories.sql
V2__add_memory_tags.sql

Never modify an already-applied migration.

Create a new migration for schema changes.

## IDs

Use UUIDs for application-level entity IDs.

Do not expose sequential database IDs.

## Dates

Use java.time.

Prefer:

Instant

for timestamps representing an absolute point in time.

Persist timestamps consistently.

Never use java.util.Date.

## Error Handling

Use a global exception handler.

Return consistent API errors.

Example:

{
"code": "MEMORY_NOT_FOUND",
"message": "Memory not found",
"timestamp": "..."
}

Do not return stack traces to clients.

## AI Rules

AI is an enhancement to the memory system.

Do not make AI responsible for basic CRUD.

AI operations should be isolated behind interfaces.

Example:

MemoryUnderstandingService
EmbeddingService
MemorySearchService

Do not directly call OpenAI/Gemini/etc. from controllers.

The AI implementation must be replaceable.

## Local-First Rule

Assume the application must work without an internet connection.

Do not introduce a cloud dependency unless explicitly requested.

Do not upload raw personal memories to third-party services by default.

## Code Quality

Prefer simple code over clever code.

Do not introduce abstractions without a reason.

Do not create unnecessary interfaces.

Keep methods small.

Use constructor injection.

Avoid field injection.

Prefer immutable DTOs where practical.

Use records for simple request/response DTOs when appropriate.

## Before Writing Code

Understand the existing architecture first.

Reuse existing services, repositories and DTOs.

Do not create duplicate functionality.

Do not silently change database schemas.

For a new feature:

1. Identify affected domain objects.
2. Define the API contract.
3. Define persistence changes.
4. Create Flyway migration.
5. Implement repository.
6. Implement service.
7. Implement controller.
8. Add validation.
9. Add tests.
10. Update OpenAPI documentation if required.

## Important

Do not add authentication.

Do not add cloud databases.

Do not add Redis.

Do not add Kafka.

Do not add microservices.

Do not add unnecessary infrastructure.

Memora should remain a small local-first application.