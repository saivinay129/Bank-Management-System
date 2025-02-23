package com.bank_application_system.bank_system_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EntityScan(basePackages = "com.bank_application_system.entity")
@EnableJpaRepositories(basePackages = "com.bank_application_system.repository")
@ComponentScan(basePackages = "com.bank_application_system")
@OpenAPIDefinition(
	info = @Info(
		title = "The Java Bank App",
		description = "Backend Rest API for java bank",
		version = "V1.0",
		contact = @Contact(
			name = "sai vinay",
			email = "saivinay1209@gmail.com",
			url = "https://github.com/saivinay129/bank_management_system"
		),
		license = @License(
			name = "The Java Coding",
			url = "https://github.com/saivinay129/bank_management_system"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "The Java Coding Bank App Documentation",
		url = "https://github.com/saivinay129/bank_management_system"
	)
)
public class BankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSystemApplication.class, args);
	}

}
