package com.youyi.ecom;

import com.youyi.ecom.config.GiteeConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication
public class EcomApp implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EcomApp.class);

    @Autowired
    private GiteeConfigProperties giteeConfigProperties;

    public static void main(String[] args) {
        SpringApplication.run(EcomApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("database: http://localhost:8110/h2");
        LOGGER.info("code-url: {}", giteeConfigProperties.getCodeUrl());
    }
}
