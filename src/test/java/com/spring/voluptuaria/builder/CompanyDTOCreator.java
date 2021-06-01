package com.spring.voluptuaria.builder;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.enums.CompanyType;

public class CompanyDTOCreator {

    public static CompanyDTO buildCompany(){
        return CompanyDTO.builder()
                .id(1L)
                .cnpj("1018723")
                .name("Gol")
                .companyType(CompanyType.HOTEL)
                .build();
    }

}
