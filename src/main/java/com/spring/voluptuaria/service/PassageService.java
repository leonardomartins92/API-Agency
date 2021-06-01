package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Passage;
import com.spring.voluptuaria.repository.PassageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PassageService {

    private final PassageRepository passageRepository;
    private final IMapper mapper = IMapper.INSTANCE;

    public List<PassageDTO> findAll() {
        return  passageRepository.findAll().stream()
                .map(mapper::passageToDTO)
                .collect(Collectors.toList());
    }

    public PassageDTO findById(Long id) throws NotFoundException {
        Passage passageWithId = verifyIfExists(id);
        return mapper.passageToDTO(passageWithId);
    }

    public PassageDTO save(PassageDTO passageDTO) throws NotFoundException {
        Passage passageSaved = passageRepository.save(mapper.passageToModel(passageDTO));
        return mapper.passageToDTO(passageSaved);
    }

    public void delete(Long id) throws NotFoundException {
        verifyIfExists(id);
        passageRepository.deleteById(id);
    }

    private Passage verifyIfExists(Long id) throws NotFoundException {
        return passageRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id));
    }
}
