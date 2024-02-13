package org.example.springbootpr1.manager;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import org.example.springbootpr1.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Manager {
    EntityManager manager;
    @PersistenceUnit
    EntityManagerFactory factory;
    @PostConstruct
    void createManager(){
         manager = factory.createEntityManager();
    }

    public List<Product> getAll(){
        return manager.createQuery("select product from Product product", Product.class).getResultList();
    }

    public List<Product> getFromName(String name){

        return manager.createQuery("select product from Product product where product.name = :name", Product.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List getTotalAgeFromMergedData() {
        String sqlQuery = "SELECT total_age FROM merged_data";
        Query query = manager.createNativeQuery(sqlQuery);
        return  query.getResultList();
    }


    public void after(){
        manager.getTransaction().begin();
        manager.clear();
        manager.getTransaction().commit();
    }

    public void writeProduct(Product product){
        manager.persist(product);
    }
    public Product readProduct(long id){
        return manager.find(Product.class, id);
    }
    public void startTransaction(){
        manager.getTransaction().begin();
    }
    public void endTransaction(){
        manager.getTransaction().commit();
    }
    public void mergeProduct(Product product){
        manager.merge(product);
    }
    public void removeProduct(Product product){
        manager.remove(product);
    }
    public void detachProduct(Product product){
        manager.detach(product);
    }
    public void refreshProduct(Product product){
        manager.refresh(product);
    }
    public void flushProduct(){
        manager.flush();
    }


}
