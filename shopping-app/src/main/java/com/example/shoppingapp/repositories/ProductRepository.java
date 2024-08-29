package com.example.shoppingapp.repositories;

import com.example.shoppingapp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
