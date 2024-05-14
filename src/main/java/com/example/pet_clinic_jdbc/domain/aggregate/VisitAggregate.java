package com.example.pet_clinic_jdbc.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "visit", schema = "public")
public class VisitAggregate {
    @Id
    private Long id;
    private OffsetDateTime visitTimestamp;
    private String description;
    private Long petId;
}
