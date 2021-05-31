package com.spring.voluptuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DestinationDTO {

    private Long id;
    @NotEmpty(message = "Start field can not be empty")
    private String start;

    @NotEmpty(message = "End field can not be empty")
    private String end;

    @NotEmpty(message = "Location field can not be empty")
    private String location;

}
