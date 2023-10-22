package com.example.authenticarionjwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double valor;
    private String motivo;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
