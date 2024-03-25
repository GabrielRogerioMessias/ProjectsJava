package com.messias.taskmanagerapi.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private Integer age;

}
