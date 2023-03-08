package com.djidjya.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class DjidyaJavaKubernetesTemplateApplication {

	@GetMapping("/test")
	public String test() {
		return "TEST";
	}

	public static void main(String[] args) {
		SpringApplication.run(DjidyaJavaKubernetesTemplateApplication.class, args);
	}
}