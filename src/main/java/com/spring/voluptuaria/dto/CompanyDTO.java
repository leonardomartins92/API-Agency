package com.spring.voluptuaria.dto;

import com.spring.voluptuaria.enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CompanyDTO {

    private Long id;

    @NotEmpty(message = "CNPJ field can not be empty")
    private String cnpj;

    @NotEmpty(message = "Name field can not be empty")
    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

}
