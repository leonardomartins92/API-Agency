package com.spring.voluptuaria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    @Column(nullable = false)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    @Column(nullable = false)
    private String location;


}
