package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.PassageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/passages")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PassageController implements Controllers<PassageDTO> {

    private final PassageService passageService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<PassageDTO> listAll(){
        return passageService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/{cod}")
    public PassageDTO listById(@PathVariable Long cod ) throws NotFoundException {
        return passageService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PassageDTO save(@RequestBody @Valid PassageDTO passageDTO) throws NotFoundException {
        return passageService.save(passageDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{id}")
    public PassageDTO update(@RequestBody @Valid PassageDTO passageDTO, @PathVariable Long id) throws NotFoundException {
        passageDTO.setId(id);
        return passageService.update(passageDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        passageService.delete(id);
    }
}





