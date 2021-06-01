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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    private Long id;

    @NotNull(message = "CNPJ field can not be empty")
    @Size(max= 20)
    private String cnpj;

    @NotNull(message = "Name field can not be empty")
    @Size(max = 40)
    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

}
