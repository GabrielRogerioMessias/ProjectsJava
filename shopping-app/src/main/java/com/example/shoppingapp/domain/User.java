package com.example.shoppingapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "name may not blank")
    private String name;
    @Column(nullable = false)
    @NotBlank(message = "email may not blank")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "password may not blank")
    private String password;
    private Instant createdAt;
}
