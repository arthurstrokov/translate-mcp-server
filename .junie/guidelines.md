### Project Guidelines

This document provides essential information for developers working on the `translate-mcp-server` project.

---

### 1. Build and Configuration

#### Prerequisites
- **Java 25**: The project uses Java 25. Ensure your environment is configured correctly.
- **Ollama**: The server relies on a local Ollama instance for LLM-based translation.
    - Base URL: `http://localhost:11434`
    - Model: `translategemma:latest` (Pull it using `ollama pull translategemma:latest`)

#### Build Commands
The project uses Gradle as the build tool.
- **Build**: `./gradlew build`
- **Run**: `./gradlew bootRun` (Note: requires `--enable-native-access=ALL-UNNAMED` which is pre-configured in `build.gradle`).
- **Clean**: `./gradlew clean`

---

### 2. Testing Information

#### Running Tests
- **All tests**: `./gradlew test`
- **Specific test class**: `./gradlew test --tests "TranslateServiceTest"`

#### Testing Guidelines
- **Integration Tests**: Most tests are `@SpringBootTest` and require a running Ollama instance with the specified model to pass.
- **Adding Tests**:
    - Place tests in `src/test/java/com/gmail/arthurstrokov/translatemcpserver/`.
    - Use JUnit 5 and AssertJ assertions.
    - For service-level tests, you can inject `TranslateService`.

#### Simple Test Example
To verify your setup, you can create a simple integration test:
```java
@SpringBootTest
class SimpleTranslationTest {
    @Autowired
    private TranslateService translateService;

    @Test
    void testTranslation() {
        String result = translateService.translate("Hello");
        assertThat(result).isNotBlank();
        assertThat(result.chars().anyMatch(c -> 
            Character.UnicodeBlock.of((char) c) == Character.UnicodeBlock.CYRILLIC))
            .isTrue();
    }
}
```

---

### 3. Development Information

#### Architecture & MCP
- This is a **Model Context Protocol (MCP)** server built with **Spring AI**.
- **Tools**: Tools are defined in `src/main/java/.../tool/` using the `@Tool` annotation.
- **Discovery**: `ToolConfiguration` uses `MethodToolCallbackProvider` to automatically expose `@Tool` methods to the MCP server.
- **Prompting**: System prompts are stored in `src/main/java/.../template/TranslatePromptTemplate.java`.

#### Code Style
- **Lombok**: Extensively used for boilerplate reduction (`@RequiredArgsConstructor`, `@Slf4j`, etc.).
- **Final Classes/Methods**: Prompt templates and utility classes are typically `final` with private constructors.
- **Lazy Loading**: `TranslateTool` uses `@Lazy` for `TranslateService` to avoid circular dependencies during MCP tool registration if any occur.
- **Test Naming**: Test methods should follow the `camelCase` naming convention.

#### Debugging
- Logging levels for MCP protocol and internal services are configured in `application.yml`. 
- Set `com.gmail.arthurstrokov: DEBUG` for detailed service logs.
- MCP client/spec logs are also available at `DEBUG` level for protocol troubleshooting.
