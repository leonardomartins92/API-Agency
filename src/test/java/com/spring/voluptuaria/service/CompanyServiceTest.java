package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Company;
import com.spring.voluptuaria.repository.CompanyRepository;
import com.spring.voluptuaria.util.CompanyDTOCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@Slf4j
@DisplayName("Test Company Service")
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepositoryMock;

    private IMapper mapper = IMapper.INSTANCE;

    @DisplayName("Save company with success")
    @Test
    void saveCompanyIfAllArgumentsArePresent() throws NotFoundException {
        CompanyDTO companyPassed = CompanyDTOCreator.buildCompany();
        Company companyToBeSaved = mapper.companyToModel(companyPassed);

        BDDMockito.when(companyRepositoryMock.save(ArgumentMatchers.any(Company.class)))
                .thenReturn(companyToBeSaved);

        CompanyDTO companySaved = companyService.save(companyPassed);

        Assertions.assertEquals(companySaved.getId(), companySaved.getId());
        Assertions.assertEquals(companySaved.getName(), companySaved.getName());
    }

    @DisplayName("List all companies with success")
    @Test
    void listAll_Companies_ComSucesso(){
      List<Company> companys = List.of(mapper.companyToModel(CompanyDTOCreator.buildCompany()));
      BDDMockito.when(companyRepositoryMock.findAll())
              .thenReturn(companys);

      var listedCompanys =  companyService.findAll();
      Assertions.assertEquals(companys.get(0).getName(), listedCompanys.get(0).getName());
    }

    @Test
    @DisplayName("List Company By id")
    void getCompany_ById_WithSuccess() throws NotFoundException {
        String expectedName = CompanyDTOCreator.buildCompany().getName();

        BDDMockito.when(companyRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.companyToModel(CompanyDTOCreator.buildCompany())));

        var companye = companyService.findById(ArgumentMatchers.any());
        Assertions.assertEquals(expectedName, companye.getName());
    }



}
