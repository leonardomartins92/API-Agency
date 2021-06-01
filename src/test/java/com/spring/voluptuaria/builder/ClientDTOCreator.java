package com.spring.voluptuaria.builder;

import com.spring.voluptuaria.dto.ClientDTO;

public class ClientDTOCreator {

   public static ClientDTO buildClient(){
        return ClientDTO.builder()
                .id(1L)
                .cpf("1018723")
                .name("Leo")
                .phone("32231")
                .email("leo@email")
                .build();
    }

}
