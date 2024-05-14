package com.example.pet_clinic_jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Pet {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Owner owner;
    private List<Visit> visits;
}
