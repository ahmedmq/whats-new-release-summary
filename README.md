# Whats New Release Summary
Whats New Release Summary is a sample implementation of a generative AI application that summarizes a product release managed in Pivotal Tracker

Still Work-In-Progress

# Technology stack
- Core Application Framework - [Spring Boot](https://spring.io/projects/spring-boot)
- Backend Programming Language - [Kotlin](https://kotlinlang.org/)
- Database - [DynamoDB](https://aws.amazon.com/dynamodb/)
- Application UI Framework - [Thymeleaf](https://www.thymeleaf.org/) + [Bootstrap](https://getbootstrap.com/)
- AI Engineering Framework - [Spring AI](https://spring.io/projects/spring-ai)
- E2E Testing - [Playwright Java](https://playwright.dev/java/docs/intro)

# Prerequisites

- Pivotal Tracker [API Token](https://www.pivotaltracker.com/help/articles/api_token/)
- Open AI Token
- Docker
  - Docker is required to run application locally. Please refer to the [Docker](https://docs.docker.com/engine/install/) documentation for more information.

# Getting Started

- From the root of the project, run the [docker-compose.yml](compose.yml) file to start the DynamoDb, Ollama containers

  ```shell
  docker compose up -d
  ```
  This will initiate the download of the Ollama LLM and the LLaMA 3.1 model, both of which are large files.


- Wait until the model is downloaded successfully
  ```shell
  docker ps
  ```
  Once the model is successfully downloaded, only the three containers listed below will be running. 
  ```text
  CONTAINER ID   IMAGE                                COMMAND                  CREATED          STATUS                             PORTS                      NAMES
  19e62dbe3462   ghcr.io/open-webui/open-webui:main   "bash start.sh"          21 seconds ago   Up 21 seconds (health: starting)   0.0.0.0:3000->8080/tcp     open-webui
  4f406c530d30   amazon/dynamodb-local:latest         "java -jar DynamoDBL…"   21 seconds ago   Up 21 seconds                      0.0.0.0:8000->8000/tcp     dynamodb-local
  9aad9110a03b   ollama/ollama:latest                 "/bin/ollama serve"      21 seconds ago   Up 21 seconds                      0.0.0.0:11434->11434/tcp   ollama
  ```

- Start the application using the `local` profile

  ```shell
  SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
  ```
