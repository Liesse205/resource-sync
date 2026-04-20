package com.resourcesync.controller;

import com.resourcesync.dto.ResourceConceptDTO;
import com.resourcesync.dto.ResourceDTO;
import com.resourcesync.dto.ResourceRequestDTO;
import com.resourcesync.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<ResourceDTO> createResource(@Valid @RequestBody ResourceRequestDTO resourceRequestDTO) { return new ResponseEntity<>(resourceService.createResource(resourceRequestDTO), HttpStatus.CREATED); }
    @GetMapping("/{id}")
    public ResponseEntity<ResourceDTO> getResourceById(@PathVariable Long id) { return ResponseEntity.ok(resourceService.getResourceById(id)); }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) { resourceService.deleteResource(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/{resourceId}/concepts/{conceptId}")
    public ResponseEntity<ResourceConceptDTO> associateConcept(@PathVariable Long resourceId, @PathVariable Long conceptId) {
        ResourceConceptDTO result = resourceService.associateConcept(resourceId, conceptId);
        return result == null ? ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build() : new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @GetMapping("/{resourceId}/concepts")
    public ResponseEntity<List<ResourceConceptDTO>> getResourceConcepts(@PathVariable Long resourceId) { return ResponseEntity.ok(resourceService.getResourceConcepts(resourceId)); }
}
