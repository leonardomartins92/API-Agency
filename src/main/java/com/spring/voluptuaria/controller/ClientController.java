package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.ClientDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.ClientService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = "/api/v1/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class ClientController {

    private final ClientService clientService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<ClientDTO> listClients(){
        return clientService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/{cod}")
    public ClientDTO listClientById(@PathVariable Long cod ) throws NotFoundException {
       return clientService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClientDTO saveClient(@RequestBody @Valid ClientDTO clientDTO) throws NotFoundException {
      return clientService.save(clientDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{id}")
    public ClientDTO updateClient(@RequestBody @Valid ClientDTO clientDTO, @PathVariable Long id) throws NotFoundException {
        clientDTO.setId(id);
        return clientService.update(clientDTO);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteClient(@PathVariable Long id) throws NotFoundException {
         clientService.delete(id);
    }
}






