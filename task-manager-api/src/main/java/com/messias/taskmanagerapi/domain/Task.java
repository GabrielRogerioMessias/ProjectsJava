package com.messias.taskmanagerapi.domain;

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
    private LocalDateTime finalDateAndHours;
    private Long elapsedDays;
    private Long elapsedMinutes;
    private Long elapsedHours;
    private Long elapsedSeconds;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
