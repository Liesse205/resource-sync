package com.resourcesync.service;

import com.resourcesync.dto.ConceptDTO;

import java.util.List;

public interface ConceptService {
    ConceptDTO createConcept(ConceptDTO conceptDTO);
    ConceptDTO getConceptById(Long id);
    List<ConceptDTO> getAllConcepts();
}
