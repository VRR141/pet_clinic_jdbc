package com.example.pet_clinic_jdbc.mapper;

import com.example.pet_clinic_jdbc.domain.Visit;
import com.example.pet_clinic_jdbc.dto.VisitDto;
import com.example.pet_clinic_jdbc.dto.request.CreateVisitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    @Mappings({
            @Mapping(target = "timestamp", source = "visitTimestamp"),
            @Mapping(target = "petId", source = "pet.id")
    })
    VisitDto map(Visit dto);

    @Mappings({
            @Mapping(target = "pet.id", source = "petIdentifier"),
            @Mapping(target = "visitTimestamp", source = "visitDate")
    })
    Visit map(CreateVisitDto dto);
}
