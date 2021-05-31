package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Destination;
import com.spring.voluptuaria.repository.DestinationRepository;
import com.spring.voluptuaria.util.DestinationDTOCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@Slf4j
@DisplayName("Test Destination Service")
@ExtendWith(SpringExtension.class)
class DestinationServiceTest {

    @InjectMocks
    private DestinationService destinationService;

    @Mock
    private DestinationRepository destinationRepositoryMock;

    private IMapper mapper = IMapper.INSTANCE;

    @DisplayName("Save destination with success")
    @Test
    void saveDestinationIfAllArgumentsArePresent() throws NotFoundException {
        DestinationDTO destinationPassed = DestinationDTOCreator.buildDestination();
        Destination destinationToBeSaved = mapper.destinationToModel(destinationPassed);

        BDDMockito.when(destinationRepositoryMock.save(ArgumentMatchers.any(Destination.class)))
                .thenReturn(destinationToBeSaved);

        DestinationDTO destinationSaved = destinationService.save(destinationPassed);

        Assertions.assertEquals(destinationSaved.getId(), destinationSaved.getId());
        Assertions.assertEquals(destinationSaved.getLocation(), destinationSaved.getLocation());
    }

    @DisplayName("List all destinations with success")
    @Test
    void listAll_Destinations_ComSucesso(){
      List<Destination> destinations = List.of(mapper.destinationToModel(DestinationDTOCreator.buildDestination()));
      BDDMockito.when(destinationRepositoryMock.findAll())
              .thenReturn(destinations);

      var listedDestinations =  destinationService.findAll();
      Assertions.assertEquals(destinations.get(0).getLocation(), listedDestinations.get(0).getLocation());
    }

    @Test
    @DisplayName("List Destination By id")
    void getDestination_ById_WithSuccess() throws NotFoundException {
        String expectedName = DestinationDTOCreator.buildDestination().getLocation();

        BDDMockito.when(destinationRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.destinationToModel(DestinationDTOCreator.buildDestination())));

        var destinatione = destinationService.findById(ArgumentMatchers.any());
        Assertions.assertEquals(expectedName, destinatione.getLocation());
    }



}
