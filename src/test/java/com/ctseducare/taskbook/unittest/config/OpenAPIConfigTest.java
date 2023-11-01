package com.ctseducare.taskbook.unittest.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.ctseducare.taskbook.config.OpenAPIConfig;

import io.swagger.v3.oas.models.OpenAPI;

@TestInstance(Lifecycle.PER_CLASS)
public class OpenAPIConfigTest {
	
	@Test
	void testOpenAPI() {
		OpenAPIConfig openAPIConfig = new OpenAPIConfig();
		OpenAPI openAPI = openAPIConfig.openAPI();
		
		assertEquals("API para gerenciamento de tarefas", openAPI.getInfo().getTitle());
		assertEquals("Esta API disponibiliza recursos para gerenciamento de tarefas.", openAPI.getInfo().getDescription());
		assertEquals("1.0", openAPI.getInfo().getVersion());
		
		assertEquals("Eros Vitor Bornatowski", openAPI.getInfo().getContact().getName());
		assertEquals("erosborna@gmail.com", openAPI.getInfo().getContact().getEmail());
		assertEquals("https://github.com/erosvitor", openAPI.getInfo().getContact().getUrl());
	}

}
