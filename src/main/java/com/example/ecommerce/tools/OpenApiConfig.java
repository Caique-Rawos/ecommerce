package com.example.ecommerce.tools;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Ecommerce")
                        .version("v1")
                        .description("API para gerenciamento de vendas online")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENCE-2.0"))
                        .contact(new Contact().name("Caique Rawos").url("https://www.linkedin.com/in/caique-caires-ramos-55297a208/")
                                .email("caique.rawos@gmail.com"))
                );
    }
}