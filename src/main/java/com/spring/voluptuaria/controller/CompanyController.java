package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path = "/api/v1/companies")
@RestController
public class CompanyController {
    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<CompanyDTO> listCompanys(){
        return companyService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/{cod}")
    public CompanyDTO listCompanyById(@PathVariable Long cod ) throws NotFoundException {
        return companyService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompanyDTO saveCompany(@RequestBody @Valid CompanyDTO companyDTO) throws NotFoundException {
        return companyService.save(companyDTO);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{id}")
    public CompanyDTO updateCompany(@RequestBody @Valid CompanyDTO companyDTO, @PathVariable Long id) throws NotFoundException {
        companyDTO.setId(id);
        return companyService.update(companyDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteCompany(@PathVariable Long id) throws NotFoundException {
        companyService.delete(id);
    }
}







