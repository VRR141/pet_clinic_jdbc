package com.example.pet_clinic_jdbc.service;

import com.example.pet_clinic_jdbc.domain.Pet;

public interface PetService {

    Pet getById(Long id);

    Pet createPet(Pet pet);

    Pet updatePet(Pet pet);
}
