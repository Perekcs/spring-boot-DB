package org.example.springbootpr1;

import org.example.springbootpr1.manager.Manager;
import org.example.springbootpr1.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest
public class MigrationTest {

    @Autowired
    Manager manager;

    @Autowired
    ProductRepository productRepository;

    @Container
    private static final MySQLContainer<?> mySQL  = new MySQLContainer<>("mysql:8.0");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Test
    void columnsNumber(){
        List number = manager.getTotalAgeFromMergedData();
        assertThat(number.get(3)).isEqualTo(90);
    }
}
