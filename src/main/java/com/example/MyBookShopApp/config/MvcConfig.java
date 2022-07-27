package com.example.MyBookShopApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    // переопределяем метод addResourceHandlers
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // при помощи метода  addResourceHandler указываем мэппинг к внешнему по отношению к приложению катологу
        // где содержатся подгружаемые из вне картинки которые будем использовать в качестве оболожек
        registry.addResourceHandler("/book-covers/**").addResourceLocations("file:" + uploadPath + "/");
    }
}
