package com.ctseducare.taskbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI openAPI() {
		Contact contact = new Contact();
		contact.setName("Eros Vitor Bornatowski");
		contact.setEmail("erosborna@gmail.com");
		contact.setUrl("https://github.com/erosvitor");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info()
				.title("API para gerenciamento de tarefas")
				.description("Esta API disponibiliza recursos para gerenciamento de tarefas.")
				.version("1.0")
				.contact(contact)
				.termsOfService("https://www.ctseducare.com/terms")
				.license(mitLicense);

		return new OpenAPI().info(info);
	}

}
