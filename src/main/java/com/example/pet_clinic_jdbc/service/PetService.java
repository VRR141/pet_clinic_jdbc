package com.example.pet_clinic_jdbc.service;

import com.example.pet_clinic_jdbc.domain.Pet;

import java.util.Collection;

public interface PetService {

    Pet getById(Long id);

    Pet createPet(Pet pet);

    Pet updatePet(Pet pet);

    Collection<Pet> getPetsByIdentifiers(Collection<Long> ids);
}
