

# Build the docker image
FROM public.ecr.aws/docker/library/maven:3.9-eclipse-temurin-21-alpine AS build

RUN apk add --no-cache curl jq
HEALTHCHECK CMD curl -sSf http://127.0.0.1:8080/details/health | jq '.status' | grep -q '\"UP\"' || exit 1

# Set the working directory
WORKDIR /app

# Set build arguments
ARG TEST_COGNITO_JWT_URL

# Copy the project files
COPY pom.xml .
COPY src ./src
COPY config ./config

# Set property files
RUN mv src/main/resources/dynamic-application.example.yml src/main/resources/application.yml
RUN mv src/test/resources/application.example.yml src/test/resources/application.yml

# Run the maven lifecycles to build the application
RUN mvn clean package

# Stage 2: Create the run time image
FROM public.ecr.aws/docker/library/eclipse-temurin:21-jdk-alpine AS run

# Set the working directory
WORKDIR /app

# Copy the JAR file built in the previous stage
COPY --from=build /app/target/*.jar ./app.jar

# Expose the applications port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]