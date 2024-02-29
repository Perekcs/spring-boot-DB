package org.example.springbootpr1;

import org.example.springbootpr1.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class TransactionalTest {

    @Autowired
    Transaction transaction;

    @Container
    private static final MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:8.0");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }


    @Test
    void addProductFailTransaction(){
        Product product = new Product();
        try{
            transaction.buyProductTransaction(product, true);
        }
        catch (RuntimeException ignored){}
        assertThat(transaction.findProduct(product)).isFalse();
    }

    @Test
    void addProductOKTransaction(){
        Product product = new Product();
        transaction.buyProductTransaction(product, false);
        assertThat(transaction.findProduct(product)).isTrue();
    }

    @Test
    void addProductFailTemplate(){
        Product product = new Product();
        try{
            transaction.buyProductTransactionTemplate(product, true);
        }
        catch (RuntimeException ignored){}
        assertThat(transaction.findProduct(product)).isFalse();
    }

    @Test
    void addProductOKTemplate(){
        Product product = new Product();
        transaction.buyProductTransactionTemplate(product, false);
        assertThat(transaction.findProduct(product)).isTrue();
    }
}
