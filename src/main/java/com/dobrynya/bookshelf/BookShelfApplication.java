package com.dobrynya.bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BookShelfApplication {

    public static void main(String[] args) {
//		SpringApplication.run(BookShelfApplication.class, args);
        ApplicationContext context = SpringApplication.run(BookShelfApplication.class);
        System.out.println("Ответ " + context.getBeanDefinitionCount());
    }
}
