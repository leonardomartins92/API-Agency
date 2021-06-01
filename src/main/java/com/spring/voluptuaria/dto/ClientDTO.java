package com.spring.voluptuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClientDTO {

    private Long id;

    @NotNull(message = "CPF field can not be empty")
    @Size(max = 15)
    private String cpf;

    @NotNull(message = "Name field can not be empty")
    @Size(max=200)
    private String name;

    @NotNull(message = "Phone field can not be empty")
    @Size(max=15)
    private String phone;

    @NotNull(message = "Email field can not be empty")
    @Size(max=30)
    private String email;

}
