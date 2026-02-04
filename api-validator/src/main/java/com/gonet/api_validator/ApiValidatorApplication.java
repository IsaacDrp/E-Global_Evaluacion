package com.gonet.api_validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // Añadir esto

@SpringBootApplication
@EnableFeignClients // Anotacion para Feign
public class ApiValidatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiValidatorApplication.class, args);
	}
}