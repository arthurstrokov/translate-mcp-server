package com.gmail.arthurstrokov.translatemcpserver;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTests.class);

    @Test
    void contextLoads() {
        // This test verifies that the application context can be loaded.
        String property = System.getProperty("spring.aot.enabled");
        log.info(property);
    }
}
