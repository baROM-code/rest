package com.geekbrains.ru.springmvcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringMvcDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcDemo2Application.class, args);
    }

}
