package org.example.springbootpr1.service;

import lombok.RequiredArgsConstructor;
import org.example.springbootpr1.entity.Product;
import org.example.springbootpr1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    void createProduct(){
        Product product = new Product();
        productRepository.save(product);
    }
}
