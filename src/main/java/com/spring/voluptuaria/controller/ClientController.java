package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class ClientController {

    private final ClientService clientService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/api/v1/clients")
    public List<ClientDTO> listClients(){
        return clientService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/api/v1/clients/{cod}")
    public ClientDTO listClientById(@PathVariable Long cod ) throws NotFoundException {
       return clientService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/api/v1/clients")
    public ClientDTO saveClient(@RequestBody @Valid ClientDTO clientDTO) throws NotFoundException {
      return clientService.save(clientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/api/v1/clients/{id}")
    public void deleteClient(@PathVariable Long id) throws NotFoundException {
         clientService.delete(id);
    }
}






