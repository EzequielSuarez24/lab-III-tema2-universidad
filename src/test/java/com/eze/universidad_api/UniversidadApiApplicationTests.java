package com.eze.universidad_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "springdoc.api-docs.enabled=false",
    "springdoc.swagger-ui.enabled=false"
})
class UniversidadApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
