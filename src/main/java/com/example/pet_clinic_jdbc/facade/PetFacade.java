package com.example.pet_clinic_jdbc.facade;

import com.example.pet_clinic_jdbc.dto.PetDto;
import com.example.pet_clinic_jdbc.dto.request.CreatePetDto;
import com.example.pet_clinic_jdbc.dto.request.UpdatePetDto;

import java.util.Collection;

public interface PetFacade {

    PetDto getPet(Long id);

    Long createPet(CreatePetDto dto);

    PetDto updatePet(UpdatePetDto dto);

    Collection<PetDto> getPets(Collection<Long> ids);
}
