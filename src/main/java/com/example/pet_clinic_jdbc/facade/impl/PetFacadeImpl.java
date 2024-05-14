package com.example.pet_clinic_jdbc.facade.impl;

import com.example.pet_clinic_jdbc.dto.PetDto;
import com.example.pet_clinic_jdbc.dto.request.CreatePetDto;
import com.example.pet_clinic_jdbc.dto.request.UpdatePetDto;
import com.example.pet_clinic_jdbc.facade.PetFacade;
import com.example.pet_clinic_jdbc.mapper.PetMapper;
import com.example.pet_clinic_jdbc.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class PetFacadeImpl implements PetFacade {

    private final PetService petService;

    private final PetMapper mapper;

    @Override
    public PetDto getPet(Long id) {
        final var pet = petService.getById(id);
        return mapper.map(pet);
    }

    @Override
    public Long createPet(CreatePetDto dto) {
        var pet = mapper.map(dto);
        pet = petService.createPet(pet);
        return pet.getId();
    }

    @Override
    public PetDto updatePet(UpdatePetDto dto) {
        var pet = mapper.map(dto);
        pet = petService.updatePet(pet);
        return mapper.mapIgnoreVisits(pet);
    }

    @Override
    public Collection<PetDto> getPets(Collection<Long> ids) {
        final var pets = petService.getPetsByIdentifiers(ids);
        return pets.stream().map(mapper::mapIgnoreVisits).toList();
    }
}
