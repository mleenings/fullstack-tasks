package org.example;

import org.example.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableConfigurationProperties(ApplicationProperties.class)
public class TasksApplicationApp {

    public static void main(String[] args) {
        SpringApplication.run(TasksApplicationApp.class);
    }
}
