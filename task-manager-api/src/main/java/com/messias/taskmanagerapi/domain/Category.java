package com.messias.taskmanagerapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Task> tasksLis;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user")
    private User user;

}
