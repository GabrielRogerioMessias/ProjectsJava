package com.messias.taskmanagerapi.domain.dtos.securityDTOs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDTO {
    private String username;
    private String password;
}

