package com.spring.voluptuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClientDTO {

    private Long id;

    @NotEmpty(message = "CPF field can not be empty")
    private String cpf;

    @NotEmpty(message = "Name field can not be empty")
    private String name;

    @NotEmpty(message = "Phone field can not be empty")
    private String phone;

    @NotEmpty(message = "Email field can not be empty")
    private String email;

}
