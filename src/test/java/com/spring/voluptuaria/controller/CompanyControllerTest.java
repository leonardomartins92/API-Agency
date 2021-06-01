package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.builder.CompanyDTOCreator;
import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

import static com.spring.voluptuaria.util.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Company Controller test")
@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    private static final String COMPANY_API_URL_PATH = "/api/v1/companies";
    private static final long INVALID_COMPANY_ID = 2L;

    private MockMvc mockMvc;

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyServiceMock;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(companyController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    @DisplayName("Save company when all required attributes are passed ")
    void when_PostIsCalled_Then_SaveCompany() throws Exception {
        CompanyDTO companyDTO = CompanyDTOCreator.buildCompany();

        when(companyServiceMock.save(companyDTO))
                .thenReturn(companyDTO);

        mockMvc.perform(post(COMPANY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(companyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(companyDTO.getName())))
                .andExpect(jsonPath("$.cnpj", is(companyDTO.getCnpj())));
    }

    @Test
    @DisplayName("Try to save Company without all required fields throw a message error")
    void saveCompanyWithoutAllRequiredFields_Error() throws Exception {

        CompanyDTO companyDTO = new CompanyDTO();

        mockMvc.perform(post(COMPANY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(companyDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("List All companys with success")
    @Test
    void listAllCompanys_WithSuccess() throws Exception {
       List<CompanyDTO> companies = List.of(CompanyDTOCreator.buildCompany());

        when(companyServiceMock.findAll())
                .thenReturn(companies);

        mockMvc.perform(MockMvcRequestBuilders.get(COMPANY_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].name", is(companies.get(0).getName())))
                .andExpect(jsonPath("$[0].cnpj", is(companies.get(0).getCnpj())));

    }

    @Test
    @DisplayName("List Company By id with success")
    void getCompanyById_WithSuccess() throws Exception {
        CompanyDTO company = CompanyDTOCreator.buildCompany();

        when(companyServiceMock.findById(1L))
                .thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.get(COMPANY_API_URL_PATH + "/" + company.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name", is(company.getName())))
                .andExpect(jsonPath("$.cnpj", is(company.getCnpj())));

    }

    @Test
    @DisplayName("List Company with invalid id throw Exception")
    void getCompanyWithInvalidId_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(companyServiceMock).findById(INVALID_COMPANY_ID);

        mockMvc.perform(MockMvcRequestBuilders.get(COMPANY_API_URL_PATH + "/" + INVALID_COMPANY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Company with success")
    void deleteCompany_WithSuccess() throws Exception {
        CompanyDTO company = CompanyDTOCreator.buildCompany();

        doNothing().when(companyServiceMock).delete(company.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(COMPANY_API_URL_PATH + "/" + company.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Try to Delete Invalid Company throw Exception")
    void deleteInvalidCompany_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(companyServiceMock).delete(INVALID_COMPANY_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(COMPANY_API_URL_PATH + "/" + INVALID_COMPANY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
