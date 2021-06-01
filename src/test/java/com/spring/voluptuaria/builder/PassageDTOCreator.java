package com.spring.voluptuaria.builder;


import com.spring.voluptuaria.dto.PassageDTO;

public class PassageDTOCreator {

    public static PassageDTO buildPassage(){
        return PassageDTO.builder()
                .id(1L)
                .origin("Rio de Janeiro")
                .destination("Dublin")
                .departureDate("2021-02-01")
                .arriveDate("2021-03-01")
                .build();
    }

}
