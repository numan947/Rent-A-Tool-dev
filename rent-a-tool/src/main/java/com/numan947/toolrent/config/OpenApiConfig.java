package com.numan947.toolrent.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name="S Mahmudul Hasan",
                        email = "smhasan.dev@gmail.com"
                ),
                title = "Tool Rent API",
                version = "1.0",
                description = "Open API Documentation for Rent-A-Tool API",
                license = @io.swagger.v3.oas.annotations.info.License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "TERMS OF SERVICE PLACEHOLDER"
        ),
        servers = {
                @Server(
                        url = "http://localhost:9999/api/v1",
                        description = "Local Server"
                ),
                @Server(
                        url = "https://rent-a-tool.herokuapp.com/api/v1",
                        description = "Production Server"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
