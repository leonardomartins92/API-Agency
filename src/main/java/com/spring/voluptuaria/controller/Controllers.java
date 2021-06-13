package com.spring.voluptuaria.controller;

import com.spring.voluptuaria.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface Controllers<T> {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<T> listAll();

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(path = "/{cod}")
    public T listById(@PathVariable Long cod ) throws NotFoundException;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public T save(@RequestBody @Valid T t) throws NotFoundException;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{id}")
    public T update(@RequestBody @Valid T t, @PathVariable Long id) throws NotFoundException;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException;
}
