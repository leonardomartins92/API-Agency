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

@CrossOrigin
@RequestMapping(path = "/api/v1/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class ClientController implements Controllers<ClientDTO> {

    private final ClientService clientService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<ClientDTO> listAll(){
        return clientService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/{cod}")
    public ClientDTO listById(@PathVariable Long cod ) throws NotFoundException {
       return clientService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClientDTO save(@RequestBody @Valid ClientDTO clientDTO) throws NotFoundException {
      return clientService.save(clientDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{id}")
    public ClientDTO update(@RequestBody @Valid ClientDTO clientDTO, @PathVariable Long id) throws NotFoundException {
        clientDTO.setId(id);
        return clientService.update(clientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
         clientService.delete(id);
    }
}






