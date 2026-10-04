# Spring Boot Instructions

These instructions apply to Spring Boot code.

## Architecture

Use:

Controller → Service → Repository

Never:

Controller → Repository

for business operations.

## Controllers

Controllers should:

- receive HTTP requests
- validate input
- call services
- return responses

Controllers must not contain:

- database queries
- business rules
- AI logic
- complex transformations

Use:

@RestController

and appropriate:

@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping

## Services

Services contain business logic.

Use:

@Service

Transactions belong at the service layer.

Example:

@Transactional
public MemoryResponse createMemory(...) {
...
}

Use read-only transactions where appropriate.

## Repositories

Use Spring Data JPA.

Example:

public interface MemoryRepository extends JpaRepository<Memory, UUID> {
}

Repository methods should represent persistence queries.

Do not put business logic in repositories.

## Entities

Use:

@Entity

Entities represent persistence state.

Do not use entities as API request/response models.

Keep relationships deliberate.

Avoid unnecessary eager relationships.

Prefer LAZY loading where appropriate.

## DTO Validation

Validate request DTOs.

Examples:

@NotBlank
@Size
@NotNull
@PastOrPresent

Validation belongs at the API boundary.

## Configuration

Use application.yml or application.properties consistently.

Do not hard-code:

- ports
- database paths
- API keys
- model URLs

## Dependency Injection

Use constructor injection.

Prefer:

private final MemoryService memoryService;

Avoid:

@Autowired
private MemoryService memoryService;

## REST Responses

Use ResponseEntity only when response control is actually required.

Prefer simple typed responses.

Correct status codes:

200 OK
201 Created
204 No Content
400 Bad Request
404 Not Found
409 Conflict
422 Unprocessable Entity
500 Internal Server Error

## Error Handling

Create a centralized:

@RestControllerAdvice

for API errors.

Use a consistent error response.

Never expose internal exception messages in production.

## OpenAPI

Document important public endpoints.

Use meaningful descriptions.

API documentation should explain what the endpoint actually does.

## Spring Boot Simplicity

Do not introduce:

- microservices
- messaging
- distributed transactions
- service discovery
- unnecessary configuration

Memora is a local application.

Keep Spring Boot as the application layer around the local memory engine.