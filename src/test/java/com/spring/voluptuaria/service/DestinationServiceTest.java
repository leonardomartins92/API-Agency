package com.spring.voluptuaria.service;

import com.spring.voluptuaria.builder.DestinationDTOCreator;
import com.spring.voluptuaria.dto.DestinationDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Destination;
import com.spring.voluptuaria.repository.DestinationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Test Destination Service")
@ExtendWith(SpringExtension.class)
class DestinationServiceTest {

    @InjectMocks
    private DestinationService destinationService;

    @Mock
    private DestinationRepository destinationRepositoryMock;

    private final IMapper mapper = IMapper.INSTANCE;
    private static final long INVALID_DESTINATION_ID = 2L;

    @DisplayName("Save destination with success")
    @Test
    void saveDestinationIfAllRequiredArgumentsArePresent_WithSuccess() throws NotFoundException {
        DestinationDTO destinationPassed = DestinationDTOCreator.buildDestination();
        Destination destinationToBeSaved = mapper.destinationToModel(destinationPassed);

        Mockito.when(destinationRepositoryMock.save(ArgumentMatchers.any(Destination.class)))
                .thenReturn(destinationToBeSaved);

        DestinationDTO destinationSaved = destinationService.save(destinationPassed);

        assertThat(destinationSaved.getId(), is(equalTo(destinationSaved.getId())));
        assertThat(destinationSaved.getLocation(), is(equalTo(destinationSaved.getLocation())));

    }

    @DisplayName("List all destinations with success")
    @Test
    void listAllDestinations_WithSuccess(){
      List<Destination> destinations = List.of(mapper.destinationToModel(DestinationDTOCreator.buildDestination()));

      Mockito.when(destinationRepositoryMock.findAll())
              .thenReturn(destinations);

      var listedDestinations =  destinationService.findAll();

      assertThat(listedDestinations.get(0).getLocation(), is(equalTo(destinations.get(0).getLocation())));

    }

    @Test
    @DisplayName("List Destination with a valid id")
    void getDestinationById_WithSuccess() throws NotFoundException {
        String expectedName = DestinationDTOCreator.buildDestination().getLocation();

        Mockito.when(destinationRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.destinationToModel(DestinationDTOCreator.buildDestination())));

        var destinatione = destinationService.findById(ArgumentMatchers.any());

        assertThat(destinatione.getLocation(), is(equalTo(expectedName)));

    }

    @Test
    @DisplayName("Not Found Destination With an invalid Id")
    void destinationNotFoundWithInvalidId_ThrownException()  {

        assertThrows(NotFoundException.class, ()-> destinationService.findById(ArgumentMatchers.any()));
    }

    @Test
    @DisplayName("Update Destination with a valid id")
    void updateDestinationWithAValidId_WithSuccess() throws NotFoundException {
        DestinationDTO destinationSaved = DestinationDTOCreator.buildDestination();
        DestinationDTO destinationToUpdate = destinationSaved;
        destinationToUpdate.setLocation("Queenstown");

        when(destinationRepositoryMock.findById(destinationSaved.getId()))
                .thenReturn(Optional.of(mapper.destinationToModel(destinationSaved)));

        when(destinationRepositoryMock.save(mapper.destinationToModel(destinationToUpdate)))
                .thenReturn(mapper.destinationToModel(destinationToUpdate));

        DestinationDTO destinationUpdated = destinationService.update(destinationToUpdate);

        assertThat(destinationToUpdate.getLocation(), is(equalTo(destinationUpdated.getLocation())));
        assertThat(destinationToUpdate.getId(), is(equalTo(destinationUpdated.getId())));
    }

    @Test
    @DisplayName("Update Destination with a invalid id throws an exception")
    void updateDestinationWithInvalidId_ThrowException() {
        DestinationDTO destinationToUpdate = DestinationDTOCreator.buildDestination();
        destinationToUpdate.setId(INVALID_DESTINATION_ID);

        when(destinationRepositoryMock.findById(destinationToUpdate.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class , ()-> destinationService.findById(INVALID_DESTINATION_ID));
    }

    @Test
    @DisplayName("Delete Destination with a valid Id")
    void deleteDestinationWhenAValidIdIsPassed_WithSuccess() throws NotFoundException {

        Destination expectedDeletedDestination = mapper.destinationToModel(DestinationDTOCreator.buildDestination());

        when(destinationRepositoryMock.findById(expectedDeletedDestination.getId()))
                .thenReturn(Optional.of(expectedDeletedDestination));

        destinationService.delete(expectedDeletedDestination.getId());

        verify(destinationRepositoryMock, times(1)).findById(expectedDeletedDestination.getId());
        verify(destinationRepositoryMock, times(1)).deleteById(expectedDeletedDestination.getId());
    }



}
