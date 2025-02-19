package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.config.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
@ComponentScan(basePackages = "ru.otus.hw")
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class,args);
        System.out.println("finish");
    }
}