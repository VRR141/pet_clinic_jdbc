package com.example.pet_clinic_jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Visit {
    private Long id;
    private OffsetDateTime visitTimestamp;
    private String description;
    private Pet pet;
}
