package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Destination;
import com.spring.voluptuaria.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DestinationService implements Services<DestinationDTO> {

    private final DestinationRepository destinationRepository;
    private final IMapper mapper = IMapper.INSTANCE;

    public List<DestinationDTO> findAll() {
        return  destinationRepository.findAll().stream()
                .map(mapper::destinationToDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO findById(Long id) throws NotFoundException {
        Destination destinationWithId = verifyIfExists(id);
        return mapper.destinationToDTO(destinationWithId);
    }

    public DestinationDTO save(DestinationDTO destinationDTO) throws NotFoundException {
        Destination destinationSaved = destinationRepository.save(mapper.destinationToModel(destinationDTO));
        return mapper.destinationToDTO(destinationSaved);
    }

    public void delete(Long id) throws NotFoundException {
        verifyIfExists(id);
        destinationRepository.deleteById(id);
    }

    public DestinationDTO update(DestinationDTO destinationDTO) throws NotFoundException {
        verifyIfExists(destinationDTO.getId());
        return save(destinationDTO);
    }

    private Destination verifyIfExists(Long id) throws NotFoundException {
        return destinationRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id));
    }

}

