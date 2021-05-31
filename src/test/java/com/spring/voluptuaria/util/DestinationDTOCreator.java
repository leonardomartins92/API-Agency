package com.spring.voluptuaria.util;

import com.spring.voluptuaria.dto.DestinationDTO;

public class DestinationDTOCreator {

    public static DestinationDTO buildDestination(){
        return DestinationDTO.builder()
                .id(1L)
                .start("2022-02-01")
                .end("2022-03-01")
                .location("Barcelona")
                .build();
    }

}
