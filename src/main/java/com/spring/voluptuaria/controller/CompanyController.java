package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class CompanyController {
    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/pesquisaEmpresas")
    public List<CompanyDTO> listCompanys(){
        return companyService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/pesquisaEmpresa/{cod}")
    public CompanyDTO listCompanyById(@PathVariable Long cod ) throws NotFoundException {
        return companyService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/manterEmpresa")
    public CompanyDTO saveCompany(@RequestBody @Valid CompanyDTO companyDTO) throws NotFoundException {
        return companyService.save(companyDTO);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/manterEmpresa/{id}")
    public void deleteCompany(@PathVariable Long id) throws NotFoundException {
        companyService.delete(companyService.findById(id));
    }
}







