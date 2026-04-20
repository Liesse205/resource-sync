package com.resourcesync.controller;

import com.resourcesync.dto.ConceptDTO;
import com.resourcesync.service.ConceptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/concepts")
@RequiredArgsConstructor
public class ConceptController {
    private final ConceptService conceptService;

    @PostMapping
    public ResponseEntity<ConceptDTO> createConcept(@Valid @RequestBody ConceptDTO conceptDTO) { return new ResponseEntity<>(conceptService.createConcept(conceptDTO), HttpStatus.CREATED); }
    @GetMapping
    public ResponseEntity<List<ConceptDTO>> getAllConcepts() { return ResponseEntity.ok(conceptService.getAllConcepts()); }
    @GetMapping("/{id}")
    public ResponseEntity<ConceptDTO> getConceptById(@PathVariable Long id) { return ResponseEntity.ok(conceptService.getConceptById(id)); }
}
