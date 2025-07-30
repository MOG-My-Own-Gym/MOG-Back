package com.project.mog.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAspectJAutoProxy
@Configuration
public class WebConfig implements WebMvcConfigurer {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/api/**")
	            .allowedOrigins("http://localhost:3000","https://myowngym.kro.kr")
	            .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
	            .allowedHeaders("*")
	            .allowCredentials(true); 
	    }
}

