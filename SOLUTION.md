# Solutions Overview for the Wezaam Withdrawal Project

## Introduction

In this document, we will discuss the key changes and improvements made to the Withdrawal project.
These changes were oriented around solid architectural patterns, code simplification,
and enhancing the separation of concerns.
the idea was to keep the code as flexible as possible, as I understood from Maksim conversation,
the project was going to be interacting with different systems, I saw the opportunity to implement an hexagonal architecture,
as it seemed a very good fit for the project, and for me was the opportunity to learn something new as never really implemented the architecture before.

### Notes 
There is plenty of room for improvement and plenty of code to write still in the solution, 
But for time reasons I had to stop working and implementing.
things I will mention later on but to name a few :
- Removing the coupling of the models as they include JPA annotations.
- Introducing mappers for the out ports.
- Adding lots of testing, I think it's weak at this point on testing.

## 1. Hexagonal Architecture (Ports & Adapters)

### Why:
This architecture provides a clear separation between the core logic of the application (the domain) and everything that communicates with it (ports and adapters).
Makes the application easier to maintain, test, and evolve.
Enables the application to be agnostic to external systems, frameworks, and databases.
### What has been done:
Built the folder structure (I Know there are some files like the userMapper not in use or implemented, but left them as holders to point to the direction where the purity of the models was achieved)
Introduced Ports: Interfaces representing the primary (driven) and secondary (driving) ports. These describe the operations the application offers and expects from external agents.
Implemented Adapters: Concrete implementations of these ports. They "adapt" the external world to our application and vice versa.
Separated Core Domain: The domain layer became clean, holding only essential business logic without any external framework details.

## 2. Refactoring the WithdrawalService

### Why:
Enhances readability: The service's methods were too long and doing multiple things.
Promotes the Single Responsibility Principle: Functions do one thing and one thing only.
Facilitates easier testing: Smaller methods are more straightforward to test as they have fewer branches and side effects.

### What has been done:
The goal was to make the code mor clean code and SOLID compliant so, things done:
Broke Down Large Methods: Methods in WithdrawalService that were doing multiple things were split into smaller helper methods.
Introduced Clear Naming: Function names were chosen to reflect their purpose and behavior, making the code self-documenting.
Separated Concerns: Business validations, data transformations, and repository calls were distinctly separated.

## 3. Clean Domain Model

### Why:
Preserves the purity of the domain model: Ensures that the domain remains untainted by external framework annotations or concerns.
Enhances the separation of concerns: The domain should only focus on business rules and not on how data is stored or how external systems work.

### planned (but deferred):
Domain & Entity Separation: The intention was to separate JPA annotations and database-related concerns from the domain model by creating separate entity classes (like UserEntity). Then use mappers to convert between domain models and entities.


To clarify the project still needs lots of effort to be put, there are parts of the architecture still not fully implemented,
the EventService class has not been worked on for example, and could benefit of using portsOut to  connect to different services like event queues or repositories.

the idea was to create the foundation of a robust software, to work in the right direction and avoid technical debt.

Thanks for the opportunity.
Thank you.