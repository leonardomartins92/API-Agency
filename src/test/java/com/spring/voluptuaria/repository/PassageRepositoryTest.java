package com.spring.voluptuaria.repository;

import com.spring.voluptuaria.dto.PassageDTO;
import com.spring.voluptuaria.mapper.IMapper;
import com.spring.voluptuaria.model.Passage;
import com.spring.voluptuaria.util.PassageDTOCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Passage Repository Test")
class PassageRepositoryTest {

    @Autowired
    private PassageRepository passageRepository;

    private final IMapper mapper = IMapper.INSTANCE;

    @Test
    @DisplayName("Save Passage with success")
    void savePassageWithSuccessWhenAttributesArePresent()  {
        PassageDTO passageToSave = PassageDTOCreator.buildPassage();
        Passage passageSaved = mapper.passageToModel(passageToSave);

        var savedPassage = passageRepository.save(passageSaved);

        assertThat(savedPassage.getDestination(), is(equalTo(passageToSave.getDestination())));

    }

    @Test
    @DisplayName("Delete passage with success")
    void delete_Passage_comSucesso() {
        PassageDTO passageToSave = PassageDTOCreator.buildPassage();
        Passage passageSaved = mapper.passageToModel(passageToSave);

        passageRepository.delete(passageSaved);
        Optional<Passage> passage = passageRepository.findById(passageSaved.getId());

        assertThat(passage, is(equalTo(Optional.empty())));

    }

    @Test
    @DisplayName("Find passage by id")
    void findById_Passage_WithSuccess()  {
        PassageDTO passageToSave = PassageDTOCreator.buildPassage();
        Passage passageSaved = passageRepository.save(mapper.passageToModel(passageToSave));

        Optional<Passage> foundPassage = passageRepository.findById(passageSaved.getId());

        assertThat(foundPassage.get(), is(equalTo(passageSaved)));

    }

    @Test
    @DisplayName("Find all passages")
    void findAll_Passages_WithSuccess() {
        PassageDTO passageToSave = PassageDTOCreator.buildPassage();
        Passage passageSaved = passageRepository.save(mapper.passageToModel(passageToSave));

        List<Passage> companies = passageRepository.findAll();

        assertThat(companies.get(0).getDestination(), is(equalTo(passageSaved.getDestination())));

    }

}
