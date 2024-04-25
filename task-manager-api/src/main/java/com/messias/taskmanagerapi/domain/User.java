package com.messias.taskmanagerapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column(nullable = false)
    @NotBlank(message = "Name may not blank")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Surname may not blank")
    private String surname;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username may not blank")
    private String username;

    @NotBlank(message = "Password may not blank")
    @Column(nullable = false)
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Task> taskList;

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Category> categoryList;
}
