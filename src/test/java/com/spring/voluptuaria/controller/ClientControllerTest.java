package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Client;
import com.spring.voluptuaria.service.ClientService;
import com.spring.voluptuaria.util.ClientDTOCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DisplayName("Client Controller test")
@ExtendWith(SpringExtension.class)
class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientServiceMock;


    @BeforeEach
    void setUp() throws NotFoundException {









    }

    @DisplayName("List All clients with success")
    @Test
    void listAll_Clientes_WithSuccess(){
      String expectedName = ClientDTOCreator.buildCliente().getName();

      BDDMockito.when(clientServiceMock.findAll()).thenReturn(List.of(ClientDTOCreator.buildCliente()));

      var clients =  clientController.listClients();

      Assertions.assertEquals(expectedName, clients.get(0).getName());
    }

    @Test
    @DisplayName("Listar Cliente por id")
    void getCliente_ById_ComSucesso() throws NotFoundException {
        String expectedName = ClientDTOCreator.buildCliente().getName();

        BDDMockito.when(clientServiceMock.findById(ArgumentMatchers.any()))
                .thenReturn(ClientDTOCreator.buildCliente());

        var client = clientController.listClientById(ArgumentMatchers.any());

        Assertions.assertEquals(expectedName, client.getName());
    }

    @Test
    @DisplayName("Salvar cliente")
    void salvar_Cliente_ComSucesso() throws NotFoundException {
        ClientDTO toBeSaved = ClientDTOCreator.buildCliente();

        BDDMockito.when(clientServiceMock.save(ArgumentMatchers.any(ClientDTO.class)))
                .thenReturn(ClientDTOCreator.buildCliente());

        ClientDTO savedClient = clientController.saveClient(toBeSaved);

        Assertions.assertEquals(toBeSaved.getName(),savedClient.getName());
    }

    @Test
    @DisplayName("Excluir cliente")
    void excluir_Cliente_ComSucesso() throws NotFoundException {

        BDDMockito.doNothing().when(clientServiceMock).delete(ArgumentMatchers.any());

        Assertions.assertEquals(HttpStatus.NO_CONTENT,
                clientController.deleteClient(1L).getStatusCode());

    }

}
