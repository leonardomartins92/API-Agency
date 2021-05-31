package com.spring.voluptuaria.repository;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Client;
import com.spring.voluptuaria.util.ClientDTOCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Client Repository Test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private final IMapper mapper = IMapper.INSTANCE;

    @Test
    @DisplayName("Save client  with success")
    void saveClientWithSuccessWhenAttributesArePresent()  {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = mapper.clientToModel(clientToSave);

        var savedClient = clientRepository.save(clientSaved);
        Assertions.assertEquals(clientToSave.getCpf(), savedClient.getCpf());
    }

    @Test
    @DisplayName("Delete client with success")
    void delete_Cliente_comSucesso() {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = mapper.clientToModel(clientToSave);

        clientRepository.delete(clientSaved);
        Optional<Client> cliente = clientRepository.findById(clientSaved.getId());
        assertEquals(Optional.empty(), cliente);
    }

    @Test
    @DisplayName("Find client by id")
    void findById_Cliente_ComSucesso()  {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = mapper.clientToModel(clientToSave);

        Optional<Client> clienteEncontrado = clientRepository.findById(clientSaved.getId());

        assertEquals(clientSaved, clienteEncontrado.get());
    }

    @Test
    @DisplayName("find all clients ")
    void findAll_Clientes_ComSucesso() {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = mapper.clientToModel(clientToSave);

        List<Client> clientesSalvos = List.of(clientSaved);

        List<Client> clientesEncontrados = clientRepository.findAll();
        assertEquals(clientesSalvos, clientesEncontrados);
    }

}
