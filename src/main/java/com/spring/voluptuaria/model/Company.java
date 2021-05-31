package com.spring.voluptuaria.model;

import com.spring.voluptuaria.enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

}
