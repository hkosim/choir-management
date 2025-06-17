package com.hk.personal.choir_management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String url;

    @Override
    public void run(String... args) {
        System.getenv().forEach((k, v) -> {
            if (k.toLowerCase().contains("datasource")) {
                System.out.println(k + " = " + v);
            }
        });
        System.out.println("🔍 Datasource URL on startup: " + url);
    }
}