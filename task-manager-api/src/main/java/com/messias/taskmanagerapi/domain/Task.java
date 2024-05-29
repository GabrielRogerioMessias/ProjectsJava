package com.messias.taskmanagerapi.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private LocalDateTime initialDateAndHours;
    private LocalDate initialDate;
    private LocalDate expectFinalDate;
    private LocalDateTime finalDateAndHours;
    private Long elapsedMinutes;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Task(String description) {
    }
}
