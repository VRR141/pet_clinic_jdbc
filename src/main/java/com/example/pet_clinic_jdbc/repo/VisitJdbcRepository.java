package com.example.pet_clinic_jdbc.repo;


import com.example.pet_clinic_jdbc.domain.aggregate.VisitAggregate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitJdbcRepository extends CrudRepository<VisitAggregate, Long> {

    @Query("""
            SELECT * FROM visit v WHERE v.pet_id = :id
            """)
    List<VisitAggregate> getVisitByPetIdentifier(Long id);
}
