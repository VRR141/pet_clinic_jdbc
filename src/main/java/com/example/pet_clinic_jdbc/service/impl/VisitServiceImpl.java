package com.example.pet_clinic_jdbc.service.impl;

import com.example.pet_clinic_jdbc.domain.Pet;
import com.example.pet_clinic_jdbc.domain.Visit;
import com.example.pet_clinic_jdbc.domain.aggregate.VisitAggregate;
import com.example.pet_clinic_jdbc.repo.VisitJdbcRepository;
import com.example.pet_clinic_jdbc.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitJdbcRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Visit> getVisitsByPetIdentifier(Long id) {
        return repository.getVisitByPetIdentifier(id)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    @Transactional
    public Visit createVisit(Visit visit) {
        var aggregate = map(visit);
        return map(repository.save(aggregate));
    }

    private VisitAggregate map(Visit dto) {
        return VisitAggregate.builder()
                .id(dto.getId())
                .visitTimestamp(dto.getVisitTimestamp())
                .description(dto.getDescription())
                .petId(Objects.isNull(dto.getPet()) ? null : dto.getPet().getId())
                .build();
    }

    private Visit map(VisitAggregate agr) {
        return Visit.builder()
                .id(agr.getId())
                .visitTimestamp(agr.getVisitTimestamp())
                .description(agr.getDescription())
                .pet(Pet.builder()
                        .id(agr.getPetId())
                        .build())
                .build();
    }
}
