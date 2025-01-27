# Energy provider

## Getting Started

### Prerequisites

- Ensure you're in the project's root directory

- Open a terminal session

First position yourself in the root folder of the project and open terminal.

## Building the Application

To compile and package the application:

```bash

mvn -f ./energy-provider/pom.xml clean package

```

_This will create an executable JAR file in the `target` directory._

---

## Running the Application

Execute the JAR with your preferred sorting order:

```bash

java -jar energy-provider/target/energy-provider-0.0.1-SNAPSHOT.jar --sorting.order=<ORDER>

```

**Parameters:**

- `<ORDER>`: Specify sorting direction

- `ASC`: Ascending order (default)

- `DESC`: Descending order

_Example:_

```bash

java -jar energy-provider-0.0.1-SNAPSHOT.jar --sorting.order=desc

```

---

## Project Architecture

### Technologies

The application is built using **Spring Boot** framework with H2 in-memory database.

### Structure

The project follows the REST API architectural style and is structured into three layers:

1. Controller

   - Defines API endpoints for the application.

2. Service

   - Contains the business logic of the application.

3. Repository

   - Handles data retrieval and storage.

## Testing

Apllication contains unit tests for each of the layers.
