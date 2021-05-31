package com.spring.voluptuaria.service;

import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.exception.NotFoundException;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Passage;
import com.spring.voluptuaria.repository.PassageRepository;
import com.spring.voluptuaria.util.PassageDTOCreator;
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
@DisplayName("Test Passage Service")
@ExtendWith(SpringExtension.class)
class PassageServiceTest {

    @InjectMocks
    private PassageService passageService;

    @Mock
    private PassageRepository passageRepositoryMock;

    private IMapper mapper = IMapper.INSTANCE;

    @DisplayName("Save passage with success")
    @Test
    void savePassageIfAllArgumentsArePresent() throws NotFoundException {
        PassageDTO passagePassed = PassageDTOCreator.buildPassage();
        Passage passageToBeSaved = mapper.passageToModel(passagePassed);

        BDDMockito.when(passageRepositoryMock.save(ArgumentMatchers.any(Passage.class)))
                .thenReturn(passageToBeSaved);

        PassageDTO passageSaved = passageService.save(passagePassed);

        Assertions.assertEquals(passageSaved.getId(), passageSaved.getId());
        Assertions.assertEquals(passageSaved.getDestination(), passageSaved.getDestination());
    }

    @DisplayName("List all passages with success")
    @Test
    void listAll_Passages_ComSucesso(){
      List<Passage> passages = List.of(mapper.passageToModel(PassageDTOCreator.buildPassage()));
      BDDMockito.when(passageRepositoryMock.findAll())
              .thenReturn(passages);

      var listedPassages =  passageService.findAll();
      Assertions.assertEquals(passages.get(0).getDestination(), listedPassages.get(0).getDestination());
    }

    @Test
    @DisplayName("List Passage By id")
    void getPassage_ById_WithSuccess() throws NotFoundException {
        String expectedName = PassageDTOCreator.buildPassage().getDestination();

        BDDMockito.when(passageRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(mapper.passageToModel(PassageDTOCreator.buildPassage())));

        var passage = passageService.findById(ArgumentMatchers.any());
        Assertions.assertEquals(expectedName, passage.getDestination());
    }



}
