package com.example.shoppingapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tb_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "Description may not blank")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Item(String description) {
    }
}
