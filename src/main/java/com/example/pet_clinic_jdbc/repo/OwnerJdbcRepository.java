package com.example.pet_clinic_jdbc.repo;


import com.example.pet_clinic_jdbc.domain.aggregate.OwnerAggregate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerJdbcRepository extends CrudRepository<OwnerAggregate, Long> {

    @Query("""
            SELECT * FROM owner where id = :id
            """)
    Optional<OwnerAggregate> getOwnerById(Long id);
}
