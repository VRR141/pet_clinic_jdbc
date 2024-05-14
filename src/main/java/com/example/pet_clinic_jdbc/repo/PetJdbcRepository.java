package com.example.pet_clinic_jdbc.repo;

import com.example.pet_clinic_jdbc.domain.aggregate.PetAggregate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PetJdbcRepository extends CrudRepository<PetAggregate, Long> {

    @Query("""
            SELECT * FROM pet WHERE id = :id
            """)
    Optional<PetAggregate> findById(Long id);

    @Query("""
            SELECT * FROM pet WHERE owner_id = :id
            """)
    Collection<PetAggregate> getByOwnerIdentifier(Long id);

    @Query("""
            SELECT * FROM pet where id in (:ids)
            """)
    Collection<PetAggregate> getByIdentifierIn(Collection<Long> ids);
}
