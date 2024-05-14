package com.example.pet_clinic_jdbc.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "pet", schema = "public")
public class PetAggregate {
    @Id
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Long ownerId;
}
