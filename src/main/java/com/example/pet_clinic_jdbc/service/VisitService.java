package com.example.pet_clinic_jdbc.service;


import com.example.pet_clinic_jdbc.domain.Visit;

import java.util.Collection;

public interface VisitService {

    Collection<Visit> getVisitsByPetIdentifier(Long id);

    Visit createVisit(Visit visit);
}
