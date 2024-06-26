#### Stage 1: Build the application
FROM openjdk:17-jdk-slim as build
LABEL authors="tuan"

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xvf ../*.jar)


#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:17-jdk-slim

ARG DEPENDENCY=/app/target/dependency

RUN if [ -d "${DEPENDENCY}" ]; then ls -la ${DEPENDENCY}; else echo "${DEPENDENCY} does not exist"; fi

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.petpal.backend.BackendApplication"]

### docker build -t back-end .
### docker run -i --entrypoint /bin/sh -p 8081:8081 --network db-network --name bContainer back-end
### java -cp app:app/lib/* com.spring.assignmentsubmissionapp.AssignmentSubmissionAppApplication
###  docker run -it --name dbcontainer -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -e POSTGRES_DB=assignment_submission_db --network db-network -p 5432:5432 -d postgres:13-alpine