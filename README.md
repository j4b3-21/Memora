# Memora — A Personal Memory That Knows When to Remind You

## 1. The idea

**Memora is not an AI assistant, chatbot, or note-taking app.**

It is a **personal memory engine for one real person**.

You give Memora small pieces of life — a voice note, photo, message, document, or text.

Memora turns them into **time-, person-, place-, and event-linked memories**.

Later, instead of asking:

> "What did I write down?"

Memora answers:

> **"What is relevant to me right now?"**

### The differentiating feature

**Contextual memory resurfacing.**

Memora does not wait for the user to search.

It detects when a past memory becomes relevant to the user's **current context** and surfaces it.

Example:

> Your friend mentioned 3 months ago that their father loves a particular restaurant.

You are currently:
- near that restaurant
- planning dinner with that friend

Memora says:

> **"You mentioned that Arun's father really liked this place. You were thinking about taking them here sometime."**

That is the core product.

---

# 2. Why this is different

| Product | Primary behavior |
|---|---|
| Siri / Google Assistant | Executes commands |
| ChatGPT / Gemini | Answers questions |
| Notes apps | Store things you deliberately write |
| Calendar | Stores scheduled events |
| Search | Finds information you explicitly look for |
| Typical AI memory | Remembers facts about conversations |
| **Memora** | **Connects past life events to the present moment** |

Memora is essentially:

**Capture → Understand → Connect → Wait → Resurface**

The "wait → resurface" part is the product.

Persistent AI memory itself is no longer novel; open-source systems such as Mem0 already provide persistent memory, semantic retrieval, and memory updating. Memora therefore should differentiate at the **user experience layer: contextual resurfacing of personal memories**, not by claiming to invent AI memory. citeturn0search0turn0search11

---

# 3. Build for ONE friend

Do not build a generic assistant.

Pick one real person.

Example:

**"I built Memora for my friend Rahul because he constantly forgets small things people tell him."**

Memora learns:

- people
- relationships
- places
- preferences
- promises
- important conversations
- events
- recurring situations
- personal stories

---

# 4. MVP

## Capture

Input:

- Text
- Voice
- Photo
- PDF/document

Example:

> "Rahul told me yesterday that his sister's birthday is November 18 and she loves Studio Ghibli."

AI extracts:

```text
Person: Rahul
Person: Rahul's sister
Event: Birthday
Date: November 18
Preference: Studio Ghibli
Relationship: Sister
Source: Conversation
Confidence: High
```

---

# 5. Memory model

Every memory should contain:

```text
Memory
├── id
├── content
├── type
├── people
├── places
├── events
├── timestamp
├── source
├── importance
├── confidence
├── embedding
└── created_at
```

Important:

**Do not store only text.**

Store the relationships around the memory.

---

# 6. The Memory Graph

Example:

```text
             Rahul
               |
             friend
               |
             Bhuvan
               |
        ┌──────┴──────┐
        |             |
      Dinner        Birthday
        |             |
   Restaurant      Nov 18
        |
   "likes ramen"
```

This allows Memora to answer questions such as:

> "What would Rahul probably like?"

and:

> "When did Rahul mention his sister's birthday?"

but more importantly:

> **"Is there anything I should remember about Rahul right now?"**

---

# 7. The killer feature: Memory Moments

The application continuously evaluates:

```text
Current Context
      ↓
Relevant memories?
      ↓
Importance + relevance + recency
      ↓
Should this interrupt the user?
      ↓
Memory Moment
```

Example:

### Context

User opens a restaurant.

Memora finds:

```text
Friend: Rahul
Memory: Rahul loved this restaurant
Recorded: 4 months ago
Confidence: 0.94
```

Instead of requiring a search:

> **💡 Memory Moment**
>
> Rahul told you he loved this place.
>
> You were considering bringing him here sometime.

This should feel like **remembering**, not chatting.

---

# 8. Privacy is part of the product

Everything should be **local-first**.

```text
Phone
│
├── SQLite
├── encrypted memories
├── local embeddings
├── local/open-weight AI
└── Spring Boot
```

No account.

No mandatory cloud.

No central personal database.

The user owns the memory.

If a cloud model is used for the prototype, make it an explicit opt-in fallback.

This directly supports the Hacktoberfest open-innovation requirement: the open model/local inference should make private, offline personal memory possible.

---

# 9. AI architecture

```text
                 INPUT
                   │
       ┌───────────┼───────────┐
       ↓           ↓           ↓
      Text        Voice       Image
       │           │           │
       └───────────┼───────────┘
                   ↓
             AI Extraction
                   ↓
          Memory Construction
                   ↓
          ┌────────┴────────┐
          ↓                 ↓
     SQLite Memory      Embeddings
          │                 │
          └────────┬────────┘
                   ↓
             Memory Graph
                   ↓
            Context Engine
                   ↓
          Memory Moment
```

---

# 10. Backend

## Stack

