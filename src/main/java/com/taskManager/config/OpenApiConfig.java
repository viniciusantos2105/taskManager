package com.taskManager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("REST API with Java 17 and Spring Boot 3").version("v1")
				.description("REST API's com Java 17, Spring Boot 3.x e Docker").termsOfService("https://github.com/viniciusantos2105?tab=repositories")
				.license(new License().name("Apache 2.0").url("https://github.com/viniciusantos2105?tab=repositories")));
	}
}