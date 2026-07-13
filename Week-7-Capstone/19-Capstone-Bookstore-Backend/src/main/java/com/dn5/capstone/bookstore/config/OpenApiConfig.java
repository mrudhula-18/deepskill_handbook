package com.dn5.capstone.bookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minimal OpenAPI/Swagger configuration for the Bookstore Backend API.
 * Swagger UI is available at /swagger-ui.html once the application is running.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookstoreOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("DN5.0 Capstone - Bookstore Backend API")
                        .description("REST API for the Bookstore capstone (Module 19) - backs the React frontend in Module 20")
                        .version("1.0.0")
                        .contact(new Contact().name("DN5.0 Java FSE Capstone")));
    }
}
