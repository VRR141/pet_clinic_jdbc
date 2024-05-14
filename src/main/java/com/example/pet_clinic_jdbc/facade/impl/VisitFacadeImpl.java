package com.example.pet_clinic_jdbc.facade.impl;

import com.example.pet_clinic_jdbc.dto.VisitDto;
import com.example.pet_clinic_jdbc.dto.request.CreateVisitDto;
import com.example.pet_clinic_jdbc.facade.VisitFacade;
import com.example.pet_clinic_jdbc.mapper.VisitMapper;
import com.example.pet_clinic_jdbc.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class VisitFacadeImpl implements VisitFacade {

    private final VisitService visitService;

    private final VisitMapper mapper;

    @Override
    public Collection<VisitDto> getByPetIdentifier(Long id) {
        return visitService.getVisitsByPetIdentifier(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Long createVisit(CreateVisitDto dto) {
        var visit = mapper.map(dto);
        visit = visitService.createVisit(visit);
        return visit.getId();
    }
}
