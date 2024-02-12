package org.example.springbootpr1;

import org.example.springbootpr1.entity.Product;
import org.example.springbootpr1.manager.Manager;
import org.junit.jupiter.api.BeforeEach;
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
public class JPQLTest {



    @Autowired
    Manager manager;

    @Container
    private static final MySQLContainer<?> mySQL  = new MySQLContainer<>("mysql:8.0");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeEach
    void after(){
        manager.after();
    }

    @Test
    void allProduct(){
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.writeProduct(product2);
        manager.writeProduct(product3);
        manager.flushProduct();
        manager.endTransaction();
        List<Product> products = manager.getAll();
        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void productsWithName(){
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setName("Tomato");
        product2.setName("Tomato");
        product3.setName("Banana");
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.writeProduct(product2);
        manager.writeProduct(product3);
        manager.flushProduct();
        manager.endTransaction();
        List<Product> products = manager.getFromName("Tomato");
        assertThat(products.size()).isEqualTo(2);
    }
}
