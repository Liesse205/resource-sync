package com.resourcesync.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceConceptDTO {
    private Long id;
    private Long resourceId;
    private Long conceptId;
}
