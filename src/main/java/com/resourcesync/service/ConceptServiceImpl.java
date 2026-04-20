package com.resourcesync.service;
import com.resourcesync.dto.ConceptDTO;
import com.resourcesync.model.Concept;
import com.resourcesync.repository.ConceptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ConceptServiceImpl implements ConceptService {
    private final ConceptRepository repo;
    @Override
    public ConceptDTO createConcept(ConceptDTO dto) {
        Concept c = Concept.builder().name(dto.getName()).description(dto.getDescription()).build();
        return mapToDTO(repo.save(c));
    }
    @Override
    public List<ConceptDTO> getAllConcepts() {
        return repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private ConceptDTO mapToDTO(Concept c) {
        return ConceptDTO.builder().id(c.getId()).name(c.getName()).description(c.getDescription()).build();
    }
}
