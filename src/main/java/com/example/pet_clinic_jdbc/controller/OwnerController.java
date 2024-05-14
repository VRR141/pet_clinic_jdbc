package com.example.pet_clinic_jdbc.controller;


import com.example.pet_clinic_jdbc.dto.OwnerDto;
import com.example.pet_clinic_jdbc.dto.request.CreateOwnerDto;
import com.example.pet_clinic_jdbc.dto.request.UpdateOwnerDto;
import com.example.pet_clinic_jdbc.facade.OwnerFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
@Slf4j
public class OwnerController {

    private final OwnerFacade ownerFacade;

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Long id, @RequestParam(value = "fetchPet", required = false) boolean fetchPets) {
        log.debug("Start get owner {} {}", id, fetchPets);
        final var output = ownerFacade.getOwner(id, fetchPets);
        log.debug("Get owner {}", output);
        return ResponseEntity.ok(output);
    }

    @PostMapping
    public ResponseEntity<Long> createOwner(@RequestBody CreateOwnerDto dto) {
        log.debug("Start create owner {}", dto);
        final var output = ownerFacade.createOwner(dto);
        log.debug("Create owner {}", output);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PatchMapping
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody UpdateOwnerDto dto) {
        log.debug("Start update owner {}", dto);
        final var output = ownerFacade.updateOwner(dto);
        log.debug("Update owner {}", output);
        return ResponseEntity.ok(output);
    }
}
