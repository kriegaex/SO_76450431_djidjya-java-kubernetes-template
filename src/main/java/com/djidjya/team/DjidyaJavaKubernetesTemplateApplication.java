package com.djidjya.team;

import com.djidjya.team.domain.Person;
import edu.ifmo.diploma.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SpringBootApplication
@RestController
public class DjidyaJavaKubernetesTemplateApplication{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test/jdbc")
    public List<Person> testTemplate() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Bean
    public AnalyzerContext analyzerContext() {
        return AnalyzerContext.getInstance(System.out::println, Collections.emptyList());
    }

    public static void main(String[] args) {
        SpringApplication.run(DjidyaJavaKubernetesTemplateApplication.class, args);
    }
}