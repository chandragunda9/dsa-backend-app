package com.learning.dsa_backend_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.learning.dsa_backend_app.service.S3Service;

@SpringBootApplication
public class DsaBackendAppApplication {
	
	@Autowired
	S3Service cacheService;

	public static void main(String[] args) {
		SpringApplication.run(DsaBackendAppApplication.class, args);
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://dsa-codes.s3-website.ap-south-1.amazonaws.com/");
			}
		};
	}
}
