package com.messias.taskmanagerapi.domain;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Task> tasksLis;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
