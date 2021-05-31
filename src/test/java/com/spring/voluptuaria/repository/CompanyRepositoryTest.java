package com.spring.voluptuaria.repository;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Company;
import com.spring.voluptuaria.util.CompanyDTOCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Company Repository Test")
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private final IMapper mapper = IMapper.INSTANCE;

    @Test
    @DisplayName("Save Company with success")
    void saveCompanyWithSuccessWhenAttributesArePresent()  {
        CompanyDTO companyToSave = CompanyDTOCreator.buildCompany();
        Company companySaved = mapper.companyToModel(companyToSave);

        var savedCompany = companyRepository.save(companySaved);

        assertThat(savedCompany.getCnpj(), is(equalTo(companyToSave.getCnpj())));

    }

    @Test
    @DisplayName("Delete company with success")
    void delete_Company_comSucesso() {
        CompanyDTO companyToSave = CompanyDTOCreator.buildCompany();
        Company companySaved = mapper.companyToModel(companyToSave);

        companyRepository.delete(companySaved);
        Optional<Company> company = companyRepository.findById(companySaved.getId());

        assertThat(company, is(equalTo(Optional.empty())));

    }

    @Test
    @DisplayName("Find company by id")
    void findById_Company_WithSuccess()  {
        CompanyDTO companyToSave = CompanyDTOCreator.buildCompany();
        Company companySaved = companyRepository.save(mapper.companyToModel(companyToSave));

        Optional<Company> foundCompany = companyRepository.findById(companySaved.getId());

        assertThat(foundCompany.get(), is(equalTo(companySaved)));

    }

    @Test
    @DisplayName("Find all companies")
    void findAll_Companies_WithSuccess() {
        CompanyDTO companyToSave = CompanyDTOCreator.buildCompany();
        Company companySaved = companyRepository.save(mapper.companyToModel(companyToSave));

        List<Company> companies = companyRepository.findAll();

        assertThat(companies.get(0).getName(), is(equalTo(companySaved.getName())));

    }

}
