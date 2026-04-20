package com.resourcesync.repository;

import com.resourcesync.model.ResourceConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceConceptRepository extends JpaRepository<ResourceConcept, Long> {
    List<ResourceConcept> findByResourceId(Long resourceId);
    boolean existsByResourceIdAndConceptId(Long resourceId, Long conceptId);
}
