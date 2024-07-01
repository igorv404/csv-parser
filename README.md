# CSV parser

This repository contains a Spring Boot application designed for parsing CSV files and storing data
in a PostgreSQL database.

## Prerequisites

Ensure you have Docker and Docker Compose installed on your machine. You can download them from
the [Docker's official website](https://www.docker.com/products/docker-desktop/).

## How to run the project

1. **Create the database:**

```bash
docker compose up -d
```

2. **Run the project:**

For Unix systems:

```bash
./mvnw spring-boot:run
```

For Windows systems:

```cmd
mvnw.cmd spring-boot:run
```

## Additional Information

- Download the CSV file from [here](https://www.kaggle.com/datasets/ritiksharma07/imdb-top-1000-movies-dataset)
- The API documentation is available at: ```http://localhost:8080/swagger-ui/index.html```
- The health check endpoint is accessible at: ```http://localhost:8080/actuator/health```
- The metrics monitoring endpoint is accessible at: ```http://localhost:8080/actuator/prometheus```
