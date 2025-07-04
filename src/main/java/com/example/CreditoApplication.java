package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class CreditoApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CreditoApplication.class).run(args);
    }
}
