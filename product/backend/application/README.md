# Application

This is the backend application for this final year project. Below are the steps for running it.

## Running

You can either run the application locally or using Docker:

### Locally

To run locally you can either build a jar file using maven
and then run that, or you can run the application with maven.

#### Maven

```
mvn clean test spring-boot:run
```

#### Jar

```
mvn clean test package
```

### Docker

You can run the appication by using Docker. First you have to build the image and then run it. 


Build the image using this command.

```
docker build -t accelerator-springboot .
```

Then run it.

```
docker run accelerator-springboot
```
