package com.spring.voluptuaria.service;

import com.spring.voluptuaria.builder.PassageDTOCreator;
import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Passage;
import com.spring.voluptuaria.repository.PassageRepository;
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

@DisplayName("Test Passage Service")
@ExtendWith(SpringExtension.class)
class PassageServiceTest {

    @InjectMocks
    private PassageService passageService;

    @Mock
    private PassageRepository passageRepositoryMock;

    private final IMapper mapper = IMapper.INSTANCE;
    private static final long INVALID_PASSAGE_ID = 2L;

    @DisplayName("Save passage with success")
    @Test
    void savePassageIfAllArgumentsArePresent_WithSuccess() throws NotFoundException {
        PassageDTO passagePassed = PassageDTOCreator.buildPassage();
        Passage passageToBeSaved = mapper.passageToModel(passagePassed);

        Mockito.when(passageRepositoryMock.save(ArgumentMatchers.any(Passage.class)))
                .thenReturn(passageToBeSaved);

        PassageDTO passageSaved = passageService.save(passagePassed);

        assertThat(passageSaved.getId(), is(equalTo(passageSaved.getId())));
        assertThat(passageSaved.getDestination(), is(equalTo(passageSaved.getDestination())));

    }

    @DisplayName("List all passages with success")
    @Test
    void listAllPassages_WithSuccess(){
      List<Passage> passages = List.of(mapper.passageToModel(PassageDTOCreator.buildPassage()));

      Mockito.when(passageRepositoryMock.findAll())
              .thenReturn(passages);

      var listedPassages =  passageService.findAll();

      assertThat(listedPassages.get(0).getDestination(), is(equalTo(passages.get(0).getDestination())));

    }

    @Test
    @DisplayName("List Passage with a valid id")
    void getPassageById_WithSuccess() throws NotFoundException {
        String expectedName = PassageDTOCreator.buildPassage().getDestination();

        Mockito.when(passageRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.passageToModel(PassageDTOCreator.buildPassage())));

        var passage = passageService.findById(ArgumentMatchers.any());

        assertThat(passage.getDestination(), is(equalTo(expectedName)));
    }

    @Test
    @DisplayName("Not Found Passage With Id")
    void passageNotFoundWithID_ThrownException()  {

        assertThrows(NotFoundException.class, ()-> passageService.findById(ArgumentMatchers.any()));
    }

    @Test
    @DisplayName("Update Passage with a valid id")
    void updatePassageWithAValidId_WithSuccess() throws NotFoundException {
        PassageDTO passageSaved = PassageDTOCreator.buildPassage();
        PassageDTO passageToUpdate = passageSaved;
        passageToUpdate.setDestination("Queenstown");

        when(passageRepositoryMock.findById(passageSaved.getId()))
                .thenReturn(Optional.of(mapper.passageToModel(passageSaved)));

        when(passageRepositoryMock.save(mapper.passageToModel(passageToUpdate)))
                .thenReturn(mapper.passageToModel(passageToUpdate));

        PassageDTO passageUpdated = passageService.update(passageToUpdate);

        assertThat(passageToUpdate.getDestination(), is(equalTo(passageUpdated.getDestination())));
        assertThat(passageToUpdate.getId(), is(equalTo(passageUpdated.getId())));
    }

    @Test
    @DisplayName("Update Passage with a invalid id throws an exception")
    void updatePassageWithInvalidId_ThrowException() {
        PassageDTO passageToUpdate = PassageDTOCreator.buildPassage();
        passageToUpdate.setId(INVALID_PASSAGE_ID);

        when(passageRepositoryMock.findById(passageToUpdate.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class , ()-> passageService.findById(INVALID_PASSAGE_ID));
    }

    @Test
    @DisplayName("Delete Passage with a valid Id")
    void deletePassageWhenAValidIdIsPassed_WithSuccess() throws NotFoundException {

        Passage expectedDeletedPassage = mapper.passageToModel(PassageDTOCreator.buildPassage());

        when(passageRepositoryMock.findById(expectedDeletedPassage.getId()))
                .thenReturn(Optional.of(expectedDeletedPassage));

        passageService.delete(expectedDeletedPassage.getId());

        verify(passageRepositoryMock, times(1)).findById(expectedDeletedPassage.getId());
        verify(passageRepositoryMock, times(1)).deleteById(expectedDeletedPassage.getId());
    }

}
