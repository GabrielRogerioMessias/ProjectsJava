package com.messias.taskmanagerapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    @OneToMany(mappedBy = "user")
    private List<Task> taskList;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Category> categoryList;
}
