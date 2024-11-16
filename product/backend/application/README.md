# Backend Application

This is the backend application for this final year project. It is split into the service that runs the backend and its integration tests. Below are the steps for running the service and the integration tests.

## Running the service

You can either run the application locally or using Docker, first make sure you setup the database and set the applications properties. To do this create a new file called `application.yml` [here](backendService/src/main/resources/) based on the examples, use [this](src/main/resources/dev-application.example.yml) example for development and [this](backendService/src/main/resources/prod-application.example.yml) example for production. Finally there is also a [test](backendService/src/main/resources/test-application.example.yml) properties which should only be ran for the integration tests.

### Locally

To run locally you can either build a jar file using maven
and then run that, or you can run the application with maven.

#### Maven

```
mvn -f ./backendService/pom.xml clean test spring-boot:run
```

#### Jar

```
mvn -f ./backendService/pom.xml clean test package
```

### Docker

You can run the appication by using Docker. First move into the correct directory:

```
cd backendService
```

Then build the image using this command.

```
docker build -t final-project-backend .
```

Then run it.

```
docker run final-project-backend
```

## Running the integration tests

## Tests

There are two test suites for this application,
first the Unit tests and then the Integration tests.
The Unit tests are part of the maven test lifecycle
but the integration tests must be run manually.

### Unit Tests

The Unit tests can be run by using these commands:

```
mvn -f ./Application clean test
```

### Integration Tests

To run the Integration tests you must first be running the
application either locally or in a container. When running the application make sure to use the [test profile](backendService/src/main/resources/test-application.example.yml) so that the tests run correctly. Also make sure to run the application with a test database so that no production or dev data is lost. Then to run the tests use these commmands:

First:

```
mvn -am compile
```

Then

```
mvn test
```
