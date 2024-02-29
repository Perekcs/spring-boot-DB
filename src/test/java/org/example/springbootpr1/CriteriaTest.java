package org.example.springbootpr1;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.example.springbootpr1.entity.Person;
import org.example.springbootpr1.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

@SpringBootTest
public class CriteriaTest {
    @Autowired
    EntityManagerFactory factory;
    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;
    CriteriaQuery<Product> criteriaQuery;
    @BeforeEach
    void start(){
        entityManager = factory.createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Product product1 = new Product("tomato", 100, "red");
        Product product2 = new Product("orange", 200, "orange");
        Product product3 = new Product("banana", 300, "yellow");
        Product product4 = new Product("pineapple", 400, "pine yellow");

        Person person1 = new Person("Petro", true, 60);
        Person person2 = new Person("Ivan", true, 35);
        Person person3 = new Person("Olga", false, 40);

        entityManager.getTransaction().begin();
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.persist(product4);

        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.getTransaction().commit();
    }

    @Test
    void test1(){
        var query = criteriaQuery.select(criteriaQuery.from(Product.class));
        var result = entityManager.createQuery(query).getResultList();
        assertThat(result).asList().hasSize(4);
    }

    //equal to some field
    @Test
    void fieldEqual(){
        var query = criteriaQuery.where(criteriaBuilder.like(criteriaQuery.from(Product.class).get("description"),"yellow"));
        var result = entityManager.createQuery(query).getSingleResult();
        assertThat(result.getName()).isEqualTo("banana");
    }

    //search by join (not working)
    @Test
    void searchOnJoin(){
        var query = criteriaQuery.where(criteriaQuery.from(Product.class).join("Person"));
    }

    //sorting
    @Test
    void sorting(){
        var query = criteriaQuery.orderBy(criteriaBuilder.asc(criteriaQuery.from(Product.class).get("name")));
        var result = entityManager.createQuery(query).getResultList();
        assertThatList(result).isSortedAccordingTo((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
    }
}