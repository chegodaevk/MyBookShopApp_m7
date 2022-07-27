package com.example.MyBookShopApp.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringfoxConfig {

    // создаем Bean от Springfox
    // возвращаем Docker у которого вызываем метод select для того чтобы выбрать ApiSelectorBuilder (объект который строит документацию)
    // в методе apis можем указать класы из которых Springfox должен брать endpoints для документирования (в basePackage
    // указывается пакет находящиеся в котором классы будут учитываться в документации basePackage("com.example.MyBookShopApp.controllers")
    // помимо basePackage строить документацию можно основываясь на соответствии классов контроллеров например если они анотированы какой нибудь определённой анотацией
    // метод path необходим для обозначения методов из классов которые мы будем воспринимать в качестве документированных (методе ant определям соответствие
    // при котором в докоментацию будут попадать только те методы в endpoint которых есть приставка api)
    // метод build завенршает построение конфигурации
    // добавлем ApiInfo после метода build
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).
//                paths(PathSelectors.ant("/api/*")).
                paths(PathSelectors.any()).
                build().
                apiInfo(apiInfo());
    }

    // редактируем сформированную документацию добавив объкт ApiInfo где укажем информацию отображающуюся в шапке документации
    // метод возвращает экземпляр класса ApiInfo  где в качестве аргумента передаются: заготовок документации, описание,
    // версия API, URL для условий пользования, контактные данные описываемые объектом Contact (имя взадельца API, URL на его личную страницу, email)
    // название лицензии, URL лицензии, различные расширения вендора (таковых пока нет поэтому просто передаем пустой ArrayList)
    public ApiInfo apiInfo(){
        return new ApiInfo(
                "Bookshop API",
                "API for bookstore",
                "1.0",
                "http://www.termsofservice.org",
                new Contact("API owner", "http://www.ownersite.com", "owner@rmailer.org"),
                "api_license",
                "http://www.license.edu.org",
                new ArrayList<>());
    }
}
