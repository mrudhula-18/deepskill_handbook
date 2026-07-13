package com.dn5.week3.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minimal springdoc-openapi configuration exposing API metadata at
 * {@code /v3/api-docs} and the Swagger UI at {@code /swagger-ui/index.html}.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookApiOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("DN5 Week 3 - Book REST API")
                        .description("Spring Boot 3 REST API with HATEOAS and JWT security")
                        .version("1.0.0"));
    }
}
