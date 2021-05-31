package com.spring.voluptuaria.repository;

import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Destination;
import com.spring.voluptuaria.util.DestinationDTOCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Destination Repository Test")
class DestinationRepositoryTest {

    @Autowired
    private DestinationRepository destinationRepository;

    private final IMapper mapper = IMapper.INSTANCE;

    @Test
    @DisplayName("Save Destination with success")
    void saveDestinationWithSuccessWhenAttributesArePresent()  {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = mapper.destinationToModel(destinationToSave);

        var savedDestination = destinationRepository.save(destinationSaved);
        Assertions.assertEquals(destinationToSave.getLocation(), savedDestination.getLocation());
    }

    @Test
    @DisplayName("Delete destination with success")
    void delete_Destination_comSucesso() {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = mapper.destinationToModel(destinationToSave);

        destinationRepository.delete(destinationSaved);
        Optional<Destination> destination = destinationRepository.findById(destinationSaved.getId());
        assertEquals(Optional.empty(), destination);
    }

    @Test
    @DisplayName("Find destination by id")
    void findById_Destination_WithSuccess()  {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = destinationRepository.save(mapper.destinationToModel(destinationToSave));

        Optional<Destination> foundDestination = destinationRepository.findById(destinationSaved.getId());

        assertEquals(destinationSaved, foundDestination.get());
    }

    @Test
    @DisplayName("Find all destinations")
    void findAll_Destinations_WithSuccess() {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = destinationRepository.save(mapper.destinationToModel(destinationToSave));

        List<Destination> companies = destinationRepository.findAll();
        assertEquals(destinationSaved.getLocation(), companies.get(0).getLocation());
    }

}
