package com.example.pet_clinic_jdbc.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "owner", schema = "public")
public class OwnerAggregate {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String mobilePhone;
}
