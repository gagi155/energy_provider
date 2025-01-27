# Energy provider

## Starting the application

First position yourself in the root folder of the project and open terminal.

### Compiling the application

The following command is used for building the application:

```
    mvn -f ./energy-provider/pom.xml clean package
```

### Running the application

The following command is used for running the application:

```
    java -jar energy-provider-0.0.1-SNAPSHOT.jar --sorting.order= <Order>
```

Order can be "ASC" or "DESC".

## Project Arhitecture

### Technologies

The application is built using **Spring Boot** framework with H2 in-memory database.

### Structure

**REST API** is used as the arhitecture style in this project \
The project contains three layers: **Controller**, **Service** and **Repository**.

#### Controller

This layer defines API endpoints of the project.

#### Service

This layer contains business logic of the project.

#### Repository

This layer is used for retrieving and storing data.
