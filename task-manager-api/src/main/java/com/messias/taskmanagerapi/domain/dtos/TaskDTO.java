package com.messias.taskmanagerapi.domain.dtos;

import java.time.LocalDateTime;

import com.messias.taskmanagerapi.domain.Category;

public record TaskDTO(Integer id, String description, LocalDateTime initialDateAndHours, Category category) {
}
