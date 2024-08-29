package com.example.shoppingapp.services;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
       return this.productRepository.findAll();
    }
}
