package com.spring.voluptuaria.mapper;

import com.spring.voluptuaria.dto.*;
import com.spring.voluptuaria.model.*;
import com.spring.voluptuaria.service.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ClientService.class, DestinationService.class, CompanyService.class,
        PassageService.class})
public interface IMapper {

    IMapper INSTANCE = Mappers.getMapper(IMapper.class);
    Client clientToModel(ClientDTO clientDTO);

    ClientDTO clientToDTO(Client client);

    @Mapping(source ="start" ,target = "start", dateFormat = "yyyy-MM-dd")
    @Mapping(source ="end" ,target = "end", dateFormat = "yyyy-MM-dd")
    Destination destinationToModel(DestinationDTO destinationDTO);


    DestinationDTO destinationToDTO(Destination destination);

    Company companyToModel(CompanyDTO companyDTO);

    CompanyDTO companyToDTO(Company company);

    @Mapping(source ="departureDate" ,target = "departureDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source ="arriveDate" ,target = "arriveDate", dateFormat = "yyyy-MM-dd")
    Passage passageToModel(PassageDTO passageDTO);

    PassageDTO passageToDTO(Passage passage);


}

