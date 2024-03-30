package com.groupal.king.store.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "King Store Docs",
                version = "1.0.0",
                description = "King store documents for development"
        )
)
public class SwaggerConfig {
        @Bean
        public GroupedOpenApi api() {
                return GroupedOpenApi.builder()
                        .group("king-store-public")
                        .pathsToMatch("/api/**")
                        .build();
        }
}
