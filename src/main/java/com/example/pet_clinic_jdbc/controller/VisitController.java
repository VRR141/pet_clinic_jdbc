package com.example.pet_clinic_jdbc.controller;

import com.example.pet_clinic_jdbc.dto.VisitDto;
import com.example.pet_clinic_jdbc.dto.request.CreateVisitDto;
import com.example.pet_clinic_jdbc.facade.VisitFacade;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
@Slf4j
public class VisitController {

    private final VisitFacade visitFacade;

    @GetMapping("/{id}")
    @SneakyThrows
    public ResponseEntity<Collection<VisitDto>> getByPetIdentifier(@PathVariable("id") Long id) {
        log.debug("Start get visit {}", id);
        final var output = visitFacade.getByPetIdentifier(id);
        log.debug("Get visit {}", output);
        return ResponseEntity.ok(output);
    }

    @PostMapping
    public ResponseEntity<Long> createVisit(@RequestBody CreateVisitDto dto) {
        log.debug("Start create visit {}", dto);
        final var output = visitFacade.createVisit(dto);
        log.debug("Create visit {}", output);
        return ResponseEntity.status(201).body(output);
    }
}
