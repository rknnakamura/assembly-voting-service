package com.test.assembly_voting_service.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Assembly Voting Service API")
                        .description("API para gerenciamento de pautas e votações em assembleias")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Renato Nakamura")));
    }
}
