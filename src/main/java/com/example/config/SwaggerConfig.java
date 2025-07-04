package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("API de Créditos Constituídos")
                                .version("1.0.0")
                                .description("API RESTful para consulta de créditos constituídos baseados em NFS-e")
                                .contact(new Contact()
                                        .name("Equipe de Desenvolvimento")
                                        .email("dev@exemplo.com"))
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")));
        }
}
