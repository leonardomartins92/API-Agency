package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.service.PassageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PassageController {

    private final PassageService passageService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/api/v1/passages")
    public List<PassageDTO> listPassages(){
        return passageService.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/api/v1/passages/{cod}")
    public PassageDTO listPassageById(@PathVariable Long cod ) throws NotFoundException {
        return passageService.findById(cod);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/api/v1/passages")
    public PassageDTO savePassage(@RequestBody @Valid PassageDTO passageDTO) throws NotFoundException {
        return passageService.save(passageDTO);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/api/v1/passages/{id}")
    public void deletePassage(@PathVariable Long id) throws NotFoundException {
        passageService.delete(id);
    }
}





