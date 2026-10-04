# Implement Feature

Implement the requested Memora backend feature.

## Before coding

1. Inspect the existing architecture.
2. Identify related entities.
3. Identify existing services and repositories.
4. Check existing Flyway migrations.
5. Avoid duplicating existing functionality.

## Implementation order

1. Define/update domain model.
2. Create Flyway migration if schema changes.
3. Create/update entity.
4. Create/update repository.
5. Create service logic.
6. Create DTOs.
7. Create controller.
8. Add validation.
9. Add error handling.
10. Add tests.
11. Update OpenAPI documentation if necessary.

## Requirements

Follow:

.github/copilot-instructions.md

and all relevant files under:

.github/instructions/

Do not add authentication.

Do not add cloud dependencies.

Do not introduce unnecessary infrastructure.

Keep the feature local-first.

## Output

After implementation, summarize:

- files changed
- database changes
- API endpoints
- important design decisions
- tests added