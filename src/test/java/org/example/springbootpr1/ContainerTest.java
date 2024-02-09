package org.example.springbootpr1;

import jakarta.persistence.EntityManager;
import org.example.springbootpr1.entity.Product;
import org.example.springbootpr1.manager.Manager;
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
public class ContainerTest {

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

    @Test
    void writeProduct(){
        Product product1 = new Product();
        product1.setName("banana");
        manager.writeProduct(product1);
        assertThat(manager.readProduct(product1.getId()).getName()).isEqualTo(product1.getName());
    }

    @Test
    void mergeProduct(){
        Product product1 = new Product();
        product1.setName("tomato");
        product1.setDescription("red");
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.flushProduct();
        assertThat(manager.readProduct(product1.getId()).getDescription()).isEqualTo("red");
        manager.detachProduct(product1);
        product1.setDescription("yellow");
        assertThat(manager.readProduct(product1.getId()).getDescription()).isEqualTo("red");
        manager.mergeProduct(product1);
        assertThat(manager.readProduct(product1.getId()).getDescription()).isEqualTo("yellow");
        manager.endTransaction();
    }

    @Test
    void refreshProduct(){
        Product product1 = new Product();
        product1.setName("tomato");
        product1.setDescription("red");
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.flushProduct();
        manager.detachProduct(product1);
        product1.setDescription("yellow");
        assertThat(manager.readProduct(product1.getId()).getDescription()).isEqualTo("red");
        manager.refreshProduct(product1);
        manager.endTransaction();
        assertThat(product1.getDescription()).isEqualTo("red");
    }

    @Test
    void deleteProduct(){
        Product product = new Product();
        product.setName("orange");
        manager.startTransaction();
        manager.writeProduct(product);
        //assertThat(manager.readProduct(product.getId())).isNull();
        manager.removeProduct(product);
        assertThat(manager.readProduct(product.getId())).isNull();
        manager.endTransaction();
    }

}
