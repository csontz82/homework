# Homework

This project contains the solution of a homework excercise.

## The excercise

The task is to implement a data processing pipeline in the cloud.
* Set up a running environment with the technologies mentioned below.
* A Readme file containing information you deem useful for someone getting to
know your code and want to try the system out.
* Develop the application in Java 11, based on Spring Boot as a foundation.
* A REST API
    * An endpoint is taking a JSON payload, and publishes it to a PubSub topic
on REDIS or a similar tool of your choice. The endpoint must reject invalid
payloads. Payload structure:
    ```json
    {
    "content": "abrakadabra",
    "timestamp": "2018-10-09 00:12:12+0100"
    }
    ```
    * A PubSub topic consumer is implemented, that persists the freshly received JSON payload to the database.
    * The received JSON payload must also be broadcasted to listening browser clients via Websockets.
    * An endpoint to retrieve all messages persisted from the database; the entities must be enriched with the longest_palindrome_size property, that contains the length of the longest palindrome contained within the value of the content property.
    ```json
    [
    {
    "content": "abrakadabra",
    "timestamp": "2018-10-08 23:12:12+0000",
    "longest_palindrome_size": 3
    }
    ]
    ```
    When computing palindrome length, only alphabetic characters are considered. https://en.wikipedia.org/wiki/Palindrome

* A simple HTML page is implemented to show the real time message delivery.

## The solution

### Architecture

The implementation of this excercise can be a microservices architecture with the following software components:
* Message Receiver Microservice
    * Provides "An endpoint is taking a JSON payload, and publishes it to a PubSub topic on REDIS or a similar tool of your choice"
* Kafka topic as PubSub implementation
    * Provides the PubSub topic of my choice
* Message Persistence Microservice
    * Provides "A PubSub topic consumer is implemented, that persists the freshly received JSON payload to the database"
* MongoDB as persistence layer
    * The database
* Message Lister Microservice
    * Provides "An endpoint to retrieve all messages persisted from the database; the entities must be enriched with the longest_palindrome_size property"

### Business analysis

#### The workflows

##### Message persistence

The tasks in this workflow are:
* Provide a REST endpoint to take the messages in JSON payload
* Provide a PubSub topic
* Publish the incoming JSON payloads to the topic
* Provide a database component to store the messages somewhere
* Provide a consumer on the topic which reads the topic and persists the messages to the database

##### Sequence diagram:
![Message persistence workflow - sequence diagram](documentation/message-persistence.png)

##### Message listing

The tasks in this workflow are:
* Query the messages from the DB
* Do max length palindrom calculation
* Response the message list enriched with the calculated length

## Build and run instructions

### How to build the microservice docker images

```
gradlew jibDockerBuild
```

### How to run all the microservices and other software components

```
docker-compose up
```

## Further improvement areas

### SDLC
* The Non-Functional requirements should be clarified with stakeholders
* Apply peer reviews in the SDLC (maybe pair programming too) to increase the quality, and share the knowledge with other devs
* Add some static code analysis to the build process
* Apply more complex branching strategy in case of multiple developers - I just committed everything to the main branch, because I am the only committer and it is just a homework task

### Architecture
* When choosing the database we should make our choice using the CAP theorem to see which characteristic of DBs are important for us. Now I chose MongoDB, because I know that we want to store JSON documents (REST payload, but it easily translatable to any other structure, e.g. An SQL table with schema) and scalability is important, but in production environment it is a more complex question.

### Implementation
* Generate Rest Controller's code from the OpenApi descriptors
* Improve error handling in RestControllerAdvices and response with less sensitive information (e.g. class names)
* Usage of webflux implementation to build a non-blocking solutions

### Scalability
* Increase scalability with running these microservices on some containerisation orchestrator, e.g. Kubernetes, and add load balancing with defining k8s services for them, and add Horisontal Pod Autoscaling to scale the nr. of running instances of microservices according to the actual load.
* Consider scaling of the WebSocket services
* Consider scaling of the DB? MongoDB as a document NoSQL DB is well scalable, but it would be fine to know if our system is write or read heavy?

### Operations
* How topic creation should happen? In my solution the Message Receiver microservice creates the topic if it doesn't exist, or we can enable auto topic creation for Kafka, but in a production environment it should happen with administrators, and then these workarounds could be dropped.