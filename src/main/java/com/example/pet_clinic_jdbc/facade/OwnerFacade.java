package com.example.pet_clinic_jdbc.facade;

import com.example.pet_clinic_jdbc.dto.OwnerDto;
import com.example.pet_clinic_jdbc.dto.request.CreateOwnerDto;
import com.example.pet_clinic_jdbc.dto.request.UpdateOwnerDto;

public interface OwnerFacade {

    OwnerDto getOwner(Long id, boolean fetchPets);

    Long createOwner(CreateOwnerDto dto);

    OwnerDto updateOwner(UpdateOwnerDto dto);
}
