package com.messias.taskmanagerapi.domain.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.messias.taskmanagerapi.domain.Category;

public record TaskDTO(
        Integer idTask,
        String description,
        LocalDateTime initialDateAndHours,
        LocalDate initialDate,
        LocalDate expectFinalDate,
        Long elapsedMinutes,
        Boolean status,
        Category category){
}
