# translate-mcp-server

A Model Context Protocol (MCP) server built with Spring Boot and Spring AI that provides translation capabilities using
a local Ollama instance.

## Overview

This project implements an MCP server that exposes a translation tool. It leverages Spring AI's MCP support and uses the
`translategemma` model via Ollama for high-quality translations.

## Requirements

- **Java 25**: The project is configured to use Java 25.
- **Ollama**: A local Ollama instance must be running.
- **translategemma model**: You need to pull the specific model used for translation.
- **Gradle**: Used as the build tool (wrapper included).

## Setup

1. **Install Ollama**: Follow the instructions at [ollama.com](https://ollama.com/).
2. **Pull the Translation Model**:
   ```bash
   ollama pull translategemma:latest
   ```
3. **Verify Ollama**: Ensure Ollama is running at `http://localhost:11434`.

## Build and Run

### Build the project

```bash
./gradlew build
```

### Run the server

```bash
./gradlew bootRun
```

Note: The application requires `--enable-native-access=ALL-UNNAMED`, which is pre-configured in `build.gradle`.

### Clean the build

```bash
./gradlew clean
```

## Testing

### Run all tests

```bash
./gradlew test
```

### Run a specific test class

```bash
./gradlew test --tests "OllamaChatClientServiceTest"
```

### Test Types

- **Unit Tests** (`OllamaChatClientServiceTest`, `TranslateToolTest`): Use Mockito for mocking dependencies. Run without external services.
- **Integration Tests** (`TranslationIntegrationTest`): Require a running Ollama instance with the `translategemma:latest` model.

## Configuration

The application is configured via `src/main/resources/application.yml`. Key configurations include:

- **Ollama Base URL**: `http://localhost:11434`
- **Model**: `translategemma:latest` (configured via `spring.ai.ollama.chat.model`)
- **Server Port**: `8080`
- **Logging**: Detailed logs for `com.gmail.arthurstrokov` and MCP protocols are enabled at `DEBUG` level.
- **Prompt Template**: Translation prompt configured via `app.prompt.translate` property.

### Environment Variables

Currently, the application relies on `application.yml`. You can override these using standard Spring Boot environment
variables if needed:
- `SPRING_AI_OLLAMA_BASE_URL`: Base URL for Ollama.
- `SPRING_AI_OLLAMA_CHAT_MODEL`: Ollama model to use.

## Project Structure

- `src/main/java/.../configuration/`: Spring configuration for ChatClient and MCP tools.
- `src/main/java/.../service/`: Core translation logic (`OllamaChatClientService` implements `ChatClientService`).
- `src/main/java/.../tool/`: MCP tool definitions using `@Tool` annotation.
- `src/main/resources/`: Configuration files including prompt templates.
- `src/test/java/.../`: Unit tests (Mockito-based) and integration tests (`@SpringBootTest`).

## MCP Tools

The server exposes the following tools:

- `translate`: Translates the provided text.
    - Parameter: `text` (The text to be translated).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
