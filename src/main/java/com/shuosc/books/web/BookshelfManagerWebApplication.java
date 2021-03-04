package com.shuosc.books.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
public class BookshelfManagerWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookshelfManagerWebApplication.class, args);
    }
}
