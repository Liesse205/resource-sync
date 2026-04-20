package com.resourcesync.service;
import com.resourcesync.dto.*;
import com.resourcesync.exception.*;
import com.resourcesync.intelligence.ResourceIntelligenceService;
import com.resourcesync.model.*;
import com.resourcesync.repository.*;
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
    private final ResourceIntelligenceService intelligenceService;
    @Override
    @Transactional
    public ResourceDTO createResource(ResourceRequestDTO request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Resource res = Resource.builder().title(request.getTitle()).description(request.getDescription()).type(request.getType()).content(request.getContent()).user(user).build();
        Resource saved = resourceRepository.save(res);
        intelligenceService.analyzeAndLinkConcepts(saved);
        return mapToDTO(saved);
    }
    @Override
    public ResourceDTO getResourceById(Long id) {
        return resourceRepository.findById(id).map(this::mapToDTO).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
    @Override
    public List<ResourceDTO> getResourcesByUserId(Long userId) {
        return resourceRepository.findByUserId(userId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private ResourceDTO mapToDTO(Resource r) {
        return ResourceDTO.builder().id(r.getId()).title(r.getTitle()).description(r.getDescription()).type(r.getType()).content(r.getContent()).createdAt(r.getCreatedAt()).userId(r.getUser().getId()).build();
    }
}
