package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.DestinationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DestinationController {

    private final DestinationService destinationService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/api/v1/destinations")
    public List<DestinationDTO> listDestinations(){
        return destinationService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/api/v1/destinations/{cod}")
    public DestinationDTO listDestinationById(@PathVariable Long cod ) throws NotFoundException {
        return destinationService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/api/v1/destinations")
    public DestinationDTO saveDestination(@RequestBody @Valid DestinationDTO destinationDTO) throws NotFoundException {
        return destinationService.save(destinationDTO);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/api/v1/destinations/{id}")
    public void deleteDestination(@PathVariable Long id) throws NotFoundException {
        destinationService.delete(id);
    }
}





