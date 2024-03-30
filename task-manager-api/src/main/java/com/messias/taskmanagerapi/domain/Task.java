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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;
    private LocalDateTime initialDateAndHours;
    private LocalDateTime finalDateAndHours;
    private LocalDateTime elapsedTime;
    private Long elapsedDays;
    private Long elapsedMinutes;
    private Long elapsedSeconds;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;


}
