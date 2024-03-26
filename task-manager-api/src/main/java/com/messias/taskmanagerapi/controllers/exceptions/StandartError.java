package com.messias.taskmanagerapi.controllers.exceptions;

import lombok.*;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandartError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
