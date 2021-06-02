package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Client;
import com.spring.voluptuaria.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final IMapper mapper = IMapper.INSTANCE;

    public List<ClientDTO> findAll() {
         return  clientRepository.findAll().stream()
                 .map(mapper::clientToDTO)
                 .collect(Collectors.toList());
    }

    public ClientDTO findById(Long id) throws NotFoundException {
        Client clientWithId = verifyIfExists(id);
        return mapper.clientToDTO(clientWithId);
    }

    public ClientDTO save(ClientDTO clientDTO) throws NotFoundException {
        Client clientSaved = clientRepository.save(mapper.clientToModel(clientDTO));
        return mapper.clientToDTO(clientSaved);
    }

    public void delete(Long id) throws NotFoundException {
        verifyIfExists(id);
        clientRepository.deleteById(id);
    }

    public ClientDTO update(ClientDTO clientDTO) throws NotFoundException {
        verifyIfExists(clientDTO.getId());
        return save(clientDTO);
    }

    private Client verifyIfExists(Long id) throws NotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id));
    }

}
