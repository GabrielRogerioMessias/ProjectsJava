package com.messias.taskmanagerapi.domain.dtos;

import java.time.LocalDateTime;

public record TaskDTO(Integer id, String description, LocalDateTime initialDateAndHours) {
}
