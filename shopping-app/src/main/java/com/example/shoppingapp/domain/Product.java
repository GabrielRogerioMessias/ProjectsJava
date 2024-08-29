package com.example.shoppingapp.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;

    public Product(String description) {
    }
}
