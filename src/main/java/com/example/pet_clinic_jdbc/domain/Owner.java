package com.example.pet_clinic_jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Owner {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String mobilePhone;
    private List<Pet> pets;
}
