package com.youyi.ecom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EcomApp implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EcomApp.class);

    public static void main(String[] args) {
        SpringApplication.run(EcomApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("http://localhost:8110/h2");
    }
}
