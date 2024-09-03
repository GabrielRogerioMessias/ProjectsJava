package com.example.shoppingapp.services;

import com.example.shoppingapp.domain.Product;
import com.example.shoppingapp.repositories.ProductRepository;
import com.example.shoppingapp.services.exceptions.NullEntityFieldException;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Validator validator;

    public ProductService(ProductRepository productRepository, Validator validator) {
        this.productRepository = productRepository;
        this.validator = validator;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    private List<String> validateProduct(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        return violations.stream().map((v) -> v.getPropertyPath().toString()).toList();
    }

    public Product insertProduct(Product product) {
        try {
            return this.productRepository.save(product);
        } catch (TransactionSystemException exception) {
            List<String> errors;
            errors = this.validateProduct(product);
            throw new NullEntityFieldException(errors);
        }

    }
}
