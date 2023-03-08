package com.djidjya.team;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(title = "Template commands API", version = "1.0", description = "Documentation Template Service API v1.0"))
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class DjidyaJavaKubernetesTemplateApplication {

	@Operation(summary = "test")
	@ApiResponse(responseCode = "200", description = "Created")
	@ApiResponse(responseCode = "401", description = "Unauthorized")
	@GetMapping("/test")
	public String test() {
		return "TEST";
	}

	public static void main(String[] args) {
		SpringApplication.run(DjidyaJavaKubernetesTemplateApplication.class, args);
	}
}