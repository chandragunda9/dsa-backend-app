package com.learning.dsa_backend_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DsaBackendAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DsaBackendAppApplication.class, args);
	}

}
