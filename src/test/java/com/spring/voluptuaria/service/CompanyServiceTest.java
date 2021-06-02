package com.spring.voluptuaria.service;

import com.spring.voluptuaria.builder.CompanyDTOCreator;
import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Company;
import com.spring.voluptuaria.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private static final long INVALID_COMPANY_ID = 2L;

    @DisplayName("Save company with success")
    @Test
    void saveCompanyIfAllArgumentsArePresent_WithSuccess() throws NotFoundException {
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
    void listAllCompanies_WithSuccess(){
      List<Company> companys = List.of(mapper.companyToModel(CompanyDTOCreator.buildCompany()));

      when(companyRepositoryMock.findAll())
              .thenReturn(companys);

      var listedCompanys =  companyService.findAll();

      assertThat(listedCompanys.get(0).getName(),is(equalTo(companys.get(0).getName())));

    }

    @Test
    @DisplayName("List Company with a valid id")
    void getCompanyById_WithSuccess() throws NotFoundException {
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
    @DisplayName("Update Company with a valid id")
    void updateCompanyWithAValidId_WithSuccess() throws NotFoundException {
        CompanyDTO companySaved = CompanyDTOCreator.buildCompany();
        CompanyDTO companyToUpdate = companySaved;
        companyToUpdate.setCnpj("9999");

        when(companyRepositoryMock.findById(companySaved.getId()))
                .thenReturn(Optional.of(mapper.companyToModel(companySaved)));

        when(companyRepositoryMock.save(mapper.companyToModel(companyToUpdate)))
                .thenReturn(mapper.companyToModel(companyToUpdate));

        CompanyDTO companyUpdated = companyService.update(companyToUpdate);

        assertThat(companyToUpdate.getCnpj(), is(equalTo(companyUpdated.getCnpj())));
        assertThat(companyToUpdate.getId(), is(equalTo(companyUpdated.getId())));
    }

    @Test
    @DisplayName("Update Company with a invalid id throws an exception")
    void updateCompanyWithInvalidId_ThrowException() {
        CompanyDTO companyToUpdate = CompanyDTOCreator.buildCompany();
        companyToUpdate.setId(INVALID_COMPANY_ID);

        when(companyRepositoryMock.findById(companyToUpdate.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class , ()-> companyService.findById(INVALID_COMPANY_ID));
    }

    @Test
    @DisplayName("Delete Company with a valid Id")
    void deleteCompanyWhenAValidIdIsPassed_WithSuccess() throws NotFoundException {

        Company expectedDeletedCompany = mapper.companyToModel(CompanyDTOCreator.buildCompany());

        when(companyRepositoryMock.findById(expectedDeletedCompany.getId()))
                .thenReturn(Optional.of(expectedDeletedCompany));

        companyService.delete(expectedDeletedCompany.getId());

        verify(companyRepositoryMock, times(1)).findById(expectedDeletedCompany.getId());
        verify(companyRepositoryMock, times(1)).deleteById(expectedDeletedCompany.getId());
    }

}
