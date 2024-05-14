package com.example.pet_clinic_jdbc.service.impl;

import com.example.pet_clinic_jdbc.domain.Owner;
import com.example.pet_clinic_jdbc.domain.Pet;
import com.example.pet_clinic_jdbc.domain.Visit;
import com.example.pet_clinic_jdbc.domain.aggregate.PetAggregate;
import com.example.pet_clinic_jdbc.domain.aggregate.VisitAggregate;
import com.example.pet_clinic_jdbc.exception.EntityNotExistException;
import com.example.pet_clinic_jdbc.repo.PetJdbcRepository;
import com.example.pet_clinic_jdbc.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetJdbcRepository petJdbcRepository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public Pet getById(Long id) {
        final var result = jdbcTemplate.query(
                """
                        SELECT
                        p.id, p.name, p.birth_date, p.owner_id,
                        v.id, v.visit_timestamp, v.description, v.pet_id
                        FROM pet p LEFT JOIN visit v ON p.id = v.pet_id
                        WHERE
                        p.id = ?
                        """,
                rs -> {

                    if (rs.wasNull()) {
                        return null;
                    }

                    final var pet = new Pet();
                    pet.setVisits(new ArrayList<>());

                    final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    while (rs.next()) {

                        if (Objects.isNull(pet.getId())) {
                            pet.setId(rs.getLong(1));
                            pet.setName(rs.getString(2));
                            final var birthDate = rs.getString(3);
                            pet.setBirthDate(LocalDate.parse(birthDate, formatter));
                            pet.setOwner(Owner.builder().id(rs.getLong(4)).build());
                        }

                        final var visit = new Visit();
                        final var visitId = rs.getString(5);
                        if (Objects.isNull(visitId)) {
                            continue;
                        }
                        visit.setId(Long.parseLong(visitId));
                        final var date = rs.getTimestamp(6);
                        visit.setVisitTimestamp(OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
                        visit.setDescription(rs.getString(7));
                        visit.setPet(Pet.builder().id(rs.getLong(8)).build());
                        pet.getVisits().add(visit);
                    }
                    return pet;
                },
                id);

        if (Objects.isNull(result)) {
            throw new EntityNotExistException(Owner.class, id);
        }

        return result;
    }

    @Override
    @Transactional
    public Pet createPet(Pet pet) {
        var aggregate = map(pet);
        var persisted = petJdbcRepository.save(aggregate);
        return map(persisted);
    }

    @Override
    @Transactional
    public Pet updatePet(Pet pet) {
        var persisted = petJdbcRepository.findById(pet.getId()).orElseThrow(() -> new EntityNotExistException(Pet.class, pet.getId()));

        var requiredUpdate = false;

        if (!Objects.equals(persisted.getName(), pet.getName()) && Objects.nonNull(pet.getName())) {
            persisted.setName(pet.getName());
            requiredUpdate = true;
        }

        if (!Objects.equals(persisted.getBirthDate(), pet.getBirthDate()) && Objects.nonNull(pet.getBirthDate())) {
            persisted.setBirthDate(pet.getBirthDate());
            requiredUpdate = true;
        }

        if (requiredUpdate) {
            persisted = petJdbcRepository.save(persisted);
        }

        return map(persisted);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Pet> getPetsByIdentifiers(Collection<Long> ids) {
        return petJdbcRepository.getByIdentifierIn(ids).stream().map(this::map).toList();
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

    private PetAggregate map(Pet domain) {
        return PetAggregate.builder()
                .id(domain.getId())
                .name(domain.getName())
                .birthDate(domain.getBirthDate())
                .ownerId(Objects.isNull(domain.getOwner()) ? null : domain.getOwner().getId())
                .build();
    }

    private Pet map(PetAggregate aggregate) {
        return Pet.builder()
                .id(aggregate.getId())
                .name(aggregate.getName())
                .birthDate(aggregate.getBirthDate())
                .owner(Owner.builder().id(aggregate.getOwnerId()).build())
                .build();
    }
}
