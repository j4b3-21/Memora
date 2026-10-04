# Java Instructions

These instructions apply to Java source files.

## Version

Use the Java version configured by the project.

Current target:
Java 25.

Do not introduce APIs requiring a newer Java version.

## Style

Use clear modern Java.

Prefer:

- records for simple DTOs
- enums for fixed states
- final variables where useful
- constructor injection
- java.time
- Optional only where it improves API clarity

Avoid:

- raw types
- wildcard imports
- unnecessary inheritance
- static mutable state
- reflection unless required
- utility classes for ordinary business logic

## Naming

Classes:
PascalCase

Methods:
camelCase

Variables:
camelCase

Constants:
UPPER_SNAKE_CASE

Boolean methods should read naturally:

isArchived()
hasEmbedding()
containsPerson()

## Nullability

Avoid returning null from services.

Prefer:

Optional<T>

when absence is expected.

Do not use Optional as entity fields.

## Exceptions

Use meaningful domain exceptions.

Example:

MemoryNotFoundException
InvalidMemoryException

Do not use RuntimeException everywhere.

Do not swallow exceptions.

Never:

catch (Exception e) {
}

## Collections

Return empty collections instead of null.

Prefer List, Set and Map interfaces.

Do not expose mutable internal collections unnecessarily.

## DTOs

Keep API DTOs separate from JPA entities.

Example:

MemoryCreateRequest
MemoryResponse

Do not expose database entities directly through REST.

## Logging

Use SLF4J.

Do not use System.out.println.

Never log:

- private memory content
- personal information
- authentication secrets
- API keys
- tokens

## Lombok

Lombok is available.

Use it only when it genuinely reduces boilerplate.

Prefer constructor injection.

Avoid excessive Lombok annotations.

## Comments

Do not write comments explaining obvious code.

Comments should explain:

- why something exists
- non-obvious constraints
- important architectural decisions

Prefer readable code over comments.
