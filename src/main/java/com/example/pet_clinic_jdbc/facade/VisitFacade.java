package com.example.pet_clinic_jdbc.facade;


import com.example.pet_clinic_jdbc.dto.VisitDto;
import com.example.pet_clinic_jdbc.dto.request.CreateVisitDto;

import java.util.Collection;

public interface VisitFacade {

    Collection<VisitDto> getByPetIdentifier(Long id);

    Long createVisit(CreateVisitDto dto);
}
