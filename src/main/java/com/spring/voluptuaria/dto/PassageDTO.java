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

public class PassageDTO {

    private Long id;

    @NotEmpty(message = "Origin field can not be empty")
    private String origin;

    @NotEmpty(message = "Destination field can not be empty")
    private String destination;

    @NotEmpty(message = "Departure Date field can not be empty")
    private String departureDate;

    private String arriveDate;


}
