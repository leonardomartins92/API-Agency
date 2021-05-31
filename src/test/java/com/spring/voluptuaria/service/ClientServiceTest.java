package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Client;
import com.spring.voluptuaria.repository.ClientRepository;
import com.spring.voluptuaria.util.ClientDTOCreator;
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
@DisplayName("Test Client Service")
@ExtendWith(SpringExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepositoryMock;

    private IMapper mapper = IMapper.INSTANCE;

    @DisplayName("Save client with success")
    @Test
    void saveClientIfAllArgumentsArePresent() throws NotFoundException {
        ClientDTO clientPassed = ClientDTOCreator.buildCliente();
        Client clientToBeSaved = mapper.clientToModel(clientPassed);

        BDDMockito.when(clientRepositoryMock.save(ArgumentMatchers.any(Client.class)))
                .thenReturn(clientToBeSaved);

        ClientDTO clientSaved = clientService.save(clientPassed);

        Assertions.assertEquals(clientSaved.getId(), clientSaved.getId());
        Assertions.assertEquals(clientSaved.getName(), clientSaved.getName());
    }

    @DisplayName("List all clients with success")
    @Test
    void listAll_Clientes_ComSucesso(){
      List<Client> clients = List.of(mapper.clientToModel(ClientDTOCreator.buildCliente()));
      BDDMockito.when(clientRepositoryMock.findAll())
              .thenReturn(clients);

      var listedClients =  clientService.findAll();
      Assertions.assertEquals(clients.get(0).getName(), listedClients.get(0).getName());
    }

    @Test
    @DisplayName("List Client By id")
    void getCliente_ById_WithSuccess() throws NotFoundException {
        String expectedName = ClientDTOCreator.buildCliente().getName();

        BDDMockito.when(clientRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.clientToModel(ClientDTOCreator.buildCliente())));

        var cliente = clientService.findById(ArgumentMatchers.any());
        Assertions.assertEquals(expectedName, cliente.getName());
    }



}
