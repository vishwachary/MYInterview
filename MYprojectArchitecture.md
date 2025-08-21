Using as a reference the clean architecture material from Uncle Bob, we want to implement a similar project structure to achieve a clean code with low coupling and high cohesion. The project structure proposed originally consisted of 5 layers:

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
  

