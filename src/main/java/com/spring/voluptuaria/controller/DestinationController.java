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

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/destinations")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DestinationController implements Controllers<DestinationDTO> {

    private final DestinationService destinationService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<DestinationDTO> listAll(){
        return destinationService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/{cod}")
    public DestinationDTO listById(@PathVariable Long cod ) throws NotFoundException {
        return destinationService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DestinationDTO save(@RequestBody @Valid DestinationDTO destinationDTO) throws NotFoundException {
        return destinationService.save(destinationDTO);

    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{id}")
    public DestinationDTO update(@RequestBody @Valid DestinationDTO destinationDTO, @PathVariable Long id) throws NotFoundException {
        destinationDTO.setId(id);
        return destinationService.update(destinationDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        destinationService.delete(id);
    }
}





