# Database Instructions

These instructions apply to SQLite, JPA and Flyway.

## Database

Memora uses SQLite.

Database file:

./data/memora.db

SQLite is intentionally used because Memora is local-first.

Do not replace it with PostgreSQL, MySQL or Firebase unless explicitly requested.

## Schema Ownership

Flyway owns the database schema.

Hibernate must not automatically modify the schema.

Never use:

spring.jpa.hibernate.ddl-auto=create
spring.jpa.hibernate.ddl-auto=update

## Migrations

Store migrations in:

src/main/resources/db/migration/

Naming:

V1__create_memories.sql
V2__add_memory_metadata.sql

Use one logical change per migration.

Never edit a migration that has already been applied.

## SQL

Use SQLite-compatible SQL.

Do not assume PostgreSQL-specific features.

Avoid:

JSONB
PostgreSQL arrays
PostgreSQL enums
PostgreSQL-specific functions

unless SQLite compatibility has explicitly been established.

## IDs

Application IDs should use UUID.

SQLite does not have a native UUID type.

Store UUIDs consistently as text.

## Timestamps

Store timestamps consistently.

Prefer ISO-8601-compatible values.

The application should use:

Instant

for absolute timestamps.

## Indexes

Create indexes for fields frequently used in:

- timeline queries
- date filtering
- memory lookup
- search
- relationships

Do not create indexes blindly.

## JPA

Repositories should extend:

JpaRepository<Entity, UUID>

Avoid native SQL unless JPA/JPQL is insufficient.

When native SQLite SQL is necessary, document why.

## Transactions

Use transactions at the service layer.

A single memory creation operation should not leave partially-created data.

## Data Safety

Memory data is personal data.

Never log raw memory content.

Never silently send database contents to external services.

Any external AI integration must explicitly define what data leaves the device.

## Future Search

Memora may eventually use:

- full-text search
- local embeddings
- vector search
- semantic retrieval

Do not prematurely add a vector database.

Start with a clean relational model.

Add specialized search storage only when required.