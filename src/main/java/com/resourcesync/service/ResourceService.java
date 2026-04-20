package com.resourcesync.service;

import com.resourcesync.dto.ResourceConceptDTO;
import com.resourcesync.dto.ResourceDTO;
import com.resourcesync.dto.ResourceRequestDTO;

import java.util.List;

public interface ResourceService {
    ResourceDTO createResource(ResourceRequestDTO resourceRequestDTO);
    ResourceDTO getResourceById(Long id);
    List<ResourceDTO> getResourcesByUserId(Long userId);
    void deleteResource(Long id);
    ResourceConceptDTO associateConcept(Long resourceId, Long conceptId);
    List<ResourceConceptDTO> getResourceConcepts(Long resourceId);
}
