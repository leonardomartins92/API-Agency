package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.builder.PassageDTOCreator;
import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.PassageService;
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

@DisplayName("Passage Controller test")
@ExtendWith(MockitoExtension.class)
class PassageControllerTest {

    private static final String PASSAGE_API_URL_PATH = "/api/v1/passages";
    private static final long INVALID_PASSAGE_ID = 2L;

    private MockMvc mockMvc;

    @InjectMocks
    private PassageController passageController;

    @Mock
    private PassageService passageServiceMock;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(passageController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    @DisplayName("Save passage when all required attributes are passed ")
    void when_PostIsCalled_Then_SavePassage() throws Exception {
        PassageDTO passageDTO = PassageDTOCreator.buildPassage();

        when(passageServiceMock.save(passageDTO))
                .thenReturn(passageDTO);

        mockMvc.perform(post(PASSAGE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(passageDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination", is(passageDTO.getDestination())))
                .andExpect(jsonPath("$.id", is(passageDTO.getId().intValue())));
    }

    @Test
    @DisplayName("Try to save Passage without all required fields throw a message error")
    void savePassageWithoutAllRequiredFields_Error() throws Exception {

        PassageDTO passageDTO = new PassageDTO();

        mockMvc.perform(post(PASSAGE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(passageDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("List All passages with success")
    @Test
    void listAllPassages_WithSuccess() throws Exception {
       List<PassageDTO> passages = List.of(PassageDTOCreator.buildPassage());

        when(passageServiceMock.findAll())
                .thenReturn(passages);

        mockMvc.perform(MockMvcRequestBuilders.get(PASSAGE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].destination", is(passages.get(0).getDestination())))
                .andExpect(jsonPath("$[0].id", is(passages.get(0).getId().intValue())));

    }

    @Test
    @DisplayName("List Passage By id with success")
    void getPassageById_WithSuccess() throws Exception {
        PassageDTO passage = PassageDTOCreator.buildPassage();

        when(passageServiceMock.findById(1L))
                .thenReturn(passage);

        mockMvc.perform(MockMvcRequestBuilders.get(PASSAGE_API_URL_PATH + "/" + passage.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.destination", is(passage.getDestination())))
                .andExpect(jsonPath("$.id", is(passage.getId().intValue())));

    }

    @Test
    @DisplayName("List Passage with invalid id throw Exception")
    void getPassageWithInvalidId_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(passageServiceMock).findById(INVALID_PASSAGE_ID);

        mockMvc.perform(MockMvcRequestBuilders.get(PASSAGE_API_URL_PATH + "/" + INVALID_PASSAGE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Passage with success")
    void deletePassage_WithSuccess() throws Exception {
        PassageDTO passage = PassageDTOCreator.buildPassage();

        doNothing().when(passageServiceMock).delete(passage.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(PASSAGE_API_URL_PATH + "/" + passage.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Try to Delete Invalid Passage throw Exception")
    void deleteInvalidPassage_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(passageServiceMock).delete(INVALID_PASSAGE_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(PASSAGE_API_URL_PATH + "/" + INVALID_PASSAGE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
