package com.messias.taskmanagerapi.domain.dtos;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
    private LocalDate birthDate;
}
