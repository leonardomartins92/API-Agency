package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Client;
import com.spring.voluptuaria.repository.ClientRepository;
import com.spring.voluptuaria.builder.ClientDTOCreator;
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


@DisplayName("Test Client Service")
@ExtendWith(SpringExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepositoryMock;

    private final IMapper mapper = IMapper.INSTANCE;

    private static final long INVALID_CLIENT_ID = 2L;

    @DisplayName("Save client with success")
    @Test
    void saveClientIfAllRequiredArgumentsArePresent_WithSuccess() throws NotFoundException {

        ClientDTO clientPassed = ClientDTOCreator.buildClient();
        Client clientToBeSaved = mapper.clientToModel(clientPassed);

        when(clientRepositoryMock.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(clientToBeSaved);

        ClientDTO clientSaved = clientService.save(clientPassed);

        assertThat(clientSaved.getId(), is(equalTo(clientSaved.getId())));
        assertThat(clientSaved.getName(), is(equalTo(clientSaved.getName())));
    }

    @DisplayName("List all clients with success")
    @Test
    void listAllClients_WithSuccess(){

      List<Client> clients = List.of(mapper.clientToModel(ClientDTOCreator.buildClient()));

      when(clientRepositoryMock.findAll())
              .thenReturn(clients);

      var listedClients =  clientService.findAll();

      assertThat(listedClients.get(0).getName(), is(equalTo(clients.get(0).getName())));
    }

    @Test
    @DisplayName("List Client with valid id")
    void getClienteById_WithSuccess() throws NotFoundException {

        String expectedName = ClientDTOCreator.buildClient().getName();

        when(clientRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.clientToModel(ClientDTOCreator.buildClient())));

        var cliente = clientService.findById(ArgumentMatchers.any());

        assertThat(cliente.getName(), is(equalTo(expectedName)));
    }

    @Test
    @DisplayName("Not Found Client With Invalid Id")
    void clientNotFoundWithID_ThrownException() {

        when(clientRepositoryMock.findById(INVALID_CLIENT_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class , ()-> clientService.findById(INVALID_CLIENT_ID));
    }

    @Test
    @DisplayName("Update Client with a valid id")
    void updateClientWhenAllRequiredParametersArePresent_WithSuccess() throws NotFoundException {
        ClientDTO clientSaved = ClientDTOCreator.buildClient();
        ClientDTO clientToUpdate = clientSaved;
        clientToUpdate.setCpf("9999");

        when(clientRepositoryMock.findById(clientSaved.getId()))
                .thenReturn(Optional.of(mapper.clientToModel(clientSaved)));

        when(clientRepositoryMock.save(mapper.clientToModel(clientToUpdate)))
                .thenReturn(mapper.clientToModel(clientToUpdate));

        ClientDTO clientUpdated = clientService.update(clientToUpdate);

        assertThat(clientToUpdate.getCpf(), is(equalTo(clientUpdated.getCpf())));
        assertThat(clientToUpdate.getId(), is(equalTo(clientUpdated.getId())));
    }

    @Test
    @DisplayName("Update Client with a invalid id throws an exception")
    void updateClientWithInvalidId_ThrowException() {
        ClientDTO clientToUpdate = ClientDTOCreator.buildClient();
        clientToUpdate.setId(INVALID_CLIENT_ID);

        when(clientRepositoryMock.findById(clientToUpdate.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class , ()-> clientService.findById(INVALID_CLIENT_ID));
    }

    @Test
    @DisplayName("Delete Client with a valid Id")
    void deleteClientWhenAValidIdIsPassed() throws NotFoundException {

        Client expectedDeletedClient = mapper.clientToModel(ClientDTOCreator.buildClient());

        when(clientRepositoryMock.findById(expectedDeletedClient.getId()))
                .thenReturn(Optional.of(expectedDeletedClient));

        clientService.delete(expectedDeletedClient.getId());

        verify(clientRepositoryMock, times(1)).findById(expectedDeletedClient.getId());
        verify(clientRepositoryMock, times(1)).deleteById(expectedDeletedClient.getId());
    }



}
