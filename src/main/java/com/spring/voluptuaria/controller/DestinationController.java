package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.DestinationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DestinationController {

    private final DestinationService destinationService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/pesquisaDestinos")
    public List<DestinationDTO> listDestinations(){
        return destinationService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/pesquisaDestino/{cod}")
    public DestinationDTO listDestinationById(@PathVariable Long cod ) throws NotFoundException {
        return destinationService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/manterDestino")
    public DestinationDTO saveDestination(@RequestBody @Valid DestinationDTO destinationDTO) throws NotFoundException {
        return destinationService.save(destinationDTO);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/manterDestino/{id}")
    public void deleteDestination(@PathVariable Long id) throws NotFoundException {
        destinationService.delete(destinationService.findById(id));
    }
}





