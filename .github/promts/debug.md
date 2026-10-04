# Debug Memora Backend

Debug the reported issue systematically.

## Process

1. Reproduce or reason about the failure.
2. Identify the first meaningful error.
3. Trace the request through:
   Controller
   → Service
   → Repository
   → Database
4. Check configuration.
5. Check entity mappings.
6. Check Flyway migrations.
7. Check SQLite compatibility.
8. Identify the root cause.
9. Apply the smallest correct fix.
10. Verify the fix with a test.

## Rules

Do not blindly change multiple files.

Do not disable validation.

Do not disable transactions.

Do not disable Hibernate checks to hide an error.

Do not replace SQLite with another database.

Do not solve database problems by deleting migrations unless explicitly requested.

## Output

Explain:

Root cause:
Fix:
Files changed:
Verification: