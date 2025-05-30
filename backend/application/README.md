# Backend Application

This is the backend application for this final year project. It is split into the service that runs the backend and its integration tests. Below are the steps for running the service and the integration tests.

## Running the service

You can either run the application locally or using Docker, first make sure you setup the database and set the applications properties. To do this create a new file called `application.yml` [here](backendService/src/main/resources/) based on the examples, use [this](backendService/src/main/resources/dev-application.example.yml) example for development and [this](backendService/src/main/resources/prod-application.example.yml) example for production. 

There is also a [test](backendService/src/main/resources/test-application.example.yml) properties which should only be ran for the integration tests. 

Finally there is a [dynamic version](backendService/src/main/resources/dynamic-application.example.yml) which uses variables for setting the properties, so you need to pass in the values using -D when running using maven or as enviroment variables.


There is also a [test profile](backendService/src/test/resources/application.example.yml) that must be copied and for the unit tests the TEST_COGNITO_JWT_URL variable must be set.

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

You can run the application by using Docker. First move into the correct directory:

```
cd backendService
```

Then build the image using this command. You may need to add a build argument if it does not work.

```
docker build -t final-project-backend .
```
```
docker build --build-arg TEST_COGNITO_JWT_URL= -t final-project-backend .
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

First you must set up the test properties for the unit tests and then they can be ran by using these commands with the enviroment variable:

```
mvn clean test -pl backendService -DTEST_COGNITO_JWT_URL=
```

### Integration Tests

To run the Integration tests you must first be running the
application either locally or in a container. When running the application make sure to use the [test profile](backendService/src/main/resources/test-application.example.yml) so that the tests run correctly. Also make sure to run the application with a test database so that no production or dev data is lost. Then to run the tests use this commmand:

Make sure the variables needed are set with the values from the test cognito user pool.
```
mvn compile test -DTEST_AWS_REGION= -DTEST_AWS_USER_POOL_ID= -DTEST_AWS_CLIENT_ID= -DJIRA_EMAIL= -DJIRA_URL= -DJIRA_ACCESS_TOKEN=
```

## Actuator

The Spring Actuator can be used to help monitor and manage the application. The config for the actuator is contained
within the [application.yml files](./backendService/src/main/resources/prod-application.example.yml). It shows examples of how to change the endpoint the actuator is under and how to exclude features. The current enabled endpoints are the actuator and health endpoints, which are at /details and /details/health

## CheckStyle 

CheckStyle is used in this project so that code that is created follows a standard that other developers can understand. 

To run CheckStyle, you can run:

```
mvn  -f ./backendService checkstyle:check
```

To configure checkstyle you can change [this file](./backendService/config/checkstyle.xml) which will change what checkstyle looks for. You can also change what checks are suppressed by editing [this file](./backendService/config/checkstyle-suppressions.xml).

## Todo 

Split up the backend service into seperate microservices.