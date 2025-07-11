package com.project.mog.config.swagger;

import java.lang.annotation.Annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;



@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
	@Bean
	protected OpenAPI openApi() {//Swagger 문서를 설정하는 OpenAPI 객체 Bean으로 등록
		// Security Scheme 정의
				SecurityScheme securityScheme = new SecurityScheme()
													.type(SecurityScheme.Type.HTTP)
													.scheme("bearer")
													.bearerFormat("JWT")
													.in(SecurityScheme.In.HEADER)
													.name("Authorization");
				// Security Requirement 정의
				SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");
		return new OpenAPI()
				.info(new Info().title("MOG(My Own Gym)")
				.description("MOG API 명세서")
				.version("v1.0.0")
				).addSecurityItem(securityRequirement)
				.schemaRequirement("BearerAuth", securityScheme);
	}
				
}
