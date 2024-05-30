package com.messias.taskmanagerapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @NonNull
    private String description;

    public Category(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Category(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Task> tasksList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user")
    private User user;


}
