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
public class DestinationDTO {

    private Long id;

    @NotNull(message = "Start field can not be empty")
    @Size(max=15)
    private String start;

    @NotNull(message = "End field can not be empty")
    @Size(max=15)
    private String end;

    @NotNull(message = "Location field can not be empty")
    @Size(max=30)
    private String location;

}
