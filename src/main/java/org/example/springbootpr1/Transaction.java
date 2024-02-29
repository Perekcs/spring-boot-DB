package org.example.springbootpr1;


import org.example.springbootpr1.entity.Product;
import org.example.springbootpr1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class Transaction {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    void buyProduct(Product product){
        productRepository.save(product);
    }

    boolean findProduct(Product product){
        return productRepository.findById(product.getId()).isPresent();
    }

    @Transactional
    void buyProductTransaction(Product product,boolean error){
        buyProduct(product);
        if(error){
            throw new RuntimeException();
        }
    }

    void buyProductTransactionTemplate(Product product,boolean error){
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(status -> {
            buyProduct(product);
            if(error){
                throw new RuntimeException();
            }
            return 0;
        });
    }

//    void buyProductEntityManager(Product product, boolean error){
//        manager.get
//    }
}
