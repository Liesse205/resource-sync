package com.resourcesync.intelligence;

import com.resourcesync.model.Concept;
import com.resourcesync.model.Resource;
import com.resourcesync.model.ResourceConcept;
import com.resourcesync.repository.ConceptRepository;
import com.resourcesync.repository.ResourceConceptRepository;
import com.resourcesync.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceIntelligenceService {

    private final ResourceRepository resourceRepository;
    private final ConceptRepository conceptRepository;
    private final ResourceConceptRepository resourceConceptRepository;

    @Transactional
    public void processResource(Resource newResource) {
        List<Resource> existingResources = resourceRepository.findAll();
        for (Resource existing : existingResources) {
            if (isSimilar(newResource, existing)) {
                linkToExistingConcepts(newResource, existing);
            }
        }
        extractConceptsFromTitle(newResource);
    }

    private boolean isSimilar(Resource r1, Resource r2) {
        if (r1.getId() != null && r1.getId().equals(r2.getId())) return false;
        String t1 = r1.getTitle().toLowerCase().trim();
        String t2 = r2.getTitle().toLowerCase().trim();
        return t1.equals(t2) || t1.contains(t2) || t2.contains(t1);
    }

    private void linkToExistingConcepts(Resource newResource, Resource existingResource) {
        List<ResourceConcept> existingAssociations = resourceConceptRepository.findByResourceId(existingResource.getId());
        for (ResourceConcept assoc : existingAssociations) {
            if (!resourceConceptRepository.existsByResourceIdAndConceptId(newResource.getId(), assoc.getConcept().getId())) {
                ResourceConcept newAssoc = ResourceConcept.builder()
                        .resource(newResource)
                        .concept(assoc.getConcept())
                        .build();
                resourceConceptRepository.save(newAssoc);
            }
        }
    }

    private void extractConceptsFromTitle(Resource resource) {
        String[] keywords = resource.getTitle().split("\\s+");
        for (String keyword : keywords) {
            if (keyword.length() > 3) {
                String conceptName = keyword.toLowerCase().replaceAll("[^a-zA-Z]", "");
                if (conceptName.isEmpty()) continue;
                Optional<Concept> conceptOpt = conceptRepository.findByName(conceptName);
                Concept concept = conceptOpt.orElseGet(() -> conceptRepository.save(Concept.builder()
                        .name(conceptName)
                        .description("Auto-generated from keyword: " + conceptName)
                        .build()));
                if (!resourceConceptRepository.existsByResourceIdAndConceptId(resource.getId(), concept.getId())) {
                    resourceConceptRepository.save(ResourceConcept.builder()
                            .resource(resource)
                            .concept(concept)
                            .build());
                }
            }
        }
    }
}
