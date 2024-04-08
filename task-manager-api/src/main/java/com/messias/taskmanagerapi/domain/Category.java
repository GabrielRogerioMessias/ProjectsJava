package com.messias.taskmanagerapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "category")
    private List<Task> tasksList;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
