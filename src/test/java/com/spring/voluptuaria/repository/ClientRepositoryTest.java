package com.spring.voluptuaria.repository;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Client;
import com.spring.voluptuaria.util.ClientDTOCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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

        assertThat(savedClient.getCpf(), is(equalTo(clientToSave.getCpf())));

    }

    @Test
    @DisplayName("Delete client with success")
    void delete_Cliente_comSucesso() {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = mapper.clientToModel(clientToSave);

        clientRepository.delete(clientSaved);
        Optional<Client> cliente = clientRepository.findById(clientSaved.getId());

        assertThat(cliente, is(equalTo(Optional.empty())));

    }

    @Test
    @DisplayName("Find client by id")
    void findById_Cliente_ComSucesso()  {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = clientRepository.save(mapper.clientToModel(clientToSave));

        Optional<Client> foundClient = clientRepository.findById(clientSaved.getId());

        assertThat(foundClient.get(), is(equalTo(clientSaved)));

    }

    @Test
    @DisplayName("find all clients ")
    void findAll_Clientes_ComSucesso() {
        ClientDTO clientToSave = ClientDTOCreator.buildCliente();
        Client clientSaved = clientRepository.save(mapper.clientToModel(clientToSave));

        List<Client> foundClients = clientRepository.findAll();

        assertThat( foundClients.get(0), is(equalTo(clientSaved)));

    }

}
