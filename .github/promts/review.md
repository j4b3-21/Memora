# Review Memora Backend

Review the current backend implementation.

Check for:

## Architecture

- Controller → Service → Repository
- business logic outside controllers
- no repository calls directly from controllers
- no unnecessary abstractions

## Java

- Java 25 compatibility
- clean naming
- null safety
- proper exception handling
- no System.out logging
- unnecessary complexity

## Spring

- constructor injection
- correct transactions
- validation
- REST conventions
- centralized error handling

## Database

- SQLite compatibility
- correct JPA mappings
- Flyway migrations
- no ddl-auto schema mutation
- correct UUID handling
- appropriate indexes

## Memora-specific

- local-first behavior
- no accidental cloud dependency
- no raw memory logging
- AI provider independence
- privacy-preserving design

## Output

Report findings as:

CRITICAL
IMPORTANT
IMPROVEMENT

Do not rewrite working code unless necessary.

For every issue, explain:

1. What is wrong.
2. Why it matters.
3. How to fix it.