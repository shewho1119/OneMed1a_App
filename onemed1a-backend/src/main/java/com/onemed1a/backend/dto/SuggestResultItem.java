package com.onemed1a.backend.dto;

import com.onemed1a.backend.model.MediaData;
import lombok.*;

import java.util.UUID;

/**
 * Represents a lightweight suggestion item for autocomplete or search hints.
 *
 * Contains only basic identifiers needed for dropdown suggestions.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SuggestResultItem {
    private UUID id;
    private String title;
    private MediaData.MediaType type;
}