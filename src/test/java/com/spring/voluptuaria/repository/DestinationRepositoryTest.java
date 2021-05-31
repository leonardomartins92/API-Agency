package com.spring.voluptuaria.repository;

import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Destination;
import com.spring.voluptuaria.util.DestinationDTOCreator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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

        assertThat(savedDestination.getLocation(), is(equalTo(destinationToSave.getLocation())));

    }

    @Test
    @DisplayName("Delete destination with success")
    void delete_Destination_comSucesso() {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = mapper.destinationToModel(destinationToSave);

        destinationRepository.delete(destinationSaved);
        Optional<Destination> destination = destinationRepository.findById(destinationSaved.getId());

        assertThat(destination, is(equalTo(Optional.empty())));

    }

    @Test
    @DisplayName("Find destination by id")
    void findById_Destination_WithSuccess()  {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = destinationRepository.save(mapper.destinationToModel(destinationToSave));

        Optional<Destination> foundDestination = destinationRepository.findById(destinationSaved.getId());

        assertThat(foundDestination.get(), is(equalTo(destinationSaved)));

    }

    @Test
    @DisplayName("Find all destinations")
    void findAll_Destinations_WithSuccess() {
        DestinationDTO destinationToSave = DestinationDTOCreator.buildDestination();
        Destination destinationSaved = destinationRepository.save(mapper.destinationToModel(destinationToSave));

        List<Destination> companies = destinationRepository.findAll();

        assertThat(companies.get(0).getLocation(), is(equalTo(destinationSaved.getLocation())));

    }

}
