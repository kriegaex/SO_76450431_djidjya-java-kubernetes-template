package com.djidjya.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DjidyaJavaKubernetesTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DjidyaJavaKubernetesTemplateApplication.class, args);
	}

}
