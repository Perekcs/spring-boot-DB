package org.example.springbootpr1;

import org.example.springbootpr1.entity.Product;
import org.junit.jupiter.api.AfterEach;
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
public class JPQLTest {


    @Autowired
    Manager manager;

    @Container
    private static final MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:8.0");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @AfterEach
    void cleaner() {
        try {
            manager.cleaner();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void deleteProduct() {
        Product product = new Product();
        product.setName("tomato");

        manager.startTransaction();
        manager.writeProduct(product);
        manager.flushProduct();
        assertThat(manager.countOfProducts()).isNotNull();

        manager.deleteProducts();
        manager.flushProduct();
        manager.endTransaction();
        assertThat(manager.countOfProducts()).isEqualTo(0);
    }

    @Test
    void updateProduct() {
        Product product = new Product();
        product.setName("banana");
        product.setDescription("yellow");
        manager.startTransaction();
        manager.writeProduct(product);
        manager.flushProduct();
        assertThat(manager.readProduct(product.getId()).getDescription()).isEqualTo("yellow");
        manager.updateProducts("blue", product.getId());
        manager.flushProduct();
        manager.endTransaction();
        assertThat(manager.getDescriptionById(product.getId())).isEqualTo("blue");

    }

    @Test
    void countOfProducts(){
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.writeProduct(product2);
        manager.writeProduct(product3);
        manager.writeProduct(product4);
        manager.writeProduct(product5);
        manager.flushProduct();
        manager.endTransaction();
        assertThat(manager.countOfProducts()).isEqualTo(5);
    }

    @Test
    void averagePrice(){
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setPrice(50);
        product2.setPrice(100);
        product3.setPrice(30);
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.writeProduct(product2);
        manager.writeProduct(product3);
        manager.flushProduct();
        assertThat(manager.averagePrice()).isEqualTo(60);
        manager.endTransaction();
    }

    @Test
    void sumOfProductsPrice(){
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setPrice(100);
        product2.setPrice(120);
        product3.setPrice(30);
        manager.startTransaction();
        manager.writeProduct(product1);
        manager.writeProduct(product2);
        manager.writeProduct(product3);
        manager.flushProduct();
        assertThat(manager.sumOfProductsPrice()).isEqualTo(220);
        manager.endTransaction();
    }
}
