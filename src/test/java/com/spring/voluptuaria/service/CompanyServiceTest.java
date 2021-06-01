package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Company;
import com.spring.voluptuaria.repository.CompanyRepository;
import com.spring.voluptuaria.builder.CompanyDTOCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Test Company Service")
@ExtendWith(SpringExtension.class)
class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepositoryMock;

    private final IMapper mapper = IMapper.INSTANCE;

    @DisplayName("Save company with success")
    @Test
    void saveCompanyIfAllArgumentsArePresent() throws NotFoundException {
        CompanyDTO companyPassed = CompanyDTOCreator.buildCompany();
        Company companyToBeSaved = mapper.companyToModel(companyPassed);

        when(companyRepositoryMock.save(ArgumentMatchers.any(Company.class)))
                .thenReturn(companyToBeSaved);

        CompanyDTO companySaved = companyService.save(companyPassed);

        assertThat(companySaved.getId(), is(equalTo(companySaved.getId())));
        assertThat(companySaved.getName(), is(equalTo(companySaved.getName())));

    }

    @DisplayName("List all companies with success")
    @Test
    void listAll_Companies_ComSucesso(){
      List<Company> companys = List.of(mapper.companyToModel(CompanyDTOCreator.buildCompany()));

      when(companyRepositoryMock.findAll())
              .thenReturn(companys);

      var listedCompanys =  companyService.findAll();

      assertThat(listedCompanys.get(0).getName(),is(equalTo(companys.get(0).getName())));

    }

    @Test
    @DisplayName("List Company By id")
    void getCompany_ById_WithSuccess() throws NotFoundException {
        String expectedName = CompanyDTOCreator.buildCompany().getName();

        when(companyRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.companyToModel(CompanyDTOCreator.buildCompany())));

        var companye = companyService.findById(ArgumentMatchers.any());

        assertThat(companye.getName(),is(equalTo(expectedName)));

    }

    @Test
    @DisplayName("Not Found Company With Id")
    void companyNotFoundWithID_ThrownException()  {

        assertThrows(NotFoundException.class, ()-> companyService.findById(ArgumentMatchers.any()));
    }

    @Test
    @DisplayName("Delete Company with a valid Id")
    void deleteCompanyWhenAValidIdIsPassed() throws NotFoundException {

        Company expectedDeletedCompany = mapper.companyToModel(CompanyDTOCreator.buildCompany());

        when(companyRepositoryMock.findById(expectedDeletedCompany.getId()))
                .thenReturn(Optional.of(expectedDeletedCompany));

        companyService.delete(expectedDeletedCompany.getId());

        verify(companyRepositoryMock, times(1)).findById(expectedDeletedCompany.getId());
        verify(companyRepositoryMock, times(1)).deleteById(expectedDeletedCompany.getId());
    }

}
