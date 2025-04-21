package com.example.zoo.infrastructure.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ZooConsoleRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("Zoo Management System is running in console mode.");
        System.out.println("Use commands like 'animal add', 'enclosure list', etc.");
    }
}