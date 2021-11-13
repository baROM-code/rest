package com.geekbrains.ru.springmvcdemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    // http://localhost:8080/data/images/{id}/image1.png
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "data";
        String staticPath = Paths.get(System.getProperty("user.dir"), dirName).toFile().getAbsolutePath();

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/"+  staticPath + "/");
    }

}
