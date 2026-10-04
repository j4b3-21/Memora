# Test Memora Backend

Create tests for the requested Memora feature.

Prioritize behavior over implementation details.

## Test layers

### Unit tests

Test:

- service business logic
- validation behavior
- domain rules
- exception behavior

### Repository tests

Test:

- persistence
- custom queries
- relationships
- SQLite-compatible behavior

### Controller tests

Test:

- HTTP status codes
- request validation
- response structure
- error responses

## Important cases

Always consider:

- valid input
- missing input
- invalid input
- empty results
- nonexistent memory
- duplicate data
- boundary dates
- database persistence

## Rules

Tests must not depend on external AI APIs.

Tests must not require internet access.

Tests must not use production data.

Use deterministic test data.

Keep tests readable.

## Goal

A developer should be able to run the test suite locally with no external services.