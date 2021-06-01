package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Passage;
import com.spring.voluptuaria.repository.PassageRepository;
import com.spring.voluptuaria.builder.PassageDTOCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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

    @DisplayName("Save passage with success")
    @Test
    void savePassageIfAllArgumentsArePresent() throws NotFoundException {
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
    void listAll_Passages_ComSucesso(){
      List<Passage> passages = List.of(mapper.passageToModel(PassageDTOCreator.buildPassage()));

      Mockito.when(passageRepositoryMock.findAll())
              .thenReturn(passages);

      var listedPassages =  passageService.findAll();

      assertThat(listedPassages.get(0).getDestination(), is(equalTo(passages.get(0).getDestination())));

    }

    @Test
    @DisplayName("List Passage By id")
    void getPassage_ById_WithSuccess() throws NotFoundException {
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
    @DisplayName("Delete Passage with a valid Id")
    void deletePassageWhenAValidIdIsPassed() throws NotFoundException {

        Passage expectedDeletedPassage = mapper.passageToModel(PassageDTOCreator.buildPassage());

        when(passageRepositoryMock.findById(expectedDeletedPassage.getId()))
                .thenReturn(Optional.of(expectedDeletedPassage));

        passageService.delete(expectedDeletedPassage.getId());

        verify(passageRepositoryMock, times(1)).findById(expectedDeletedPassage.getId());
        verify(passageRepositoryMock, times(1)).deleteById(expectedDeletedPassage.getId());
    }

}