```text
Java 25
Spring Boot 4
Spring Web
Spring Data JPA
Hibernate
SQLite
Flyway
Validation
SpringDoc
```

Current database:

```text
./data/memora.db
```

---

# 11. Backend structure

```text
com.memora.backend

├── memory
│   ├── Memory.java
│   ├── MemoryController.java
│   ├── MemoryService.java
│   └── MemoryRepository.java
│
├── context
│   ├── Context.java
│   └── ContextService.java
│
├── moment
│   ├── MemoryMoment.java
│   └── MomentService.java
│
├── ai
│   ├── AiService.java
│   ├── ExtractionService.java
│   └── EmbeddingService.java
│
└── common
    └── ...
```

---

# 12. REST API

### Memories

```http
POST   /api/memories
GET    /api/memories
GET    /api/memories/{id}
DELETE /api/memories/{id}
```

### Search

```http
GET /api/memories/search?q=rahul
```

### Context

```http
POST /api/context
```

### Memory Moments

```http
GET /api/moments
POST /api/moments/{id}/dismiss
```

---

# 13. Database

Start with:

```text
memories
people
places
events
memory_people
memory_places
memory_events
memory_embeddings
```

Do not over-engineer the database during the weekend.

The important thing is proving the **memory → context → resurfacing** loop.

---

# 14. AI features

### Phase 1

Text → structured memory

### Phase 2

Voice → text → memory

### Phase 3

Photo/document → memory

### Phase 4

Semantic retrieval

### Phase 5

Contextual resurfacing

Stop here for the competition MVP.

---

# 15. Open-source AI

Use an open-weight model locally where practical.

Possible architecture:

```text
Spring Boot
     │
     ↓
Local AI service
     │
     ├── extraction
     ├── classification
     ├── summarization
     └── embeddings
```

The exact model is less important than demonstrating:

**the product still works without sending someone's personal memories to a proprietary AI provider.**

---

# 16. Demo that judges should see

Do NOT start with architecture.

Start with your friend.

### Scene 1

Record:

> "Rahul said his sister loves Ghibli and her birthday is November 18."

Memora processes it.

### Scene 2

A few memories are added.

### Scene 3

The user opens Memora later.

Memora shows:

> **Memory Moment**
>
> Rahul's sister's birthday is coming up.
>
> You recorded that she loves Studio Ghibli.

### Scene 4

User asks:

> "What have I promised Rahul?"

Memora returns:

```text
• Help him prepare for his interview
• Send him the project link
• Take him to the restaurant he liked
```

### Scene 5

Turn off the internet.

Show the core memory/search experience still working locally.

That is the demo.

---

# 17. What NOT to build

Do not waste the weekend building:

- Login
- Social network
- Generic chatbot
- AI avatar
- Calendar replacement
- To-do app
- Generic RAG chatbot
- Complex agent swarm
- Cloud dashboard
- 20 database tables
- Authentication
- Payments

The product should be tiny.

---

# 18. The one-sentence pitch

> **Memora is a private, local-first AI memory that doesn't just remember your life — it knows when a past memory matters to the moment you're living right now.**

---

# 19. Hacktoberfest positioning

The story is:

**"I didn't build another AI assistant. I built something for one person I actually know."**

The friend provides the real problem.

Open AI provides the privacy and control.

Local memory provides persistence.

Context provides the differentiator.

The final loop is:

```text
Something happened
       ↓
Memora remembered it
       ↓
Life moved on
       ↓
A relevant moment happened
       ↓
Memora brought it back
```

That is the entire product.

---

# 20. Weekend build plan

## Day 1

```text
✓ Spring Boot
✓ SQLite
✓ Flyway
✓ Memory entity
✓ CRUD APIs
✓ Text capture
✓ AI extraction
```

## Day 2

```text
✓ Embeddings
✓ Semantic search
✓ People/events/places
✓ Context engine
✓ Memory Moments
✓ Mobile UI
```

## Final hours

```text
✓ Test with the real friend
✓ Record demo
✓ Add offline demonstration
✓ Write DEV article
✓ Explain open-source AI
✓ Show before/after examples
```

---

# 21. Success metric

Don't measure:

```text
number of memories
number of AI calls
number of features
```

Measure:

> **"Did Memora surface something the user had forgotten, at the moment it became useful?"**

For the demo, deliberately create 5–10 memories with your friend.

Then create 3 real contexts where those memories should surface.

Record the results.

That becomes your evidence.

---

# 22. Final product

```text
                 MEMORA

        Your life happens normally.

                 ↓

       You casually capture things.

                 ↓

       AI understands the memory.

                 ↓

       Memora connects people,
       places, events and time.

                 ↓

             Life moves on.

                 ↓

       A relevant situation occurs.

                 ↓

        ┌─────────────────────┐
        │   MEMORY MOMENT     │
        │                     │
        │ "You remembered     │
        │  this about Rahul." │
        └─────────────────────┘
```

**Memora should feel less like talking to an AI and more like having a second memory that knows when to remind you.**
