package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.CompanyDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Company;
import com.spring.voluptuaria.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final IMapper mapper = IMapper.INSTANCE;

    public List<CompanyDTO> findAll() {
        return  companyRepository.findAll().stream()
                .map(mapper::companyToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO findById(Long id) throws NotFoundException {
        Company companyWithId = companyRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id));
        return mapper.companyToDTO(companyWithId);
    }

    public CompanyDTO save(CompanyDTO companyDTO) throws NotFoundException {
        Company companySaved = companyRepository.save(mapper.companyToModel(companyDTO));
        return mapper.companyToDTO(companySaved);
    }

    public void delete(CompanyDTO companyDTO) throws NotFoundException {
        companyRepository.delete(mapper.companyToModel(companyDTO));
    }
}