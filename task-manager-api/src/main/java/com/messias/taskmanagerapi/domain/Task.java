package com.messias.taskmanagerapi.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private LocalDateTime initialDateAndHours;
    private LocalDateTime finalDateAndHours;
    private LocalDateTime elapsedTime;
    private  Long elapsedDays;
    private Long elapsedMinutes;
    private Long elapsedSeconds;


}
