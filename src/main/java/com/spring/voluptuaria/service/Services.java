package com.spring.voluptuaria.service;

import com.spring.voluptuaria.exception.NotFoundException;

import java.util.List;

public interface Services<T> {

    public List<T> findAll();
    public T findById(Long id) throws NotFoundException;
    public T save(T t) throws NotFoundException;
    public T update(T t) throws NotFoundException;
    public void delete(Long id) throws NotFoundException;
}
