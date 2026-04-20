package com.resourcesync.repository;

import com.resourcesync.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {
    Optional<Concept> findByName(String name);
}
