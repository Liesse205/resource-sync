package com.resourcesync.service.impl;

import com.resourcesync.dto.ConceptDTO;
import com.resourcesync.model.Concept;
import com.resourcesync.repository.ConceptRepository;
import com.resourcesync.service.ConceptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {

    private final ConceptRepository conceptRepository;

    @Override
    public ConceptDTO createConcept(ConceptDTO conceptDTO) {
        Concept saved = conceptRepository.save(Concept.builder().name(conceptDTO.getName()).description(conceptDTO.getDescription()).build());
        return mapToDTO(saved);
    }

    @Override
    public ConceptDTO getConceptById(Long id) {
        return conceptRepository.findById(id).map(this::mapToDTO).orElseThrow(() -> new RuntimeException("Concept not found"));
    }

    @Override
    public List<ConceptDTO> getAllConcepts() {
        return conceptRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ConceptDTO mapToDTO(Concept c) {
        return ConceptDTO.builder().id(c.getId()).name(c.getName()).description(c.getDescription()).build();
    }
}
