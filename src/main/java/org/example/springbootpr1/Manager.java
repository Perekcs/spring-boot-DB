package org.example.springbootpr1;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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


    public String getDescriptionById(Long id){
        return (String) manager.createQuery("select product.description from Product product where product.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    public void deleteProducts(){
        manager.createQuery("delete from Product")
                .executeUpdate();
    }

    public void updateProducts(String description, Long id){
        manager.createQuery("UPDATE Product product SET product.description = :description WHERE product.id = :id")
                .setParameter("description", description)
                .setParameter("id", id)
                .executeUpdate();
    }

    public long countOfProducts(){
        return (long) manager.createQuery("SELECT COUNT(*) FROM Product p").getSingleResult();
    }

    public double averagePrice(){
        return (double) manager.createQuery("SELECT AVG(p.price) FROM Product p").getSingleResult();
    }

    public double sumOfProductsPrice(){
        return (double) manager.createQuery("SELECT SUM(p.price) FROM Product p where p.price >= 100").getSingleResult();
    }


    public List getTotalAgeFromMergedData() {
        String sqlQuery = "SELECT total_age FROM merged_data";
        Query query = manager.createNativeQuery(sqlQuery);
        return  query.getResultList();
    }


    public void cleaner(){
        if (manager.getTransaction().isActive()){
            manager.clear();
            manager.getTransaction().commit();
        }
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
