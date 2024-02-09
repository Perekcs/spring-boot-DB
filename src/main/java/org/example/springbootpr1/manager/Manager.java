package org.example.springbootpr1.manager;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.Transactional;
import org.example.springbootpr1.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class Manager {
    EntityManager manager;
    @PersistenceUnit
    EntityManagerFactory factory;
    @PostConstruct
    void createManager(){
         manager = factory.createEntityManager();
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
