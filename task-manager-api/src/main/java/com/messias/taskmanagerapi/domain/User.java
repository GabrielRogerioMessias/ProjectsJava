package com.messias.taskmanagerapi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    private String password;
    private Integer age;
}
