package com.example.pet_clinic_jdbc.service.impl;

import com.example.pet_clinic_jdbc.domain.Owner;
import com.example.pet_clinic_jdbc.domain.Pet;
import com.example.pet_clinic_jdbc.domain.aggregate.OwnerAggregate;
import com.example.pet_clinic_jdbc.domain.aggregate.PetAggregate;
import com.example.pet_clinic_jdbc.exception.EntityNotExistException;
import com.example.pet_clinic_jdbc.repo.OwnerJdbcRepository;
import com.example.pet_clinic_jdbc.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class OwnerServiceImpl implements OwnerService {

    private final OwnerJdbcRepository repository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(readOnly = true)
    public Owner getOwnerById(Long id) {
        return repository.getOwnerById(id)
                .map(this::map)
                .orElseThrow(() -> new EntityNotExistException(Owner.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getOwnerByIdWithRelatedPets(Long id) {
        final var result = jdbcTemplate.query(
                """
                        SELECT
                        o.id as o_id, o.name as o_name, o.surname as o_surname, o.address as o_address, o.mobile_phone as o_mobile_phone,
                        p.id as p_id, p.name as p_name, p.birth_date as p_birth_date
                        FROM owner o LEFT JOIN pet p ON o.id = p.owner_id
                        WHERE
                        o.id = ?
                        """,
                rs -> {

                    if (rs.wasNull()) {
                        return null;
                    }

                    final var owner = new Owner();
                    owner.setPets(new ArrayList<>());

                    final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    while (rs.next()) {

                        if (Objects.isNull(owner.getId())) {
                            owner.setId(rs.getLong("o_id"));
                            owner.setName(rs.getString("o_name"));
                            owner.setSurname(rs.getString("o_surname"));
                            owner.setAddress(rs.getString("o_address"));
                            owner.setMobilePhone(rs.getString("o_mobile_phone"));
                        }
                        final var pet = new Pet();
                        final var petId = rs.getString("p_id");
                        if (Objects.isNull(petId)) {
                            continue;
                        }
                        pet.setId(Long.parseLong(petId));
                        pet.setName(rs.getString("p_name"));
                        final var birthDate = rs.getString("p_birth_date");

                        pet.setBirthDate(LocalDate.parse(birthDate, formatter));
                        owner.getPets().add(pet);
                    }
                    return owner;
                },
                id);

        if (Objects.isNull(result)) {
            throw new EntityNotExistException(Owner.class, id);
        }

        return result;

//        var owner = this.getOwnerById(id);
//        var pets = petJdbcRepository.getByOwnerIdentifier(id).stream().map(this::map).toList();
//        owner.setPets(pets);
//        return owner;
    }

    @Override
    @Transactional
    public Owner createOwner(Owner owner) {
        var aggregate = map(owner);
        aggregate = repository.save(aggregate);
        return map(aggregate);
    }

    @Override
    @Transactional
    public Owner updateOwner(Owner owner) {
        var persistedOwner = repository
                .getOwnerById(owner.getId())
                .orElseThrow(() -> new EntityNotExistException(Owner.class, owner.getId()));

        var requiredUpdate = false;

        if (!Objects.equals(owner.getSurname(), persistedOwner.getSurname()) && Objects.nonNull(owner.getSurname())) {
            persistedOwner.setSurname(owner.getSurname());
            requiredUpdate = true;
        }

        if (!Objects.equals(owner.getName(), persistedOwner.getName()) && Objects.nonNull(owner.getName())) {
            persistedOwner.setSurname(owner.getName());
            requiredUpdate = true;
        }

        if (!Objects.equals(owner.getAddress(), persistedOwner.getAddress()) && Objects.nonNull(owner.getAddress())) {
            persistedOwner.setSurname(owner.getAddress());
            requiredUpdate = true;
        }

        if (!Objects.equals(owner.getMobilePhone(), persistedOwner.getMobilePhone()) && Objects.nonNull(owner.getMobilePhone())) {
            persistedOwner.setSurname(owner.getMobilePhone());
            requiredUpdate = true;
        }

        if (requiredUpdate) {
            persistedOwner = repository.save(persistedOwner);
        }

        return map(persistedOwner);
    }

    private OwnerAggregate map(Owner owner) {
        return OwnerAggregate.builder()
                .id(owner.getId())
                .name(owner.getName())
                .surname(owner.getSurname())
                .address(owner.getAddress())
                .mobilePhone(owner.getMobilePhone())
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

    private Owner map(OwnerAggregate aggregate) {
        return Owner.builder()
                .id(aggregate.getId())
                .name(aggregate.getName())
                .surname(aggregate.getSurname())
                .address(aggregate.getAddress())
                .mobilePhone(aggregate.getMobilePhone())
                .build();
    }
}
