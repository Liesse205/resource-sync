package com.resourcesync.service.impl;

import com.resourcesync.dto.ResourceConceptDTO;
import com.resourcesync.dto.ResourceDTO;
import com.resourcesync.dto.ResourceRequestDTO;
import com.resourcesync.exception.ResourceNotFoundException;
import com.resourcesync.exception.UserNotFoundException;
import com.resourcesync.intelligence.ResourceIntelligenceService;
import com.resourcesync.model.Concept;
import com.resourcesync.model.Resource;
import com.resourcesync.model.ResourceConcept;
import com.resourcesync.model.User;
import com.resourcesync.repository.ConceptRepository;
import com.resourcesync.repository.ResourceConceptRepository;
import com.resourcesync.repository.ResourceRepository;
import com.resourcesync.repository.UserRepository;
import com.resourcesync.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final ConceptRepository conceptRepository;
    private final ResourceConceptRepository resourceConceptRepository;
    private final ResourceIntelligenceService intelligenceService;

    @Override
    @Transactional
    public ResourceDTO createResource(ResourceRequestDTO resourceRequestDTO) {
        User user = userRepository.findById(resourceRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + resourceRequestDTO.getUserId()));
        Resource resource = Resource.builder()
                .title(resourceRequestDTO.getTitle())
                .description(resourceRequestDTO.getDescription())
                .type(resourceRequestDTO.getType())
                .content(resourceRequestDTO.getContent())
                .user(user)
                .build();
        Resource saved = resourceRepository.save(resource);
        intelligenceService.processResource(saved);
        return mapToDTO(saved);
    }

    @Override
    public ResourceDTO getResourceById(Long id) {
        return resourceRepository.findById(id).map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    @Override
    public List<ResourceDTO> getResourcesByUserId(Long userId) {
        return resourceRepository.findByUserId(userId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteResource(Long id) {
        if (!resourceRepository.existsById(id)) throw new ResourceNotFoundException("Resource not found");
        resourceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResourceConceptDTO associateConcept(Long resourceId, Long conceptId) {
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        Concept concept = conceptRepository.findById(conceptId).orElseThrow(() -> new RuntimeException("Concept not found"));
        if (resourceConceptRepository.existsByResourceIdAndConceptId(resourceId, conceptId)) return null;
        ResourceConcept saved = resourceConceptRepository.save(ResourceConcept.builder().resource(resource).concept(concept).build());
        return ResourceConceptDTO.builder().id(saved.getId()).resourceId(resourceId).conceptId(conceptId).build();
    }

    @Override
    public List<ResourceConceptDTO> getResourceConcepts(Long resourceId) {
        return resourceConceptRepository.findByResourceId(resourceId).stream()
                .map(rc -> ResourceConceptDTO.builder().id(rc.getId()).resourceId(resourceId).conceptId(rc.getConcept().getId()).build())
                .collect(Collectors.toList());
    }

    private ResourceDTO mapToDTO(Resource r) {
        return ResourceDTO.builder().id(r.getId()).title(r.getTitle()).description(r.getDescription())
                .type(r.getType()).content(r.getContent()).createdAt(r.getCreatedAt()).userId(r.getUser().getId()).build();
    }
}
