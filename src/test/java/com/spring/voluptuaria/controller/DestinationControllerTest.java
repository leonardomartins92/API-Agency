package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.builder.DestinationDTOCreator;
import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.DestinationService;
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

@DisplayName("Destination Controller test")
@ExtendWith(MockitoExtension.class)
class DestinationControllerTest {

    private static final String DESTINATION_API_URL_PATH = "/api/v1/destinations";
    private static final long INVALID_DESTINATION_ID = 2L;

    private MockMvc mockMvc;

    @InjectMocks
    private DestinationController destinationController;

    @Mock
    private DestinationService destinationServiceMock;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(destinationController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    @DisplayName("Save destination when all required attributes are passed ")
    void when_PostIsCalled_Then_SaveDestination() throws Exception {
        DestinationDTO destinationDTO = DestinationDTOCreator.buildDestination();

        when(destinationServiceMock.save(destinationDTO))
                .thenReturn(destinationDTO);

        mockMvc.perform(post(DESTINATION_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(destinationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.location", is(destinationDTO.getLocation())))
                .andExpect(jsonPath("$.id", is(destinationDTO.getId().intValue())));
    }

    @Test
    @DisplayName("Try to save Destination without all required fields throw a message error")
    void saveDestinationWithoutAllRequiredFields_Error() throws Exception {

        DestinationDTO destinationDTO = new DestinationDTO();

        mockMvc.perform(post(DESTINATION_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(destinationDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("List All destinations with success")
    @Test
    void listAllDestinations_WithSuccess() throws Exception {
       List<DestinationDTO> destinations = List.of(DestinationDTOCreator.buildDestination());

        when(destinationServiceMock.findAll())
                .thenReturn(destinations);

        mockMvc.perform(MockMvcRequestBuilders.get(DESTINATION_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].location", is(destinations.get(0).getLocation())))
                .andExpect(jsonPath("$[0].id", is(destinations.get(0).getId().intValue())));

    }

    @Test
    @DisplayName("List Destination By id with success")
    void getDestinationById_WithSuccess() throws Exception {
        DestinationDTO destination = DestinationDTOCreator.buildDestination();

        when(destinationServiceMock.findById(1L))
                .thenReturn(destination);

        mockMvc.perform(MockMvcRequestBuilders.get(DESTINATION_API_URL_PATH + "/" + destination.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.location", is(destination.getLocation())))
                .andExpect(jsonPath("$.id", is(destination.getId().intValue())));

    }

    @Test
    @DisplayName("List Destination with invalid id throw Exception")
    void getDestinationWithInvalidId_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(destinationServiceMock).findById(INVALID_DESTINATION_ID);

        mockMvc.perform(MockMvcRequestBuilders.get(DESTINATION_API_URL_PATH + "/" + INVALID_DESTINATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Destination with success")
    void deleteDestination_WithSuccess() throws Exception {
        DestinationDTO destination = DestinationDTOCreator.buildDestination();

        doNothing().when(destinationServiceMock).delete(destination.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(DESTINATION_API_URL_PATH + "/" + destination.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Try to Delete Invalid Destination throw Exception")
    void deleteInvalidDestination_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(destinationServiceMock).delete(INVALID_DESTINATION_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(DESTINATION_API_URL_PATH + "/" + INVALID_DESTINATION_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
