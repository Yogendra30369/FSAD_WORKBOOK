package com.klu.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Student CRUD API",
                version = "1.0",
                description = "OpenAPI documentation for Full-Stack Student Management CRUD operations",
                contact = @Contact(name = "FSAD Team", email = "team-fsad@example.com"),
                license = @License(name = "Educational Use")
        ),
        servers = {
                @Server(description = "Local server", url = "http://localhost:8080")
        }
)
public class OpenApiConfig {
}
