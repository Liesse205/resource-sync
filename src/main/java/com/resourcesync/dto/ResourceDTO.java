package com.resourcesync.dto;

import com.resourcesync.model.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
    private Long id;
    private String title;
    private String description;
    private ResourceType type;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
}
