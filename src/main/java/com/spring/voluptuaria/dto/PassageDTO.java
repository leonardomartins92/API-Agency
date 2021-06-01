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

public class PassageDTO {

    private Long id;

    @NotNull(message = "Origin field can not be empty")
    @Size(max=20)
    private String origin;

    @NotNull(message = "Destination field can not be empty")
    @Size(max=30)
    private String destination;

    @NotNull(message = "Departure Date field can not be empty")
    @Size(max=15)
    private String departureDate;

    @Size(max=15)
    private String arriveDate;


}
