package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.builder.ClientDTOCreator;
import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.ClientService;
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

@DisplayName("Client Controller test")
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private static final String CLIENT_API_URL_PATH = "/api/v1/clients";
    private static final long INVALID_CLIENT_ID = 2L;

    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientServiceMock;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    @DisplayName("Save client when all required attributes are passed ")
    void when_PostIsCalled_Then_SaveClient() throws Exception {
        ClientDTO clientDTO = ClientDTOCreator.buildClient();

        when(clientServiceMock.save(clientDTO))
                .thenReturn(clientDTO);

        mockMvc.perform(post(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(clientDTO.getName())))
                .andExpect(jsonPath("$.cpf", is(clientDTO.getCpf())));
    }

    @Test
    @DisplayName("Try to save Client without all required fields throw a message error")
    void saveClientWithoutAllRequiredFields_Error() throws Exception {

        ClientDTO clientDTO = new ClientDTO();

        mockMvc.perform(post(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("List All clients with success")
    @Test
    void listAllClients_WithSuccess() throws Exception {
       List<ClientDTO> clients = List.of(ClientDTOCreator.buildClient());

        when(clientServiceMock.findAll())
                .thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].name", is(clients.get(0).getName())))
                .andExpect(jsonPath("$[0].cpf", is(clients.get(0).getCpf())));

    }

    @Test
    @DisplayName("List Client By id with success")
    void getClientById_WithSuccess() throws Exception {
        ClientDTO client = ClientDTOCreator.buildClient();

        when(clientServiceMock.findById(1L))
                .thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH + "/" + client.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name", is(client.getName())))
                .andExpect(jsonPath("$.cpf", is(client.getCpf())));

    }

    @Test
    @DisplayName("List Client with invalid id throw Exception")
    void getClientWithInvalidId_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(clientServiceMock).findById(INVALID_CLIENT_ID);

        mockMvc.perform(MockMvcRequestBuilders.get(CLIENT_API_URL_PATH + "/" + INVALID_CLIENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Client with success")
    void deleteClient_WithSuccess() throws Exception {
        ClientDTO client = ClientDTOCreator.buildClient();

        doNothing().when(clientServiceMock).delete(client.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(CLIENT_API_URL_PATH + "/" + client.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Try to Delete Invalid Client throw Exception")
    void deleteInvalidClient_ThrowError() throws Exception {

        doThrow(NotFoundException.class).when(clientServiceMock).delete(INVALID_CLIENT_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(CLIENT_API_URL_PATH + "/" + INVALID_CLIENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
