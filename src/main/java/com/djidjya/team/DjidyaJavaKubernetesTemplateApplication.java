package com.djidjya.team;

import com.djidjya.team.service.FileService;
import com.djidjya.team.domain.Person;
import edu.ifmo.diploma.context.AnalyzerContext;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@OpenAPIDefinition(info = @Info(title = "Template commands API", version = "1.0", description = "Documentation Template Service API v1.0"))
@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class DjidyaJavaKubernetesTemplateApplication{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FileService fileService;

    @Operation(summary = "test")
    @ApiResponse(responseCode = "200", description = "Created")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("/test/jdbc")
    public List<Person> testTemplate() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Bean
    public AnalyzerContext analyzerContext() {
        return AnalyzerContext.getInstance(fileService::saveFile, Collections.emptyList());
    }

    public static void main(String[] args) {
        SpringApplication.run(DjidyaJavaKubernetesTemplateApplication.class, args);
    }
}