Using as a reference the clean architecture material from Uncle Bob, we want to implement a similar project structure to achieve a clean code with low coupling and high cohesion. The project structure proposed originally consisted of 5 layers:

YsN6twE3-4Q4OYpgxoModmx29I8zthQ3f0OR
Entities

    Represent your domain object
    Apply only logic that is applicable in general to the whole entity (e.g., validating the format of a hostname)
    Plain objects: no frameworks, no annotations

Use Cases

    Represent your business actions: it’s what you can do with the application. Expect one use case for each business action
    Pure business logic, plain code (except maybe some utils libraries)
    The use case doesn’t know who triggered it and how the results will be presented (for example, it could be on a web page, or — returned as JSON, or simply logged, and so on.)
    Throws business exceptions

Interfaces / Adapters

    Retrieve and store data from and to a number of sources (database, network devices, file system, 3rd parties, and so on.)
    Define interfaces for the data that they need in order to apply some logic. One or more data providers will implement the interface, but the use case doesn’t know where the data is coming from
    Implement the interfaces defined by the use case
    There are ways to interact with the application and they typically involve a delivery mechanism (for example, REST APIs, scheduled jobs, GUI, and other systems)
    Trigger a use case and convert the result to the appropriate format for the delivery mechanism
    the controller for an MVC

External Interfaces

    Use whatever framework is most appropriate (they are going to be isolated here anyway)
    <img width="1311" height="585" alt="image" src="https://github.com/user-attachments/assets/09b5da94-e48d-47ab-9141-203f611167e6" />


Example of a clean architecture project structure
src
|-- main
|   |-- java
|   |   |-- com.example.myapp
|   |   |   |-- config
|   |   |   |   |-- DatabaseConfig.java
|   |   |   |   |-- ...
|   |   |   |-- application
|   |   |   |   |-- usecases
|   |   |   |   |   |-- RegisterUserUseCase.java
|   |   |   |   |   |__ ... other use cases
|   |   |   |   |__ ...
|   |   |   |-- domain
|   |   |   |   |-- entities
|   |   |   |   |   |-- User.java
|   |   |   |   |   |__ ... other entities
|   |   |   |   |-- valueobjects
|   |   |   |   |   |-- ValidationResult.java
|   |   |   |   |   |__ ... other value objects
|   |   |   |   |__ ...
|   |   |   |-- infrastructure
|   |   |   |   |-- adapters
|   |   |   |   |   |-- UserRepository.java
|   |   |   |   |   |-- UserRepositoryJdbi.java
|   |   |   |   |   |__ ... other adapters
|   |   |   |   |__ ...
|   |   |   |__ Main.java (Application entry point)
|   |   |__ resources
|   |       |__ application.properties (Database and other configurations)
|   |__ resources
|       |-- db
|       |   |-- migrations
|       |   |   |__ ...
|       |   |__ hikari.properties (HikariCP database connection pool config)
|       |__ logback.xml (Logging configuration)
|__ test
    |-- java
    |   |__ com.example.myapp
    |       |-- application
    |       |   |-- usecases
    |       |   |   |-- RegisterUserUseCaseTest.java
    |       |   |   |__ ... other use case tests
    |       |   |__ ...
    |       |-- infrastructure
    |       |   |-- adapters
    |       |   |   |-- UserRepositoryJdbiTest.java
    |       |   |   |__ ... other adapter tests
    |       |   |__ ...
    |       |-- domain
    |       |   |   |-- valueobjects
    |       |   |   |   |-- ValidationResultTest.java
    |       |   |   |   |__ ... other value object tests
    |       |   |   |__ ...
    |       |__ IntegrationTests.java (Integration tests)
    |__ resources
        |__ ... (Test resources)
Implementation Plan
Nio:

Entities / Repositories
Library

Use Cases
OxygenPlugin

Controllers / Adapters
Nio

    Entities
    Repositories for each Entity

 
Library

    Commands
    Use Cases
    Command Handler

 
Plugin

    Command Context initializer
    Command adapter

 
